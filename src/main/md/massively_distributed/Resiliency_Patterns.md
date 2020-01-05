# Resiliency patterns like circuit breakers

## Circuit Breakers

* The goal of a circuit breaker, much like you would find in an electrical panel, is to stop the failure from cascading. When you drop a hair dryer in a bath tub, the breaker pops and it shuts the circuit off. The damage is limited to that one item and not the rest of the electrical system. 

* Latency is a Form of Failure, Latency means that something takes too long. Latency is the same as failure. If it takes you five minutes to save a work item, we’re down. 

* As calls come in and go through the circuit breaker, normally the circuit breaker is closed. Normally things are flowing through. The breaker is looking at the failure rates. When the failure rates exceed some percentage in a given window of time with a certain volume, the breaker opens. And when it opens it just starts failing calls. You may have a problem in the code and that problem might have been triggered by somebody’s behavior, but we’re going to start failing all those calls to save the system. Circuit breakers are all about saving the system to prevent failure from spreading through the rest of system. This is a blunt instrument but effective.


## Bulkheads (Partition)

* Elements of an application are isolated into pools so that if one fails, the others will continue to function. It's named after the sectioned partitions (bulkheads) of a ship's hull.

### Issues and considerations

* Consider combining bulkheads with retry, circuit breaker, and throttling patterns to provide more sophisticated fault handling.
When partitioning consumers into bulkheads, consider using processes, thread pools, and semaphores. Projects like Netflix Hystrix and Polly offer a framework for creating consumer bulkheads.

* When partitioning services into bulkheads, consider deploying them into separate virtual machines, containers, or processes. Containers offer a good balance of resource isolation with fairly low overhead.


## Back-Pressure

* When one component is struggling to keep-up, the system as a whole needs to respond in a sensible way. It is unacceptable for the component under stress to fail catastrophically or to drop messages in an uncontrolled fashion. Since it can’t cope and it can’t fail it should communicate the fact that it is under stress to upstream components and so get them to reduce the load. This back-pressure is an important feedback mechanism that allows systems to gracefully respond to load rather than collapse under it. The back-pressure may cascade all the way up to the user, at which point responsiveness may degrade, but this mechanism will ensure that the system is resilient under load, and will provide information that may allow the system itself to apply other resources to help distribute the load, see Elasticity.

* One of the way of managing backpressure may be through the rate-limiting technique. The limitRate(n) operator splits the downstream demand into smaller batches not bigger than n. In this way, we may protect our delicate producer from an unjustified data request from a downstream consumer.

* Producer/upstream - should have a way asses the situation and throttle limit, it can have api to accept feedback from downstream

* RxJava  supports backpressure

* [Patterns for Resiliency in the Cloud - Circuit Breakers] (https://docs.microsoft.com/en-us/azure/devops/learn/devops-at-microsoft/patterns-resiliency-cloud)
* [Cloud Design Patterns - Bulk Head] (https://docs.microsoft.com/en-us/azure/architecture/patterns/bulkhead)
* [Resilence4j - ~~Hystrix~~](https://github.com/resilience4j/resilience4j)