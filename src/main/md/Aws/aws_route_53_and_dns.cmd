nslookup -type=ns benpiper.com a.root-servers.net
nslookup -type=ns google.com a.root-servers.net
nslookup mail.rediff.com 1.1.1.1 
nslookup -type=ns mail.rediff.com 1.1.1.1
nslookup -type=ns benpiper.com a.gtld-severs.net
nslookup -type=A benpiper.com ns-816.awsdns-38.net

## Nslookup Type

* NS - Query the name-server that has information about domain
* A - Query the IP address for the given domain (you should query apporpriate name-server)


## AWS - Route 53

* Creating public hosted zones and simple records
* Configuration health checks and fail over records
* Weighted records for distributing traffic
* Geolocation and latency-based routing policy
* Creating traffic flow policies
* Load balancing with multivalue answer routing policies
* Creating private hosted zones for Amazon VPC
* Transferring existing domain to Amazon route 53

## DNS Zones

* A DNS zone is a distinct part of the domain namespace which is delegated to a legal entity
* A DNS zone is also an administrative function, allowing for granular control of DNS components, such as authoritative name servers.

## 

## References
* [IANA root servers](https://www.iana.org/domains/root/servers)
