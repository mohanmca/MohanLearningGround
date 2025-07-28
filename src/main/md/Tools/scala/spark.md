## What are task, job
1. A job in Spark is created whenever you call an *action* (e.g. .count(), .collect(), .write()).
   1. Here, .count() is the action. Spark will plan out everything needed to compute the distinct count, then submit it as Job 0, Job 1,
2. A stage is a set of parallel tasks that can run without requiring data to be reshuffled. Spark builds a DAG of your operations and breaks it into stages at points where a shuffle is required (e.g. groupBy, join, repartition).
   1. mapped = df.filter(df.event_type == "click").select("user_id", "timestamp")
   2. filter and select is a stage (no shuffle here)
   3. Some stage is all about shuffling without filter and map (groupBy  partition)
3. A task is the smallest unit of work in Spark—it’s one call to a transformation or action on a single partition of data. Each stage is split into as many tasks as there are partitions.
4. A shuffle is Spark’s mechanism for redistributing data across the cluster by key. Whenever you do operations like groupBy, reduceByKey, join, or repartition, Spark writes intermediate data to disk (or memory), ships it across the network, and then reads it back. This is the most expensive part of a job.


## How to chunk prompt?
```
System prompt:
You are a Spark performance expert. When asked how to process very large time-series data in fixed intervals (e.g. weeks) without driver-side loops or running out of memory, recommend:

Native partition writes: read the full range once, annotate with time-columns (year, week, etc.), repartition and write.partitionBy, letting executors each handle one slice.

Parallelized date-ranges: build a driver list of (start,end) tuples, parallelize() it with a sensible number of slices, broadcast any lookup maps, and .foreach() to run load/transform/write entirely on workers.
Also remind to avoid collect(), enable predicate pushdown on timestamps, tune spark.sql.shuffle.partitions, and use dynamic allocation so executors scale to the number of time-slice tasks.
```

## How to query from multiple parquet file
```
%sql
select count(*), accountId, symbol, eventId from parquet.`s3a://bucket/event_los/staging/*/*/*.parquet`
where balance is null group by accountId, symbol, eventId having count(*) > 1 order by 1 desc
```

## What are A job in Spark is created whenever you call an action (e.g. .count(), .collect(), .write()).

## How to create temp table in Spark backed by S3 parquet data
```sql
-- Load the data into a temporary table
CREATE OR REPLACE TEMPORARY VIEW account_balances_ts AS
SELECT * FROM parquet.`s3://somebucket/data/year=2025/month=1/`
UNION ALL
SELECT * FROM parquet.`s3://somebucket/data/year=2025/month=1/`;
```

## How to find current namespace

```
%sql
-- Shows current database
SELECT current_database();
```

```

## How to create table based on other SQL
```sql
CREATE TABLE account_ts_view as  select * from account_ts_view_temp where assetId=1 order by date asc limit 100;
```

## Challenges in ETL
1. at t=0, there are n files, but t=n ther are more files
2. Listing cloud files are costly, and we need to deal only newfiles
3. New file might contain new column, how to manage new columns

## Dabricks commands
```bash
databricks jobs list --name "rds_job"
databricks jobs list-runs  --job-id  654421358946423
databricks jobs get-run  847204524992418
databricks jobs list-runs  --job-id  654421358946423 --start-time-from 1752444000000 --start-time-to 1752501600000 --completed-only
databricks jobs get-run-output  15254730485937
databricks jobs get-run-output  529358573202460
```

## Further prompt quiz
1. API for autoloader allowing incremental data loading
2.   spark.readStream().writeStream() and relationship with autoloader
3. checkpointLocation
4. Cloudfiles va Autoloader
5. Steps to create pipeline
   1. Create job > Task  > simple_etl_pipeline_v1 > (Notebook/Sql/Jar/DBT/Pytho) > choose_cluster + create
 2. Spark Autoloader uses Cloud Queue

 ## Auto loader feature
 1. Use existing files via listing and load delta via queue
 2. cloudFiles.useNotifications cloudFiles.includeExistingFiles
 3. cloudFiles.format = json/parquet
 4. Usage of rocksDB for map and failover

 ## Schema evaluation
 1. structure steam fails when there is new column, and restarts and would add new columns
 2. cloudFiles.schemaEvolutionMode - addNewColumn|rescue|failOnNewColumns|none (new column rescued would contain the data)

## What are they? how they are being used inside spark?
1. 2671 TaskSetManager:
1. 478 DAGScheduler:
1. 434 BlockManagerInfo:
1. 140 TaskSchedulerImpl:
1. 133 MemoryStore:
1. 104 SparkContext:
1.  39 FileSourceStrategy:
1.  36 FileFormatWriter:
1.  36 AnalyzerLogHelper$:
1.  29 GlueLogger:
1.  26 SQLConfCommitterProvider:
1.  26 FileSourceScanExec:
1.  26 FileOutputCommitter:
1.  26 EmrOptimizedParquetOutputCommitter:
1.  25 ParquetOutputFormat:
1.  15 ExecutorTaskManagement:
1.  13 ParquetFileFormat:
1.  13 JESSchedulerBackend:
1.  13 InMemoryFileIndex:
1.  13 HadoopFSUtils:
1.  12 ShufflePartitionsUtil:
1.  12 MapOutputTrackerMasterEndpoint:
1.  11 ExecutorAllocationManager:
1.   6 TaskGroupInterface:
1.   6 BlockManagerMasterEndpoint:
1.   5 SecurityManager:
1.   4 Utils:
1.   4 SparkEnv:
1.   4 JESSchedulerBackend$JESAsSchedulerBackendEndpoint:
1.   3 PlatformInfo:
1.   3 ObservabilityTaskInfoRecorderListener:
1.   3 GlueCloudwatchSink:
1.   3 ExecutorMonitor:
1.   3 ExecutorEventListener:
