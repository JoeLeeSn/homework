# 一、作业原题 ： 
利用report(transactions).executeInsert("spend_report")
将transactions表经过report函数处理后写入到spend_report表

# 二、解题思路

## 1、修改SpendReport.java，实现方法public static Table report(Table transactions)

    public static Table report(Table transactions){
        Table table = transactions.window(Slide.over(lit(v:5).minutes())
                        .every(lit(v:1).minutes())
                        .on($(name:"transaction_time"))
                        .as(alias:"log_ts"))
                    .groupBy($(name:"account_id"),$(name:"log_ts"))
                    .select($(name:"accout_id"),
                            $(name:"log_ts").start().as(name:"log_ts"),
                            $(name:"amount"),avg().as(name:"amount"));
        return table;
    }

## 2、演示和验证

### 创建检查点和保存目录
    mkdir -p /tmp/flink-checkpoints-directory
    mkdir -p /tmp/flink-savepoints-directory

### docker
    docker-compose build
    docker-compose up -d
    docker-compose down -v

### Flink UI
    http://localhost:8082/#/overview

### 查看结果
    docker-compose exec mysql mysql -Dsql-demo -usql-demo -pdemo-sql
    use sql-demo;
    select count(*) from spend_repost;
