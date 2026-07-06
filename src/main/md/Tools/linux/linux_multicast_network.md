# RHEL 9 UDP Multicast Tutorial on `bond0`

This tutorial walks through designing, validating, testing, and troubleshooting UDP multicast on RHEL 9 using interface `bond0`.

The examples use:

* Interface: `bond0`
* Node A: `169.91.200.111/26`
* Node B: `169.91.200.112/26`
* Node C: `169.91.200.113/26`
* Multicast group: `239.91.200.111`
* UDP port: `5000`
* Same-subnet multicast TTL: `1`

The tutorial proves multicast in layers. First prove the host and `bond0`, then unicast, then multicast routing and firewall, then application behavior, then packet flow.

## 1. Understand the Subnet

The example host address is:

```text
169.91.200.111/26
```

The `/26` is CIDR notation. It means the first 26 bits identify the network and the remaining 6 bits identify hosts.

For `169.91.200.111/26`:

| Item | Value |
|---|---|
| CIDR | `/26` |
| Subnet mask | `255.255.255.192` |
| Block size | `64` addresses |
| Network address | `169.91.200.64` |
| First usable IP | `169.91.200.65` |
| Last usable IP | `169.91.200.126` |
| Broadcast address | `169.91.200.127` |
| Total addresses | `64` |
| Usable host addresses | `62` |

`169.91.200.111/26` is valid because `.111` is between `.65` and `.126`.

Example same-subnet node design:

| Node | IP address | Valid in subnet? |
|---|---:|---|
| Node A | `169.91.200.111/26` | Yes |
| Node B | `169.91.200.112/26` | Yes |
| Node C | `169.91.200.113/26` | Yes |

Do not use the network address `.64` or broadcast address `.127` as a node address.

## 2. Multicast Design Used in This Tutorial

UDP multicast uses one sender and one or more receivers.

* Producer: sends UDP datagrams to a multicast group and port.
* Consumer: joins the multicast group and receives UDP datagrams on that port.
* Network: forwards multicast packets to interested receivers.

Use this application multicast design:

| Setting | Value |
|---|---|
| Multicast group | `239.91.200.111` |
| UDP port | `5000` |
| Interface | `bond0` |
| Producer source IP | Node A, `169.91.200.111` |
| Consumer IPs | Node B/C, `169.91.200.112`, `169.91.200.113` |
| TTL | `1` for same subnet |

Use `239.x.x.x` for private application multicast. This range is administratively scoped and is normally suitable for internal applications.

Do not send multicast traffic to a normal unicast IP like `169.91.200.112`. A unicast destination identifies one host. A multicast destination identifies a group that multiple hosts can join.

## 3. Install Tools on RHEL 9

Run this on every node:

```bash
sudo dnf install -y socat nmap-ncat tcpdump net-tools lsof ethtool
```

Useful tools:

| Tool | Purpose |
|---|---|
| `ip` | Address, route, link, neighbor, multicast checks |
| `nmcli` | NetworkManager connection checks and persistent routes |
| `ss` | UDP/TCP socket proof |
| `tcpdump` | Packet proof |
| `socat` | Multicast sender/receiver testing |
| `ncat` | Simple unicast UDP testing |
| `firewall-cmd` | RHEL firewall validation |
| `lsof` | Process socket inspection |
| `ethtool` | Link driver/speed/duplex checks |

## 4. Validate `bond0`

Start with the interface. Multicast cannot work reliably if the link, bond, address, or route is wrong.

### 4.1 Check Link State

```bash
ip -br link show dev bond0
ip -details link show dev bond0
```

Look for:

* `UP`
* `LOWER_UP`
* `MULTICAST`
* Expected MTU

If `bond0` is down:

```bash
sudo ip link set dev bond0 up
```

### 4.2 Check IP Address

```bash
ip -br addr show dev bond0
ip addr show dev bond0
```

Expected on Node A:

```text
169.91.200.111/26
```

Expected on Node B:

```text
169.91.200.112/26
```

Expected on Node C:

```text
169.91.200.113/26
```

### 4.3 Check MAC Address and MTU

```bash
ip link show dev bond0
cat /sys/class/net/bond0/address
cat /sys/class/net/bond0/mtu
```

