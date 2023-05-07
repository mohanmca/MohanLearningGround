## What is Apache Hive?

1. Users can write SQL-like queries, called HiveQL, that are translated into MapReduce jobs vt Hive.
2. Provides data warehouse infrastructure that provides data summarization, query, and analysis capabilities on top of Apache Hadoop
3. Indexing: Hive supports indexing for faster query performance.
4. Hive allows users to write custom user-defined functions (UDFs) in Java, Python, or other languages.

## Spark and Metadata

1. Spark by default uses the Apache Hive metastore
   1. located at /user/hive/warehouse, to persist all the metadata about your tables.
   2. spark.sql.warehouse.dir - can be used to configure metastore

## Managed vs UnManaged Tables

1. For a managed table, Spark manages both the metadata and the data in the file store. 
   1. This could be a local filesystem
   2. HDFS
   3. An object store such as Amazon S3 or Azure Blob.
   4. DROP TABLE table_name deletes both the metadata and the data.
2. For an unmanaged table, Spark only manages the metadata
   1. while you manage the data yourself in an external data source such as Cassandra
   2. DROP TABLE table_name - command 
      1. will delete only the metadata, not the actual data.

## How to create Spark Database (default - it is using `default`)

// In Scala/Python
spark.sql("CREATE DATABASE learn_spark_db")
spark.sql("USE learn_spark_db")

## Create Flight Delay View, Show flight that delayed for distance more than 1000 KM

```java
# In Python
from pyspark.sql import SparkSession        
# Create a SparkSession
spark = (SparkSession.builder
  .appName("SparkSQLExampleApp")
  .getOrCreate())

schema = "`date` STRING, `delay` INT, `distance` INT, `origin` STRING, `destination` STRING"

# Path to data set
csv_file = "/databricks-datasets/learning-spark-v2/flights/departuredelays.csv"

# Read and create a temporary view
# Infer schema (note that for larger files you 
# may want to specify the schema)
df = (spark.read.format("csv")
  .schema(customSchema)
  .option("header", "true")
  .load(csv_file))
df.createOrReplaceTempView("us_delay_flights_tbl")

spark.sql("""SELECT distance, origin, destination FROM us_delay_flights_tbl WHERE distance > 1000 ORDER BY distance DESC""").show(10)
```

## Two ways to order the data

```sql
# In Python
from pyspark.sql.functions import col, desc
(df.select("distance", "origin", "destination")
  .where(col("distance") > 1000)
  .orderBy(desc("distance"))).show(10)

# Or
(df.select("distance", "origin", "destination")
  .where("distance > 1000")
  .orderBy("distance", ascending=False).show(10))
```

## Categorize the delay using Case When

```sql
spark.sql("""SELECT delay, origin, destination, 
              CASE
                  WHEN delay > 360 THEN 'Very Long Delays'
                  WHEN delay >= 120 AND delay <= 360 THEN 'Long Delays'
                  WHEN delay >= 60 AND delay < 120 THEN 'Short Delays'
                  WHEN delay > 0 and delay < 60 THEN 'Tolerable Delays'
                  WHEN delay = 0 THEN 'No Delays'
                  ELSE 'Early'
               END AS Flight_Delays
               FROM us_delay_flights_tbl
               ORDER BY origin, delay DESC""").show(10)
```

## How to create managed table using Python and DataFrame-API

```python
// In Scala/Python
spark.sql("CREATE TABLE managed_us_delay_flights_tbl (date STRING, delay INT,  
   distance INT, origin STRING, destination STRING)")
```

```java
# In Python
# Path to our US flight delays CSV file 
csv_file = "/databricks-datasets/learning-spark-v2/flights/departuredelays.csv"
# Schema as defined in the preceding example
schema="date STRING, delay INT, distance INT, origin STRING, destination STRING"
flights_df = spark.read.csv(csv_file, schema=schema)
flights_df.write.saveAsTable("managed_us_delay_flights_tbl")
```

## How to create Un-managed table using Python and DataFrame-API

