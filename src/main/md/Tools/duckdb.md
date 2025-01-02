## Query parquet file from S3
1. set up the AWS CLI properties
2.
   ```sql
  SELECT * FROM parquet_scan('s3://bucket/data_type/time_series/**/*.parquet') where year=2025;
```