All nodes do not need the same MAC address. Each node should have its own unique MAC address for `bond0`.

### 4.4 Check Bonding Status

```bash
cat /proc/net/bonding/bond0
```

Look for:

* Bonding mode
* Currently active slave
* MII status: `up`
* Slave interfaces
* Link failure count
* Speed and duplex

If a slave is down, inspect the physical interface:

```bash
ip -br link
ethtool <slave-interface>
```

Example:

```bash
ethtool ens1f0
ethtool ens1f1
```

### 4.5 Check NetworkManager

```bash
nmcli device status
nmcli connection show --active
nmcli -f GENERAL,IP4 device show bond0
```

Find the active NetworkManager connection for `bond0`:

```bash
nmcli -t -f NAME,DEVICE connection show --active | awk -F: '$2=="bond0"{print $1}'
```

Save it for later:

```bash
CONN=$(nmcli -t -f NAME,DEVICE connection show --active | awk -F: '$2=="bond0"{print $1; exit}')
echo "$CONN"
```

## 5. Prove Unicast Before Multicast

Do not troubleshoot multicast before basic unicast works. All nodes must be able to reach each other on the same subnet.

### 5.1 Check Routes

```bash
ip route
ip route get 169.91.200.112
ip route get 169.91.200.113
```

On Node A, `ip route get 169.91.200.112` should use `bond0`.

### 5.2 Ping Matrix

Run these from Node A:

```bash
ping -c 3 169.91.200.112
ping -c 3 169.91.200.113
```

Run these from Node B:

```bash
ping -c 3 169.91.200.111
ping -c 3 169.91.200.113
```

Run these from Node C:

```bash
ping -c 3 169.91.200.111
ping -c 3 169.91.200.112
```

If ping fails, fix unicast first.

### 5.3 Check ARP and Neighbor State

```bash
ip neigh show dev bond0
```

Expected good states include:

* `REACHABLE`
* `STALE`
* `DELAY`

Bad signs:

* `FAILED`
* Missing neighbor entry after ping
* Wrong MAC address for a peer

Flush and retest if needed:

```bash
sudo ip neigh flush dev bond0
ping -c 3 169.91.200.112
ip neigh show dev bond0
```

## 6. Validate Linux Multicast Settings

### 6.1 Check That `bond0` Supports Multicast

```bash
ip -details link show dev bond0 | grep -o MULTICAST
```

If the `MULTICAST` flag is missing:

```bash
sudo ip link set dev bond0 multicast on
ip -details link show dev bond0
```

### 6.2 Check the Multicast Route

Linux normally knows that `224.0.0.0/4` is multicast, but explicit routing can help when the host has multiple interfaces.

```bash
ip route get 239.91.200.111
ip route show 224.0.0.0/4
```

If the group routes through the wrong interface, add a route:

```bash
sudo ip route add 224.0.0.0/4 dev bond0
ip route get 239.91.200.111
```

If the route already exists, replace it:

```bash
sudo ip route replace 224.0.0.0/4 dev bond0
```

### 6.3 Persist the Multicast Route with `nmcli`

Find the `bond0` connection:

```bash
CONN=$(nmcli -t -f NAME,DEVICE connection show --active | awk -F: '$2=="bond0"{print $1; exit}')
echo "$CONN"
```

Add a persistent multicast route:

```bash
sudo nmcli connection modify "$CONN" +ipv4.routes "224.0.0.0/4"
sudo nmcli connection up "$CONN"
ip route show 224.0.0.0/4
```

If your NetworkManager version requires a next hop field, use `0.0.0.0`:

```bash
sudo nmcli connection modify "$CONN" +ipv4.routes "224.0.0.0/4 0.0.0.0"
sudo nmcli connection up "$CONN"
```

### 6.4 Check Reverse Path Filtering

Reverse path filtering can drop packets when the return path does not match the receiving interface. It is more common with multihomed hosts, asymmetric routing, or traffic arriving on an interface Linux does not expect.

Check it:

```bash
sysctl net.ipv4.conf.all.rp_filter
sysctl net.ipv4.conf.default.rp_filter
sysctl net.ipv4.conf.bond0.rp_filter
```

