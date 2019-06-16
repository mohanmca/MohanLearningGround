* With container images, we confine the application code, its runtime, and all of its dependencies in a pre-defined format. And, with container runtimes like runC, containerd, or rkt we can use those pre-packaged images, to create one or more containers.
* We would like to have a fault-tolerant and scalable solution, which can be achieved by creating a single controller/management unit - referred as "container orchestrator"
* Container orchestrators are the tools which group hosts together to form a cluster, and help us fulfill the following requirements
  * fault-tolerant
  * on-demand scaling
  * discover and communicate with other applications
  * Can update/rollback without any downtime
* Container orchestrators
  * Docker Swarm
  * Kubernetes
  * Mesos Marathon
  * Amazon ECS
  * Hashicorp Nomad
* Container orchestrators can:
 * Bring multiple hosts together and make them part of a cluster
 * Schedule containers to run on different hosts
 * Help containers running on one host reach out to containers running on other hosts in the cluster
 * Bind containers and storage
 * Bind containers of similar type to a higher-level construct, like services, so we don't have to deal with individual containers
 * Keep resource usage in-check, and optimize it when necessary
 * Allow secure access to applications running inside containers.
  
* Kubernetes based on Borg, which is a cluster manager created by Google
 * "Google's Borg system is a cluster manager that runs hundreds of thousands of jobs from many thousands of different applications, across a number of clusters each with up to tens of thousands of machines.
 * Kubernetes offers a very rich set of features for container orchestration.
   * Automatic binpacking
   * Self-healing
   * Horizontal scaling
   * Service discovery and Load balancing
   * Automated rollouts and rollbacks
   * Secrets and configuration management
   * Storage orchestration
   * Batch execution
 * Kubernetes 
   * Kubernetes is an open-source system for automating deployment, scaling, and management of containerized applications.
   * Manager for shipping containers
   * It is an open source project written in the Go language
   * Kubernetes has new releases every three months. The current stable version is 1.11 (as of September 2018).

* Kubernetes Architecture
  * K8S Architecture: ![K8S Architecture][arch]
  * One or more master nodes,  only one of them will be the leader
  * One or more worker nodes
  * Distributed key-value store, like etcd.
 
* Master node
  * API server
  * Scheduler
  * Controller manager
  * etcd (Key value store)


 # Image references
 [arch]: img/Kubernetes_Architecture1.png "Kubernetes Architecture"  
 
 # Reference 
 * (Case Studies)[https://kubernetes.io/case-studies/]
 * 