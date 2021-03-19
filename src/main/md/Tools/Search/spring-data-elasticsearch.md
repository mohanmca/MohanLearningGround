## Spring Data Elasticsearch

* Spring Data ElasticSearch - Spring Data Community maintained extension
* Reactive Extension
* Spring Data ElasticSearch v4.0 (for ES 7.0)  with Spring Boot 2.3
* SDE -  uses ElasticSearch Rest Client


## Spring Data Elasticsearch RestClient

* Depends on ES core-project
* Based on Apache HTTP Client
* Supports cancellable async calls

## Spring Data Elasticsearch RestClient architecture

* org.apache...CloseableHttpAsynchClient
  * Org.elasticsearch.client.RestClient
    * Org.elasticsearch.client.RestHighLevelClient

## Spring Data Elasticsearch Basics

* ElasticsearchTemplate & ElasticsearchRestTemplate
* MappingElasticsearchConverter
* CrudRepository
* Auditing, Entity Callbacks, efficient scroll searching




## Reference
* [Reference Project](https://github.com/spinscale/link-rating)
* [Introduction into Elasticsearch & Spring Data Elasticsearch- A presentation at Elastic User Group Morocco by Alexander Reelsen](https://noti.st/spinscale/YCsKrf/introduction-into-elasticsearch-spring-data-elasticsearch#sDqFvc3)
* [5. Elasticsearch Clients](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#reference)
* [ElasticSearch project](https://github.com/spring-projects/spring-data-examples/tree/master/elasticsearch)
* [elasticsearch-sample-app - Alexander](https://github.com/spinscale/javalin-elasticsearch-sample-app/tree/main)
* [BooleanQueryBuilder](https://www.baeldung.com/spring-data-elasticsearch-queries)
* [Introduction into the Java HTTP REST client for Elasticsearch](https://on.notist.cloud/pdf/deck-524ebd13f5076e5b.pdf)
