package SparkTest

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

object SparkScala2 {
  /**
   *DistCp（分布式复制）是一个用于大型集群间/集群内复制的工具
   *• 使用 MapReduce 来实现分布式、错误处、恢复、报告等特性
   *• 将文件和目录列表作为 Map 任务的输入，每个 Map 任务将复制源列表中指定的文件的一个分区
   **/

  def checkDir(sparkSession: SparkSession, sourcePath: Path, targetPath: Path,
               fileList: ArrayBuffer[(Path, Path)], options: SparkDistCPOptions): Unit ={

    val fs = FileSystem.get(sparkSession.sparkContext.hadoopConfiguration)
    fs.listSatus(sourcePath)//获取文件列表
      . foreach(currPath => {
        if (currPath.isDirectory) {
          val subPath = currPath.getPath.toString.split(sourcePath.toString)(1)
          val nextTargetPath = new Path(targetPath + subPath)
          try {
            fs.mkdirs(nextTargetPath)//在目标路径下建立对应目录
          } catch {
            case ex: Exception => if (!options.ignoreFailures) throw ex else logWarning(ex.getMessage)
          }
          checkDir(sparkSession, currPath.getPath, nextTargetPath, fileList, options)//递归调用生产文件树
        } else {
          fileList.append((currPath.getPath, targetPath))
        }
      })
  }

  def copy(sparkSession: SparkSession, fileList: ArrayBuffer[(Path, Path)], options: SparkDistCPOptions): Unit ={
    val sc = sparkSession.sparkContext
    val maxConcurrenceTask = Some(options.maxConcurrenceTask).getOrElse(5)
    val rdd = sc.makeRDD(fileList, maxConcurrenceTask)

    rdd.mapPartitions(ite => {
      val hadoopConf = new Configuration()
      ite.foreach(tup => {
        try {
          FileUtil.copy(tup._1.getFileSystem(hadoopConf), tup._1, tup._2.getFileSystem(hadoopConf),
            tup._2, deleteSource = false, hadoopConf)
        } catch {
          case ex: Exception => if (!options.ignoreFilures) throw ex else logWarning(ex.getMessage)
        }
      })
      ite
    }).collect()
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("DistCp").setMaster("local")
    val sc = new SparkContext(conf)

    val soPath = new Path(args.head) //源
    val tarPath = new Path(args(1)) //目标

    val fileList = new ArrayBuffer[(Path, Path)]()

    val options = SparkDistCpOptions(args(2).toInt, args(3).toBoolean) //discp参数: -m:最大并发数；-i 忽略失败

    checkDir(sc, soPath, tarPath, fileList, options) //拿到文件树

    copy(sc, fileList, options) //复制

    sc.stop()
  }
}
