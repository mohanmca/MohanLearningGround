## Apache Spark Predecessor

1. GFS, Hadoop, MR
2. They are slow and complicated
3. Disk IO, too complex API
4. Unmaintainable

## Spark Original Creators
1. Matei Zaharia, Ali Ghodsi, Reynold Xin, Patrick Wendell, Ion Stoica, and Andy Konwinski
2.  Unified Engine for Big Data Processing.

## What is Apache Spark DAG

1. A Directed Acyclic Graph (DAG) is a fundamental concept in Apache Spark that represents a sequence of data processing steps in a logical workflow. In Spark, DAGs are used to optimize the execution of data processing tasks by breaking them down into a series of stages that can be executed in parallel on a cluster of machines.
2. When a Spark application is submitted, the Spark engine first analyzes the code to build a DAG of the tasks that need to be executed. This DAG represents the logical flow of the data processing operations, and it contains all the dependencies between the different stages of the computation.
3. Each stage of the DAG represents a set of tasks that can be executed in parallel, and the Spark engine uses this information to optimize the execution of the computation. Specifically, Spark can schedule tasks across multiple nodes in the cluster in a way that minimizes data shuffling and maximizes the utilization of available resources.
4. In addition to optimizing task execution, the DAG is also used to track the progress of the computation and to handle failures and retries. If a task fails, for example, Spark can use the information in the DAG to re-execute the task and to make sure that all downstream stages are updated with the correct data.
5. Overall, the DAG is a powerful tool that enables Spark to perform complex data processing tasks in a highly scalable and efficient way. By breaking the computation down into a series of stages that can be executed in parallel, Spark can take advantage of the distributed computing resources of a cluster to process large volumes of data quickly and reliably.

## What are all the components of Spark
1. Spark Application
2. Spark Driver
3. SparkSession (facade)
   1. SparkContext
   2. SQLContext
   3. HiveContext
   4. SparkConf
   5. ..
4. Cluster Manager
   1. Kubernetes
   2. Apache Hadoop Yarn2
   3. Mesos
   4. Standalone
5. Spark Executor
6. Access it via a global variable called spark or sc.

## What is the role of Cluster Manager
1. The cluster manager is responsible for managing and allocating resources for the cluster of nodes on which your Spark application runs.

## How data from other sources can be read?

1. Spark’s DataFrameReaders and DataFrameWriters can also be extended to read data from other sources, 
   1. such as Apache Kafka, 
   2. Kinesis, Azure Storage, and Amazon S3, into its logical data abstraction, on which it can operate.

## How Spark Exector and Spark Driver communicates upon failures
1. In Apache Spark, the Spark driver and executors communicate with each other using a protocol called the RPC (Remote Procedure Call) protocol. This protocol is used to send messages between the driver and the executors, and it is responsible for coordinating the execution of tasks and managing the state of the Spark application.
2. When an executor fails or becomes unresponsive, the Spark driver will detect the failure and take appropriate action. The exact behavior depends on the configuration of the Spark application and the specific error that occurred, but in general, the driver will attempt to recover from the failure by restarting the failed task on another executor.
3. If the failure is due to a node failure or network issue, the driver will mark the failed tasks as "lost" and reschedule them to be executed on a different node. If the failure is due to an error in the application code or an out-of-memory error, the driver will abort the job and report an error to the user.
4. In addition to handling failures, the RPC protocol is also used to exchange data between the driver and the executors. When a task is executed on an executor, it may need to read data from disk or from other nodes in the cluster. To facilitate this, the executor can request data from other executors or from the driver using the RPC protocol.
5. Overall, the RPC protocol is a critical component of the Apache Spark architecture, as it enables the driver and executors to communicate with each other and coordinate the execution of complex data processing tasks. By handling failures and exchanging data efficiently, the RPC protocol helps to ensure the reliability and scalability of Spark applications

## Which RPC protocol is used between Executor and Driver

1. Use of the Akka-based RPC protocol is a key factor in the performance and reliability of Apache Spark, enabling efficient communication between the driver and executors and allowing Spark to scale to handle very large data sets and complex processing tasks.
2. As of Spark 1.6 Spark no longer uses Akka - Akka was replaced by Netty.

##  Spark Partition and RDD
1. Spark treats each partition as a high-level logical data abstraction—as a DataFrame in memory. 
2. Though this is not always possible, each Spark executor is preferably allocated a task that requires it to read the partition closest to it in the network, observing data locality.
3. A distributed scheme of breaking up data into chunks or partitions allows Spark executors to process only data that is close to them, minimizing network bandwidth.

