## What is DPDK?
1. Kernel bypass
2. UserIo and user-space libraries
3. Open source - starge by Intel and supported by various NIC manufacturers


## How to measure network speed with various packet size?
1. iperf -c 192.168.1.1 -w 1024
   1. Send 1K packet to remote IP addresses?
2. Default Window size is 64K, if we reduce to 1024, It reduced from 22.GBytes 19.5 Gbytes/sec to 124MBye 104 Mbits/sec

## Why such a huge drop in performance?
1. For every packet, application to kernel and multiple context switch has to happen
2. It is like sending 1 GB zipped file vs sending 1k file million times
3. Kernel and user-space context switching
4. Copying data from user-space to kernel-space
5. NIC -> Kernel -> (Copy) -> Into User-space


## What are all the limitation
1. CPU Core should be dedicated to the DPDK applications
2. DPDK core applications should not be pre-empted by Operating system
3. Application continuously poll NIC and gets the data
4. Application can directly access the memory of the NIC (which could be more than 1GB)
5. 
