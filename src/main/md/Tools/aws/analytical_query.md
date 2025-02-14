## [What powers AWS Athena](https://news.ycombinator.com/item?id=33074120)
1. Athena is Trino under the hood
2. Athena was Apache Presto under the hood in the past
3. By storing your data on S3 in an open format, like Apache Parquet or Delta Lake,
    1. you can just use a different engine on it without needing to export / import it.
    2. In addition to Spark, Presto & Trino are popular engines to use when querying a data lake.
4.   

