## How to query list of ports and containers
```bash
docker ps -a --format "table {{.Names}}\t{{.Ports}}"
```

## How to run as a root user
```bash
docker exec -it -u 0 containerid bash
```

## Docker installation and trouble-shoot

```cmd
docker-machine --debug create --driver virtualbox newone
## Dockerd commands
dockerd --storage-opt lcow.kernel=kernel.efi
```

## Docker on WSL (Windows)

* wsl -d docker-desktop
* wsl --list
* wsl --shutdown
* notepad "$env:USERPROFILE/.wslconfig"
```.wslconfig
[wsl2]
memory=3GB   # Limits VM memory in WSL 2 up to 3GB
processors=4 # Makes the WSL 2 VM use two virtual processors
```


## Docker theory
* The difference between a Docker image (class) and a Docker container (object) is the same as that of the difference between a class and an object.

```bash
#!/bin/bash
# to run docker container -p host_port:container_port.. http://localhost:host_port/
docker container run -p 9999:8888 YOUR_DOCKER_ID/myhello
# stop all containers
docker stop $(docker ps -aq)
# Delete all containers
docker rm $(docker ps -a -q)
# Delete all images
docker rmi $(docker images -q)
# Start a mongodb
docker run --restart unless-stopped --name mongo -d -p 0.0.0.0:27017:27017 mongo:3.4.18
# Start a mysql
docker run --restart unless-stopped --name mysql -e MYSQL_ROOT_PASSWORD=root -p 0.0.0.0:31306:3306 -d mysql:8.0.14
# inspect image
docker inspect $image_id_cc126d830f47
# Bash as deamon process
docker run -it -d -p 8000:8000 -p 8002:8002 busybox bin/bash
```

## Why docker container exited

```bash
docker logs help
docker logs -f cass1 ## Tail the log

docker logs container-id

```

## Docker and Python

* Docker compose is already using python-3, hence try to ensure py3 in on path
* If there are named-pipe error or python stack-trace, ensure docker-compose commnd executed on the same window where docker-machine started

# Delete All Exited Containers (Linux): Or remove with certain image
```shell
docker rm $(docker ps -q -f status=exited)
docker images | grep "pattern" | awk '{print $1}' | xargs docker rm
```

# Docker (container using daemon process) for mvn remote debugging

```bash
docker pull maven:3.6.3-jdk-8
docker run -it -d -p 8000:8000 -p 8002:8002  maven:3.6.3-jdk-8 bin/bash
```

# Docker Scala JDK remote debug, Scala, sbt and ammonite

```bash
sh -c '(echo "#!/usr/bin/env sh" && curl -L https://github.com/lihaoyi/Ammonite/releases/download/2.0.4/2.13-2.0.4) > /usr/local/bin/amm && chmod +x /usr/local/bin/amm' && amm
echo "deb https://dl.bintray.com/sbt/debian /" |  tee -a /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add
apt-get update
apt-get install sbt

apt-get remove scala-library scala
wget www.scala-lang.org/files/archive/scala-2.11.8.deb
dpkg -i scala-2.11.8.deb

curl -L -o sbt.deb http://dl.bintray.com/sbt/debian/sbt-0.13.15.deb
dpkg -i sbt.deb
apt-get update
apt-get install sbt

mvn -Dmaven.surefire.debug test
mvn -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -Xnoagent -Djava.compiler=NONE" test
```


# Delete All Dangling Images
```shell
docker rmi $(docker images -f dangling=true -q)
```

## Docker based mongodb
```bash
docker-compose -f src/main/docker/mongodb.yml up
docker run --name mongo -d mongo:3.4.18
```
## Docker based Cassandra
```bash
docker run --name some-cassandra -p 9042:9042 -p 7000:7000 --network host -d cassandra:latest
```
## docker image elasticsearch
```bash
docker pull opensearchproject/opensearch:latest
docker run -d --name opensearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" opensearchproject/opensearch:latest
docker start opensearch ## successive times

docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.8.6
docker run -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" amazon/opendistro-for-elasticsearch:1.3.0
```

## docker image build
```bash
docker image build -t imagename:version .
```

## Docker machine on Windows

```bash
docker-machine ls
docker-machine rm default
docker-machine create -d virtualbox  default
docker-machine start
docker-machine stop
docker-machine upgrade
docker-machine ssh
docker-machine ip
# C:\Users\nikia>docker-machine ip 192.168.99.101
# Host ip IPv4 Address. . . . . . . . . . . : 192.168.0.109
docker run --name some-cassandra -p 9042:9042 -p 7000:7000 --network host -d cassandra:latest
docker exec -it some-cassandra sh
```

## Docker DSE Cassandra

```bsh
docker pull datastax/dse-server
docker pull datastax/dse-opscenter
docker pull datastax/dse-studio
# Start opscenter first and later link it dse-server
docker run -e DS_LICENSE=accept -9 8888:8888 -name my-opscenter -d datastax/dse-opscenter
docker run -e DS_LICENSE=accept -9 8888:8888 --link my-opscenter:opscenter -name my-dse -d datastax/dse-server
docker run -e DS_LICENSE=accept -9 9091:9091 --link my-dse -name my-studio -d datastax/dse-studio
docker exec -it my-dse cqlsh ip_address
```

### Docker PGSQL
```
docker run --name postgres -e POSTGRES_PASSWORD=pgSecretProd26 -d -p 5432:5432 postgres
```

## Docker AMPS

1. [Running AMPS using docker](https://support.crankuptheamps.com/hc/en-us/articles/360047510833-How-do-I-run-AMPS-in-a-Docker-container-)
2. 
    ```bash
       docker build --tag amps .
       docker run -p 8085:8085 -p 9007:9007 -p 9008:9008 amps
    ```

## If docker command is not connecting to docker-machine

* Pay attention to environment variables

```pre
  DOCKER_CERT_PATH=C:\Users\nikias\.docker\machine\machines\default
  DOCKER_HOST=tcp://192.168.99.111:2376
  DOCKER_MACHINE_NAME=default
  DOCKER_TLS_VERIFY=1
  DOCKER_TOOLBOX_INSTALL_PATH=D:\Apps\Docker Toolbox
```

```bat
docker-machine env default
@FOR /f "tokens=*" %i IN ('docker-machine env default') DO @%i
```

## docker network

```bash
$docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
713e294d5638        bridge              bridge              local
34d058f03720        host                host                local

docker pull alpine
docker run -itd -network 713e294d5638 --name=alpine1 alpine
docker run -itd -network 713e294d5638 --name=alpine2 alpine
docker network inspect 713e294d5638 --format '{{ .Containers }}'
docker exec -it alpine1 ping 172.17.0.3
#Above command worked
$ docker exec -it alpine1 ping alpine2
ping: bad address 'alpine2'

## Creating custom network (to ping using container-name)
docker network create --driver=bridge javahome
docker run -itd --network javahome --name=alpine1 alpine
docker run -itd --network javahome --name=alpine2 alpine
docker exec -it alpine1 ping alpine2
```

## To connect to docker service from Windows
* Enable NAT and port-warding
* Add port forwarding like 7042 without host-ip and guest-ip
* docker-machine ip (use output of this ip to connect from the application)

## $Reference
* [Docker samples](https://docs.docker.com/samples/)
* [Docker context](https://www.youtube.com/watch?v=x0Kbj4lEOag)
* https://itnext.io/wsl2-tips-limit-cpu-memory-when-using-docker-c022535faf6f
