## how failover election happens?
1. Leader Failure Detection:  if a follower does not receive a heartbeat within a specified timeout period, it assumes the leader has failed.
2. Starting an Election: Follower transitions to a candidate state and increments its term. The candidate then starts an election by sending RequestVote messages to other nodes in the cluster.
3. Voting process
4. Winning an Election
5. Handling Split Votes
6. Reestablishing Normal Operation


## What is the voting process?
1. Voting Process: Other nodes in the cluster respond to the candidate's RequestVote message. A node can vote for a candidate if:
    1. The candidate's log is at least as complete as its own.
    1. The node has not already voted in the current term.

## How leader establishes authority?
1. The new leader sends out heartbeat messages to reestablish authority and prevent new elections.

## Leader election mechanism handles failures of what kind?
1. This leader election mechanism is designed to handle various failure scenarios, such as network partitions or node crashes.

## What is Term?
1. Term is a crucial concept that helps in managing leader election and ensuring consistency. 
1. In RAFT, the term is used as a logical clock to detect stale information and resolve conflicts. 
1. The term comparison helps in determining which information (votes, entries, etc.) is more recent and should be prioritized.

## What are properties of Terms
1. The term is a monotonically increasing integer that represents a logical clock across the cluster. 
1. When a server's term is updated, the new term is immediately persisted to stable storage. This ensures that even if the server crashes and restarts, it will not revert to an outdated term.
1. An increase in term causes the server to transition to the follower state, relinquishing its candidate or leader status if it held such roles.

## When and how the term gets incremented:
1. Starting a New Election:
1. Receiving a RequestVote RPC with a Higher Term:
1. Receiving an AppendEntries RPC with a Higher Term:
1. Discovering a Higher Term in a Response:


## What are all the messages sent by Leader without any request
1. Heartbeat Messages (AppendEntries RPCs with no log entries)
2. Commit Index Updates

## Heartbeat Messages (AppendEntries RPCs with no log entries)
1. Purpose: Heartbeat messages are used by the leader to maintain its authority and to prevent new elections by resetting the election timeout on followers. They also provide a way for the leader to replicate the log entries to the followers.
1. Content: These messages usually contain the current term, the leader's identifier, the index of the last log entry applied by the leader, and a commitment index indicating up to which point the log entries are committed.
1. Frequency: Heartbeat messages are typically sent at regular intervals, which are shorter than the election timeout period, ensuring that followers do not start a new election due to a perceived leader failure.

## Commit Index Updates
1. Purpose: When the leader commits new log entries, it sends updates to the followers to inform them about the latest committed index. This allows followers to apply the committed entries to their state machines.
1. Content: These updates include the current commit index and, if necessary, new log entries to be appended.
1. Timing: These updates are sent whenever the leader commits new log entries, either as part of heartbeat messages or as separate AppendEntries RPCs.


## Heartbeat messages

```json
{
  "term": 5,
  "leaderId": 1,
  "prevLogIndex": 8,
  "prevLogTerm": 4,
  "entries": [],
  "leaderCommit": 10
}

{
  "term": 5,
  "leaderId": 1,
  "prevLogIndex": 9,
  "prevLogTerm": 4,
  "entries": [],
  "leaderCommit": 10
}
```

## RequestVote (Candidate to Follower):

```json
{
  "term": 5,
  "candidateId": 3,
  "lastLogIndex": 10,
  "lastLogTerm": 4
}
```

## Commit Index Updates (AppendEntries RPCs with log entries)

```json
{
  "term": 5,
  "leaderId": 1,
  "prevLogIndex": 8,
  "prevLogTerm": 4,
  "entries": [
    {
      "index": 9,
      "term": 5,
      "command": "set x = 10"
    }
  ],
  "leaderCommit": 9
}
{
  "term": 5,
  "leaderId": 1,
  "prevLogIndex": 9,
  "prevLogTerm": 5,
  "entries": [
    {
      "index": 10,
      "term": 5,
      "command": "set y = 20"
    }
  ],
  "leaderCommit": 10
}
```

## The follower response to the RequestVote request indicating whether it voted for the candidate.
```json
{
  "term": 5,
  "voteGranted": true
}
```

## Tracking State for Each Follower:

1. The leader maintains a state for each follower, including:
    1. Next Index: The index of the next log entry the leader will send to the follower.
    1. Match Index: The index of the highest log entry known to be replicated on the follower.
1. These indices help the leader keep track of which entries have been acknowledged by each follower and which entries still need to be sent.    

## When does Leader Committs Entries:
1. Once the leader has received a successful response from a majority of followers for a particular log entry (i.e., it has been replicated on a majority of servers), the entry can be considered committed. 
1. The leader then updates its commitIndex and notifies the followers of the new commit.

## Why Raft over paxos?
* Raft uses randomization over agreeing on single value in Paxos
* Raft assumes leader log is always correct
* sometimes PAXOS assumes there are no leader (like leaderless)


## Raft complexities and depth understaning

1. Leadership Stability and Term Conflicts: "How does RAFT ensure leadership stability in the presence of network partitions and how does it handle potential term conflicts when network partitions heal?"
1. Explore how RAFT handles split votes and network partitions, especially when partitions merge and multiple candidates might believe they are the leader. 
1. Discuss the concept of "leadership disambiguation" and the role of the term number in resolving conflicts.

## Log Compaction and Snapshot Handling: "Can you explain how log compaction works in RAFT, and how the system ensures consistency and safety when restoring from snapshots?"
1. Investigate their understanding of the snapshot process, including how entries are safely discarded, and the process of installing and applying snapshots.
1. Discuss how log indices are handled in the context of snapshots and what happens to entries that precede the snapshot.

## Commit Index Calculation and Log Matching: "In what situations can a leader's commit index move backward, and how does RAFT handle such scenarios to ensure consistency across all followers?"
1. Probe into scenarios where the leader might misinterpret the majority's acknowledgment and incorrectly adjust the commit index.
1. Examine their understanding of the log matching property and its role in maintaining a consistent replicated log.

## Cluster Membership Changes
1. "How does RAFT handle dynamic membership changes (such as adding or removing nodes), and what are the potential pitfalls and solutions associated with these changes?"
1. Discuss the joint consensus approach used in RAFT for safe membership changes.
1. Explore the issues that might arise during the transition phase and how they are mitigated.

## Election Timing and Randomization
1. "Why does RAFT use randomized election timeouts, and how would the system behave if this randomness were removed or improperly implemented?"
1. Discuss the importance of randomized timeouts in preventing split votes and reducing the likelihood of multiple candidates starting an election simultaneously.
1. Analyze the potential issues that might arise with deterministic timeouts and how they could lead to decreased system reliability or availability.

## How to create raft anki
mdanki raft_anki.md raft_anki.apkg --deck "Mohan::DeepWork::Raft"