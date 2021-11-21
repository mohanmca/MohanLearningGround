## What is the purpose of Distributed Tracing

* Allows engineers to see the how long an operation took in 
    1. A web server
    2. Database
    3. Application code
    4. Messaging queue
    5. Entirely different systems

## Logs, Metrics vs Tracing

1. Applications start with logging and monitoring, then later they add tracing when the need arises.
1. Metrics statistics, not specific for single request vs Trace per request

## Steps to acheive Tracing

1. Engineering teams must instrument code
1. Add tracing infrastructure components such as load balancers, and deploy the tracing system itself. 
1. The solution must factor in language and library support

## Jaeger - Open Tracing

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

## Zipkin

* Zipkin is a tracing system originally created by Twitter and is currently run by the OpenZipkin volunteer organization.
* Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in service architectures. Features include both the collection and lookup of this data.
* Zipkin also has an instrumentation library to instrument other libraries to support tracing

## Algorithms

* [Leak bucket algorithms](https://en.wikipedia.org/wiki/Leaky_bucket)

## Kafka tracing
* [OpenTracing Tutorials](https://github.com/yurishkuro/opentracing-tutorial)
* [https://www.shkuro.com/books/2019-mastering-distributed-tracing/](https://www.shkuro.com/books/2019-mastering-distributed-tracing/)
* [Kafka OpenTrace](https://github.com/opentracing-contrib/java-kafka-client/tree/master/opentracing-kafka-spring)
* [Spring Sleuth](https://github.com/mohanmca/spring_guides/blob/master/spring-cloud-sleuth/sleuth_log.json)
* [OpenTracing Presentation](https://www.bit.ly/cncfopentracing)
* [Kafka Tracing](https://github.com/opentracing-contrib/java-kafka-client)

## Reference
* [Evolving Distributed Tracing at Uber Engineering-Yuri Shkuro](https://eng.uber.com/distributed-tracing/)
* [Jager tutorial](https://www.scalyr.com/blog/jaeger-tracing-tutorial/#:~:text=Span%20%E2%80%93%20The%20logical%20unit%20of,way%20Jaeger%20presents%20execution%20requests.)
* [Zipkin vs Jaeger: Getting Started With Tracing](https://logz.io/blog/zipkin-vs-jaeger/)
* [ZIPKIN TUTORIAL: GET STARTED EASILY WITH DISTRIBUTED TRACING](https://www.scalyr.com/blog/zipkin-tutorial-distributed-tracing/))
