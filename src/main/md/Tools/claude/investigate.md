This document contains a series of diagnostic scripts for troubleshooting system processes, network sockets, and multicast traffic. Each section provides command-line instructions followed by an explanation of their function.

9.1 Identify process and config


hostname -f
PID=$(pgrep -f "UA2FRT01|exb-fixrouter|FixRouterRunner" | head -1)
echo "PID=$PID"
tr "\0" " " < /proc/$PID/cmdline; echo

tr "\0" "\n" < /proc/$PID/environ | sort | grep -E "LBM|JAVA|LD_LIBRARY|PATH|CONFIG|INSTANCE|APOLLO"
readlink -f /proc/$PID/cwd
This block identifies a running process matching specific names, prints its Process ID (PID), displays the exact command used to launch it, lists its environment variables (filtered for configuration keywords), and shows the directory from which it was started.
9.2 UDP and TCP socket proof


ss -H -u -a -n -p | grep -E "pid=$PID,|java" || true
ss -H -t -a -n -p | grep -E "pid=$PID,|java|15658|6763" || true
lsof -Pan -p $PID -iUDP -iTCP 2>/dev/null || true

# Count UDP sockets by local port.
ss -H -u -a -n -p | grep "pid=$PID," | awk "{print $5}" | sort | uniq -c
This block inspects network activity by listing all UDP and TCP sockets associated with the identified process, filters them for relevant activity, and lists open files related to those network connections. It also summarizes UDP socket usage by counting connections on each local port.
9.3 Multicast and packet proof


ip maddr show
cat /proc/net/igmp
GROUP=239.204.228.146
ip route get $GROUP
IFACE=$(ip route get $GROUP | awk "{for(i=1;i<=NF;i++) if($i==\"dev\") print $(i+1); exit}")
echo "IFACE=$IFACE GROUP=$GROUP"
timeout 10 tcpdump -ni "$IFACE" "host $GROUP and udp"
timeout 10 tcpdump -ni any "host $GROUP or udp"
This block diagnoses multicast network traffic by listing current multicast group memberships, determining the network interface path for a specific multicast group, and then capturing packets for 10 seconds to verify traffic flow on that specific interface and globally across all interfaces.
9.4 Finding Multicast Group of a Process
To identify which multicast group a process has joined, you can use socket statistics:
ss -p -m -u | grep "pid=<PID>"
-p: Shows the process using the socket.
-m: Shows the multicast group memberships.
-u: Limits the search to UDP sockets, as multicast runs on UDP.
You can also list all multicast memberships on your network interfaces using:
ip maddr show

