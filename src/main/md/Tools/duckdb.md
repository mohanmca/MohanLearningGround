## Query parquet file from S3
1. set up the AWS CLI properties
2. DESCRIBE TABLE_EVENTS;
3. aws s3 sync s3://your-bucket-name/path/to/files /mnt/ramdisk/files
4. SET temp_directory='/Users/mohan.narayanaswamy/duckdb'; 
5.
   ```sql
         SELECT * FROM parquet_scan('s3://bucket/data_type/time_series/**/*.parquet') where year=2025;
   ```
4.
   ```bash
       CREATE TEMPORARY TABLE cached_result AS SELECT * FROM parquet_scan('/path/to/files/**/*.parquet');
   ```

## Often used SQL
1. SELECT column_name FROM information_schema.columns WHERE table_name = 'TRANSFER_EVENTS';
2. select to_timestamp(timestamp/1000000000) from TRANSFER_EVENTS; (Convert epoch in nanos to timestamp)
