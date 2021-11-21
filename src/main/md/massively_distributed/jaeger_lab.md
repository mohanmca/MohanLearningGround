## [How to run Jaeger locally?](https://www.jaegertracing.io/docs/1.28/getting-started/#all-in-one)

```bash
docker run -d --name jaeger \
    -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
    -p 5775:5775/udp \
    -p 6831:6831/udp \
    -p 6832:6832/udp \
    -p 5778:5778 \
    -p 16686:16686 \
    -p 14268:14268 \
    -p 14250:14250 \
    -p 9411:9411 \
    jaegertracing/all-in-one:1.28
## To Generate trace
docker run  --rm   jaegertracing/jaeger-tracegen:1.28 -traces 50
```


1. http://localhost:16686

## Jaeger UI url

1. http://localhost:16686/trace/479ef25ebd105483?uiFind=479ef25ebd105483
2. 

## Jaeger Docker

1. https://github.com/jaegertracing/jaeger/blob/master/cmd/tracegen/Dockerfile
1. https://github.com/jaegertracing/jaeger/blob/master/cmd/all-in-one/Dockerfile