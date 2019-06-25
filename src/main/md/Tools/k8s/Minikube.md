```cmd
install choclatey - @"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
install virtualbox
@ echo off choco install minikube kubernetes-cli
kubectl version
minikube addons enable metrics-server
minikube start
minikube start --vm-driver=hyperv --kubernetes-version="v1.14"
minikube delete
minikube dashboard
kubectl version --client
kubectl config current-context
kubectl get nodes
```

* https://instruqt.com/public/tracks/deploying-an-app-on-kubernetes/
* https://www.katacoda.com/courses/kubernetes