Setting up and troubleshooting UDP multicast on Linux can feel like a dark art. Unlike unicast, where packets travel straight from A to B, multicast relies on subscriptions (IGMP), specific routing behavior, and precise socket bindings. When it fails, it usually fails silently.

Here is your comprehensive, step-by-step guide to designing, validating, and troubleshooting a UDP multicast network on RHEL 9 over a `bond0` interface.

* Node IP example: `169.91.200.111/26`
* Interface: `bond0`
* OS: RHEL 9
* Need both:
---
This is an excellent **"first 5 minutes of troubleshooting"** checklist. The trick to remembering these commands is not the syntax, but the **question each command answers**.

Think of troubleshooting in this order:

> **1. Do I have a network card?**
> **2. Does it have an IP?**
> **3. Will Linux use it?**
> **4. Did I join multicast?**
> **5. Is my application actually listening?**

I'll explain each command with **3 practical troubleshooting scenarios**.

---

# 1. `ip -br link`

```bash
ip -br link
```

Answers:

> "What network interfaces exist and are they UP?"

Example:

```bash
lo               UNKNOWN
eno1             UP
eno2             UP
bond0            UP
```

---

## Use case 1 — Bond interface missing

You expect:

```bash
bond0 UP
```

But see:

```bash
eno1 UP
eno2 UP
```

### Conclusion

Bond wasn't created.

Before debugging UME, fix bonding.

---

## Use case 2 — Interface DOWN

You see:

```bash
bond0 DOWN
```

### Conclusion

Cable unplugged,
switch issue,
or interface administratively down.

Fix:

```bash
ip link set bond0 up
```

---

## Use case 3 — Wrong interface

Application configured:

```
169.91.200.x
```

But machine only has:

```bash
ens192
ens224
```

No bond exists.

### Conclusion

UME configured for non-existent interface.

---

## Memory hook

> **LINK = Does the wire exist?**

---

# 2. `ip -br addr`

```bash
ip -br addr
```

Answers:

> "What IP addresses belong to each interface?"

Example:

```bash
bond0 UP 169.91.200.111/26
```

---

## Use case 1 — IP missing

You expect:

```bash
bond0 169.91.200.111
```

But see:

```bash
bond0 UP
```

### Conclusion

Interface exists but no IP.

---

## Use case 2 — Wrong subnet

Expected:

```
169.91.200.x
```

Actual:

```
192.168.10.x
```

### Conclusion

Application configured for wrong network.

---

## Use case 3 — Duplicate network

Node A:

```
169.91.200.111
```

Node B:

```
169.91.201.112
```

### Conclusion

Different subnet.

No communication.

---

## Memory hook

> **ADDR = Does the wire have a house number?**

---

# 3. `ip -o -4 addr show | grep 169.91.200`

Example:

```bash
ip -o -4 addr show | grep 169.91.200
```

Answers:

> "Which interface owns my deployment subnet?"

Example:

```bash
2: bond0 inet 169.91.200.111/26
```

---

## Use case 1 — Multiple NICs

Server:

```bash
eno1 10.x
bond0 169.91.200.x
eth5 172.x
```

Which one should UME use?

This command tells you.

---

## Use case 2 — Wrong bind address

UME config:

```
169.91.200.111
```

Machine has:

```
169.91.201.111
```

No match.

---

## Use case 3 — Typo in configuration

Config:

```
169.91.20.111
```

Actual:

```
169.91.200.111
```

Easy to miss.

---

## Memory hook

> **WHO OWNS THIS IP?**

---

# 4. `ip route`

```bash
ip route
```

or

```bash
ip route get 169.91.200.112
```

Answers:

> "Which interface will Linux actually use?"

Example:

```bash
169.91.200.112 dev bond0 src 169.91.200.111
```

---

## Use case 1 — Wrong interface selected

Expected:

```
bond0
```

Actual:

```
eno1
```

### Conclusion

Routing table issue.

---

## Use case 2 — No route

Output:

```bash
RTNETLINK answers: Network unreachable
```

### Conclusion

Linux cannot reach destination.

---

## Use case 3 — Multiple networks

Server:

```
management network
trading network
backup network
```

You want:

```
trade -> bond0
```

Verify:

```bash
ip route get
```

---

## Memory hook

> **ROUTE = Which road will Linux drive on?**

---

# 5. `ip maddr show dev bond0`

Example:

```bash
ip maddr show dev bond0
```

Answers:

> "Which multicast groups have been joined?"

Example:

```bash
inet 239.1.1.1
inet 239.1.1.2
```

---

## Use case 1 — Receiver never joined

Expected:

```
239.1.1.1
```

Actual:

Nothing.

### Conclusion

Application never subscribed.

---

## Use case 2 — Wrong multicast group

Sender:

```
239.1.1.1
```

Receiver:

```
239.1.1.2
```

