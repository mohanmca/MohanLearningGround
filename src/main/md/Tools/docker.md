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
docker run --restart unless-stopped --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.14
```


# Delete All Exited Containers (Linux): Or remove with certain image
```shell
docker rm $(docker ps -q -f status=exited)
docker images | grep "pattern" | awk '{print $1}' | xargs docker rm
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

## To connect to docker service from Windows
* Enable NAT and port-warding
* Add port forwarding like 7042 without host-ip and guest-ip

## $Reference
* [Docker samples](https://docs.docker.com/samples/)
