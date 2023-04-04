## Apache Spark Predecessor

1. GFS, Hadoop, MR
2. They are slow and complicated
3. Disk IO, too complex API
4. Unmaintainable

## Spark Original Creators
1. Matei Zaharia, Ali Ghodsi, Reynold Xin, Patrick Wendell, Ion Stoica, and Andy Konwinski
2.  Unified Engine for Big Data Processing.

## What is Apache Spark DAG

1. DAG > Stage < Task <-> Partition
2. A Directed Acyclic Graph (DAG) is a fundamental concept in Apache Spark that represents a sequence of data processing steps in a logical workflow. In Spark, DAGs are used to optimize the execution of data processing tasks by breaking them down into a series of stages that can be executed in parallel on a cluster of machines.
3. When a Spark application is submitted, the Spark engine first analyzes the code to build a DAG of the tasks that need to be executed. This DAG represents the logical flow of the data processing operations, and it contains all the dependencies between the different stages of the computation.
4. Each stage of the DAG represents a set of tasks that can be executed in parallel, and the Spark engine uses this information to optimize the execution of the computation. Specifically, Spark can schedule tasks across multiple nodes in the cluster in a way that minimizes data shuffling and maximizes the utilization of available resources.
5. In addition to optimizing task execution, the DAG is also used to track the progress of the computation and to handle failures and retries. If a task fails, for example, Spark can use the information in the DAG to re-execute the task and to make sure that all downstream stages are updated with the correct data.
6. Overall, the DAG is a powerful tool that enables Spark to perform complex data processing tasks in a highly scalable and efficient way. By breaking the computation down into a series of stages that can be executed in parallel, Spark can take advantage of the distributed computing resources of a cluster to process large volumes of data quickly and reliably.

## What is the relationship between DAG and Stage

1. Each node within a DAG could be a single or multiple Spark stages.
2. Not all Spark operations can happen in a single stage
3. They may be divided into multiple stages. 
   1. Often stages are delineated on the operator’s computation boundaries, where they dictate data transfer among Spark executors.

## What is the relationship between Stage and Tasks

1. Each stage comprises Spark tasks (a unit of execution), which are then federated across each Spark executor;
2. Each task maps to a single core and works on a single partition of data

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

## How Spark Executor and Spark Driver communicates upon failures
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
2. Data storage: Storing the data in a structured or unstructured format in a data lake, data warehouse, or other data storage system.
3. Data processing: Transforming the data to make it usable for downstream applications and analytics, such as cleaning, filtering, aggregating, and joining.
4. Data integration: Combining data from different sources to create a unified view of the data.
5. Data orchestration: Scheduling and managing the flow of data through the pipeline.
6. Data quality: Ensuring that the data is accurate, consistent, and reliable.

## What is the current Spark Version

1. 3.3.2
2. Databricks Runtime 12.2 LTS, powered by Apache Spark 3.3.2.
3. Four shells
   1. pyspark, spark-shell, spark-sql, and sparkR
2. How to find version
   1. spark.version

## How to install pyspark

1. pip install pyspark[sql,ml,mllib]

## How to show only 10 lines

```python
strings = spark.read.text("../README.md")
strings.show(10, truncate=false)
strings.count()
```

## RDD is low-level and DataFrame is highlevel

1. Every computation expressed in high-level Structured APIs is decomposed 
   1. into low-level optimized and generated RDD operations 
   2. and then converted into Scala bytecode for the executors’ JVMs. 
2. This generated RDD operation code is not accessible to users, nor is it the same as the user-facing RDD APIs.

##  How to connect to the Spark cluster manager.

1. park-shell --help
2. spark-shell --help

## How to run sample programs of Apache Spark

```bash
./bin/run-example JavaWordCount README.md
./bin/run-example <class> [params]
```

## Spark Transformation vs Actions

1. Spark operations on distributed data can be classified into two types: transformations and actions.
2. An action triggers the lazy evaluation of all the recorded transformations 
3. All transformations T are recorded until the action A is invoked. Each transformation T produces a new DataFrame.
4. DataFrames are immutable between transformations

## Give 4 sample Transformations
1. orderBy()
2. groupBy() 	        
3. filter() 	        
4. select() 	        
5. join()
6. read()

