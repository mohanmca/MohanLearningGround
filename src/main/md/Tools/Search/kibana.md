## Kibana

* Version - 5.6.5 and V-6.7.0
* Learning material can be find in - kibana 5 site:https://www.elastic.co/videos/

## Two types of data set

* Static data set
* Timeseries data set


## Time series

* index name would be changing everyday or hour
  * Sample index name: tweet-2010-03-21
  * Index pattern is important to work with kibana while working with timeseries data


## Query language

* KQL (Kibana Query Language)
* The Lucene query
* The query DSL (Domain Specific Language)

```sql
--John in US
"john us"
"john and us"
"user:john country:us"
"user:john and country:us"
"user:john and country:us and age:>40"
'user:john and country:us and not user.lastname:"smith"'
geo.country : France and (occupation : "Software Engineer" or occupation : "Sales" )
geo.country : France and (occupation : "Software Engineer" or occupation : "Sales" ) and geo.city : Lyon

```


## Useful Kibana Queries


```sql
statck_trace: null
_exists_: stack_trace
mod_date:[20020101 TO 20030101]
level: ERROR
@timestamp: ["2020-10-29T00:50:13" TO "2020-10-29T20:50:13"]
module_name is one of ["a", "b"]
```