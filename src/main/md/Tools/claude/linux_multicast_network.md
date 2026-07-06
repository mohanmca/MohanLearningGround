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

13. Additional process-level UDP multicast investigation:

    * Also include a dedicated section for investigating a real running UDP multicast process.
    * Retain the following investigation material from `investigate.md`.
    * Keep the command examples in fenced `bash` code blocks.
    * Explain what each block proves and how to interpret the output.
    * Adapt the examples to the RHEL 9 multicast guide while keeping the original process-name example available.

### Investigation notes to retain from `investigate.md`

This document contains a series of diagnostic scripts for troubleshooting system processes, network sockets, and multicast traffic. Each section provides command-line instructions followed by an explanation of their function.

#### 9.1 Identify process and config

```bash
hostname -f
PID=$(pgrep -f "UA2FRT01|exb-fixrouter|FixRouterRunner" | head -1)
echo "PID=$PID"
tr "\0" " " < /proc/$PID/cmdline; echo

tr "\0" "\n" < /proc/$PID/environ | sort | grep -E "LBM|JAVA|LD_LIBRARY|PATH|CONFIG|INSTANCE|APOLLO"
readlink -f /proc/$PID/cwd
```

This block identifies a running process matching specific names, prints its Process ID (PID), displays the exact command used to launch it, lists its environment variables (filtered for configuration keywords), and shows the directory from which it was started.

#### 9.2 UDP and TCP socket proof

```bash
ss -H -u -a -n -p | grep -E "pid=$PID,|java" || true
ss -H -t -a -n -p | grep -E "pid=$PID,|java|15658|6763" || true
lsof -Pan -p $PID -iUDP -iTCP 2>/dev/null || true

# Count UDP sockets by local port.
ss -H -u -a -n -p | grep "pid=$PID," | awk "{print $5}" | sort | uniq -c
```

This block inspects network activity by listing all UDP and TCP sockets associated with the identified process, filters them for relevant activity, and lists open files related to those network connections. It also summarizes UDP socket usage by counting connections on each local port.

#### 9.3 Multicast and packet proof

```bash
ip maddr show
cat /proc/net/igmp
GROUP=239.204.228.146
ip route get $GROUP
IFACE=$(ip route get $GROUP | awk "{for(i=1;i<=NF;i++) if($i==\"dev\") print $(i+1); exit}")
echo "IFACE=$IFACE GROUP=$GROUP"
timeout 10 tcpdump -ni "$IFACE" "host $GROUP and udp"
timeout 10 tcpdump -ni any "host $GROUP or udp"
```

This block diagnoses multicast network traffic by listing current multicast group memberships, determining the network interface path for a specific multicast group, and then capturing packets for 10 seconds to verify traffic flow on that specific interface and globally across all interfaces.

#### 9.4 Finding Multicast Group of a Process

To identify which multicast group a process has joined, you can use socket statistics:

```bash
ss -p -m -u | grep "pid=<PID>"
```

* `-p`: Shows the process using the socket.
* `-m`: Shows the multicast group memberships.
* `-u`: Limits the search to UDP sockets, as multicast runs on UDP.

You can also list all multicast memberships on your network interfaces using:

```bash
ip maddr show
```