## Give 4 sample Actions
1. save()
2. show()
3. take()
4. count()
5. collect()
6. tail(n)

## How to count number of lines of a file using pyspark

```python
>>> strings = spark.read.text("../README.md")
>>> filtered = strings.filter(strings.value.contains("Spark"))
>>> filtered.count()
20
```

## Narrow and Wide Transformations

1. Transformations can be classified as having either narrow dependencies or wide dependencies. 
2. Any transformation where a single output partition can be computed from a single input partition is a narrow transformation.

## What is wide transformation
1. Transformations such as groupBy() or orderBy() instruct Spark to perform wide transformations, where data from other partitions is read in, combined, and written to disk.

## The Spark UI

1. A list of scheduler stages and tasks
1. A summary of RDD sizes and memory usage
1. Information about the environment
1. Information about the running executors
1. All the Spark SQL queries

## Counting and aggregating M&Ms (Python version) - <state, mnm_color, count>) - Sort By Count, State, Color

```python
# Import the necessary libraries.
# Since we are using Python, import the SparkSession and related functions
# from the PySpark module.
import sys

from pyspark.sql import SparkSession

if __name__ == "__main__":
   if len(sys.argv) != 2:
       print("Usage: mnmcount <file>", file=sys.stderr)
       sys.exit(-1)

   # Build a SparkSession using the SparkSession APIs.
   # If one does not exist, then create an instance. There
   # can only be one SparkSession per JVM.
   spark = (SparkSession
     .builder
     .appName("PythonMnMCount")
     .getOrCreate())
   # Get the M&M data set filename from the command-line arguments
   mnm_file = sys.argv[1]
   # Read the file into a Spark DataFrame using the CSV
   # format by inferring the schema and specifying that the
   # file contains a header, which provides column names for comma-
   # separated fields.
   mnm_df = (spark.read.format("csv") 
     .option("header", "true") 
     .option("inferSchema", "true") 
     .load(mnm_file))

   # We use the DataFrame high-level APIs. Note
   # that we don't use RDDs at all. Because some of Spark's 
   # functions return the same object, we can chain function calls.
   # 1. Select from the DataFrame the fields "State", "Color", and "Count"
   # 2. Since we want to group each state and its M&M color count,
   #    we use groupBy()
   # 3. Aggregate counts of all colors and groupBy() State and Color
   # 4  orderBy() in descending order
   count_mnm_df = (mnm_df
     .select("State", "Color", "Count")
     .groupBy("State", "Color")
     .sum("Count")
     .orderBy("sum(Count)", ascending=False))
   # Show the resulting aggregations for all the states and colors;
   # a total count of each color per state.
   # Note show() is an action, which will trigger the above
   # query to be executed.
   count_mnm_df.show(n=60, truncate=False)
   print("Total Rows = %d" % (count_mnm_df.count()))
   # While the above code aggregated and counted for all 
   # the states, what if we just want to see the data for 
   # a single state, e.g., CA? 
   # 1. Select from all rows in the DataFrame
   # 2. Filter only CA state
   # 3. groupBy() State and Color as we did above
   # 4. Aggregate the counts for each color
   # 5. orderBy() in descending order  
   # Find the aggregate count for California by filtering
   ca_count_mnm_df = (mnm_df
     .select("State", "Color", "Count")
     .where(mnm_df.State == "CA")
     .groupBy("State", "Color")
     .sum("Count")
     .orderBy("sum(Count)", ascending=False))
   # Show the resulting aggregation for California.
   # As above, show() is an action that will trigger the execution of the
   # entire computation. 
   ca_count_mnm_df.show(n=10, truncate=False)
   # Stop the SparkSession
   spark.stop()
```


## Generate ANKI
* mdanki ApacheSparkManual_Anki.md ApacheSparkManual_Anki.md.apkg --deck "Mohan::Spark"

## References
1. [Spark](https://github.com/databricks/LearningSparkV2)
2. [Mnm DataSet](https://github.com/databricks/LearningSparkV2/blob/master/chapter2/py/src/data/mnm_dataset.csv)
3. [Fire Data](https://data.sfgov.org/Public-Safety/Fire-Incidents/wr8u-xric/data)