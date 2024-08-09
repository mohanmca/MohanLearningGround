## Parquet File
1. [Record striping and assembly algorithms from the Dremel paper](https://github.com/julienledem/redelm/wiki/The-striping-and-assembly-algorithms-from-the-Dremel-paper)
2. [very compact representation of nesting that can be efficiently encoded using a combination of Run Length Encoding and bit packing.](https://blog.x.com/engineering/en_us/a/2013/dremel-made-simple-with-parquet)
3. [Apache Parquet: Parquet file internals and inspecting Parquet file structure](https://www.youtube.com/watch?v=rVC9F1y38oU)

4. ## Who uses Parquet?
5. 1. Apache Spark
   2. Apache Hive
  
   ## Performance of Parquet?
   1. Depends on underly performance of Block size. (Hadoop S3)
   2. When storing data in S3 it is important to consider the size of files you store in S3. Parquet files have an ideal file size of 128 MB - 1 GB
