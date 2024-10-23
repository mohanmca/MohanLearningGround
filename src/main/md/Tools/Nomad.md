## Nomad - Container and Application Orchestration

1. Nomad is simpler the K8s
2. It also supports non-containerzied workloads VM, binaries
3. Nomad allows deploy on different public cloud and on-prem
4. Batch applications also supported
5. Nomad is single binary


## Architecture

1. Node - VM or Physical node running "Nomad Agent"
1. Agent
    1. Client mode - responsible for running workload, fingerprints the host
        1. This is where containers are scheduled
        2. Rus the set of tasks defined in the job spec
    2. Server mode - agent running on server, participa how to in scheduling decisions, holds the global state of the server
1. JOBS (similar to deployments)
    1. Definition of workload should be scheduled
    1. Task groups, each task defines series of resource configurations and constrains
1. Job submission
    1. Job is submitted to nomad with desired state
    1. Run 3 instances of my docker images and spread across 3 nodes
1. [Job configuration](https://github.com/mohanmca/hashicorp/blob/master/nomad/job.hcl)
    1. HCL configuration file on disk which describes how a workload should be scheduled
1. Driver - Plugaable component that executes a task and provide resource isolation
    1. Driver (similar to windows / mac)
        1. Podman
        1. Docker
        1. Java
        1. raw-exec


## [Tasks](https://github.com/mohanmca/hashicorp/blob/master/nomad/job.hcl#L13C1-L24C6)

1. A command, service, application or "set of work" to be executed by Nomad
1. Tasks are executed by their driver
1. Example
    1. Execute these commands
    1. Run these containers
    1. Run this java application from .jar file


## [Task group]

1. A collection of individual task that should be co-located on the same node
1. Any task within the group would will be placed on the same Nomad client

## Evaulation && Allocation

1. Calculation between target state and required state is called Evaulations
2. Mapping of tasks in a job to client is done using allocations
3. Allocation fails when there are no resources available

## Nomad Client

1. Nomad client sends RPC to nomad server
1. Nomad server has leader and follower

## How Client knows about state of nodes?

1. Clients uses Serf, a gossip-based protocol, for cluster membership and failure detection. This helps Nomad clients and servers maintain awareness of the state of nodes in the cluster. Serf is used for:
2. Nomad clients and servers also use RPC for more interactive tasks. RPC calls are used for operations such as


## Nomad vvs K8s


| Nomad         | K8s           |
| ------------- | ------------- |
| Job             | Job  |
| Tasks/Group     | Pods  |
| Client Agent    | Kubelet  |
| Server Node     | Control Plane  |
| Client Node     | Worker Node  |
| Tasks Driver    | Container Runtime  |
| Service Discovery     | Load Balancer  |
| Integrated API     | API Server  |
| Raft     | ETCD  |
| HCL     | Yaml  | 