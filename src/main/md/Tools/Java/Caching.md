## Java caching concurrent
1. LinkedHashMap is closest to map and ordered key
  1. We can easily remove the oldest added key
  2. Keys are ordered in which the order that were inserted into the map (insertion-order)
  3. Natural TTL caching
  4. Configure in constructor - accessOrder - the ordering mode - true for access-order, false for insertion-order
2. ReferenceMap - Supports Weak and Soft Reference (Available in Googe Guava)
3. ReferenceCache - Magic function how to calculate (or load), AutoWeaver (Available in Googe Guava)
   1. ComputingMap (auto loads)
   2. Self-populating reference map
   3. Calling get on the map resulted in the creation of a new cached entry


## Cache design?
1. How to reload delta?
2. How to set time based expiry for this cache?
3. 

## Reference
1. [Concurrent Caching at Google](https://www.infoq.com/presentations/Concurrent-Caching-at-Google/)
2. [Java Caching with Guava](https://www.youtube.com/watch?v=keqKDhGIJZ8)
3. [ConcurrentHashMapSlides](https://ress.infoq.com/downloads/pdfdownloads/presentations/StrangeLoop2011-CharlesFry-ConcurrentCachingatGoogle.pdf)
4. [Java Caffeine](https://www.baeldung.com/java-caching-caffeine)
