## How to query from multiple parquet file
```
%sql
select count(*), accountId, symbol, eventId from parquet.`s3a://bucket/event_los/staging/*/*/*.parquet`
where balance is null group by accountId, symbol, eventId having count(*) > 1 order by 1 desc
```



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
