I need a step-by-step guide for designing, validating, and troubleshooting a UDP multicast network on RHEL 9 using interface `bond0`.

Network details:

* Node IP example: `169.91.200.111/26`
* Interface: `bond0`
* OS: RHEL 9
* Need both:

  1. Python producer/consumer multicast UDP example
  2. Non-Python command-line version using tools like `socat`, `ncat`, `tcpdump`, `ss`, `ip`, `nmcli`, `firewall-cmd`

Please explain from the beginning in a gradual way.

Cover these topics:

1. Basic networking introduction:

   * What `/26` means
   * Subnet mask
   * Network address
   * Usable IP range
   * Broadcast address
   * Number of usable nodes
   * Whether `169.91.200.111/26` is valid
   * Example node design using the same subnet

2. Linux interface validation:

   * Check `bond0` link state
   * Check IP address
   * Check MAC address
   * Check MTU
   * Check bonding status using `/proc/net/bonding/bond0`
   * Check slave interfaces
   * Check `ethtool`
   * Check NetworkManager connection using `nmcli`

3. Unicast validation before multicast:

   * `ip route`
   * `ip route get`
   * `ping`
   * `ip neigh`
   * ARP troubleshooting
   * Confirm all nodes can reach each other

4. Multicast design:

   * Explain producer/consumer model
   * Choose a safe multicast group, for example `239.91.200.111`
   * Use UDP port `5000`
   * Explain why not to use normal unicast IP as multicast destination
   * Explain why `239.x.x.x` is suitable for private/application multicast
   * Explain TTL 1 for same subnet

5. Multicast Linux validation:

   * Check multicast route
   * Add route `224.0.0.0/4 dev bond0` if required
   * Make route persistent using `nmcli`
   * Check interface has `MULTICAST` flag
   * Enable multicast on interface if needed
   * Check `rp_filter`
   * Explain when reverse path filtering may affect multicast

6. Firewall validation:

   * Open UDP port `5000`
   * Use `firewall-cmd`
   * Show how to temporarily stop firewall only for controlled testing
   * Explain not to permanently disable firewall in production

7. Python version:

   * Provide complete Python multicast consumer code
   * Consumer should join multicast group using interface IP
   * Provide complete Python multicast producer code
   * Producer should send to multicast group using `bond0` source IP
   * Use `IP_MULTICAST_IF`
   * Use `IP_MULTICAST_TTL`
   * Show how to run producer and consumer
   * Show expected output

8. Non-Python command-line version:

   * Install required tools on RHEL 9:
     `socat`, `nmap-ncat`, `tcpdump`, `net-tools`
   * Use `socat` as the main multicast receiver and sender
   * Explain why plain `nc`/`netcat` is usually not enough for multicast receiving
   * Use `ncat` for simple unicast UDP testing
   * Show multicast consumer command using `socat`
   * Show multicast producer command using `socat`
   * Show repeated producer loop
   * Show unicast UDP test using `ncat`

9. Process and packet validation:

   * How to check if producer process is running
   * How to check if consumer process is listening
   * Use `ss -uapn`
   * Use `ps -ef`
   * Use `tcpdump` on producer to prove packets are leaving
   * Use `tcpdump` on consumer to prove packets are arriving
   * Use `ip maddr show dev bond0`
   * Use `netstat -g`

10. Troubleshooting flow:

    * If ping fails, fix unicast first
    * If producer tcpdump does not show packets, check producer/app/interface/route
    * If producer tcpdump shows packets but consumer tcpdump does not, check network/switch/VLAN/IGMP/firewall
    * If consumer tcpdump sees packets but app does not print, check app bind/group join/firewall/port
    * Include common failure cases and fixes

11. Multiple-node validation:

    * Assume Node A: `169.91.200.111`
    * Node B: `169.91.200.112`
    * Node C: `169.91.200.113`
    * Provide ping matrix
    * Provide multicast test from one producer to multiple consumers
    * Provide commands to run on every node

12. Final memory hook:

    * Give me a simple memory hook to troubleshoot in order
    * Example: `LISA-MAP`

      * Link
      * IP
      * Subnet
      * ARP
      * Multicast group
      * Application socket
      * Packets
    * For each step, provide the exact Linux command

Make the answer practical and command-heavy. Use clear sections. Assume I am learning gradually and want to reproduce this on real RHEL 9 servers.