For controlled testing, disable it temporarily:

```bash
sudo sysctl -w net.ipv4.conf.all.rp_filter=0
sudo sysctl -w net.ipv4.conf.default.rp_filter=0
sudo sysctl -w net.ipv4.conf.bond0.rp_filter=0
```

For production, decide with your network/security team. Loose mode `2` is often safer than strict mode `1` on multihomed systems:

```bash
sudo sysctl -w net.ipv4.conf.bond0.rp_filter=2
```

## 7. Validate Firewall Rules

Open UDP port `5000` on every consumer node.

Check active zones:

```bash
sudo firewall-cmd --get-active-zones
```

Open the port temporarily:

```bash
sudo firewall-cmd --add-port=5000/udp
```

Open the port permanently:

```bash
sudo firewall-cmd --permanent --add-port=5000/udp
sudo firewall-cmd --reload
sudo firewall-cmd --list-ports
```

If you use a named zone, include it explicitly:

```bash
sudo firewall-cmd --zone=public --permanent --add-port=5000/udp
sudo firewall-cmd --reload
```

For controlled testing only, you can stop the firewall briefly:

```bash
sudo systemctl stop firewalld
```

Restart it immediately after the test:

```bash
sudo systemctl start firewalld
sudo systemctl status firewalld
```

Do not permanently disable the firewall in production.

## 8. Python Multicast Test

This test proves application-level multicast with explicit interface selection.

### 8.1 Python Consumer

Create `multicast_consumer.py`:

```python
#!/usr/bin/env python3
import argparse
import socket
import struct


parser = argparse.ArgumentParser(description="UDP multicast consumer")
parser.add_argument("--group", default="239.91.200.111")
parser.add_argument("--port", type=int, default=5000)
parser.add_argument("--iface-ip", required=True, help="Local IP on bond0")
args = parser.parse_args()

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

try:
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)
except AttributeError:
    pass
except OSError:
    pass

sock.bind(("", args.port))

mreq = socket.inet_aton(args.group) + socket.inet_aton(args.iface_ip)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

print(f"listening group={args.group} port={args.port} iface_ip={args.iface_ip}")

while True:
    data, addr = sock.recvfrom(65535)
    print(f"from={addr} bytes={len(data)} message={data.decode(errors='replace')}")
```

Run it on Node B:

```bash
python3 multicast_consumer.py --iface-ip 169.91.200.112
```

Run it on Node C:

```bash
python3 multicast_consumer.py --iface-ip 169.91.200.113
```

Expected startup:

```text
listening group=239.91.200.111 port=5000 iface_ip=169.91.200.112
```

### 8.2 Python Producer

Create `multicast_producer.py`:

```python
#!/usr/bin/env python3
import argparse
import socket
import struct
import time


parser = argparse.ArgumentParser(description="UDP multicast producer")
parser.add_argument("--group", default="239.91.200.111")
parser.add_argument("--port", type=int, default=5000)
parser.add_argument("--iface-ip", required=True, help="Local IP on bond0")
parser.add_argument("--ttl", type=int, default=1)
parser.add_argument("--interval", type=float, default=1.0)
args = parser.parse_args()

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_IF, socket.inet_aton(args.iface_ip))
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, struct.pack("B", args.ttl))

counter = 0
print(f"sending group={args.group} port={args.port} iface_ip={args.iface_ip} ttl={args.ttl}")

while True:
    counter += 1
    message = f"hello multicast {counter} from {args.iface_ip}"
    sock.sendto(message.encode(), (args.group, args.port))
    print(message)
    time.sleep(args.interval)
```

Run it on Node A:

```bash
python3 multicast_producer.py --iface-ip 169.91.200.111
```

Expected consumer output:

```text
from=('169.91.200.111', 54321) bytes=42 message=hello multicast 1 from 169.91.200.111
from=('169.91.200.111', 54321) bytes=42 message=hello multicast 2 from 169.91.200.111
```

If the producer prints messages but the consumer does not, move to packet proof with `tcpdump`.

## 9. Non-Python Command-Line Test

