# Delete All Exited Containers (Linux): Or remove with certain image
```shell
docker rm $(docker ps -q -f status=exited)
docker images | grep "pattern" | awk '{print $1}' | xargs docker rm
```

# Delete All Dangling Images
```shell
docker rmi $(docker images -f dangling=true -q)
```