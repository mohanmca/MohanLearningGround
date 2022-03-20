## Q Language Derived from K
1. Derivative of APL (Array processing language)
2. Read from right to left
3. Sorts a list of strings by their lengths: ```x@>#:'x```
4. A function to determine if a number is prime can be written as: ```{&/x!/:2_!x}```


## KDB+ Timeseries database

1. kdb+, a columnar relational time-series database
   1. Entire database is 500Kb
      1. DB + WebSocket + HttpServer
   2. Supports Q Programming language
   3. It uses Lambda Architecture since 1970s
2. Has tightly integrated query language called q
3. It can do aggregations and consolidations on billions of streaming 
4. Real-time and historical records for complex analytics.

## KDB Architecture
1. Events Engine (Also known as ticker plant)
   1. Persists to log file
2. RDB -  Real time DB (in memory  - memory intensive)
   1. Purges to Historical database (to disk/ssd)
3. Stream Queries
   1. Can be written in Q language
   2. We can have multiple Stream-Query-Engine
   3. Running calculation on the fly (like app-server for KDB)
   4. CPU intensive
4. Data
   1. They are column arrays
   2. Files are memory mapped for performance
5. Query is same for (streaming/in-memory/disk)
   1. 90 different data-types
      1. Boolean
      2. Byte
      3. Integer (short/long/int)
      4. Floating Point (real/float)
      5. Character
      6. GUID
      7. Enumeration
      8. Dictionaries
      9. (Keyed)Tables
      10. Functions
   2. Symbol (Varchar.String)
      1. `AAPL
      2. `MOHAN
   3. Time series data-type
      1. Date
      2. Time
      3. Minute
      4. Second
      5. Month
      6. Datetime
      7. Timespan (ns)
      8. Timestamp (ns)
6. Compression
   1. On-disk
      1. kdb+ algorithm
      2. gzip
      3. Google snappy
      4. lzh4c
   2. Web-socket compression
   3. In-flight compression between hosts
7. Function can be inside queries
8. Queries can be used inside function defintion
   1. Upto 3 implicit parameters x,y and z 
   2. func3:{[param] select from t where a=param}
   3. func3:{select from t where a=x}

## Demo numbers
1. 31 TB of data
2. 1.3 trillion records
3. 8-core with 512GB Ram
4. With HDD of 14K RPM


## Q Programming language
1. Functional programming
2. Comes with 200+ built-in functions
   1. First/Min/Max/Last
   2. Matrix function (matrix inversion/multiplication)
   3. Trignometric
   4. Geometric
   5. MovingAvg, WeightedAvg, MovingSum
3. Array/Vector
4. Query
5. Interpreted
6. Temporal function (time-series)
   1. xbar - uniform buckets of data
   2. Time-series join
      1. Bi-Temporal
      2. aj - as-of-join
      3. wj - window-join
   3. Temporal Arithmetic
7. Attributes - equivalent to index/indices(use sparingly on column) 
   1. `s# - sorted
   2. `p# - parted
   3. `u# - unique
   4. `g# - grouped
8. Parallelism
   1. Native-Map-Reduce
   2. Across machine and multi-threaded
   3. Exploits intel native instruction sets

## Q Language Function
1. ```select date,open,high,low,close from daily where sym=`AAPL```
2. \t - To time the SQL
3. \t = When we time second time, we can find the effect of the cache
4. func:{x+y} (Simple function to add two numbers)
5. ``` select count i from trade where date within (.z.D-7;.z.D).sym=`AAPL ```
6. ```select open:first price,high:max price,low:min price,close:last price by date, 5 xbar time.minute from trade where date within (.z.D-7;.z.D).sym=`AAPL,time within 09:30 16:00```

## As of join
1. aj -is a function name
   1. 3 parameters are required
      1. Field or column that we are joining
   2. ; semi-colon used to separate the arguments
2. Joins based on timestamp
3. It internally uses binary search to look-back matching/prevailing column
4. nbbo - national best bid and offer price
```java
aj[`time; select time,price from trade where date-last date.sym=`AAPL,time within 09:30 16:00;
          select time,bid.ask from nbb where date=last date,sym=`AAPL,time within 09:30 16:00]
```
5. wj - window join (lookup within window)
   1. 1 seconds before event
   2. 2 seconds after event 
   3. wj is generalized version of AJ (as of join)
6. Specific join such as +join, left-join and right joins are supported

## About Kx
1. Irish company founded in 1993 (based on london stock exchange)
2. 2000+ employees
3. Kenneth Iverson - Canadian/IBM/Harvard
   1. APL
   2. Vector Programming Language
   3. Arthur Whitney - founder of Kx, created K/Q
   

## Reference
1. [Q for mortals - book](https://code.kx.com/q4m3/)
2. [KC Academy](https://kx.com/academy/)
3. [Time Series Database Lectures #4 - Fintan Quill (Kdb+)](https://www.youtube.com/watch?v=AiGdfmxEP68)
   1. [Same as above](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=dbb8a888-2448-4edf-bf7f-f8ca0f3dd490)
   2. [Notes from above talks](https://kakhuseyinoglu.wordpress.com/2017/10/27/talk-time-series-analytics-for-streaming-big-fast-data-by-fintan-quill/)
4. [Time Series Database Lectures - Fall 2017](https://db.cs.cmu.edu/seminar2017/)
5. [Notation as a tool of thought](https://dl.acm.org/doi/pdf/10.1145/1283920.1283935)
6. [Kdb from student perspective](https://cs.ulb.ac.be/public/_media/teaching/infoh415/student_projects/2019/kdb.pdf)
7. [Computer science talks](http://halley.exp.sis.pitt.edu/comet/presentColloquium.do?col_id=12511)
8. [Code KX](https://code.kx.com/q/)
9. [KDB Personal Google Groups](https://groups.google.com/g/personal-kdbplus)
10. [Q Tips book](https://www.amazon.com/Tips-Fast-Scalable-Maintainable-Kdb/dp/9881389909)
11. [Fintan Quill Engineer, Kx Systems]