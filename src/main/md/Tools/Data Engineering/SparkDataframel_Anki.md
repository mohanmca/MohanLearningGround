## What is the core abstraction in Apache Spark

1. RDD - Resilient Distributed DataSet
   1. Dependencies
   2. Partitions
   3. function: Partition => Iterator[T]
   
## What are all pain points of RDD
1. It is Opaque
   1. Spark can't optimize expression
   2. Can't do data-compression

## What is the use of Structured API
1. Expressiablity
2. Composability

## Example of Expressability using RDD (name, age)

```python
# In Python
# Create an RDD of tuples (name, age)
dataRDD = sc.parallelize([("Brooke", 20), ("Denny", 31), ("Jules", 30), 
  ("TD", 35), ("Brooke", 25)])
# Use map and reduceByKey transformations with their lambda 
# expressions to aggregate and then compute average

agesRDD = (dataRDD
  .map(lambda x: (x[0], (x[1], 1)))
  .reduceByKey(lambda x, y: (x[0] + y[0], x[1] + y[1]))
  .map(lambda x: (x[0], x[1][0]/x[1][1])))
```

```python3
# In Python 
from pyspark.sql import SparkSession
from pyspark.sql.functions import avg
# Create a DataFrame using SparkSession
spark = (SparkSession.builder.appName("AuthorsAges")
         .getOrCreate())
# Create a DataFrame 
data_df = spark.createDataFrame([("Brooke", 20), ("Denny", 31), ("Jules", 30), 
  ("TD", 35), ("Brooke", 25)], ["name", "age"])
# Group the same names together, aggregate their ages, and compute an average
avg_df = data_df.groupBy("name").agg(avg("age"))
# Show the results of the final execution
avg_df.show()
```
1. More readable code
2. Declarative
3. Unified in all the languages


## The DataFrame API

1. Inspired by pandas DataFrames
2. It is just alias of DataSet[Row]
   1. Every DataFrame is just DataSet[Row]
3. Tabular datamodel

## What are all the Spark Data-Types

