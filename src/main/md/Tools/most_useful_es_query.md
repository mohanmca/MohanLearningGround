# Most often used URL
* *ElasticSearch Get can accept body* 
* /_search
* /_cluster/health
* /gb/_mapping/tweet  - mapping for type tweet that is under index -gb
* GET /gb/tweet/_validate/query query
* GET /gb/tweet/_validate/query?explain 
* GET /_search?explain or /_search?explain?format=yaml
* GET /_search?routing=user_1,user2
* GET /_search?search_type=count  
* GET /old_index/_search?search_type=scan&scroll=1m
* DELETE /index_one,index_two or DELETE /index_* or DELETE /_all
* PUT /my_index_v1/_alias/my_index and/or GET /*/_alias/my_index
* 
* To count the number of documents in the cluster
 * ```{"query": { "match_all": {} }}```
* To count the number of documents in the cluster
 * ```{  "query" : {  "match" : {  "last_name" : "Smith"  }  }}```
* To find all employees with a last name of Smith, but we want only employees who are older than 30
 * ```{  "query" : {  "filtered" : {  "filter" : {  "range" : {  "age" : { "gt" : 30 } 1  }  },  "query" : {  "match" : {  "last_name" : "smith" 2  }  }  }  }}```
* To Full-Text Search -  search for all employees who enjoy rock climbing - relevance
 * ```{    "query" : {  "match" : { "about" : "rock climbing" }}}``
* To Full-Text Search -  search for all employees who enjoy rock climbing - mandatory
 * ```{    "query" : {"match_phrase" : {"about" : "rock climbing"}}}```
*  Highlight - produces html <em> around matched words
```{   "query" : {     "match_phrase" : {     "about" : "rock climbing"     }   },   "highlight": {     "fields" : {     "about" : {}     }   } }```
* Sample aggregation query
```{   "aggs": {     "all_interests": {       "terms": { "field": "interests" }     }   } }```
* let’s find the average age of employees who share a particular interest:
```{"aggs" : {        "all_interests" : {            "terms" : { "field" : "interests" },           "aggs" : {                "avg_age" : {                    "avg" : { "field" : "age" }                }            }        }    }}```
* To update mappings
  * ```PUT /gb `{"mappings":{"tweet":{"properties":{"tweet":{"type":"string","analyzer":"english"},"date":{"type":"date"},"name":{"type":"string"},"user_id":{"type":"long"}}}}}```
* To highlight data
  * ```{"query":{"match_phrase":{"about":"rock climbing"}},"highlight":{"fields":{"about":{}}}}```

* Create an index with 3 shards
  * PUT /blogs - ```{   "settings" : {      "number_of_shards" : 3,      "number_of_replicas" : 1   }}``` 
* ```{"bool":{"must":{"match":{"title":"how to make millions"}},"must_not":{"match":{"tag":"spam"}},"should":[{"match":{"tag":"starred"}},{"range":{"date":{"gte":"2014-01-01"}}}]}}```
  * The above query finds documents whose title field matches the query string how to make millions and that are not marked as spam. If any documents are starred or are from 2014 onward,
  
* To update number of replicas
  * PUT /blogs/_settings - ```{   "number_of_replicas" : 2}```
* PUT /{index}/{type}/{id}
* To create new document and fail if existing one already available - PUT /website/blog/123/_create or PUT /website/blog/123?op_type=create
* PUT /website/blog/1?version=1 [We want this update to succeed only if the current _version of this document in our index is version 1.][To update first version]
* Retrieve only selected fields - GET /website/blog/123?_source=title,text
* To check if document exists without retrieving content - curl -i -XHEAD http://localhost:9200/website/blog/123 (HTTP-200 or HTTP-404)
* DELETE /website/blog/123 (HTTP-200 or HTTP-404)
* PUT /website/blog/2?version=5&version_type=external

# Search URL
* /_search - Search all types in all indices
* /gb/_search - Search all types in the gb index
* /gb,us/_search - Search all types in the gb and us indices
* /g*,u*/_search - Search all types in any indices beginning with g or beginning with u
* /gb/user/_search - Search type user in the gb index
* /gb,us/user,tweet/_search - Search types user and tweet in the gb and us indices
* /_all/user,tweet/_search - Search types user and tweet in all indices
* GET /_search?size=5
* GET /_search?size=5&from=10
* ?format=yaml
* ?preference=prefrences, _primary, _primary_first, _local, _only_node:xyz, _prefer_node:xyz
* _id=hash_uuid and _uid = concatenare(_type, _id) = type#id
* 

# Query DSL
* Query DSL = Query DSL + Filter DSL.
* ```{"query":{"match_all":{}}}```
* ```{"QUERY_NAME":{"ARGUMENT":"VALUE","ARGUMENT2":"VALUE"}}```
* ```{"QUERY_NAME": { "FIELD_NAME" : {"ARGUMENT":"VALUE","ARGUMENT2":"VALUE"}}}```
* Leaf clauses (like the match clause) that are used to compare a field (or fields) to a query string.
* Compound clauses that are used to combine other query clauses. For instance, a bool clause allows you to combine other clauses that either must match, must_not match, or should match if possible:
  * Compound query
   ```{"bool":{"must":{"match":{"email":"business opportunity"}},"should":[{"match":{"starred":true}},{"bool":{"must":{"folder":"inbox"},"must_not":{"spam":true}}}],"minimum_should_match":1}}```
* A filter asks a yes|no question of every document and is used for fields that contain exact values:
* A query is similar to a filter, but also asks the question: How well does this document match? - how relevant each document is to the query
* Queries have to not only find matching documents, but also calculate how relevant each document is, which typically makes queries heavier than filters. Also, query results are not cachable.
* Filter results are cacheable
* The goal of filters is to reduce the number of documents that have to be examined by the query.
* As a general rule, use query clauses for full-text search or for any condition that should affect the relevance score, and use filter clauses for everything else.
* Always analyze production queries
  * GET /gb/tweet/_validate/query query
  * GET /gb/tweet/_validate/query?explain 1
