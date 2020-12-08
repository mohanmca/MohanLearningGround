## Raft origin

* Raft was designed by Diego Ongaro and John Ousterhout
* [Raft slides](https://ongardie.github.io/raft-talk/)
* [Raft Paper](https://raft.github.io/raft.pdf)
* [Guy who designed explains Raft](https://www.youtube.com/watch?v=vYp4LYbnnW8)
  * Above talk was presented by Professor John Ousterhout on August 29, 2016 as part of the CS @ Illinois Distinguished Lecture Series
* [Raft Home](https://raft.github.io/)

## Why Consensus algorithm?

* Distributed consensus is a fundamental primitive for constructing fault-tolerant systems
* Strongly-consistent distributed systems.
* Consensus involves multiple servers agreeing on values. Once they reach a decision on a value, that decision is final.


## What does Raft solves?

* Consensus algorithms are the most important algorithms in distributed systems
* Consensus algorithms allow a collection of machines to work as a coherent group that can provide continuous service even if some of its members fail.
* Paxos and Raft both solves CA


## Why Raft over paxos?

## Raft presentation by Diego Ongaro

* [An Introduction to Raft (CoreOS Fest 2015)](https://www.youtube.com/watch?v=6bBggO6KN_k)
* [Tech Talk - Raft, In Search of an Understandable Consensus Algorithm by Diego Ongaro-LinkedIn Engineering](https://www.youtube.com/watch?v=LAqyTyNUYSY)
* [An overview of Raft & LogCabin with creator Diego Ongaro-in-Sourcegraph](https://www.youtube.com/watch?v=2dfSOFqOhOU)
* [Raft lecture (Raft user study)-by-John Ousterhout](https://www.youtube.com/watch?v=YbZ3zDzDnrw)



## References

* [Advanced Topics in Computer Systems- Raft-vs-Paxos](https://people.eecs.berkeley.edu/~kubitron/cs262/lectures/lec18-Paxos-Raft.pdf)
* [Reference API in Go-lang](http://cs.brown.edu/courses/cs138/s17/content/projects/raft.pdf)
* [Replicated State Machines, RAFT](https://www.cs.princeton.edu/courses/archive/fall16/cos418/docs/L8-consensus-2.pdf)
* [In Search of an Understandable- Consensus Algorithm](http://cgi.di.uoa.gr/~mema/courses/m120/raft.pdf)
* [Distribtued Timeseries InfluxDB using RAFT](https://s3.amazonaws.com/vallified/InfluxDBRaft.pdf)
* [IIT-Delhi- Raft Introduction](https://www.cse.iitd.ernet.in/~srsarangi/courses/2020/col_819_2020/docs/raft.pptx)