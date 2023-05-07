## Views vs Tables?
1. Views are not available under Data tab for preview
2. Views are automatically dropped

## What is DBFS?

The Databricks File System (DBFS) is a distributed file system mounted into a Databricks workspace and available on Databricks clusters. DBFS is an abstraction on top of scalable object storage that maps Unix-like filesystem calls to native cloud storage API calls.

## Hot to get, **Detailed Table Information** contains information about the table's database name, original source file type and location, and more.

1. DESCRIBE EXTENDED DCDataRaw;

## Partition

1. Each partition would be in separate directory
2. It helps to scan only portion of the entire data

## How to view Parquet file using Spark.sql without creating Table/View

```SELECT * FROM parquet.`/mnt/training/ecommerce/events/events.parquet```

## How to create Table based on existing Parquet file
```
DROP TABLE IF EXISTS eventsRaw;
CREATE TABLE eventsRaw using  parquet OPTIONS (path '/mnt/training/ecommerce/events/events.parquet')
```

## Hot to create table using CTE

```sql
DROP TABLE  if exists purchaseEvents;
CREATE TABLE purchaseEvents
(WITH purchase as (
  SELECT
    ecommerce.purchase_revenue_in_usd purchases,
    Cast ((event_previous_timestamp/1000000) as timestamp) previousEventDate,
    Cast ((event_timestamp/1000000) as timestamp) eventDate,
    geo.city city,
    geo.state state,
    user_id userId
  from eventsRaw   
  where ecommerce.purchase_revenue_in_usd is not null
)
select * from purchase order by purchases desc, city, state);
```

## How to work with nested data (in Parquet format)
data was in format dc-102, 2019-11-30, {sensor-igauge: {description: "Sensor attached to the container ceilings" , ip: "145.83.297.143", id:16, temps: [2,43,32], co2_level: [2,43,32]}, sensor-ipad: {description: "Sensor attached to the container ceilings" , ip: "145.83.297.143", id:16, temps: [2,43,32], co2_level: [2,43,32]},sensor-istick: {description: "Sensor attached to the container ceilings" , ip: "145.83.297.143", id:16, temps: [2,43,32], co2_level: [2,43,32]}  }
```sql
DROP TABLE IF EXISTS DCDataRaw;
CREATE TABLE DCDataRaw
USING parquet  OPTIONS (PATH "/mnt/training/iot-devices/data-centers/2019-q2-q3")

DROP TABLE IF EXISTS DeviceData;
CREATE TABLE DeviceData                 
USING parquet
WITH ExplodeSource                       -- The start of the CTE from the last cell
AS
  (
  SELECT 
  dc_id,
  to_date(date) AS date,
  EXPLODE (source)
  FROM DCDataRaw
  )
SELECT 
  dc_id,
  key device_type,                       
  date,
  value.description,
  value.ip,
  value.temps,
  value.co2_level
  
FROM ExplodeSource;
```

## How to create table using CTE ( CTAS (Create table as Select) Pattern)

```    
CREATE TABLE IF NOT EXISTS DeviceData     
USING parquet                               
WITH ExplodeSource                        
AS
  (
  SELECT 
  dc_id,
  to_date(date) AS date,
  EXPLODE (source)
  FROM DCDataRaw
  )
SELECT 
  dc_id,
  key device_type,                         
  date,
  value.description,
  value.ip,
  value.temps,
  value.co2_level co2Level
  
FROM ExplodeSource;
```


## How to select 3 random records

```java
SELECT * FROM DCDataRaw ORDER BY RAND() LIMIT 3;
```

## How to retrieve only sample data (without WHERE and limit)
SELECT * FROM outdoorProductsRaw TABLESAMPLE (15 ROWS)
SELECT * FROM outdoorProductsRaw TABLESAMPLE (2 PERCENT) ORDER BY InvoiceDate

## How to use Split method to parse date from - 1/11/11 14:38

```sql
CREATE OR REPLACE TEMPORARY VIEW outdoorProducts AS
SELECT
    InvoiceNo,
    StockCode,
    COALESCE(Description, "Misc") AS Description,
    Quantity,
    SPLIT(InvoiceDate, "/")[0] month,
    SPLIT(InvoiceDate, "/")[1] day,
    SPLIT(SPLIT(InvoiceDate, " ")[0], "/")[2] year,
    UnitPrice,
    Country
FROM
    outdoorProductsRaw
