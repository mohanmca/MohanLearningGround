## Parquet File
1. [Record striping and assembly algorithms from the Dremel paper](https://github.com/julienledem/redelm/wiki/The-striping-and-assembly-algorithms-from-the-Dremel-paper)
2. [very compact representation of nesting that can be efficiently encoded using a combination of Run Length Encoding and bit packing.](https://blog.x.com/engineering/en_us/a/2013/dremel-made-simple-with-parquet)
3. [Apache Parquet: Parquet file internals and inspecting Parquet file structure](https://www.youtube.com/watch?v=rVC9F1y38oU)

4. ## Who uses Parquet?
1. Apache Spark
2. Apache Hive
3. Presto (AWS - Athena is Presto based)
4. 

## Open-questions
1. Would Parquest support 128-int?
2. How to adjust size of file using block-size and row-group?
3. 

## Tidbits
1. Borrowed from Dremel for nested columns
2. Dictionary encoding can be used inside Paquet
3. Slow-moving data naturally fits for Parquet, 2020 product and 2021 prodct are different and moves slowly, product used in 2012 many may be available in 2024
4. We can use different encoding based on timeline itself due to slow moving data

## Performance of Parquet?
1. Depends on underly performance of Block size. (Hadoop S3), Parquet block-size should be multiples of underlying hadoop/S3 block-size
2. When storing data in S3 it is important to consider the size of files you store in S3. Parquet files have an ideal file size of 128 MB - 1 GB
   
## Parquet sub-compoennts
1. Blocksize
2. Row-group
   3. How many columns per row-group
   4. There are multiple row-groups
   5. Row-groups can be configured to control the size of the parquet file
5. Page
   6. It is inside a row-group, where actual data stored
   7. It is based on Thrift
   8. Repetition Level and Defintion Level

## How to analyze Parquet file
1. parquet-tools
   2. parquet-tools schema hdfs://quickstar-hadoop/usr/data/parquet-file.gz.parquet
   3. parquet-tools head hdfs://quickstar-hadoop/usr/data/parquet-file.gz.parquet
   4. parquet-tools meta hdfs://quickstar-hadoop/usr/data/parquet-file.gz.parquet --we can view the compression ration, and encoding details
   5. parquet-tools dump hdfs://quickstar-hadoop/usr/data/parquet-file.gz.parquet --we can view the compression ration, and encoding details
3. duckdb
