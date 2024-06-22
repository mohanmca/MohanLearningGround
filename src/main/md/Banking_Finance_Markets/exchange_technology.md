## Exchange vidoes
1. [AWS re:Invent 2023 - Coinbase: Building an ultra-low-latency crypto exchange on AWS (FSI309)](https://www.youtube.com/watch?v=iB78FrFWrLE)
2. [SREcon23 Americas - The Making of an Ultra Low Latency Trading System with Go and Java](https://www.youtube.com/watch?v=6SXd0cNRVN8)
3. [Evolution of Financial Exchange Architectures](https://www.youtube.com/watch?v=qDhTjE0XmkE)
4. [Hot Deploying Low-Latency Services for 24/7 Operation • Martin Thompson • YOW! 2022](https://www.youtube.com/watch?v=_KvFapRkR9I)
5. ["Aeron: Open-source high-performance messaging" by Martin Thompson](https://www.youtube.com/watch?v=tM4YskS94b0)
6. [High Performance Managed Languages • Martin Thompson • YOW! 2017](https://www.youtube.com/watch?v=VbTJHQe3nNg&t=961s)
7. [Fault Tolerant 24/7 Operations with Aeron Cluster - Todd Montgomery, Adaptive Financial Consulting](https://www.youtube.com/watch?v=H9yqzfNiEb4)
8. [SREcon23 Americas - The Making of an Ultra Low Latency Trading System with Go and Java](https://www.youtube.com/watch?v=6SXd0cNRVN8&t=17s)
9. [How low can you go? Ultra low latency Java in the real world - Daniel Shaya](https://youtu.be/BD9cRbxWQx8?si=5n8zu2foYZP2qGVj)
10. [Coleman Ruiz: Overcoming Physical & Emotional Challenges](https://www.youtube.com/watch?v=acgz0C-z-gc)
11. [Lightning Talk: Dependency Injection for Modern C++ - Tyler Weaver - CppCon 2022](https://www.youtube.com/watch?v=Yr0w62Gjrlw)
12. [Don't chase test coverage](https://www.youtube.com/watch?v=BVErL_Ez9LI)
13. [Unit Testing Is Not Good Enough](https://www.youtube.com/watch?v=h-4i5N89TUI)
14. ["How NOT to Measure Latency" by Gil Tene](https://www.youtube.com/watch?v=lJ8ydIuPFeU)
15. [Top 5 techniques for building the worst microservice system ever - William Brander - NDC London 2023](https://www.youtube.com/watch?v=88_LUw1Wwe4)
16. [Smarter Cpp Atomic Smart Pointers - Efficient Concurrent Memory Management - Daniel Anderson  CppCon](https://www.youtube.com/watch?v=OS7Asaa6zmY)
17. [Modifying gRPC Services Over Time [I] - Eric Anderson, Google](https://www.youtube.com/watch?v=F2WYEFLTKEw)
18. [CppCon 2015: Greg Law " Give me 15 minutes & I'll change your view of GDB" ](https://www.youtube.com/watch?v=PorfLSr3DDI)
19. (https://www.youtube.com/watch?v=Bt3zcJZIalk&list=PLHTh1InhhwT4TJaHBVWzvBOYhp27UO7mI)[CPP 2021 Back to Basics]
20. [CPP 2020 Back to Basics](https://www.youtube.com/watch?v=ZAji7PkXaKY&list=PLHTh1InhhwT5o3GwbFYy3sR7HDNRA353e)
21. [CPP 2019 Back to Basics](https://www.youtube.com/watch?v=32tDTD9UJCE&list=PLHTh1InhhwT4CTnVjJqnAKeMfGzOWjsRa)
22. [CppCon 2014: Herb Sutter "Back to the Basics! Essentials of Modern C++ Style"](https://www.youtube.com/watch?v=xnqTKD8uD64&t=28m27s)
23. [CppCon 2019: Alisdair Meredith, Pablo Halpern “Getting Allocators out of Our Way”](https://www.youtube.com/watch?v=RLezJuqNcEQ)
22. [CppCon 2017: Pablo Halpern “Allocators: The Good Parts”](https://www.youtube.com/watch?v=v3dz-AKOVL8)


## [Exchange@Coinbase Slides](https://www.usenix.org/sites/default/files/conference/protected-files/sre23amer_slides_sun.pdf)
1. It is coinbase derivatie exchange architecture
2. Users are Bots, HF/MM would connect to exchange.
3. MarketData consumed by everyone in near realtime
4. Exchagnge = OMS + ME + Ledger

## [Open AI Summary](https://www.usenix.org/sites/default/files/conference/protected-files/sre23amer_slides_sun.pdf)
1. **Trading System**: This is a mechanism for buying and selling securities, currencies, or other financial instruments. It involves multiple components such as order submission, matching orders, generating fill and order events, and sending them out. It is treated as a deterministic state machine.

2. **Matching Logic**: This is the core of the exchange system. It handles the submission of orders, matches them against orders that have not yet been filled, and generates fill and order events.

3. **Consensus Algorithm**: This is used to achieve high availability and fault tolerance in a replicated state machine. If the leader fails, the other nodes can hold a re-election, choose a new leader, and continue processing messages.

4. **Replicated State Machine**: This is a state machine that is replicated across multiple nodes for fault tolerance and high availability. It uses a consensus algorithm to ensure that all nodes agree on the state of the system.

5. **Raft Protocol**: This is a consensus algorithm used in distributed systems to ensure that all nodes agree on the state of the system. It is used for high availability and fault tolerance.

6. **Aeron Cluster**: This is a high-performance messaging system used for consensus and replication in the trading system.

7. **Regulated Exchange**: This is an exchange that operates under regulations set by a financial regulatory authority. The books of a regulated exchange are public, meaning every order is matchable and there are no complex trading relationships where only certain parties can trade with others.

8. **Trading System State**: This is the state of the trading system at any given time. It can be snapshotted to a file and restored from that file to have a live trading system. It can also be replayed from an old snapshot plus some old input to catch up to the live system.

9. **Feature Flags**: These are used to control the visibility and rollout of features in an application. They allow developers to enable or disable features during runtime.

10. **Data Center**: This is a facility used to house computer systems and related components. It is used for data storage, processing, and distribution. In the context of the document, the trading system is co-located in a data center with its customers for low latency.

11. **Commodity Hardware**: This refers to a standard-issue hardware that is widely available and not specialized for any specific task. In the context of the document, the trading system runs on commodity hardware with very fast disks.

12. **Network Latency**: This is the delay that occurs when processing data over a network. In the context of the document, network latency is a dominant factor in the overall latency of the trading system.

13. **Kernel Bypass**: This is a method of sending and receiving network packets without the involvement of the operating system's kernel. It is used to reduce network latency.

14. **Garbage Collection**: This is a form of automatic memory management. The garbage collector attempts to reclaim memory occupied by objects that are no longer in use by the program. In the context of the document, understanding the behavior of garbage collection in different programming languages and its impact on performance is discussed.

15. **CPU Layout and Memory Access**: Understanding CPU layout and memory access is important for performance optimization. In the context of the document, it is mentioned as a key point in optimizing system performance.

16. **Market Data Feed**: This is a critical component of a trading system. It provides the system with information about what's happening in the market, which is essential for placing orders. Timely delivery of market data to trading firms and market makers is emphasized in the document.
 

## [Exchange@Coinbase Keysummary](https://www.usenix.org/sites/default/files/conference/protected-files/sre23amer_slides_sun.pdf)
1. The speakers are discussing the architecture and optimization of trading systems, specifically focusing on latency and throughput.
1. They mention the use of different programming languages and technologies such as Go, Java, and AWS.
1. They discuss the importance of understanding the internal workings of AWS and the impact of its architecture on latency.
1. They talk about the challenges of optimizing legacy systems and the need to understand the behavior of these systems.
1. They mention the use of different methodologies and tools for performance optimization, such as flame graphs, schedulers, and profilers.
1. They discuss the importance of understanding the behavior of garbage collection in different programming languages and its impact on performance.
1. They mention the use of different data structures and their impact on performance.
1. They discuss the concept of "happy path" and "unhappy path" in system performance.
1. They mention the use of different JVMs and their impact on performance.
1. They discuss the concept of "Heisenberg system", where observing the system can affect its latency.
1. They mention the use of different network optimizations, such as kernel bypass, to improve system performance.
1. They discuss the importance of understanding CPU layout and memory access for performance optimization.
1. They mention the use of different strategies for improving system performance, such as spinning and batching.
1. They discuss the importance of understanding the behavior of databases and their impact on system performance.
1. They conclude by emphasizing the importance of not optimizing something that should not exist in the first place, quoting Elon Musk.

## [Exchange@Coinbase HOT PATH](https://www.usenix.org/sites/default/files/conference/protected-files/sre23amer_slides_sun.pdf)
1. An order is submitted by a client.
1. The order goes through an order gateway where it is parsed and validated.
1. The validated order is sent as a request to the trading system.
1. The trading system performs consensus and once it gets quorum, it runs through the matching algorithm.
1. The matching algorithm generates any fills or order events.
1. These events are sent back to the order gateway to be translated via whatever protocol is being used.
1. Finally, an order acknowledgment is sent back to the client.
1. This path is critical because it involves the processing of trading orders, which is the core function of their system. Any delay or inefficiency in this path can significantly impact the system's performance and the user's experience.


## [Exchange@Coinbase Latencies](https://www.usenix.org/sites/default/files/conference/protected-files/sre23amer_slides_sun.pdf)Round-trip time outliers less than 100 microseconds.
1. Medians less than 50 microseconds.
1. Trading system processing times around one microsecond.
1. Achieved 300,000 requests per second peak throughput.
1. Network latency is a dominant factor in the overall latency of the trading system.
1. Inter-AZ latency in AWS can be between 1 millisecond to 2 milliseconds.
1. The latency from a given node to the live system scales with log n of the number of nodes in the graph.
1. The system latency on AWS is dominated by network hops and is higher than on their data center setup.
1. The latency introduced by store-and-forward switches can be between 5 microseconds to 50 microseconds.
1. Cut-through forwarding switches can offer lower latency but are not used in AWS.
1. The latency introduced by garbage collection in Java, which can cause stop-the-world events, is a significant factor in overall system latency. However, the exact values are not mentioned.
1. The latency introduced by AWS's compute and storage choices, as well as its network architecture, is a significant factor in overall system latency. However, the exact values are not mentioned.
