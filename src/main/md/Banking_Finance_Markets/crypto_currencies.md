## What is Collision Free? And never collide?
1. H(x) != H(y) for all x!=y (99.8% to fail this for 2**130 samples)


## What is Hiding?
1. If we know H(x), can you find x?
1. ```Hiding.A hash function H is hiding if: when a secret value r is chosen from a probability distribution
that has high min‐entropy, then given H(r ‖ x) it is infeasible to find x.```

## Crypto Princeton professor
1. [Edward.w. Felten](https://www.cs.princeton.edu/~felten/index.html)

## What is the application of Hiding property?
1. Commitment API

## Puzzle friendly
1. Unable to find msg that produces similar to H(x) = y

## How sha-256 works?
1. It is used by bitcoin
2. No collision has ever been found for sha-256
3. Padding process in SHA-256:
  1. Append a '1' bit: The message is padded with a single '1' bit after the original data.
  2. Append '0' bits: Add enough '0' bits to the message until the length of the message plus the padding is 64 bits less than a multiple of 512. The resulting length should be congruent to 448 (mod 512).
  3. Append message length: The length of the original message (in bits) is then appended as a 64-bit integer.

## What are the data structure we can build using hash-pointer
1. LinkedList (BlockChain)
2. BinaryTree - Merkle-Tree
   1. Non-Membership and Membership can be proved in O(log.n) times
2.  

## Reference
1. [Princeton Bitcoin](https://d28rh4a8wq0iu5.cloudfront.net/bitcointech/readings/princeton_bitcoin_book.pdf)