Use `socat` for multicast because it supports multicast group membership options. Plain `nc` or `netcat` is usually not enough for multicast receiving because a multicast consumer must join the group with `IP_ADD_MEMBERSHIP`.

Use `ncat` for simple unicast UDP tests.

### 9.1 Unicast UDP Test with `ncat`

On Node B:

```bash
ncat -u -l 5000
```

On Node A:

```bash
printf 'hello unicast\n' | ncat -u 169.91.200.112 5000
```

If this fails, troubleshoot unicast, firewall, and the local listener before multicast.

### 9.2 Multicast Consumer with `socat`

On Node B:

```bash
socat -u UDP4-RECVFROM:5000,ip-add-membership=239.91.200.111:169.91.200.112,reuseaddr,fork -
```

On Node C:

```bash
socat -u UDP4-RECVFROM:5000,ip-add-membership=239.91.200.111:169.91.200.113,reuseaddr,fork -
```

### 9.3 Multicast Producer with `socat`

On Node A:

```bash
printf 'hello multicast from node A\n' | socat -u - UDP4-DATAGRAM:239.91.200.111:5000,ip-multicast-if=169.91.200.111,ip-multicast-ttl=1
```

Repeated producer loop:

```bash
while true; do
  printf 'hello multicast %s\n' "$(date -Is)"
  sleep 1
done | socat -u - UDP4-DATAGRAM:239.91.200.111:5000,ip-multicast-if=169.91.200.111,ip-multicast-ttl=1
```

## 10. Process and Packet Validation

Use this section to prove what is happening instead of guessing.

### 10.1 Check Producer or Consumer Process

```bash
ps -ef | grep -E 'multicast_producer|multicast_consumer|socat|python' | grep -v grep
pgrep -af 'multicast_producer|multicast_consumer|socat|python'
```

### 10.2 Check UDP Listeners

```bash
ss -uapn | grep ':5000'
```

You may see the consumer bound to `0.0.0.0:5000`, `*:5000`, or the specific node IP. A multicast consumer often binds to all local addresses and then joins the group on one interface.

### 10.3 Check Multicast Membership

On the consumer:

```bash
ip maddr show dev bond0
netstat -g
cat /proc/net/igmp
```

Look for `239.91.200.111`. If the consumer process is running but the group is not present, the app probably did not join the group correctly.

### 10.4 Prove Packets Leave the Producer

On Node A:

```bash
sudo tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"
```

Then run the producer. If nothing appears, check:

* Producer process
* Destination group and port
* `IP_MULTICAST_IF`
* Multicast route
* `bond0` status

### 10.5 Prove Packets Arrive at the Consumer

On Node B or Node C:

```bash
sudo tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"
```

Also test on all interfaces:

```bash
sudo tcpdump -ni any "udp and host 239.91.200.111 and port 5000"
```

If packets appear on `any` but not on `bond0`, the traffic is likely using another interface, VLAN interface, container namespace, or route.

## 11. Retained Process Investigation Blocks

This section retains the original investigation command blocks. Use it when troubleshooting a real running application process, not just the Python or `socat` examples.

### 11.1 Identify Process and Config

```bash
hostname -f
PID=$(pgrep -f "UA2FRT01|exb-fixrouter|FixRouterRunner" | head -1)
echo "PID=$PID"
tr "\0" " " < /proc/$PID/cmdline; echo

tr "\0" "\n" < /proc/$PID/environ | sort | grep -E "LBM|JAVA|LD_LIBRARY|PATH|CONFIG|INSTANCE|APOLLO"
readlink -f /proc/$PID/cwd
```

This identifies a running process matching the sample names, prints the PID, shows the exact launch command, filters relevant environment variables, and shows the working directory.

### 11.2 UDP and TCP Socket Proof

```bash
ss -H -u -a -n -p | grep -E "pid=$PID,|java" || true
ss -H -t -a -n -p | grep -E "pid=$PID,|java|15658|6763" || true
lsof -Pan -p $PID -iUDP -iTCP 2>/dev/null || true

# Count UDP sockets by local port.
ss -H -u -a -n -p | grep "pid=$PID," | awk "{print $5}" | sort | uniq -c
```

This proves whether the process owns UDP or TCP sockets and summarizes UDP socket usage by local port.

