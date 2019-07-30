```bash
  minikube stop
  minikube delete
  minikube version
  minikube  start  --v=0 > kubernetes.log
  minikube start -v=10 > kubernetes.log
  #minikube version: v1.2.0
  minikube dashboard
```

```
  * minikube v1.2.0 on windows (amd64)
  * Creating virtualbox VM (CPUs=2, Memory=2048MB, Disk=20000MB) ...
  * Found network options:
    - NO_PROXY=192.168.99.100
  * Configuring environment for Kubernetes v1.15.0 on Docker 18.09.6
    - env NO_PROXY=192.168.99.100
  * Pulling images ...
  * Launching Kubernetes ... 
  * Verifying: apiserver proxy etcd scheduler controller dns
  * Done! kubectl is now configured to use "minikube"
```


* After started
```
kubectl get pod --all-namespaces
kubectl get deployment --all-namespaces
#install docker-toolbox
Use "Docker Quickstart Terminal"
docker run hello-world
git clone https://github.com/cloudnativedevops/demo.git
cd demo/hello
docker image build -t mhello .
docker run -p 9999:8888 mhello:latest
# Find IP address of the docker host machine
docker-machine ip default
http://192.168.99.100:9999/

kubectl run demo --image=YOUR_DOCKER_ID/myhello --port=9999 --labels app=demo
kubectl run demo --image=mhello:latest --port=9999 --labels app=demo

minikube start -p onecluster
kubectl run demo --image=cloudnatived/demo:hello --port=9999 --labels app=demo
#kubectl run --generator=deployment/apps.v1 is DEPRECATED and will be removed in a future version. Use kubectl run --generator=run-pod/v1 or kubectl create instead.
#deployment.apps/demo created
kubectl port-forward deploy/demo 9999:8888
curl http://localhost:9999/
```