### Conclusion

Packets discarded.

---

## Use case 3 — Wrong interface

Receiver joined multicast on:

```
eno1
```

Traffic arriving on:

```
bond0
```

### Conclusion

Multicast on wrong NIC.

---

## Memory hook

> **MADDR = Which radio station are you listening to?**

---

# 6. `ss -uapn`

```bash
ss -uapn
```

Answers:

> "Which process owns the UDP socket?"

Example:

```bash
udp UNCONN 0 0 239.1.1.1:5000
users:(("lbmrcv",pid=1234))
```

---

## Use case 1 — Application never started

You expect:

```
lbmrcv
```

But:

```
nothing
```

### Conclusion

Application not running.

---

## Use case 2 — Wrong port

Sender:

```
5000
```

Receiver:

```
6000
```

### Conclusion

No packets delivered.

---

## Use case 3 — Another process owns the port

You expect:

```
myreceiver
```

Actual:

```
java
```

### Conclusion

Port collision.

---

## Memory hook

> **SS = Who owns the telephone line?**

---

# Complete UME troubleshooting flow

When publisher and subscriber cannot communicate:

```
1. ip -br link
   ↓
   Is interface UP?

2. ip -br addr
   ↓
   Does interface have correct IP?

3. ip route get <peer>
   ↓
   Will Linux use the right NIC?

4. ip maddr show
   ↓
   Did receiver join multicast?

5. ss -uapn
   ↓
   Is application listening?

6. tcpdump
   ↓
   Are packets actually arriving?
```

---

# Harry Lorayne style memory story

Imagine a **postman delivering a multicast radio**:

| Command       | Visual memory                                 |
| ------------- | --------------------------------------------- |
| `ip -br link` | Is there a road?                              |
| `ip -br addr` | Does the house have an address?               |
| `ip route`    | Which road does the postman take?             |
| `ip maddr`    | Which radio station is the house tuned to?    |
| `ss -uapn`    | Is somebody inside the house listening?       |
| `tcpdump`     | Did the postman actually deliver the package? |

Remember:

> **Road → House → Route → Radio → Listener → Packet**.

If you follow these six questions in order, you'll solve most Linux multicast/UME connectivity issues before even opening the application logs.

---

### 1. Basic Networking Introduction

Before touching Linux configurations, we must ensure the fundamental IP mathematics are correct.

**What is a `/26` Subnet?**
The `/26` refers to CIDR (Classless Inter-Domain Routing) notation. It means the first 26 bits of the 32-bit IP address are locked as the network identifier, leaving 6 bits for host addresses.

* **Subnet Mask:** $255.255.255.192$
* **Network Address:** `169.91.200.64`
* **Usable IP Range:** `169.91.200.65` through `169.91.200.126`
* **Broadcast Address:** `169.91.200.127`
* **Number of usable nodes:** $2^{32-26} - 2 = 64 - 2 = 62$ nodes. (We subtract 2 for the network and broadcast addresses).

**Is `169.91.200.111/26` valid?**
Yes. `111` falls perfectly inside the usable range of `.65` to `.126`.

**What if it were a `/25`?**
A `/25` locks 25 bits, leaving 7 for hosts.

* **Mask:** $255.255.255.128$
* **Usable range:** `169.91.200.1` to `169.91.200.126` (if using the 0 subnet) or `.129` to `.254` (if using the 128 subnet).
* **Usable nodes:** 126.

**Example Node Design (Same `/26` Subnet):**

* **Node A (Producer):** `169.91.200.111`
* **Node B (Consumer 1):** `169.91.200.112`
* **Node C (Consumer 2):** `169.91.200.113`

---

### 2. Linux Interface Validation

Let's verify the physical and logical state of your `bond0` interface on RHEL 9.

* **Check link state, IP address, MAC, and MTU:**
```bash
ip addr show dev bond0

```


*Look for `UP` and `LOWER_UP` to confirm the link is active. Verify the MTU is standard (typically 1500).*
* **Check bonding status and slave interfaces:**
```bash
cat /proc/net/bonding/bond0

```


*This file shows the bonding mode (e.g., LACP/802.3ad or Active/Backup) and the MII status of the underlying physical slave interfaces (e.g., `eth0`, `eth1`).*
* **Check physical speed and duplex via ethtool:**
```bash
ethtool bond0

```


* **Check NetworkManager connection profile:**
```bash
nmcli device show bond0
nmcli connection show

```



---

### 3. Unicast Validation Before Multicast

**Rule of thumb:** If unicast fails, multicast will never work. Validate basic connectivity first.

* **Check the routing table:**
```bash
ip route

```


* **Simulate how the kernel routes to a specific peer (Node B):**
```bash
ip route get 169.91.200.112

```


* **Test basic ICMP Unicast:**
```bash
ping -c 4 169.91.200.112

```


