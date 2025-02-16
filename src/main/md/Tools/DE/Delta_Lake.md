## What is Data Lake?
- A data lake is a centralized repository that allows you to store all your structured and unstructured data at any scale.
- Amazon S3 / Hadoop / Azure Data Lake / Google Cloud Storage

## What is Data Lakehouse?
- Data Lakehouse is a new data management paradigm that combines the best of data lakes and data warehouses.
- Delta Lake / Apache Iceberg / 

## What is DeltaLake.io?
- Delta Lake is an open-source storage layer that brings ACID transactions to Apache Spark and big data workloads.
- It is not fixed Storage format
- It is a transactional storage layer that runs on top of cloud storage like S3, ADLS, GCS, etc.

## What is Delta Lake?
1. Delta Lake uses a transaction log (also called the Delta Log) to keep track of changes to your data.
2. Located in the _delta_log/ directory in your Delta table’s storage location (e.g., S3, HDFS, local FS).  
3. Contains a sequence of JSON files and checkpoint Parquet files
3. The Delta Log records every change made to the table, including inserts, updates, deletes, and schema changes.
4. Delta Lake provides ACID transactions, scalable metadata handling, and unifies streaming and batch data processing.

## Why Delta Lake table preferred over Parquet?
1. Delta allows easier column additions or type changes compared to raw Parquet.
2. You can enable mergeSchema or overwriteSchema without worrying about breaking readers.
3. Append-only tables tend to create many small files over time, hurting read performance.
   1. Auto-Optimize – Compact small files automatically. 
   1. Z-Ordering – Reorganize data based on columns for faster queries 

## What is the difference between Delta Lake and Parquet?
1. File Format - Parquet vs Parquet + Delta Log
2. ACID Transactions - No vs Yes
3. Schema Evolution - No vs Yes
4. Time Travel - No vs Yes
5. Deletes, Updates, Merges - No vs Yes
9. Schema Enforcement - No vs Yes
10. Partition Evolution - No vs Yes
11. ```pre
      df.write.format("parquet").save("/data/parquet_table/")
      /data/parquet_table/
         └── part-00000-abc.snappy.parquet
         └── part-00001-xyz.snappy.parquet
   
      df.write.format("delta").save("/data/delta_table/")
       /data/delta_table/
           └── _delta_log/
               └── 00000000000000000000.json
               └── 00000000000000000001.json
           └── part-00000-abc.snappy.parquet
           └── part-00001-xyz.snappy.parquet
      ```

## How metdata and parquet files are lifecycle managed in Delta Lake?
1. OPTIMIZE (Small file compaction)	Weekly or based on ingestion
2. VACUUM (Clean unused parquet)	Weekly
3. Checkpoint (Log Compaction)	Let Delta auto-manage (every 10 commits)

## What is the default format in Databricks?
1. Delta Lake is the default format
2. Create table foo using Delta; // using delta is not required because it is default

## What is Delta Live Tables or DLT?
- DLT aim for Make reliable ETL easy on Delta Lake - using SQL
- DLT is a declarative framework for building batch and streaming data pipelines.
- It is built on top of Delta Lake and Apache Spark.

## What is the difference between delta-lake and iceberg
1. Delta Lake and Apache Iceberg are both table formats designed to work with large-scale data lakes

## How to stream batch data into Delta Lake?

```sql
CREATE TABLE orders_table (
  order_id BIGINT,
  customer_name STRING,
  order_amount DOUBLE
) USING DELTA LOCATION 's3://your-bucket/orders_table/';

INSERT INTO orders_table SELECT * FROM parquet.`s3://your-bucket/staging_area/`;

MERGE INTO orders_table AS target
USING parquet.`s3://your-bucket/staging_area/` AS source
ON target.order_id = source.order_id
WHEN MATCHED THEN UPDATE SET * WHEN NOT MATCHED THEN  INSERT *;
```

## Give an example to use Delta Live Tables (DLT) with Auto Loader to stream from S3?

- Auto Loader is a feature of Databricks Delta that automatically loads new data files from cloud storage into a Delta table.
- Auto Loader is designed to work with cloud storage systems like S3, ADLS, and GCS.
- Auto Loader monitors the cloud storage location for new data files and automatically loads them into the Delta table.


## Why we need Hive-Meta-Store?
1. SELECT * FROM delta.`s3://your-bucket/orders_table/`; (without hive meta store)
2. SELECT * FROM orders_table;
3. Hive Metastore is a central repository to store metadata for tables and partitions in a data warehouse.
4. ```SQL
      CREATE TABLE orders_table (
         order_id BIGINT,
         customer_name STRING,
         order_amount DOUBLE
      )
      USING DELTA
      LOCATION 's3://your-bucket/orders_table/';
   ```

## Why we need Unity Catalog?
1. Unity Catalog does internally use Hive Metastore for certain functionality, but it goes beyond it by adding additional layers of governance, security, and data management.
2. Unity Catalog allows you to query data from multiple sources, including Delta Lake, Apache Hive, and other data sources, using a single SQL query.
3. Data Governanace, Fine-grained access control, Data lineage tracking, Centralized governance across multiple Databricks workspaces, 

## ## Generate mdanki
mdanki Delta_Lake.md Delta_Lake.apkg --deck "Mohan::DeltaLake::Delta_Lake"
