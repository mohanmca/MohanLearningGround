# ElasticSearch continious Query
* https://qbox.io/blog/elasticsesarch-percolator
* https://stackoverflow.com/questions/21536599/what-does-percolator-mean-do-in-elasticsearch
* [Robert Muir](https://www.elastic.co/cn/blog/author/robert-muir)
* https://sematext.com/blog/top-10-elasticsearch-mistakes/
* https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html
* 

# Getting Started Elastic

```bash
	export JAVA_HOME=/C/Apps/Java/jdk1.8.0_181/
	curl -L -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.1.zip
	unzip elasticsearch-6.4.1.zip
	cd  elasticsearch-6.4.1
	bin/elasticsearch-plugin.bat  install x-pack (by default x-pack is available)
	wget -L http://127.0.0.1:9200/
	curl -XGET http://localhost:9200/_xpack
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
* 
* 

# Plugins
bin/elasticsearch-plugin install http://some.domain/path/to/plugin.zip
bin/elasticsearch-plugin install file:///C:/path/to/plugin.zip

# Comparision
* Relational DB  ⇒ Databases ⇒ Tables ⇒ Rows      ⇒ Columns
* Elasticsearch  ⇒ Indices   ⇒ Types  ⇒ Documents ⇒ Fields
* Relational DB  ⇒ B-Tree ⇒ Index
* Elasticsearch  ⇒ Inverted index ⇒ Index


# ElasticSearch Queries
```
GET /megacorp/employee/_search?q=last_name:Smith

```