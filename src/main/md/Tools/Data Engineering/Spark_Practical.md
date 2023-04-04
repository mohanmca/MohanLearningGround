## How to run unit test in Pipeline

1. pip install -r test_requirements.txt --trusted-host https://known_trusted.registry/ -i  https://known_trusted.registry/api/pypi/pypi/simple
2. python -m nose --verbosity-2 --with-coverage --cover-xml --cover-erase --cover-package=stream --cover-xml-file=coverage.xml --with-xunit -xunit-file=xunittest.xml --all-modules


## How to invoke Spark without Client Library

1. Use Apache - Livy interface
2. It supports RESTful interface

## Apache Hive and Spark Data-Type has to Match

## How to troubleshoot pip installation failure

1. Check the contents of
   1. /etc/pip.conf
   2. ~/pip.conf
2. 


## How to operate if a column exists

```python
df = # spark Dataframe
if `column_name` in df.columns:
    <do something
```

## How to submit Spark jobs

```bash
https://spark.apache.org/docs/latest/submitting-applications.html

./bin/spark-submit \
  --class org.apache.spark.examples.SparkPi \
  --properties-file=/prod/data/spark-prp.conf \
  --master k8s://xx.yy.zz.ww:443 \
  --deploy-mode cluster \
  --client-driver-memory 10G \
  --executor-memory 20G \
  --num-executors 50 \
  http://path/to/examples.jar \
  1000
```

## What is the role of STS

1. Spark Thrift Server or STS. 
2. The STS allows JDBC/ODBC clients to execute SQL queries over JDBC and ODBC protocols on Apache Spark.
3. ./sbin/start-thriftserver.sh