1. [Python DataType](https://spark.apache.org/docs/latest/api/python/reference/pyspark.sql/data_types.html)
2. [Scala datatype](https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/types/DataTypes.html)
3. All are subtypes of the class DataTypes, except for DecimalType.




## What are all the Spark Data-Types for Python

```python
Data type 	Value assigned in Python 	API to instantiate
ByteType 	int 	          DataTypes.ByteType
ShortType 	int 	          DataTypes.ShortType
IntegerType    int            DataTypes.IntegerType
LongType 	int 	          DataTypes.LongType
FloatType 	float 	          DataTypes.FloatType
DoubleType 	float 	          DataTypes.DoubleType
StringType 	str 	          DataTypes.StringType
BooleanType 	bool 	         DataTypes.BooleanType
DecimalType 	decimal.Decimal 	DecimalType
```


## What are all the Spark Structured and Complex Data Types

```python
DateType    -  Date (datetime.date) data type.
TimestampType - Timestamp (datetime.datetime) data type.
StructType([fields]) - Struct type, consisting of a list of StructField.
MapType -	dict 	MapType(keyType, valueType, [nullable])
```

## Why we need to declare DataType upfront?

1. You relieve Spark from the onus of inferring data types.
2. You prevent Spark from creating a separate job just to read a large portion of your file to ascertain the schema, which for a large data file can be expensive and time-consuming. 
3. You can detect errors early if data doesn’t match the schema.

## How many ways are there to declare Schema

1. Programmatic way
   ```python
   from pyspark.sql.types import *
   schema = StructType([
      StructField("author", StringType(), False),
      StructField("title", StringType(), False),
      StructField("pages", IntegerType(), False)
   ])      
   ```

2. DDL Way
   ```schema = "author STRING, title STRING, pages INT"```

## Sample of creating DF using Programming Way

```python
from pyspark.sql.types import *
from pyspark.sql import SparkSession
from pyspark.sql.functions import *

# define schema for our data
schema = StructType([
   StructField("Id", IntegerType(), False),
   StructField("First", StringType(), False),
   StructField("Last", StringType(), False),
   StructField("Url", StringType(), False),
   StructField("Published", StringType(), False),
   StructField("Hits", IntegerType(), False),
   StructField("Campaigns", ArrayType(StringType()), False)])
   
# Alternarive wasy to define schema for our data using DDL 
schema = "`Id` INT, `First` STRING, `Last` STRING, `Url` STRING, 
  `Published` STRING, `Hits` INT, `Campaigns` ARRAY<STRING>"

#create our data
data = [[1, "Jules", "Damji", "https://tinyurl.1", "1/4/2016", 4535, ["twitter", "LinkedIn"]],
       [2, "Brooke","Wenig","https://tinyurl.2", "5/5/2018", 8908, ["twitter", "LinkedIn"]],
       [3, "Denny", "Lee", "https://tinyurl.3","6/7/2019",7659, ["web", "twitter", "FB", "LinkedIn"]],
       [4, "Tathagata", "Das","https://tinyurl.4", "5/12/2018", 10568, ["twitter", "FB"]],
       [5, "Matei","Zaharia", "https://tinyurl.5", "5/14/2014", 40578, ["web", "twitter", "FB", "LinkedIn"]],
       [6, "Reynold", "Xin", "https://tinyurl.6", "3/2/2015", 25568, ["twitter", "LinkedIn"]]
      ]
# main program
if __name__ == "__main__":
   #create a SparkSession
   spark = (SparkSession
       .builder
       .appName("Example-3_6")
       .getOrCreate())
   # create a DataFrame using the schema defined above
   blogs_df = spark.createDataFrame(data, schema)
   # show the DataFrame; it should reflect our table above
   blogs_df.show()
   print()
   # print the schema used by Spark to process the DataFrame
   print(blogs_df.printSchema())
   # Show columns and expressions
   blogs_df.select(expr("Hits") * 2).show(2)
   blogs_df.select(col("Hits") * 2).show(2)
   blogs_df.select(expr("Hits * 2")).show(2)
   # show heavy hitters
   blogs_df.withColumn("Big Hitters", (expr("Hits > 10000"))).show()
   print(blogs_df.schema)
```

## What are Spark Columns

1. Named columns in DataFrames are conceptually similar to named columns in pandas or R DataFrames or in an RDBMS table
2. They describe a type of field
3. You can list all the columns by their names
4. You can perform operations on their values using relational or computational expressions.


## Column Expressions

1. expr("columnName * 5")
2. (expr("columnName - 5") > col(anothercolumnName))
3. pyspark.sql.functions
   1. expr()
   2. col()

## Column Expressions - Basic Example for expressions

```java

// In Scala 
scala> import org.apache.spark.sql.functions._
scala> blogsDF.columns
res2: Array[String] = Array(Campaigns, First, Hits, Id, Last, Published, Url)

// Access a particular column with col and it returns a Column type
scala> blogsDF.col("Id")
res3: org.apache.spark.sql.Column = id

// Use an expression to compute a value
scala> blogsDF.select(expr("Hits * 2")).show(2)
// or use col to compute value
scala> blogsDF.select(col("Hits") * 2).show(2)


// Use an expression to compute big hitters for blogs
// This adds a new column, Big Hitters, based on the conditional expression
blogsDF.withColumn("Big Hitters", (expr("Hits > 10000"))).show()

// Concatenate three columns, create a new column, and show the 
// newly created concatenated column
blogsDF
  .withColumn("AuthorsId", (concat(expr("First"), expr("Last"), expr("Id"))))
  .select(col("AuthorsId"))
  .show(4)

// These statements return the same value, showing that 
// expr is the same as a col method call
blogsDF.select(expr("Hits")).show(2)
blogsDF.select(col("Hits")).show(2)
blogsDF.select("Hits").show(2)
        
// Sort by column "Id" in descending order
blogsDF.sort(col("Id").desc).show()
```

## How to let Spark infer the schema

```scala
// In Scala
val sampleDF = spark
  .read
  .option("samplingRatio", 0.001)
  .option("header", true)
  .csv("""/databricks-datasets/learning-spark-v2/
  sf-fire/sf-fire-calls.csv""")
```

## DataFrameReader and DataFrameWriter

1. Spark comes with  DataFrameReader and DataFrameWriter
2. NoSQL stores
3. RDBMSs
4. Apache Kafka
5. Kinesis

## How to create DF using set of Rows

```python
# In Python    
rows = [Row("Matei Zaharia", "CA"), Row("Reynold Xin", "CA")]
authors_df = spark.createDataFrame(rows, ["Authors", "State"])
authors_df.show()
```

# Use the DataFrameReader interface to read a CSV file
```python
sf_fire_file = "/databricks-datasets/learning-spark-v2/sf-fire/sf-fire-calls.csv"
fire_df = spark.read.csv(sf_fire_file, header=True, schema=fire_schema)
```

## Saving DataFrame as a Parquet file or SQL table

```python
# In Python to save as a Parquet file
parquet_path = "/databricks-datasets/learning-spark-v2/sf-fire/output_fire_analysis.parquet"
fire_df.write.format("parquet").save(parquet_path)
```

## What is Table

1. Table registers metadata with the Hive metastore in Spark
```java
parquet_table = ... # name of the table
fire_df.write.format("parquet").saveAsTable(parquet_table)
```

## What is Parquet format
1. Parquet, a popular columnar format
2. It is the default format; 
3. it uses snappy compression to compress the data. 
4. If the DataFrame is written as Parquet, the schema is preserved as part of the Parquet metadata.

## How to create Parquet file using Pandas

```java
import pandas as pd

# read
df = pd.read_parquet('myfile.parquet')

# write
df.to_parquet('my_newfile.parquet')
df.head()
```

## How many distinct CallTypes were recorded as the causes of the fire calls?

```python
# In Python
few_fire_df = (fire_df
  .select("IncidentNumber", "AvailableDtTm", "CallType") 
  .where(col("CallType") != "Medical Incident")
  .agg(countDistinct("CallType").alias("DistinctCallTypes"))
  .show())
```

## How to Renaming, adding, and dropping columns

1. withColumnRenamed, withColumn and drop
2. fire_df.withColumnRenamed("Delay", "ResponseDelayedinMins")
3. fire_df.withColumn("IncidentDate", to_timestamp(col("CallDate"), "MM/dd/yyyy"))
     .drop("CallDate")


## what were the most common types of fire calls?

```python
# In Python
(fire_ts_df
  .select("CallType")
  .where(col("CallType").isNotNull())
  .groupBy("CallType")
  .count()
  .orderBy("count", ascending=False)
  .show(n=10, truncate=False))
```

## Other common DataFrame Aggregate operations

2. correlation(), covariance(), approxQuantile(), frequentItems()
3. describe()
4. stat(), sampleBy()
5. min(), max(), sum(), and avg()

## The Dataset API
1.  A Dataset, by contrast, is a collection of strongly typed JVM objects in Scala or a class in Java.

## How to create DataSet

```java
// In Scala
case class DeviceIoTData (battery_level: Long, c02_level: Long,
                          cca2: String, cca3: String, cn: String, device_id: Long,
                          device_name: String, humidity: Long, ip: String, latitude: Double,
                          lcd: String, longitude: Double, scale:String, temp: Long,
                          timestamp: Long)
val ds = spark.read
 .json("/databricks-datasets/learning-spark-v2/iot-devices/iot_devices.json")
 .as[DeviceIoTData]

ds: org.apache.spark.sql.Dataset[DeviceIoTData] = [battery_level...]

ds.show(5, false)
```

## How Spark SQL works?

1. Core of the Spark SQL engine are the Catalyst optimizer and Project Tungsten

## The Catalyst Optimizer - Exceution Plan

1. Spark computation’s four-phase journey
   1. Analysis
      1. Spark SQL engine begins by generating an abstract syntax tree (AST) for the SQL or DataFrame query
      2. Table names will be resolved by consulting an internal Catalog
      3. Programmatic interface to Spark SQL that holds a list of names of columns, data types, functions, tables, databases
   2. Logical optimization
   3. Physical planning
      1. Spark SQL generates an optimal physical plan for the selected logical plan
      2. Using physical operators that match those available in the Spark execution engine.
   4. Code generation
      1. Generating efficient Java bytecode to run on each machine.
      2. Project Tungsten, which facilitates whole-stage code generation

## What is Logical Optimization
   1. Applying a standard-rule based optimization approach
   2. The Catalyst optimizer will first construct a set of multiple plans and then
      1. Using its cost-based optimizer (CBO), assign costs to each plan.
      2. These plans are laid out as operator trees
         1. The process of constant folding,
         2. Predicate pushdown,
         3. Projection pruning,
         4. Boolean expression simplification, etc. Th


## Sample execution plan

```python
# In Python
count_mnm_df = (mnm_df
    .select("State", "Color", "Count")
    .groupBy("State", "Color")
    .agg(sum("Count")
    .alias("Total"))
    .orderBy("Total", ascending=False))
```

```java
count_mnm_df.explain(True)

== Parsed Logical Plan ==
'Sort ['Total DESC NULLS LAST], true
+- Aggregate [State#10, Color#11], [State#10, Color#11, sum(Count#12) AS...]
   +- Project [State#10, Color#11, Count#12]
      +- Relation[State#10,Color#11,Count#12] csv

== Analyzed Logical Plan ==
State: string, Color: string, Total: bigint
Sort [Total#24L DESC NULLS LAST], true
+- Aggregate [State#10, Color#11], [State#10, Color#11, sum(Count#12) AS...]
   +- Project [State#10, Color#11, Count#12]
      +- Relation[State#10,Color#11,Count#12] csv

== Optimized Logical Plan ==
Sort [Total#24L DESC NULLS LAST], true
+- Aggregate [State#10, Color#11], [State#10, Color#11, sum(Count#12) AS...]
   +- Relation[State#10,Color#11,Count#12] csv

== Physical Plan ==
*(3) Sort [Total#24L DESC NULLS LAST], true, 0
+- Exchange rangepartitioning(Total#24L DESC NULLS LAST, 200)
   +- *(2) HashAggregate(keys=[State#10, Color#11], functions=[sum(Count#12)],
output=[State#10, Color#11, Total#24L])
      +- Exchange hashpartitioning(State#10, Color#11, 200)
         +- *(1) HashAggregate(keys=[State#10, Color#11],
functions=[partial_sum(Count#12)], output=[State#10, Color#11, count#29L])
            +- *(1) FileScan csv [State#10,Color#11,Count#12] Batched: false,
Format: CSV, Location:
InMemoryFileIndex[file:/Users/jules/gits/LearningSpark2.0/chapter2/py/src/...
dataset.csv], PartitionFilters: [], PushedFilters: [], ReadSchema:
struct<State:string,Color:string,Count:int>
```

## How to configure and display catalog

```scala
// Create a SparkSession. No need to create SparkContext
// You automatically get it as part of the SparkSession
val warehouseLocation = "file:${system:user.dir}/spark-warehouse"
val spark = SparkSession
   .builder()
   .appName("SparkSessionZipsExample")
   .config("spark.sql.warehouse.dir", warehouseLocation)
   .enableHiveSupport()
   .getOrCreate()
//set new runtime options
spark.conf.set("spark.sql.shuffle.partitions", 6)
spark.conf.set("spark.executor.memory", "2g")
//get all settings
val configMap:Map[String, String] = spark.conf.getAll()
//fetch metadata data from the catalog
spark.catalog.listDatabases.show(false)
spark.catalog.listTables.show(false)
```

## How to connect to Cassandra multiple cluster

```java
spark.conf.set(s"spark.sql.catalog.cass100", "com.datastax.spark.connector.datasource.CassandraCatalog")
spark.conf.set(s"spark.sql.catalog.cass100.spark.cassandra.connection.host", "127.0.0.100")

//Catalog Cass100 for Cluster at 127.0.0.100
spark.conf.set(s"spark.sql.catalog.cass100", "com.datastax.spark.connector.datasource.CassandraCatalog")
spark.conf.set(s"spark.sql.catalog.cass100.spark.cassandra.connection.host", "127.0.0.100")

//Catalog Cass200 for Cluster at 127.0.0.200
spark.conf.set(s"spark.sql.catalog.cass200", "com.datastax.spark.connector.datasource.CassandraCatalog")
spark.conf.set(s"spark.sql.catalog.cass200.spark.cassandra.connection.host", "127.0.0.200")

spark.sql("INSERT INTO cass200.ks.tab SELECT * from cass100.ks.tab")

spark.read.table("cass100.ks.tab").writeTo("cass200.ks.tab").append
```

## Generate ANKI
* mdanki SparkDataframel_Anki.md SparkDataframel_Anki.apkg --deck "Mohan::Spark::DataFrame"

## References
1. [Deep Dive into Spark SQL's Catalyst Optimizer](https://www.databricks.com/blog/2015/04/13/deep-dive-into-spark-sqls-catalyst-optimizer.html)
2. [SparkSession Unified Entry Point](https://www.databricks.com/blog/2016/08/15/how-to-use-sparksession-in-apache-spark-2-0.html)
3. [Mnm DataSet](https://github.com/databricks/LearningSparkV2/blob/master/chapter2/py/src/data/mnm_dataset.csv)
4. [Fire Data](https://data.sfgov.org/Public-Safety/Fire-Incidents/wr8u-xric/data)