* **Validate Address Resolution Protocol (ARP):**
```bash
ip neigh show dev bond0

```


*Troubleshooting ARP:* If a neighbor shows as `INCOMPLETE`, the kernel cannot find the MAC address for that IP. This indicates a Layer 2 issue (VLAN mismatch, switch port configuration, or incorrect cabling). It must read `REACHABLE` or `STALE`.

---

### 4. Multicast Design

* **Producer/Consumer Model:** Multicast works on a Publish/Subscribe model. A Producer "publishes" packets to a specific group IP. Consumers "subscribe" (join) that group IP via IGMP to receive copies of the packets.
* **Group and Port:** We will use `239.91.200.111` on UDP port `5000`.
* **Why not unicast?** Unicast IP acts as a point-to-point destination. If you want to send data to 10 nodes, a unicast producer must send 10 separate streams, wasting CPU and bandwidth. Multicast sends *one* stream; the network switches duplicate the packets to subscribed ports.
* **Why `239.x.x.x`?** RFC 2365 designates the `239.0.0.0/8` block as "Administratively Scoped IP Multicast." It is the private, local-use space for multicast, ensuring it won't route out to the public internet.
* **Time-To-Live (TTL) 1:** By setting TTL to 1, we instruct the IP packet to expire after one hop. This is a safety mechanism ensuring multicast traffic stays purely on your local subnet and doesn't accidentally flood adjacent routed networks.

---

### 5. Multicast Linux Validation

Linux needs explicit instructions on how to handle multicast routing.

* **Check interface `MULTICAST` flag:**
```bash
ip link show bond0

```


*Ensure `<BROADCAST,MULTICAST,UP,LOWER_UP>` is present.*
* **Enable MULTICAST (if missing):**
```bash
ip link set bond0 multicast on

```


* **Check and Add Multicast Route:** Sometimes Linux doesn't know which interface to use for multicast if no default route exists. Force it out `bond0`:
```bash
ip route add 224.0.0.0/4 dev bond0

```


* **Make Route Persistent via NetworkManager:**
```bash
nmcli connection modify bond0 +ipv4.routes "224.0.0.0/4"
nmcli connection up bond0

```


* **Reverse Path Filtering (`rp_filter`):**
This kernel security feature drops packets if the reply route doesn't match the incoming interface. This often breaks multi-homed multicast environments.
```bash
sysctl net.ipv4.conf.bond0.rp_filter

```


*If it returns `1` (strict) and multicast is dropping silently, you may need to set it to `2` (loose) or `0` (disabled) in `/etc/sysctl.conf`.*

---

### 6. Firewall Validation

RHEL 9 uses `firewalld` by default. You must open UDP port 5000.

* **Open port permanently:**
```bash
firewall-cmd --permanent --add-port=5000/udp
firewall-cmd --reload

```


* **Temporarily stop firewall (For isolation testing ONLY):**
```bash
systemctl stop firewalld

```


*Do not do this in production. However, disabling it for 5 minutes during a maintenance window is a highly effective way to prove whether the firewall or the application is to blame for dropped packets.*

---

### 7. Python Version

Here is the exact code required to produce and consume multicast streams.

**Consumer (`consumer.py`)**
Run this on Node B and Node C.

```python
import socket
import struct

MCAST_GRP = '239.91.200.111'
MCAST_PORT = 5000
# Interface IP of the node running the consumer
IF_IP = '169.91.200.112' 

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

# Bind to the port. 0.0.0.0 ensures it listens on all interfaces
sock.bind(('0.0.0.0', MCAST_PORT))

# Tell the kernel to join the multicast group via IGMP on the specific interface
mreq = socket.inet_aton(MCAST_GRP) + socket.inet_aton(IF_IP)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

print(f"Listening on {MCAST_GRP}:{MCAST_PORT} via {IF_IP}...")

while True:
    data, addr = sock.recvfrom(1024)
    print(f"Received {data.decode('utf-8')} from {addr}")

```

**Producer (`producer.py`)**
Run this on Node A.

```python
import socket
import time

MCAST_GRP = '239.91.200.111'
MCAST_PORT = 5000
# Interface IP of the producer node
IF_IP = '169.91.200.111'

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)

# Set TTL to 1 (local subnet only)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 1)
# Force the packets to exit via bond0 (using its IP)
sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_IF, socket.inet_aton(IF_IP))

print(f"Sending to {MCAST_GRP}:{MCAST_PORT} via {IF_IP}...")

counter = 1
while True:
    message = f"Multicast Message #{counter}"
    sock.sendto(message.encode('utf-8'), (MCAST_GRP, MCAST_PORT))
    print(f"Sent: {message}")
    counter += 1
    time.sleep(1)

```

---

### 8. Non-Python Command-Line Version