### 11.3 Multicast and Packet Proof

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

This lists multicast memberships, determines the route/interface for a multicast group, and captures packets on the selected interface and globally.

### 11.4 Find Multicast Group of a Process

To identify which multicast group a process has joined, use socket statistics:

```bash
ss -p -m -u | grep "pid=<PID>"
```

Options:

* `-p`: shows the process using the socket
* `-m`: shows socket memory and extended socket information, which may include membership details depending on kernel/tool output
* `-u`: limits output to UDP sockets

List all multicast memberships:

```bash
ip maddr show
```

## 12. Adapt the Process Investigation to Your App

Replace the sample process matcher with your own producer, consumer, Java process, or service pattern.

```bash
APP_PATTERN="my-producer|my-consumer|python.*multicast|java.*FixRouterRunner"
PID=$(pgrep -f "$APP_PATTERN" | head -1)
echo "PID=$PID"
```

A running process alone is not proof that multicast is working. Prove each layer separately:

| Proof | What to verify |
|---|---|
| Process exists | PID is present and stable |
| Launch command | Correct binary, script, config, and instance |
| Environment | Expected group, port, interface, Java, library, config variables |
| UDP socket | Process opened a UDP socket |
| Bind address | Socket is bound to expected address and port |
| Group join | Consumer joined the expected multicast group |
| Route | Kernel selected `bond0` for the group |
| Producer packets | Packets leave producer host |
| Consumer packets | Packets arrive at consumer host |
| App handling | Application reads and processes messages |

### 12.1 Inspect Process Identity

```bash
ps -fp "$PID"
tr "\0" " " < "/proc/$PID/cmdline"; echo
readlink -f "/proc/$PID/exe"
readlink -f "/proc/$PID/cwd"
ls -l "/proc/$PID/fd" | head
```

### 12.2 Inspect UDP Sockets for One Process

```bash
ss -uapn | grep -E "pid=$PID,"
lsof -Pan -p "$PID" -iUDP 2>/dev/null
```

### 12.3 Inspect Consumer Multicast Membership

```bash
GROUP=239.91.200.111
ip maddr show dev bond0
cat /proc/net/igmp
ss -uapnmi | grep -E "pid=$PID,|$GROUP|:5000"
```

### 12.4 Prove Route and Interface Selection

```bash
GROUP=239.91.200.111
ip route get "$GROUP"
ip route show 224.0.0.0/4
ip -details link show dev bond0
```

### 12.5 Producer-Side Packet Proof

```bash
GROUP=239.91.200.111
PORT=5000
timeout 15 tcpdump -ni bond0 "udp and host $GROUP and port $PORT"
```

### 12.6 Consumer-Side Packet Proof

```bash
GROUP=239.91.200.111
PORT=5000
timeout 15 tcpdump -ni bond0 "udp and host $GROUP and port $PORT"
timeout 15 tcpdump -ni any "udp and host $GROUP and port $PORT"
```

## 13. Troubleshooting Flow

Use this order. Do not skip to multicast before link and unicast are correct.

### 13.1 If Ping Fails

Fix unicast first:

```bash
ip -br addr show dev bond0
ip route
ip route get <peer-ip>
ping -c 3 <peer-ip>
ip neigh show dev bond0
```

Check:

* Wrong IP or prefix
* Wrong VLAN
* `bond0` down
* Slave links down
* Firewall blocking ICMP
* ARP failure
* Duplicate IP

### 13.2 If Producer `tcpdump` Shows No Packets

Check the producer:

```bash
pgrep -af 'producer|python|socat|java'
ss -uapn | grep ':5000'
ip route get 239.91.200.111
ip -details link show dev bond0
```

Likely causes:

* Producer not running
* Wrong group
* Wrong port
* Wrong interface source IP
* Missing `IP_MULTICAST_IF`
* Bad route to multicast group

### 13.3 If Producer Sees Packets but Consumer Does Not

Check the network path:

```bash
sudo tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"
ip maddr show dev bond0
sudo firewall-cmd --list-ports
```

Likely causes:

* Wrong VLAN
* Switch IGMP snooping issue
* Missing IGMP querier
* Multicast disabled or filtered on switch
* TTL too low for routed multicast
* Firewall blocks UDP `5000`
* Consumer joined wrong group/interface