```

## How to pad numbers with prefix 0
```
LPAD(month, 2, 0) AS month,
LPAD(day, 2, 0) AS day,
```

## How to concat multiple fields

concat_ws("/", month, day, year) sDate

## Hot to convert string into date

to_date(sDate, "MM/dd/yy") date,

## Hot to Cast String into Double
CAST(UnitPrice AS DOUBLE)

## What are all high order functions
1. TRANSFORM
2. FILTER
3. EXISTS

## What are examples of aggregating and summarizing data function

1. Pivot
2. Rollup and Cube
3. Window

## How to use high-order function

1. Create one more column if array has some threshold value
2. SELECT  temps,   EXISTS(temps, t -> t > 23) highTempsFlag   FROM DeviceData2

## How to calculate avg of array inside SQL

REDUCE(co2_level, 0, (c, acc) -> c + acc, acc ->(acc div size(co2_level)))

```sql
  SELECT
    dc_id, 
    device_type,
    co2Level,
    reduce(co2Level, 0, (c, acc) -> c+acc, (acc -> acc div size(co2Level)) ) as averageCo2Level
  
  FROM DeviceData2  
  SORT BY averageCo2Level DESC;
```

## Pivot Example

```sql
SELECT * FROM (
  SELECT device_type, averageCo2Level 
  FROM Co2LevelsTemporary
)
PIVOT (
  ROUND(AVG(averageCo2Level), 2) avg_co2 
  FOR device_type IN ('sensor-ipad', 'sensor-inest', 
    'sensor-istick', 'sensor-igauge')
  );
```
```
SELECT
*
FROM
(
SELECT month(date) month,
    REDUCE(co2Level, 0, (c, acc) -> c + acc, acc ->(acc div size(co2Level))) averageCo2Level
FROM
DeviceData3) PIVOT (
    avg(averageCo2Level) avg FOR month IN (7 AS JUL, 8 AS AUG, 9 AS SEPT, 10 AS OCT, 11 AS NOV)
)
```

## GroupBy and Rollup 
* CUBE is also an operator used with the GROUP BY clause. Similar to ROLLUP, you can use CUBE to generate summary values for sub-elements grouped by column value. CUBE is different than ROLLUP in that it will also generate subtotals for all combinations of grouping columns specified in the GROUP BY clause.
```sql
SELECT 
  COALESCE(dc_id, "All data centers") AS dc_id,
  COALESCE(device_type, "All devices") AS device_type,
  ROUND(AVG(averageCo2Level))  AS avgCo2Level
FROM Co2LevelsTemporary
GROUP BY Rollup (dc_id, device_type)
ORDER BY dc_id, device_type;

SELECT 
  COALESCE(dc_id, "All data centers") AS dc_id,
  COALESCE(device_type, "All devices") AS device_type,
  ROUND(AVG(averageCo2Level))  AS avgCo2Level
FROM Co2LevelsTemporary
GROUP BY CUBE (dc_id, device_type)
ORDER BY dc_id, device_type;
```

## Partition SQL

```sql
CREATE TABLE IF NOT EXISTS AvgTemps
PARTITIONED BY (device_type)
AS
  SELECT
    dc_id,
    date,
    temps,
    REDUCE(temps, 0, (t, acc) -> t + acc, acc ->(acc div size(temps))) as avg_daily_temp_c,
    device_type
  FROM DeviceData;
```

## Show partitions of a table

1. SHOW PARTITIONS AvgTemps

## DataBricks Widget

1. 

## How to connect to Databricks Spark

1. jdbc:spark://community.cloud.databricks.com:443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/1025635937589762/0408-004348-gsh3b7rb;AuthMech=3;UID=token;PWD=<personal-access-token>


## Data Lakes vs Data Warehouses

1. DL - All types of data, structured, un-structured, semi-structured
2. DW - Only structured data

## Data Lakehouse
1. Lakehouses are enabled by a new system design: implementing similar data structures and data management features to those in a data warehouse directly on top of low cost cloud storage in open formats.

## Delta Lake
1. Delta Lake helps to build Data Lakehouse
2. Supports ACID/Streaming/Governance/De-couple-compute-and-data
3. Delta Lake follows medallion architecture
   1. Bronze - Raw table - not easily queryable
   2. Silver
      1. Cleaned table
      2. Acts as source for multiple business aggregate Gold level tables
   3. Gold
4. Powered by Delta Engine

## What is Delta table
1. Delta files (in object storage)
2. Delta Transaction log
3. Delta table registered in the Metastore

## How to create Delta Table

```sql
CREATE OR REPLACE TABLE health_tracker_silver
USING DELTA
PARTITIONED BY (p_device_id)
LOCATION "/health_tracker/silver" AS (
    SELECT
        value.name,
        value.heartrate,
        CAST(FROM_UNIXTIME(value.time) AS timestamp) AS time,
        CAST(FROM_UNIXTIME(value.time) AS DATE) AS dte,
        value.device_id p_device_id
    FROM
        health_tracker_data_2020_01
)
```

## How to describe table with timestamp (when was created)
1. DESCRIBE DETAIL health_tracker_silver

## How to append data to existing table

```sql
INSERT INTO
  health_tracker_silver
SELECT
  value.name,
  value.heartrate,
  CAST(FROM_UNIXTIME(value.time) AS timestamp) AS time,
  CAST(FROM_UNIXTIME(value.time) AS DATE) AS dte,
  value.device_id p_device_id
FROM
  health_tracker_data_2020_02
