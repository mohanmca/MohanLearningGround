## Docker theory
* The difference between a Docker image (class) and a Docker container (object) is the same as that of the difference between a class and an object.

```bash
#!/bin/bash
# Delete all containers
docker rm $(docker ps -a -q)
# Delete all images
docker rmi $(docker images -q)
# Start a mongodb
#docker run --name container -d mongo:version -p host:port:docker_port
docker run --name mongo -d mongo:3.4.18 -p 0.0.0.0:27017:27017
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