14. Additional UDP multicast process troubleshooting depth:

    * Show how to replace the sample process matcher with a generic application matcher:

      ```bash
      APP_PATTERN="my-producer|my-consumer|python.*multicast|java.*FixRouterRunner"
      PID=$(pgrep -f "$APP_PATTERN" | head -1)
      echo "PID=$PID"
      ```

    * Explain that a running process is not enough proof. Prove all of these separately:

      * The process exists.
      * The command line points to the expected binary, script, config file, and instance.
      * The process environment has the expected multicast, port, interface, Java, library, or config variables.
      * The process has UDP sockets open.
      * The UDP socket is bound to the expected local address and port.
      * The consumer has joined the expected multicast group.
      * The kernel selected `bond0` as the route/interface for the multicast group.
      * Packets leave the producer host.
      * Packets arrive at the consumer host.
      * The application actually reads from the socket and prints/processes messages.

    * Include commands to inspect process identity:

      ```bash
      ps -fp "$PID"
      tr "\0" " " < "/proc/$PID/cmdline"; echo
      readlink -f "/proc/$PID/exe"
      readlink -f "/proc/$PID/cwd"
      ls -l "/proc/$PID/fd" | head
      ```

    * Include commands to inspect UDP sockets for one process:

      ```bash
      ss -uapn | grep -E "pid=$PID,"
      lsof -Pan -p "$PID" -iUDP 2>/dev/null
      ```

    * Include commands to inspect whether a consumer joined the multicast group:

      ```bash
      GROUP=239.91.200.111
      ip maddr show dev bond0
      cat /proc/net/igmp
      ss -uapnmi | grep -E "pid=$PID,|$GROUP|:5000"
      ```

    * Include commands to prove route and interface selection:

      ```bash
      GROUP=239.91.200.111
      ip route get "$GROUP"
      ip route show 224.0.0.0/4
      ip -details link show dev bond0
      ```

    * Include producer-side packet proof:

      ```bash
      GROUP=239.91.200.111
      PORT=5000
      timeout 15 tcpdump -ni bond0 "udp and host $GROUP and port $PORT"
      ```

    * Include consumer-side packet proof:

      ```bash
      GROUP=239.91.200.111
      PORT=5000
      timeout 15 tcpdump -ni bond0 "udp and host $GROUP and port $PORT"
      timeout 15 tcpdump -ni any "udp and host $GROUP and port $PORT"
      ```

    * Include process-level failure patterns and fixes:

      * Process is not running: start the service, check `systemctl status`, check logs with `journalctl -u <service>`.
      * Process is running but no UDP socket appears: check app configuration, startup arguments, and whether the app failed before opening the socket.
      * UDP socket appears on the wrong port: correct the application config or command-line port.
      * UDP socket binds only to `127.0.0.1`: bind to `0.0.0.0`, `169.91.200.111`, or the correct interface-specific address depending on the app design.
      * Consumer socket exists but no multicast membership appears: check the app's `IP_ADD_MEMBERSHIP` call, group address, interface IP, and permissions/errors during startup.
      * Route to multicast group uses the wrong interface: add or fix `224.0.0.0/4 dev bond0` and persist it with `nmcli`.
      * Producer sends from the wrong source interface: set `IP_MULTICAST_IF` in code or use the correct `socat` interface options.
      * Producer tcpdump sees packets but consumer tcpdump does not: check switch multicast handling, VLAN, IGMP snooping, querier, routing, TTL, and firewall.
      * Consumer tcpdump sees packets but application does not: check bind address, UDP port, multicast group join, `SO_REUSEADDR`, firewall, SELinux logs, and whether another process consumed or conflicted with the socket.
      * Packets appear on `any` but not `bond0`: verify interface selection, VLAN subinterfaces, namespaces, containers, and whether the traffic is on a different physical or bonded interface.

    * Include a concise process troubleshooting ladder:

      ```bash
      # 1. Find process.
      pgrep -af "my-producer|my-consumer|python.*multicast|java"

      # 2. Capture PID.
      PID=$(pgrep -f "my-producer|my-consumer|python.*multicast|java" | head -1)
      echo "$PID"

      # 3. Prove launch command and config context.
      tr "\0" " " < "/proc/$PID/cmdline"; echo
      tr "\0" "\n" < "/proc/$PID/environ" | sort | grep -Ei "group|port|iface|interface|config|java|lbm|path"
      readlink -f "/proc/$PID/cwd"

      # 4. Prove UDP socket.
      ss -uapn | grep -E "pid=$PID,|:5000"

      # 5. Prove multicast membership.
      ip maddr show dev bond0
      cat /proc/net/igmp

      # 6. Prove route.
      ip route get 239.91.200.111

      # 7. Prove packets.
      timeout 15 tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"
      ```

Keep the merged answer practical and command-heavy. Use clear sections. Assume I am learning gradually and want to reproduce this on real RHEL 9 servers.
