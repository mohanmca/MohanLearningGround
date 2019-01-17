
## Logstash functions
* Ingest - Collect input from multiple sources
* Enhance/Modify - Enrich the input
* Forward - to any storage
* Configuration
  * Input
    * File
    * Application log
    * Beat input
  * Filter
    * How to parse data, Can perform lookup
    * Ignore some
    * Modify Any
  * Output
    * Where should we store the logs
    * Back end? / Elasticserch?

## Logstah plugins
* Apache logs
* log4j files
* Windows Event log
* Can do filter, and look-up geo-location
* Dozens of filters

# Filters
* Grok filter
  * Can parse unstructured data and convert into strutured data
  * Web server logs into json storage
* Geoip
  * Internally uses Grok filter
  * Can covert IP address into rich set of information using lookup

## Kibana
* General graphing and visualization tool
* Originally written in NodeJs
* installation
  ```bash
  wget -q0 - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
  sudo apt-get update && apt-get install kibana
  server.host: point_to_local_ip
  server.name: "globo-kibana01"
  elasticserach.url: "http://192.168.0.1:9200/"
  ```
* /etc/kibana/kibana.yml
* http://192.168.1:5601/app/kibana

## Elasticsearch Installation
```bash
sudo apt-get install openjdk-8-jre-headless
mkdir pkg; cd pkg
wget http://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.0.0.deb
sudo dpkg -i elasticsearch-5.0.0.deb
/etc/elaticsearch/elasticsearch.yml
```

```bash
Change clustername and memory maps settings in kernel
cluster.name: globo-monitoring
network.host: bind_to_local_ip_address/0.0.0.0
sysctl -w vm.max_map_count=262144
service start elasticsearch
curl http://whateverIpd:9200/
systemctl enable elasticsearch #--ensure system start would automatically start elasticsearch
```

## Windows service registration
```bat
* elasticsearch/config/jvm.options file is on windows, where we can modify stack size of elasticsearch -Xss1m
.\elasticsearch-service.bat install ElasticSearch
start-service ElasticSearch
Invoke-WebRequest http://localhost:9200/
```

## logstash Installation
```bash
sudo apt-get install openjdk-8-jre-headless
wget -q0 - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
echo "deb https://artifacts.elastic.co/packages/5.x/apt stable main | tee -a /etc/apt/sources.list.d/elastic-5.x.list"
apt-get update && apt-get install logstash
cd /usr/share/logstsh; ll;
/usr/share/logstash/bin/logstash -e 'input { stdin { } } output { elasticsearch { hosts => ["192.168.0.1:9200"] } }'
# type some sample message for above command and ensure it is in elasticsearch
wget http://192.168.0.1:9200/logstash-*/search
```


# Additional settings
* elasticsearch/config/jvm.options file is on windows, where we can modify stack size of elasticsearch
* cat /etc/issue.net -- shows the distribution of ubuntu
* systemctl can register service to ubuntu
* systemctl enable logstash
* systemctl logstash start
