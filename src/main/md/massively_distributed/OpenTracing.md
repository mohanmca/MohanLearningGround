## Jaeger

* https://www.jaegertracing.io/docs/1.13/architecture/
* Trace can be thought of as a directed acyclic graph of spans.
* A span represents a logical unit of work in Jaeger 
  * Spans may be nested and ordered to model causal relationships.
     * That has an operation name, 
     * The start time of the operation, 
     * The duration. 
* Jaegar storage can be ElasticSearch, Cassandra, Kafka



## Cassandra Opentracing

* [Cassandra OpenTrace](https://github.com/opentracing-contrib/java-cassandra-driver)
* 
```xml
<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-cassandra-driver-4</artifactId>
    <version>VERSION</version>
</dependency>
```
* ```java
// Instantiate CqlSession:
CqlSession session = CqlSession.builder().build()

// Decorate CqlSession with TracingCqlSession:
CqlSession tracingSession = new TracingCqlSession(session, tracer);

// execute query with TracingCqlSession:
tracingSession.execute("...");
```

## Kafka tracing
* [Kafka OpenTrace](https://github.com/opentracing-contrib/java-kafka-client/tree/master/opentracing-kafka-spring)


## Reference
* [Uber distributed tracing](https://eng.uber.com/distributed-tracing/)
* [Jager tutorial](https://www.scalyr.com/blog/jaeger-tracing-tutorial/#:~:text=Span%20%E2%80%93%20The%20logical%20unit%20of,way%20Jaeger%20presents%20execution%20requests.)