For same subnet testing, TTL `1` is correct. If multicast must cross routers, routing and TTL require a separate routed multicast design.

### 13.4 If Consumer `tcpdump` Sees Packets but App Does Not Print

Check the consumer app:

```bash
ss -uapn | grep ':5000'
ip maddr show dev bond0
cat /proc/net/igmp
journalctl -xe
```

Likely causes:

* App bound to wrong address
* App uses wrong port
* App did not call `IP_ADD_MEMBERSHIP`
* App joined group on wrong interface IP
* App did not set `SO_REUSEADDR` when needed
* Another process has a conflicting socket
* Firewall or SELinux blocks delivery

### 13.5 Process-Level Failure Patterns

| Symptom | Fix |
|---|---|
| Process is not running | Start service, check `systemctl status`, inspect `journalctl -u <service>` |
| Process runs but no UDP socket appears | Check app config, startup arguments, and startup logs |
| UDP socket appears on wrong port | Correct application config or command-line port |
| UDP socket binds only to `127.0.0.1` | Bind to `0.0.0.0`, `169.91.200.111`, or correct interface IP |
| Consumer socket exists but no membership appears | Check `IP_ADD_MEMBERSHIP`, group, interface IP, startup errors |
| Route to group uses wrong interface | Add/fix `224.0.0.0/4 dev bond0` and persist with `nmcli` |
| Producer sends from wrong interface | Set `IP_MULTICAST_IF` or correct `socat` interface option |
| Producer sees packets but consumer does not | Check switch, VLAN, IGMP snooping, querier, TTL, firewall |
| Consumer sees packets but app does not | Check bind, port, group join, `SO_REUSEADDR`, firewall, SELinux |
| Packets appear on `any` but not `bond0` | Check VLAN subinterfaces, namespaces, containers, alternate routes |

## 14. Concise Process Troubleshooting Ladder

Run this when you need a compact end-to-end proof for one app process.

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

## 15. Multiple-Node Validation Runbook

Use this exact order for Node A, Node B, and Node C.

### 15.1 On Every Node

```bash
ip -br addr show dev bond0
ip -details link show dev bond0
cat /proc/net/bonding/bond0
ip route
ip route get 239.91.200.111
ip maddr show dev bond0
sudo firewall-cmd --list-ports
```

### 15.2 On Consumers, Node B and Node C

Open firewall:

```bash
sudo firewall-cmd --permanent --add-port=5000/udp
sudo firewall-cmd --reload
```

Start consumers:

```bash
python3 multicast_consumer.py --iface-ip 169.91.200.112
```

```bash
python3 multicast_consumer.py --iface-ip 169.91.200.113
```

### 15.3 On Producer, Node A

Run packet proof:

```bash
sudo tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"
```

Start producer:

```bash
python3 multicast_producer.py --iface-ip 169.91.200.111
```

Both consumers should print messages from Node A.

## 16. Memory Hook: LISA-MAP

Use `LISA-MAP` to remember the troubleshooting order.

| Step | Meaning | Command |
|---|---|---|
| L | Link | `ip -details link show dev bond0` |
| I | IP | `ip -br addr show dev bond0` |
| S | Subnet | `ip route get <peer-ip>` |
| A | ARP | `ip neigh show dev bond0` |
| M | Multicast group | `ip maddr show dev bond0` |
| A | Application socket | `ss -uapn | grep ':5000'` |
| P | Packets | `tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"` |

Quick version:

```bash
ip -details link show dev bond0
ip -br addr show dev bond0
ip route get 169.91.200.112
ip neigh show dev bond0
ip route get 239.91.200.111
ip maddr show dev bond0
ss -uapn | grep ':5000'
sudo tcpdump -ni bond0 "udp and host 239.91.200.111 and port 5000"
```

## 17. Final Rule

Troubleshoot in this order:

```text
bond0 link -> IP/subnet -> unicast ping -> ARP -> multicast route -> firewall -> app socket -> group membership -> tcpdump producer -> tcpdump consumer -> app logs
```

If one layer fails, fix that layer before moving to the next one.
