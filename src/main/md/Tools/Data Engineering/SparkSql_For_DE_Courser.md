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

## Reference

1. [apache-spark-sql-for-data-analysts](https://www.coursera.org/learn/apache-spark-sql-for-data-analysts/lecture/JYiZW/what-is-nested-data)
2. [Community Cluster](https://community.cloud.databricks.com/login.html)
3. [Array Notebook](https://docs.databricks.com/_extras/notebooks/source/apache-spark-functions.html)
4. [Data Lakehouse](https://www.databricks.com/blog/2020/01/30/what-is-a-data-lakehouse.html)
5. [FAQ DL](https://www.databricks.com/blog/2021/08/30/frequently-asked-questions-about-the-data-lakehouse.html)