You can use standard CLI tools to test this without writing code. Standard `nc` (netcat) is usually insufficient for multicast because it lacks the ability to easily craft IGMP `IP_ADD_MEMBERSHIP` joins. `socat` handles this natively.

* **Install required tools on RHEL 9:**
```bash
dnf install -y socat nmap-ncat tcpdump net-tools

```


* **Unicast Test (using `ncat`):**
On Node B (listen): `ncat -u -l 5000`
On Node A (send): `echo "Unicast Test" | ncat -u 169.91.200.112 5000`
* **Multicast Consumer (Node B):**
This command binds to UDP 5000 and explicitly joins the multicast group using the node's local interface IP.
```bash
socat UDP4-RECVFROM:5000,ip-add-membership=239.91.200.111:169.91.200.112,fork STDOUT

```


* **Multicast Producer (Node A):**
This command sets the outgoing interface and sets TTL to 1.
```bash
while true; do \
  echo "Testing from CLI $(date)" | socat STDIN UDP4-DATAGRAM:239.91.200.111:5000,ip-multicast-if=169.91.200.111,ip-multicast-ttl=1; \
  sleep 1; \
done

```



---

### 9. Process and Packet Validation

To prove the systems are acting exactly as designed, use these commands on running servers:

* **Check if the process is bound to the port:**
```bash
ss -uapn | grep 5000

```


* **Verify IGMP Multicast Group Joins:**
```bash
ip maddr show dev bond0

```


*Alternatively:* `netstat -gn`
*(Look for `239.91.200.111` in the output. If it is missing on the consumer, the kernel hasn't joined the group, and the switch will not forward packets).*
* **Packet capture on Producer (Node A):** Prove packets are leaving.
```bash
tcpdump -i bond0 -nn dst host 239.91.200.111 and port 5000

```


* **Packet capture on Consumer (Node B):** Prove packets are arriving.
```bash
tcpdump -i bond0 -nn dst host 239.91.200.111 and port 5000

```



---

### 10. Troubleshooting Flow

When multicast is failing, apply this logical pipeline:

1. **Does Ping work?** If `ping 169.91.200.112` fails, fix your unicast layer (Layer 2 VLANs, cabling, ARP, basic IP config). Do not attempt to fix multicast yet.
2. **Does the Producer `tcpdump` show packets leaving?** If no, your application logic is failing, or you are missing the `224.0.0.0/4` route, and the kernel is routing packets out a different interface.
3. **Does the Producer `tcpdump` show packets, but Consumer `tcpdump` shows nothing?** The network switch is dropping the traffic. Check for IGMP Snooping configuration on your switch. If the switch has IGMP Snooping enabled but no IGMP Querier configured on the VLAN, it will drop multicast packets after ~260 seconds. Also, verify `ttl=1` isn't causing a router to drop it.
4. **Does Consumer `tcpdump` see packets, but the Application prints nothing?** The packet made it to the server hardware, but the Linux kernel rejected it before handing it to the Python/socat application.
* Check `firewall-cmd`.
* Check `rp_filter`.
* Check that the application successfully ran `IP_ADD_MEMBERSHIP`.



---

### 11. Multiple-Node Validation (Execution Order)

Assume Node A (`.111`), B (`.112`), and C (`.113`).

**Step 1: The Ping Matrix**
From Node A: `ping 169.91.200.112` and `ping 169.91.200.113`
From Node B: `ping 169.91.200.111` and `ping 169.91.200.113`
From Node C: `ping 169.91.200.111` and `ping 169.91.200.112`

**Step 2: Start Consumers**
On Node B: `python3 consumer.py` (or the `socat` receive command).
On Node C: `python3 consumer.py` (or the `socat` receive command).

**Step 3: Start Producer**
On Node A: `python3 producer.py` (or the `socat` send loop).

**Step 4: Observe**
Both Node B and Node C terminal windows should simultaneously begin scrolling with identical "Received" messages.

---

### 12. Final Memory Hook: LISA-MAP

When a multicast outage wakes you up at 3:00 AM, rely on the **LISA-MAP** troubleshooting flow. Run these in exactly this order:

* **L - Link:** `ip link show dev bond0`
*(Is the cable plugged in? Is the bond UP?)*
* **I - IP:** `ip addr show dev bond0`
*(Is the IP correct?)*
* **S - Subnet/Route:** `ip route`
*(Do we have a default or `224.0.0.0/4` route?)*
* **A - ARP:** `ip neigh show dev bond0`
*(Can we resolve MAC addresses via unicast?)*
* **M - Multicast Group:** `ip maddr show dev bond0`
*(Did the application actually tell the kernel to join the IGMP group?)*
* **A - Application Socket:** `ss -uapn | grep 5000`
*(Is the application actually listening on UDP port 5000?)*
* **P - Packets:** `tcpdump -i bond0 -nn dst host 239.91.200.111`
*(Are packets physically touching the wire?)*
