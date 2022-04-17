package SparkTest

import org.apache.commons.io.IOUtils
import org.apache.hadoop.fs.Path
import org.apache.hadoop.fs.permission.{FsAction, FsPermission}
import org.apache.spark.{util, SparkConf, SparkContext}
import org.apache.spark.api.java.JavaSparkContext

import java.io.IOException
import java.lang.Boolean
import scala.collection.immutable.HashSet


object test2 {
  private var sparkConf = null
  private var sparkContext = null
  private var javaSparkContext = null
  private var configuration = null

  @throws[IOException]
  def main(args: Array[String]): Unit = {
    val sourceRootPathStr = args(0) //"file:///tmp/source/";
    val targetRootPathStr = args(1) //"file:///tmp/target/";
    val maxConcurrency = args(2).toInt
    val ignoreFailure = Boolean.parseBoolean(args(3))
    val sourceFileListRDD = getSourceFileLists(sourceRootPathStr, targetRootPathStr, maxConcurrency)
    sourceFileListRDD.foreachPartition((sourceFileIterator: util.Iterator[String]) => {
      def foo(sourceFileIterator: util.Iterator[String]) = {
        val sourceFileSystem = new Path(sourceRootPathStr).getFileSystem(configuration)
        val targetFileSystem = new Path(targetRootPathStr).getFileSystem(configuration)
        while ( {
          sourceFileIterator.hasNext
        }) {
          val sourceFilePath = sourceFileIterator.next
          val sourceFileRelativePath = new Path(new Path(sourceRootPathStr).toUri.relativize(new Path(sourceFilePath).toUri))
          val targetPath = new Path(targetRootPathStr, sourceFileRelativePath)
          try {
            val sourceInputStream = sourceFileSystem.open(new Path(sourceFilePath))
            val fsDataOutputStream = targetFileSystem.create(targetPath, true)
            try IOUtils.copy(sourceInputStream, fsDataOutputStream)
            catch {
              case t: Throwable =>
                if (!ignoreFailure) throw t
            } finally {
              if (sourceInputStream != null) sourceInputStream.close()
              if (fsDataOutputStream != null) fsDataOutputStream.close()
            }
          }
        }
      }

      foo(sourceFileIterator)
    })
  }

  @throws[IOException]
  private def getSourceFileLists(sourceRootPathStr: String, targetRootPathStr: String, maxConcurrency: Int) = {
    val sourceRootPath = new Path(sourceRootPathStr)
    val targetRootPath = new Path(targetRootPathStr)
    val sourceFileSystem = sourceRootPath.getFileSystem(configuration)
    val targetFileSystem = targetRootPath.getFileSystem(configuration)
    val iterator = sourceFileSystem.listFiles(sourceRootPath, true)
    val distinctDirPaths = new util.HashSet[Path]
    val fileList = new util.ArrayList[String]
    while ( {
      iterator.hasNext
    }) {
      val locatedFileStatus = iterator.next
      val filePath = locatedFileStatus.getPath
      distinctDirPaths.add(filePath.getParent)
      fileList.add(filePath.toString)
    }
    distinctDirPaths.remove(sourceRootPath)
    for (distinctDirPath <- distinctDirPaths) {
      val sourceChildrenDirRelativePathStr = sourceRootPath.toUri.relativize(distinctDirPath.toUri).toString
      targetFileSystem.mkdirs(new Path(targetRootPath, sourceChildrenDirRelativePathStr), new FsPermission(FsAction.ALL, FsAction.READ, FsAction.READ))
    }
    javaSparkContext.parallelize(fileList, maxConcurrency)
  }

  try sparkConf = new SparkConf
  sparkConf.set("spark.master", "local[*]")
  sparkConf.set("spark.app.name", "localrun")
  sparkContext = SparkContext.getOrCreate(sparkConf)
  javaSparkContext = new JavaSparkContext(sparkContext)
  configuration = sparkContext.hadoopConfiguration

}