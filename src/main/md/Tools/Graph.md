* PageRank algorithms
* THE PREGEL PROCESSING MODEL
  * Idea behind Pregel
    * One vertex can “send a message” to another vertex
    * Typically those messages are sent along the edges in a graph
    * In each iteration, a function is called for each vertex, passing it all the messages that were sent to it
    * a vertex remembers its state in memory from one iteration to the next
    * The function only needs to process new incoming messages. If no messages are being sent in some part of the graph, no work needs to be done
    * Pregel is similar to actor model
* GraphChi - Graph processing framework
* FlumeJava 