##  How to read from a JSON file stored on Amazon S3 and execute SQL on top of it

```scala
spark.read.json("s3://apache_spark/data/committers.json").createOrReplaceTempView("committers")
// Issue a SQL query and return the result as a Spark DataFrame
val results = spark.sql("""SELECT name, org, module, release, num_commits
    FROM committers WHERE module = 'mllib' AND num_commits > 10
    ORDER BY num_commits DESC""")
```

## Spark MLlib

1. spark.mllib - for RDD
2. spark.ml - for DataFrame

## In Python Read a stream from a local host

```python
from pyspark.sql.functions import explode, split
lines = (spark 
  .readStream
  .format("socket")
  .option("host", "localhost")
  .option("port", 9999)
  .load())

# Perform transformation
# Split the lines into words
words = lines.select(explode(split(lines.value, " ")).alias("word"))

# Generate running word count
word_counts = words.groupBy("word").count()

# Write out to the stream to Kafka
query = (word_counts
  .writeStream 
  .format("kafka") 
  .option("topic", "output"))
```

## What is explode function does in Apache Spark?
```python
df = spark.createDataFrame([(1, ["a","b"]),(2,["c","d"])], ("id", "name"))
from pyspark.sql.functions import explode, col
df.select(df["id"]).alias("word").show()
df.select(df["id"], explode(df["name"])).alias("word").show()
```

```python
df = spark.createDataFrame([(1, ["a","b"]),(2,["c","d"])], ("id", "name"))
from pyspark.sql.functions import explode, col
df.select(col("id"),  explode(col("name")).alias("name")).show()
```


| id|name|
|---|----|
|  1|   a|
|  1|   b|
|  2|   c|
|  2|   d|

## Write code will break up the physical data stored across clusters into eight partitions

```python
log_df = spark.read.text("path_to_large_text_file").repartition(8)
print(log_df.rdd.getNumPartitions())
```

## code to create a DataFrame of 10,000 integers distributed over eight partitions in memory:
```python
df = spark.range(0, 10000, 1, 8)
print(df.rdd.getNumPartitions())
```

## What is data scientist do?

1. Data science is about using data to tell stories.
2. But before they can narrate the stories, data scientists have to 
   1. cleanse the data
   2. explore it to discover patterns 
   3. and build models to predict or suggest outcomes

## Project Hydrogen vs Project tungsten

1. Project Hydrogen and Project Tungsten are two major projects in Apache Spark that focus on improving the performance and efficiency of Spark applications.
2. Project Hydrogen, also known as Spark 2.0, was released in 2016 and focused on simplifying the API for Spark and improving its performance. It introduced several new features, including the Structured Streaming API, improved SQL support, and better integration with popular machine learning libraries like TensorFlow and Keras.
3. Project Tungsten, on the other hand, is a more low-level project that focuses on optimizing the runtime performance of Spark applications. It was first introduced in Spark 1.5 and has continued to evolve in subsequent releases. The goal of Project Tungsten is to improve the efficiency of Spark's execution engine by using modern CPU architectures, such as SIMD (Single Instruction Multiple Data) instructions, and optimizing memory management.
4. Some of the key features of Project Tungsten include:
    1. Code generation: Instead of using the Java Virtual Machine (JVM) to interpret code at runtime, Tungsten generates optimized code at compile time, resulting in faster execution.
    2. Memory management: Tungsten uses a custom memory manager that reduces the overhead of garbage collection and allows Spark to better utilize available memory.
    3. Binary processing: Tungsten uses a binary format for data processing that reduces the overhead of serialization and deserialization.
5. Overall, while both Project Hydrogen and Project Tungsten aim to improve the performance of Apache Spark, they focus on different aspects of the runtime environment. Project Hydrogen is more focused on high-level APIs and ease of use, while Project Tungsten is focused on low-level optimizations that can significantly improve the runtime performance of Spark applications.

## Data engineers are responsible for:

1. Data ingestion: Extracting data from various sources, such as databases, APIs, and log files, and transforming it into a usable format.
1. Data storage: Storing the data in a structured or unstructured format in a data lake, data warehouse, or other data storage system.
1. Data processing: Transforming the data to make it usable for downstream applications and analytics, such as cleaning, filtering, aggregating, and joining.
1. Data integration: Combining data from different sources to create a unified view of the data.
1. Data orchestration: Scheduling and managing the flow of data through the pipeline.
1. Data quality: Ensuring that the data is accurate, consistent, and reliable.