```python
spark.sql("""CREATE TABLE us_delay_flights_tbl(date STRING, delay INT, 
  distance INT, origin STRING, destination STRING) 
  USING csv OPTIONS (PATH 
  '/databricks-datasets/learning-spark-v2/flights/departuredelays.csv')""")
```

```python
(flights_df
  .write
  .option("path", "/tmp/data/us_flights_delay")
  .saveAsTable("us_delay_flights_tbl"))
```

## Global views vs Temporary Views
1. global: (visible across all SparkSessions on a given cluster)
2. temporary: they disappear after your Spark application terminates.

## Global Temporary View
1. a global temporary view is visible across multiple SparkSessions within a Spark application.
2. multiple SparkSessions within a single Spark application—this is handy
   1. where you want to access (and combine) data from two different SparkSessions
   2. That don’t share the same Hive metastore configurations.

## How to create Global and Temporary view

```python
# In Python
df_sfo = spark.sql("SELECT date, delay, origin, destination FROM 
  us_delay_flights_tbl WHERE origin = 'SFO'")
df_jfk = spark.sql("SELECT date, delay, origin, destination FROM 
  us_delay_flights_tbl WHERE origin = 'JFK'")

# Create a temporary and global temporary view
df_sfo.createOrReplaceGlobalTempView("us_origin_airport_SFO_global_tmp_view")
df_jfk.createOrReplaceTempView("us_origin_airport_JFK_tmp_view")
```

## Catalog API's

// In Scala/Python
spark.catalog.listDatabases()
spark.catalog.listTables()
spark.catalog.listColumns("us_delay_flights_tbl")

## DataFrameReader usage
1. Only access a DataFrameReader through a SparkSession
2. DataFrameWriter from the DataFrame you wish to save
```java
DataFrameReader.format(args).option("key", "value").schema(args).load()
```

## How to read Parquet file using SQL

```SQL
CREATE OR REPLACE TEMPORARY VIEW us_delay_flights_tbl
    USING parquet
    OPTIONS (
      path "/databricks-datasets/learning-spark-v2/flights/summary-data/parquet/
      2010-summary.parquet/" )
```

## How to write to Parquet file

```python
(df.write.format("parquet")
  .mode("overwrite")
  .option("compression", "snappy")
  .save("/tmp/data/parquet/df_parquet"))
  ```
## Writing a DataFrame to a SQL table

```python
(df.write
  .mode("overwrite")
  .saveAsTable("us_delay_flights_tbl"))
```

## Json file read

1. singleLineMode
2. multilineMode


### How to read/write JSON file as a Table (from DF to JSON)

```SQL
CREATE OR REPLACE TEMPORARY VIEW us_delay_flights_tbl
USING json
OPTIONS (
   path  "/databricks-datasets/learning-spark-v2/flights/summary-data/json/*"
)

# In Python
(df.write.format("json")
  .mode("overwrite")
  .option("compression", "snappy")
  .save("/tmp/data/json/df_json"))
```


## What is Avro format

1. Row level format
2. Used by Apache Kafka
   1. for message serializing and deserializing.
3. It offers many benefits, 
   1. including direct mapping to JSON, 
   2. speed and efficiency, and 
   3. bindings available for many programming languages.

## ORC vs Parquet

1. Parquet is generally better for write-once, read-many analytics, 
2. ORC is more suitable for read-heavy operations. 
3. ORC is optimized for Hive data, while Parquet is considerably more efficient for querying.

## Generate ANKI
* mdanki SparkSQL_Dataframe_Anki.md SparkSQL_Dataframe_Anki.apkg --deck "Mohan::Spark::SparkSQL_Dataframe_Anki"

## References
1. [Deep Dive into Spark SQL's Catalyst Optimizer](https://www.databricks.com/blog/2015/04/13/deep-dive-into-spark-sqls-catalyst-optimizer.html)
2. [SparkSession Unified Entry Point](https://www.databricks.com/blog/2016/08/15/how-to-use-sparksession-in-apache-spark-2-0.html)
3. [Mnm DataSet](https://github.com/databricks/LearningSparkV2/blob/master/chapter2/py/src/data/mnm_dataset.csv)
4. [Fire Data](https://data.sfgov.org/Public-Safety/Fire-Incidents/wr8u-xric/data)