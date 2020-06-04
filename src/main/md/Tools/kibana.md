## Kibana

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