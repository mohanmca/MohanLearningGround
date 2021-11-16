## Jaeger

* [OpenTracing Architecture](https://www.jaegertracing.io/docs/1.28/architecture/)
* What would happen if we log the entire stack-trace to elastic-search and investigate?
  * What is the use of it?
  * Can I trace DB/Queue/Infrastructure level issues?
  * What happens if it crosses boundary (different servers)
* A span represents a logical unit of work in Jaeger 
  * Spans may be nested and ordered to model causal relationships.
     * That has an operation name, 
     * The start time of the operation, 
     * The duration. 
* Jaegar storage can be ElasticSearch, Cassandra, Kafka
* Trace can be thought of as a directed acyclic graph of spans.

## How to trace Java applications?

1. @Traced annotations
2. Uses ThreadLocalContext to store the span-contex
   1. This would break for async code
3. Async Calls must be activated via API
4. HTTP Rest Calls can be traced
   1. restTemplate.setInterceptors( new TracingRestTemplateInterceptor(tracer))
   2. JDBC can use Trace Driver
       ```java
       Driver: Class.forName("io.opentracing.contrib.jdbc.TracingDriver");
       Connection String: jdbc:tracing:mysql://localhost:3306/test
       ```
4. HTTP Rest Calls can be traced
      
## [How to trace Kafka (producer, consumer, streams)](https://github.com/opentracing-contrib/java-kafka-client)
```java
// Declare Tracer bean
@Bean
public Tracer tracer() {
    return ...
}
// Decorate ConsumerFactory with TracingConsumerFactory
@Bean
public ConsumerFactory<Integer, String> consumerFactory() {
    return new TracingConsumerFactory<>(new DefaultKafkaConsumerFactory<>(consumerProps()), tracer());
}

// Decorate ProducerFactory with TracingProducerFactory
@Bean
public ProducerFactory<Integer, String> producerFactory() {
    return new TracingProducerFactory<>(new DefaultKafkaProducerFactory<>(producerProps()), tracer());
}

// Use decorated ProducerFactory in KafkaTemplate
@Bean
public KafkaTemplate<Integer, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
}

// Use an aspect to decorate @KafkaListeners
@Bean
public TracingKafkaAspect tracingKafkaAspect() {
    return new TracingKafkaAspect(tracer());
}
```

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

```java
// Instantiate CqlSession:
CqlSession session = CqlSession.builder().build()
// Decorate CqlSession with TracingCqlSession:
CqlSession tracingSession = new TracingCqlSession(session, tracer);
// execute query with TracingCqlSession:
tracingSession.execute("...");
```

## Kafka tracing
* [OpenTracing Tutorials](https://github.com/yurishkuro/opentracing-tutorial)
* [https://www.shkuro.com/books/2019-mastering-distributed-tracing/](https://www.shkuro.com/books/2019-mastering-distributed-tracing/)
* [Kafka OpenTrace](https://github.com/opentracing-contrib/java-kafka-client/tree/master/opentracing-kafka-spring)
* [Spring Sleuth](https://github.com/mohanmca/spring_guides/blob/master/spring-cloud-sleuth/sleuth_log.json)
* [OpenTracing Presentation](https://www.bit.ly/cncfopentracing)

## Reference
* [Uber distributed tracing](https://eng.uber.com/distributed-tracing/)
* [Jager tutorial](https://www.scalyr.com/blog/jaeger-tracing-tutorial/#:~:text=Span%20%E2%80%93%20The%20logical%20unit%20of,way%20Jaeger%20presents%20execution%20requests.)
