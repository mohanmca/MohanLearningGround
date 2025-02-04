## How to create temp table in Spark backed by S3 parquet data

```sql
-- Load the data into a temporary table
CREATE OR REPLACE TEMPORARY VIEW account_balances_ts AS
SELECT * FROM parquet.`s3://somebucket/data/year=2025/month=1/`
UNION ALL
SELECT * FROM parquet.`s3://somebucket/data/year=2025/month=1/`;
```

## How to create table based on other SQL
```sql
CREATE TABLE account_ts_view as  select * from account_ts_view_temp where assetId=1 order by date asc limit 100;
```
