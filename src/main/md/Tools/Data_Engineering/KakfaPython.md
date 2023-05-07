## Interact Kafka with Python   
1. pip install kafka-python
2. KafkaAdminClient
   1. Fundamental administrative management operations
      on kafka server such as creating/deleting topic, retrieving, and updating topic configurations

## Write python program that is equivalent to 
"kafka-topics.sh --bootstrap-server localhost:9092 --create --topic bankbranch  --partitions 2 --replication_factor 1"
%

```python
admin_client = KafkaAdminClient(bootstrap_servers="localhost:9092", client_id='test')
topic_list = []
new_topic = NewTopic(name="bankbranch", num_partitions= 2, replication_factor=1)
topic_list.append(new_topic)
admin_client.create_topics(new_topics=topic_list)
```

## Write python program that is equivalent to describe topic
```python
configs = admin_client.describe_configs(config_resources=[ConfigResource(ConfigResourceType.TOPIC, "bankbranch")])
```

## Write python program that is equivalent to KafkaProducer
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic bankbranch
%
```python
producer = KafkaProducer(value_serializer=lambda v: json.dumps(v).encode('utf-8'))
producer.send("bankbranch", {'atmid':1, 'transid':100})
producer.send("bankbranch", {'atmid':2, 'transid':101})
```

## Write python program that is equivalent to KafkaConsumer
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic bankbranch
%
```python
consumer = KafkaConsumer('bankbranch')
for msg in consumer:
    print(msg.value.decode("utf-8"))
```
