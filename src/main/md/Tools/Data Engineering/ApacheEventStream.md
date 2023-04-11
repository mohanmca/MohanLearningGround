Welcome to Distributed Event Streaming Platform Components.
After watching this video, you will be able to:
Describe what an Event is.
List the common Event formats.
Describe what an Event Stream Platform (ESP) is.
List the main components of an ESP.
And, list the popular ESPs.
An event normally means something worth noticing is happening.
In the context of event streaming, an event is a type of data which describes the entity’s
observable state updates over time.
For example,
The GPS coordinates of a moving car.
The temperature of a room.
Blood pressure measurements of a patient,
or a RAM usage of a running application.
An Event, as a special type for data, has different formats.
Let’s have a look at the three most common formats:
It can be as a primitive type such as a plain text, number, or date,
or an event may be in key-value format, and its value can be a primitive data type, or
complex data type like list, tuple, JSON, XML, or even bytes.
For example, the coordinates tuple of a car with car_id_1.
Also, very often, an event can be associated with a timestamp to make it time-sensitive.
Next, we will have a closer look at event streaming.
Suppose we have one event source such as a group of sensors, a monitoring device, a database,
or a running application.
This event source may continuously generate a large event volume at a short time interval
or nearly real-time.
Those real-time events need to be properly transported to an event destination such as
a file system, another external database, or an application.
The continuous event transportation between an event source and an event destination is
called event streaming.
After all you have learned about ETL so far, you may think that to implement such an ETL
process between one event source to one event destination should be straightforward.
However, what if we have multiple different event sources and destinations?
In a real-world scenario, event streaming can be really complicated with multiple distributed
event sources and destinations, as data transfer pipelines may be based on different communication
protocols such as:
FTP: File Transfer Protocol
HTTP: Hypertext Transfer Protocol
JDBC: Java Database Connectivity
SCP: Secure copy.
And so on.
An event destination can also be an event source simultaneously.
For example, one application could receive an event stream and process it, then transport
the processed results as an event stream to another destination.
To overcome such a challenge of handling different event sources and destinations, we will need
to employ the Event Stream Platform, or ESP.
An ESP acts as a middle layer among various event sources and destinations and provides
a unified interface for handling event-based ETL.
As such, all event sources only need to send events to an ESP instead of sending them to
the individual event destination.
On the other side, event destinations only need to subscribe to an ESP,
and just consume the events sent from the ESP instead of the individual event source.
Different ESPs may have different architectures and components.
Here we show you some common components included in most ESP systems.
The first and foremost component is the Event broker, which is designed to receive and consume
events.
Since it is the core component of an ESP, we will explain it in more detail in the next
slide.
The second common component of an ESP is Event Storage, which is used for storing events
being received from event sources.
Accordingly, event destinations do not need to synchronize with event sources, and stored
events can be retrieved at will.
The third common component is the Analytic and Query Engine which is used for querying
and analyzing the stored events.
Let’s have a look at the Event broker, 
There are many ESP solutions, including:
Apache Kafka
Amazon Kinesis
Apache Flink
Apache Spark
Apache Storm
and more such as Logstash in Elastic Stack, and so on.
Each has its unique features and application scenarios.
Among these ESPs, Apache Kafka is probably THE most popular one.
In this video, you learned that:
An event stream represents entities’ status updates over time.
Common event formats include primitive data types, key-value, and key-value with a timestamp.
An ESP is needed especially when there are multiple event sources and destinations.
The main components of an ESP are Event broker, Event storage, Analytic and Query Engine.
Apache Kafka is the most popular open source ESP.
And finally,
popular ESPs include Apache Kafka, Amazon Kinesis, Apache Flink, Apache Spark, Apache
Storm, and more. 