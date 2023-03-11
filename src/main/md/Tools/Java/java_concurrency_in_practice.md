

## Sharing Objects

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