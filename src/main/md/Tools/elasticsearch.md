# ElasticSearch continious Query
* https://qbox.io/blog/elasticsesarch-percolator
* https://stackoverflow.com/questions/21536599/what-does-percolator-mean-do-in-elasticsearch
* [Robert Muir](https://www.elastic.co/cn/blog/author/robert-muir)
* https://sematext.com/blog/top-10-elasticsearch-mistakes/
* https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html
* *ElasticSearch Get can accept body* 

# Getting Started Elastic

```bash
	export JAVA_HOME=/C/Apps/Java/jdk1.8.0_181/
	curl -L -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.1.zip
	unzip elasticsearch-6.4.1.zip
	cd  elasticsearch-6.4.1
	bin/elasticsearch-plugin.bat  install x-pack (by default x-pack is available)
	wget -L http://127.0.0.1:9200/
	curl -XGET http://localhost:9200/_xpack
	curl -XGET http://localhost:9200/_cluster/health
	curl -XPOST 'http://localhost:9200/_shutdown'
```

# X-Pack
```
http://localhost:9200/_xpack
http://localhost:9200/_xpack?categories=build,features
```

# Getting Started Kibana

```bash
	export JAVA_HOME=/C/Apps/Java/jdk1.8.0_181/
	curl -L -O https://artifacts.elastic.co/downloads/kibana/kibana-6.4.1-windows-x86_64.zip
	unzip kibana-6.4.1-windows-x86_64.zip
	cd  kibana-6.4.1-windows-x86_64
	wget -L http://localhost:5601/
```

# Features
* ElasticSearch would return result even if partially matches unlike RDBMS - Relevance
* Two client Java API
  * Node client - The node client joins a local cluster as a non data node. In other words, it doesn’t hold any data itself, but it knows what data lives on which node in the cluster, and can forward requests directly to the correct node.
  * Transport client - The lighter-weight transport client can be used to send requests to a remote cluster. It doesn’t join the cluster itself, but simply forwards requests to a node in the cluster.

# Theory
* In Elasticsearch, all data in every field is indexed by default. ES can use all of those inverted indices in the same query, to return results at breathtaking speed.
* A node is a running instance of Elasticsearch
* Documents can have their own _id or let Elasticsearch generate one for them.
* While a cluster consists of one or more nodes with the same cluster.name that are working together to share their data and workload.
* One node in the cluster is elected to be the master node, which is in charge of managing cluster-wide changes like creating or deleting an index, or adding or removing a node from the cluster. 
  * The master node does not need to be involved in document-level changes or searches
* An index is just a logical namespace that points to one or more physical shards.
* A shard is a low-level worker unit that holds just a slice of all the data in the index. 
* As your cluster grows or shrinks, Elasticsearch will automatically migrate shards between nodes so that the cluster remains balanced
* Multiple nodes can share the same directory.
* As long as the second node has the same cluster.name as the first node (see the ./config/elasticsearch.yml file), it should automatically discover and join the cluster run by the first node.  
* The more copies of data that you have, the more search throughput you can handle
* green - All primary and replica shards are active.,  yellow - All primary shards are active, but not all replica shards are active., red - Not all primary shards are active.
* When document deleted the _version number has been incremented. This is part of the internal bookkeeping, which ensures that changes are applied in the correct order across multiple nodes.
* ES uses - Optimistic Concurrency Control - Elasticsearch needs a way of ensuring that an older version of a document never overwrites a newer version.
* ES update = retrieve-change-reindex process (within the shard)
* ES Scripts - Scripts can be passed in as part of the request, retrieved from the special .scripts index, or loaded from disk.
* shard = hash(routing) % number_of_primary_shards
* There is no special mapping required for arrays. Any field can contain zero, one, or more values, in the same way as a full-text field is analyzed to produce multiple terms.
  * all the values of an array must be of the same datatype - Elasticsearch will use the datatype of the first value in the array to determine the type of the new field.
* Empty values
  * "null_value":               null, "empty_array":              [] and "array_with_null_value":    [ null ]
* Lucene doesn’t understand inner objects. A Lucene document consists of a flat list of key-value pairs. In order for Elasticsearch to index inner objects usefully, it converts our document into something like (flattened object)
  * {"tweet":["elasticsearch","flexible","very"],"user.id":["@johnsmith"],"user.gender":["male"],"user.age":[26],"user.name.full":["john","smith"],"user.name.first":["john"],"user.name.last":["smith"]}
*  To distinguish between two fields that have the same name, we can use the full path (for example, user.name.first) or even the type name plus the path (tweet.user.name.first).
* ```{"followers":[{"age":35,"name":"Mary White"},{"age":26,"name":"Alex Jones"},{"age":19,"name":"Lisa Smith"}]}``` would be convereted to ```{"followers.age":[19,26,35],"followers.name":["alex","jones","lisa","smith","mary","white"]}```
  * This is sufficient for us to ask, “Is there a follower who is 26 years old?”
  * We can’t get an accurate answer to this: “Is there a follower who is 26 years old and who is called Alex Jones?”

# Query Theory
* You can find only terms that exist in your index, so both the indexed text and the query string must be normalized into the same form.
* Process of tokenization and normalization is called analysis
  * First, tokenizing a block of text into individual terms suitable for use in an inverted index,
  * Then normalizing these terms into a standard form to improve their “searchability,” or recall
* Standard analyzer
  * Character filters, Tokenizer and Tokenizer filter (a, and the)
* When we query an exact-value field, the query will not analyze the query string, but instead search for the exact value that you have specified.
* String: string, Whole number: byte, short, integer, long, Floating-point: float, double, Boolean: boolean and Date: date
* mapping contains one of -  analyzed, not_analyzed and no (don't index this field)
* Default field type is string, string types are analyzed (transformed)
* Other types are not analzyed
* 

# Type and mappings
* _type, _id, _type, _version, _source
* Every type has its own mapping or schema definition, which defines the data structure for documents of that type, much like the columns in a database table. Documents of all types can be stored in the same index, but the mapping for the type tells Elasticsearch how the data in each document should be indexed.
* If your main database already has version numbers—or a value such as timestamp that can be used as a version number. version_type=external
* timeout, sync (disable for performance)
* _all - concatenated field
* You can always update partial mappings without explicitly mention about existing fields
* 

# Plugins
bin/elasticsearch-plugin install http://some.domain/path/to/plugin.zip
bin/elasticsearch-plugin install file:///C:/path/to/plugin.zip

# Comparision
* Relational DB  â‡’ Databases â‡’ Tables â‡’ Rows      â‡’ Columns
* Elasticsearch  â‡’ Indices   â‡’ Types  â‡’ Documents â‡’ Fields
* Relational DB  â‡’ B-Tree â‡’ Index
* Elasticsearch  â‡’ Inverted index â‡’ Index


# ElasticSearch Queries
```
GET /megacorp/employee/_search?q=last_name:Smith

```

# https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting.html
# 