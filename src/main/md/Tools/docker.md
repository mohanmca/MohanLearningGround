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


## docker image build
```
docker image build -t imagename:version .
```

## $Reference
* [Docker samples](https://docs.docker.com/samples/)
