## What is analogy type for ThreadLocal<T>

1. ThreadLocal<T> = Map<Thread,T>

## ThreadLocal - Example for Connection

```java
public class ConnectionDispenser {
    static String DB_URL = "jdbc:mysql://localhost/mydatabase";

    private ThreadLocal<Connection> connectionHolder
            = new ThreadLocal<Connection>() {
                public Connection initialValue() {
                    try {
                        return DriverManager.getConnection(DB_URL);
                    } catch (SQLException e) {
                        throw new RuntimeException("Unable to acquire Connection, e");
                    }
                };
            };

    public Connection getConnection() {
        return connectionHolder.get();
    }
}
```

## What is race condition?

1. A race condition is a software bug that occurs when the correctness of a program depends on the timing or interleaving of two or more concurrent threads or processes.
2. There is no deterministic way to deduce the output of the following program, it is based on interleaving thread!
```java
package jdk.thread;

class Counter {
    private int count;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class CounterThread extends Thread {
    private Counter counter;

    public CounterThread(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 100000; i++) {
            counter.increment();
        }
    }
}

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        CounterThread thread1 = new CounterThread(counter);
        CounterThread thread2 = new CounterThread(counter);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Count: " + counter.getCount());
    }
}
```

## What is invariant?
1. In a concurrent program, an invariant might be that a shared data structure remains consistent and free of race conditions at all times.
2. an invariant is a property or condition that remains true throughout the execution of a program or a specific portion of a program
3. The invariant is that the balance of the savings account must always be non-negative. This is important because allowing a negative balance could result in overdrafts, which the bank would have to cover at a cost.

## Example for invariant in stack.

```java
// Invariant: the top index is always within the bounds of the array
public boolean checkInvariant() {
   return top >= -1 && top < data.length;
}
```

## Postcondition
1. A postcondition is a condition or assertion that is guaranteed to be true after a program, method, or function has executed
2. "The sum returned by the function is non-negative and equal to the sum of all positive integers in the input array."
3. "After the function executes, the input array is sorted in non-descending order."

## What is thread-safety
1. Program that holds invariant and postcondition
2. Ensures correctness of the program
   1. Correctness = Invariant + Postcondition

## ThreadLocal object for buffer usage

1. ThreadLocal technique can also be used when a frequently used operation requires a temporary object such as a buffer and wants to avoid reallocating the temporary object on each invocation.

## What is Immutable Object

1. Its state cannot be modified after construction; 
2. All its fields are final;
3. It is technically possible to have an immutable object without all fields being final—String is such a class—but this relies on delicate reasoning about benign data races that requires a deep understanding of the Java Memory Model. (For the curious: String lazily computes the hash code the first time hashCode is called and caches it in a nonfinal field, but this works only because that field can take on only one nondefault value that is the same every time it is computed because it is derived deterministically from immutable state. Don't try this at home.)
4. It is properly constructed (the this reference does not escape during construction).

## Immutable LockLess ThreadSafe OneCacheValue

```java
@ThreadSafe
public class VolatileCachedFactorizer extends GenericServlet implements Servlet {
    private volatile OneValueCache cache = new OneValueCache(null, null);

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = cache.getFactors(i);
        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
        }
        encodeIntoResponse(resp, factors);
    }
}

@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i))  return null;
        else
            return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}
```

## Is simple constructor is not thread-safe
1. While it may seem that field values set in a constructor are the first values written to those fields and therefore that there are no “older” values to see as stale values.
2. The Object constructor first writes the default values to all fields before subclass constructors run.
3. It is therefore possible to see the default value for a field as a stale value.

## Which object needs to be safely published?

1. Immutable objects can be used safely by any thread without additional synchronization, even when synchronization is not used to publish them.
2. Objects that are not immutable must be safely published

## How to publish safely

1. Initializing an object reference from a static initializer;
2. Storing a reference to it into a volatile field or AtomicReference;
3. Storing a reference to it into a final field of a properly constructed object; or 
4. Storing a reference to it into a field that is properly guarded by a lock.
5. Static initializers are executed by the JVM at class initialization time; because of internal synchronization in the JVM, this mechanism is guaranteed to safely publish any objects initialized in this way

## “rules of engagement” for a shared object.
1. Thread-confined. A thread-confined object is owned exclusively by and confined to one thread, and can be modifled by its owning thread.
2. Shared read-only. A shared read-only object can be accessed concurrently by multiple threads without additional synchronization, but cannot be modified by any thread. Shared read-only objects include immutable and effectively immutable objects.
3. Shared thread-safe. A thread-safe object performs synchronization internally, so multiple threads can freely access it through its public interface without further synchronization.
4. Guarded. A guarded object can be accessed only with a specific lock held. Guarded objects include those that are encapsulated within other thread-safe objects and published objects that are known to be guarded by a specific lock.


## [3. Sharing Objects](https://github.com/yjfox/-Java-Concurrency-in-Practice-Source-Code/tree/master/Sharing_Objects)



## References
1. [JCIP](http://jcip.net.s3-website-us-east-1.amazonaws.com/listings.html)
2. [JCIP with downloader](https://github.com/yjfox/-Java-Concurrency-in-Practice-Source-Code/tree/master)
3. [JCIP Book](https://learning.oreilly.com/library/view/java-concurrency-in/0321349601/ch04.xhtml#ch04lev1sec1)
4. [TDP Notes](https://github.com/teamdailypractice/effective-java-jcip/blob/main/jcip-notes/jcip-05-02-01.md)