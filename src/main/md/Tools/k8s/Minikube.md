```cmd
install choclatey - @"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
install virtualbox
@ echo off choco install minikube kubernetes-cli
kubectl version
minikube addons enable metrics-server
minikube start
minikube start --vm-driver=hyperv --kubernetes-version="v1.14"
minikube delete
# Remove the iso file and images of minikube
minikube delete --all --purge
minikube dashboard
kubectl version --client
kubectl config current-context
kubectl get nodes
```

## We might encounter this error,  --no-vtx-check could be used to make it work

```txt
! StartHost failed, but will try again: creating host: create: precreate: This computer doesn't have VT-X/AMD-v enabled. Enabling it in the BIOS is mandatory
* Creating virtualbox VM (CPUs=2, Memory=4096MB, Disk=20000MB) ...
* Failed to start virtualbox VM. Running "minikube delete" may fix it: creating host: create: precreate: This computer doesn't have VT-X/AMD-v enabled. Enabling it in the BIOS is mandatory
X Exiting due to HOST_VIRT_UNAVAILABLE: Failed to start host: creating host: create: precreate: This computer doesn't have VT-X/AMD-v enabled. Enabling it in the BIOS is mandatory
* Suggestion: Virtualization support is disabled on your computer. If you are running minikube within a VM, try '--driver=docker'. Otherwise, consult your systems BIOS manual for how to enable virtualization.
```

### Solution to ignore hyperv

minikube start  --memory=4g --cpus=2 --vm-driver=virtualbox  --no-vtx-check --kubernetes-version="v1.15.1"

* https://instruqt.com/public/tracks/deploying-an-app-on-kubernetes/
* https://www.katacoda.com/courses/kubernetes
