## What is the main component of the Spark architecture?
* The main component of the Spark architecture is the Spark Core, which provides basic functionality for distributed task scheduling, memory management, and fault recovery.

## What is the role of the Spark Driver in the Spark architecture?
* The Spark Driver is responsible for coordinating the execution of tasks in a Spark application and communicating with the cluster manager to allocate resources.

## What is a Spark Executor?
* A Spark Executor is a worker node in the Spark cluster that is responsible for executing tasks assigned by the Spark Driver.

## What is the role of the Cluster Manager in the Spark architecture?
* The Cluster Manager is responsible for managing resources and scheduling tasks across the nodes in the Spark cluster.

## What is the difference between a Spark Driver and a Spark Executor?
* The Spark Driver is responsible for coordinating the execution of tasks in a Spark application, while a Spark Executor is responsible for executing tasks assigned by the Spark Driver.

## What is the Spark DAG (Directed Acyclic Graph) in the Spark architecture?
* The Spark DAG is a data structure that represents the sequence of stages and tasks that are executed in a Spark application.

## What is a Spark Task in the Spark architecture?
* A Spark Task is a unit of work that is executed by a Spark Executor, such as a map or reduce operation.

## What is the role of the Block Manager in the Spark architecture?
* The Block Manager is responsible for managing the storage of data blocks in the memory and disk of the Spark Executors.

## What is the role of the Spark Shuffle in the Spark architecture?
* The Spark Shuffle is a mechanism that is used to redistribute data across the nodes in the Spark cluster during a reduce operation.

## What is a Spark Stage in the Spark architecture?
* A Spark Stage is a collection of tasks that can be executed together in a single batch, such as a set of map operations followed by a reduce operation.

##  What is the purpose of the following Python code?

```python
from pyspark.sql import SparkSession

spark = SparkSession.builder.appName("example").getOrCreate()
data = [("Alice", 25), ("Bob", 30), ("Charlie", 35)]
columns = ["Name", "Age"]
df = spark.createDataFrame(data, columns)
df.show()
```


Back:
The purpose of the code is to create a Spark DataFrame from a list of tuples, where each tuple represents a row of data, and then display the contents of the DataFrame using the show() method.

##  What is the purpose of the following Python code?
```python
from pyspark.sql.functions import col

df.filter(col("age") > 25).show()
```
The purpose of the code is to filter a Spark DataFrame to only include rows where the age column is greater than 25, and then display the filtered DataFrame using the show() method.

##  What is the purpose of the following Python code?

```python
from pyspark.sql.functions import sum

df.groupBy("department").agg(sum("salary")).show()
```

Back:
The purpose of the code is to group a Spark DataFrame by the department column, calculate the sum of the salary column for each group, and then display the results using the show() method.

##  What is the purpose of the following Python code?

```python
from pyspark.sql.functions import udf

def square(x):
return x * x

square_udf = udf(square)
df.withColumn("squared_age", square_udf(col("age"))).show()
```
The purpose of the code is to define a user-defined function (UDF) that squares a number, apply the UDF to the age column of a Spark DataFrame using the withColumn() method, and then display the modified DataFrame using the show() method.

##  What is the purpose of the following Python code?

```python
from pyspark.sql.functions import lit

df.withColumn("new_column", lit(0)).show()
```

Back:
The purpose of the code is to add a new column called new_column to a Spark DataFrame, where all the values in the column are set to 0, and then display the modified DataFrame using the show() method.

##  What is the purpose of the following Python code?

```python
df.write.mode("overwrite").parquet("path/to/parquet")
```
The purpose of the code is to write the contents of a Spark DataFrame to a Parquet file format, where the file will be overwritten if it already exists.

##  What is the purpose of the following Python code?

```python
from pyspark.sql.functions import col

df.select(col("name").alias("employee_name"), col("salary") * 2).show()
```

Back:
The purpose of the code is to select two columns from a Spark DataFrame, where the first column is renamed to employee_name, and the second column is multiplied by 2, and then display the results using the show() method


##  at is the purpose of the following Python code?

```python
from pyspark.sql.functions import when

df.withColumn("discounted_salary", when(col("age") < 30, col("salary") * 0.9).otherwise(col("salary"))).show()

```

Back:
The purpose of the code is to add a new column called discounted_salary to a Spark DataFrame, where the salary values are reduced by 10% for employees under the age of 30, and the original salary values are retained for all other employees. The results are then displayed using the show() method.

##  What is the purpose of the following Python code?

```python
from pyspark.sql.functions import desc

df.orderBy(desc("salary")).show()
```
The purpose of the code is to sort the rows of a Spark DataFrame in descending order based on the salary column, and then display the sorted DataFrame using the show() method.

##  What is the purpose of the following Python code?

```python
from pyspark.sql.functions import explode

df.select(explode(split(col("names"), ",")).alias("name")).show()
```
The purpose of the code is to split a string column called names into multiple rows based on the comma separator, and then display the resulting rows using the show() method.

## What is the purpose of the following Python code?

```python
from pyspark.sql.functions import window
df.groupBy(window(col("timestamp"), "1 hour")).agg(sum("sales")).show()
```

The purpose of the code is to group a Spark DataFrame by hourly windows based on a timestamp column, calculate the sum of the sales column for each window, and then display the results using the show() method.

## What is the purpose of the following Python code?
```python
from pyspark.sql.functions import approx_count_distinct

df.select(approx_count_distinct("name")).show()
```

## How pyspark is executed by Spark JVM engine?

The purpose of the code is to estimate the number of distinct values in a column called name of a Spark DataFrame, and then display the estimated count using the show() method. The approx_count_distinct() function is used for performance reasons, as it provides an approximate count that can be much faster to compute than an exact count.

PySpark is executed by the Spark JVM engine using the Py4J library, which allows Java programs to dynamically access Python objects and methods.

When a PySpark application is submitted to a Spark cluster, the following happens:

    The PySpark driver program is executed as a Java process, which initializes the SparkContext object in the JVM. This object is responsible for coordinating the execution of the PySpark application on the cluster.

    The PySpark driver program creates a Py4J gateway server in the JVM, which allows Python code to be executed in a separate Python process.

    The PySpark driver program sends the Python code to the Python process via the Py4J gateway server, which creates a Python interpreter and executes the code.

    When the Python code calls PySpark functions, such as SparkContext.parallelize() or RDD.map(), Py4J translates the function call into a message that is sent to the JVM process via the Py4J gateway server.

    The Spark JVM engine processes the message and executes the corresponding function on the Spark engine, such as creating a distributed RDD or applying a transformation to an existing RDD.

    When an action is triggered on the final RDD, such as RDD.collect() or RDD.saveAsTextFile(), the Spark JVM engine returns the result to the Python process via the Py4J gateway server.

    The Python process collects the results and sends them back to the PySpark driver program in the JVM via the Py4J gateway server.

Overall, PySpark is executed by the Spark JVM engine using the Py4J library to translate PySpark function calls between the Python process and the JVM process. This allows users to write Spark applications using Python code, which is executed on the Spark engine by the JVM process. The PySpark driver program coordinates the execution of the PySpark application and manages the Py4J gateway server to communicate between the Python process and the JVM process.

## Generate ANKI
* mdanki SparkAnki.md SparkAnki.apkg --deck "Mohan::Spark::DataFrame"