```

## Create Table for late arriving data

```
CREATE OR REPLACE TABLE health_tracker_late
USING JSON
OPTIONS (path "dbfs:/mnt/training/healthcare/tracker/raw-late.json")
```

## How to find all the version of the delta table
* describe history health_tracker_silver

## How to time travel delta table

1. SELECT COUNT(*) FROM health_tracker_silver VERSION AS OF 0
2. SELECT COUNT(*) FROM health_tracker_silver VERSION AS OF 1

## What is Late-arriving data
1. The absence of records from the last few days of the month shows a phenomenon that may often occur in a production data pipeline: late-arriving data.

## How setup graph

To set up your graph:

    Click Plot Options
    Drag dte into the Keys dialog
    Drag p_device_idinto the Series Groupings dialog
    Drag heartrate into the values dialog
    Choose COUNT as your Aggregation type (the dropdown in the lower left corner)
    Select "Bar Chart" as your display type.

## How to use prev + succ data as missing data

```sql
CREATE OR REPLACE TEMPORARY VIEW updates 
AS (
  SELECT name, (prev_amt+next_amt)/2 AS heartrate, time, dte, p_device_id
  FROM (
    SELECT *, 
    LAG(heartrate) OVER (PARTITION BY p_device_id, dte ORDER BY p_device_id, dte) AS prev_amt, 
    LEAD(heartrate) OVER (PARTITION BY p_device_id, dte ORDER BY p_device_id, dte) AS next_amt 
    FROM health_tracker_silver
  ) 
  WHERE heartrate < 0
)
```

## Create Upsers View


## What is upsert
1. The word "upsert" is a portmanteau of the words "update" and "insert,"
2. This is what it does. 
   1. An upsert will update records where some criteria are met 
   2. Otherwise will insert the record.
   3.
```sql
CREATE OR REPLACE TEMPORARY VIEW upserts
AS (
    SELECT * FROM updates 
    UNION ALL 
    SELECT * FROM inserts
    )
```
4. 
```sql
MERGE INTO health_tracker_silver                            -- the MERGE instruction is used to perform the upsert
USING upserts
   ON health_tracker_silver.time = upserts.time AND        
   health_tracker_silver.p_device_id = upserts.p_device_id  -- ON is used to describe the MERGE condition

WHEN MATCHED THEN                                           -- WHEN MATCHED describes the update behavior
   UPDATE SET
   health_tracker_silver.heartrate = upserts.heartrate   
WHEN NOT MATCHED THEN                                       -- WHEN NOT MATCHED describes the insert behavior
   INSERT (name, heartrate, time, dte, p_device_id)              
   VALUES (name, heartrate, time, dte, p_device_id)
```

## What is ZOrder optimization

1. If your organization continuously writes data to a Delta table, it will over time accumulate a large number of files, especially if you add data in small batches. 
2. For analysts, a common complaint in querying data lakes is read efficiency; and having a large collection of small files to sift through everytime data is queried can create performance problems. 
3. Ideally, a large number of small files should be rewritten into a smaller number of larger files on a regular basis, which will improve the speed of read queries from a table. This is known as compaction. 
4. You can compact a table using the OPTIMIZE command shown below.
5. Z-ordering co-locates column information (recall that Delta is columnar storage). 
6. Co-locality is used by Delta Lake data-skipping algorithms to dramatically reduce the amount of data that needs to be read. You can specify multiple columns for ZORDER BY as a comma-separated list. 
7. However, the effectiveness of the locality drops with each additional column. Read more about optimizing Delta tables here.
8. OPTIMIZE flights ZORDER BY (DayofWeek);


## How to optimize

1. OPTIMIZE table ZORDER BY (Col1, column2, column3);
2. OPTIMIZE flights ZORDER BY (DayofWeek);


## Delta Cache

1. Using the Delta cache is an excellent way to optimize performance. 
2. Note: The Delta cache is not the same as caching in Apache Spark. 
3. One notable difference is that the Delta cache is stored entirely on the local disk, so that memory is not taken away from other operations within Spark. 
4. When enabled, the Delta cache automatically creates a copy of a remote file in local storage so that successive reads are significantly sped up. 
5. Unfortunately, to enable it, you must choose a cluster type that is not available in Databricks Community Edition.

## Reference

1. [apache-spark-sql-for-data-analysts](https://www.coursera.org/learn/apache-spark-sql-for-data-analysts/lecture/JYiZW/what-is-nested-data)
2. [Community Cluster](https://community.cloud.databricks.com/login.html)
3. [Array Notebook](https://docs.databricks.com/_extras/notebooks/source/apache-spark-functions.html)
4. [Data Lakehouse](https://www.databricks.com/blog/2020/01/30/what-is-a-data-lakehouse.html)
5. [FAQ DL](https://www.databricks.com/blog/2021/08/30/frequently-asked-questions-about-the-data-lakehouse.html)
6. [Kafka and Delta workshop](https://github.com/jaceklaskowski)