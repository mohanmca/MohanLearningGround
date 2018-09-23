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