* Query debug for a document
   ```
	GET /us/tweet/12/_explain
	{
	   "query" : {
	      "filtered" : {
	         "filter" : { "term" :  { "user_id" : 2           }},
	         "query" :  { "match" : { "tweet" :   "honeymoon" }}
	      }
	   }
	}
```   


# Filter DSL
* Term Filter - The term filter is used to filter by exact values, be they numbers, dates, Booleans, or not_analyzed exact-value string fields:
* Range filter
* For exact-value searches, you probably want to use a filter instead of a query, as a filter will be cached.
* You can use filter alone without query dsl (default query dsl for filter would be assuemed  ```{ "query":  { "match_all": {}} }```
* 

# Query DSL
* must  - And
* must_not - Not
* should - or
* If there are no must clauses, at least one should clause has to match. However, if there is at least one must clause, no should clauses are required to match.
* The multi_match query allows to run the same match query on multiple fields
  * ```{"multi_match":{"query":"full text search","fields":["title","body"]}}```
* The bool query, like the bool filter, is used to combine multiple query clauses. However, there are some differences. 
  * While filters give binary yes/no answers, queries calculate a relevance score instead. 
  * The bool query combines the _score from each must or should clause that matches.
  
* Example of fitlered query 
  * ```{"filtered":{"query":{"match":{"email":"business opportunity"}},"filter":{"term":{"folder":"inbox"}}}}``` 
 * ```{"sort": { "date": { "order": "desc" }}}```   

# Search tips
* +means that the word must be present.
* Default sort order is _score descending.
* _score can be quite expensive, and usually its only purpose is for sorting, can be forced =  track_scores parameter to true.
* Multiple sorts - ```{"sort":[{"date":{"order":"desc"}},{"_score":{"order":"desc"}}]}```

# Analyzer
* GET /_analyze?analyzer=standard

# Query String Query
* GET /_search?size=5&from=10
* Name has john and tweet has mary ---> +name:john +tweet:mary ---> GET /_search?q=%2Bname%3Ajohn+%2Btweet%3Amary
* mary in any field (_all field) ---> +name:john +tweet:mary ---> GET /_search?q=mary
* The _all field contains either of the words aggregations or geo ---> +name:(mary john) +date:>2014-09-10 ---> (aggregations geo) ---> ?q=%2Bname%3A(mary+john)+%2Bdate%3A%3E2014-09-10+%2B(aggregations+geo)
* GET /_search?sort=date:desc&sort=_score&q=search

Search types user and tweet in all indices

* To update 
	```POST /website/blog/1/_update
	{
	   "doc" : {
	      "tags" : [ "testing" ],
	      "views": 0
	   }
	}```
* Scripting	
```
POST /website/blog/1/_update
{
   "script" : "ctx._source.views+=1"
}```

* Below would produce "tags":  ["testing", "search"] inside json document
```
POST /website/blog/1/_update
{
   "script" : "ctx._source.tags+=new_tag",
   "params" : {
      "new_tag" : "search"
   }
}
```
```
POST /website/blog/1/_update
{
   "script" : "ctx.op = ctx._source.views == count ? 'delete' : 'none'",
    "params" : {
        "count": 1
    }
}

POST /website/pageviews/1/_update
{
   "script" : "ctx._source.views+=1",
   "upsert": {
       "views": 1
   }
}
//where the order of increments does not matter, below could be used
POST /website/pageviews/1/_update?retry_on_conflict=5 1
{
   "script" : "ctx._source.views+=1",
   "upsert": {
       "views": 0
   }
}
```

* Retrieving Multiple Documents
```
GET /_mget
{
   "docs" : [
      {
         "_index" : "website",
         "_type" :  "blog",
         "_id" :    2
      },
      {
         "_index" : "website",
         "_type" :  "pageviews",
         "_id" :    1,
         "_source": "views"
      }
   ]
}


GET /website/blog/_mget
{
   "ids" : [ "2", "1" ]
}
```

* ElasticSearch - Bulk - bulk should have newline even for last line
```
{ action: { metadata }}\n
{ request body        }\n
{ action: { metadata }}\n
{ request body        }\n
```
```
POST /_bulk
{ "delete": { "_index": "website", "_type": "blog", "_id": "123" }} 
{ "create": { "_index": "website", "_type": "blog", "_id": "123" }}
{ "title":    "My first blog post" }
{ "index":  { "_index": "website", "_type": "blog" }}
{ "title":    "My second blog post" }
{ "update": { "_index": "website", "_type": "blog", "_id": "123", "_retry_on_conflict" : 3} }
{ "doc" : {"title" : "My updated blog post"} }
 
```
* Multi value use mode (min, max, avg) for sorting
```
{"sort":{"dates":{"order":"asc","mode":"min"}}}
```
```
PUT /my_temp_index/_settings
{
    "number_of_replicas": 1
}
PUT /my_temp_index
{
    "settings": {
        "number_of_shards" :   1,
        "number_of_replicas" : 0
    }
}
```

```
PUT /my_index
{
    "mappings": {
        "my_type": {
            "_id": {
                "path": "doc_id" 1
            },
            "properties": {
                "doc_id": {
                    "type":   "string",
                    "index":  "not_analyzed"
                }
            }
        }
    }
}
```
```
POST /_aliases
{
    "actions": [
        { "remove": { "index": "my_index_v1", "alias": "my_index" }},
        { "add":    { "index": "my_index_v2", "alias": "my_index" }}
    ]
}
```