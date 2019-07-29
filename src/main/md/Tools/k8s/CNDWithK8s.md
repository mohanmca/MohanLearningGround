 * Error encountered while starting minicube
 ```pre
 X Unable to start VM: start: Unable to start the VM: D:\Apps\Oracle\VirtualBox\VBoxManage.exe startvm minikube --type headless failed:
VBoxManage.exe: error: Call to WHvSetupPartition failed: ERROR_SUCCESS (Last=0xc000000d/87) (VERR_NEM_VM_CREATE_FAILED)
VBoxManage.exe: error: Details: code E_FAIL (0x80004005), component ConsoleWrap, interface IConsole
 ```
 * Above error was caused by HyperV usage. To disable HyperV 
 bcdedit /set hypervisorlaunchtype off
 https://github.com/kubernetes/minikube/issues/4587


 * minikube / kubectl commands
 ```bash
    minikube version
    minikube start
    minikube stop
    kubectl get nodes
    kubectl get pods
    minikube dashboard
 ```

 * Docker hello world
 ```bash
  docker container run -p 9999:8888 --name hello cloudnatived/demo:hello
 ```

 ## Reference
 * https://github.com/cloudnativedevops/demo.git