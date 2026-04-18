# Modern Effective Java — Concurrency

VIRTUAL THREADS, structured concurrency, and scoped values reshape how Java programs express concurrent work. The advice in Bloch's original Chapter 11 (Items 78–84) is still correct; this chapter extends it with ten items aimed at JDK 13 and later. Every code sample here has a runnable counterpart in `src/main/java/com/modern/effective/java/concurrency/`, built against OpenJDK 25 with `--enable-preview`. File anchors refer to the JDK 25 source archive at `/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home/lib/src.zip`.

---

## Item 85: Prefer virtual threads for blocking I/O; keep CPU-bound work on platform threads

A virtual thread is a `Thread` that unmounts cleanly from its carrier when it blocks. Creation is essentially free — tens of thousands of them cost no more than a few megabytes of heap — and the JDK schedules them on a dedicated `ForkJoinPool` of carrier threads. For work that spends most of its time waiting on a socket, a database, or another thread, virtual threads let you write straight-line blocking code with the throughput of a reactive pipeline.

**Virtual threads are an I/O story, not a CPU story.** A tight numeric loop does no I/O, so there is nothing to unmount on, and the extra mount/unmount bookkeeping makes virtual threads slightly *slower* than a sized platform pool. The default carrier pool has one worker per CPU core; submitting more CPU-bound work than that just adds queueing.

```java
// Correct — one virtual thread per blocking task
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    requests.forEach(r -> executor.submit(() -> handle(r)));
}

// Correct — sized platform pool for CPU-bound work
ForkJoinPool cpuPool = ForkJoinPool.commonPool(); // or a sized custom pool
```

The runnable demo at `src/main/java/com/modern/effective/java/concurrency/VirtualVsPlatform.java` compares both executors under I/O and CPU workloads; on a 12-core Mac the virtual executor finishes 1,000 × 100ms sleeps in ~107 ms against ~2,086 ms for a 50-thread platform pool, and is within noise of the platform pool on a tight `fib(30)` loop.

In summary, **use virtual threads when tasks spend most of their time blocked, and keep CPU-bound work on a sized platform pool.** The decision is per-task, not per-application.

---

## Item 86: Create one virtual thread per task; never pool them

The previous item recommended `Executors.newVirtualThreadPerTaskExecutor()`. It is tempting to wrap a virtual-thread factory in a *fixed* pool to "limit memory" — don't. Pooling reintroduces a bounded queue, which is the very thing virtual threads were designed to eliminate.

```java
// Anti-pattern — pooling defeats the purpose
ExecutorService bad = Executors.newFixedThreadPool(50, Thread.ofVirtual().factory());

// Correct — unbounded thread-per-task
ExecutorService good = Executors.newVirtualThreadPerTaskExecutor();
```

The factory returned by `Thread.ofVirtual().factory()` produces a fresh virtual thread for every task — `Executors.newVirtualThreadPerTaskExecutor()` simply wraps it in a `ThreadPerTaskExecutor` (see `java.base/java/util/concurrent/Executors.java:252`). **Virtual threads are so cheap that the right resource limit is applied at the admission point (a semaphore, rate limiter, or connection pool), not at the thread level.** The runnable demo at `VirtualThreadPerTask.java` shows a 5,000-task workload finishing in about 1 second on the per-task executor versus many seconds on a 50-wide "virtual pool."

In summary, **do not pool virtual threads**. If you think you need to, you want a semaphore or a connection pool instead (Item 87).

---

## Item 87: Close executors with try-with-resources

Since JDK 19, `ExecutorService` extends `AutoCloseable`. Its `close()` method performs an orderly `shutdown()`, waits for in-flight tasks to finish, and escalates to `shutdownNow()` if the caller is interrupted. This replaces a mechanical try/finally idiom that Bloch's Item 80 could not yet use.

```java
// Old — manual lifecycle
ExecutorService exec = Executors.newFixedThreadPool(4);
try {
    exec.execute(task);
} finally {
    exec.shutdown();
    exec.awaitTermination(1, TimeUnit.HOURS);
}

// Modern — the try-with-resources block IS the completion barrier
try (var exec = Executors.newVirtualThreadPerTaskExecutor()) {
    tasks.forEach(exec::submit);
} // close() waits for all submitted tasks
```

`ForkJoinPool` also implements `close()` as of JDK 19. **Prefer a single try-with-resources block over matched `shutdown`/`awaitTermination` calls — it is shorter, it cannot leak the executor on exceptions, and it makes the scope of the concurrent work lexically obvious.** See `TryWithResourcesExecutor.java`.

In summary, **treat `ExecutorService` like any other resource: open it in `try`, let the block close it.** The result is fewer moving parts and no chance of a forgotten `shutdown`.

---

## Item 88: Scope concurrent subtasks with `StructuredTaskScope`

The classical pattern — submit several subtasks to an `ExecutorService`, wait on their `Future`s, handle partial failures by hand — is awkward and error-prone. A failed subtask does not automatically cancel its siblings; a forgotten `get()` can swallow an exception; timeouts require bolt-on scheduling. JEP 505 (fifth preview in JDK 25) replaces this with a lexically scoped API.

```java
// Broken! — ad-hoc parallel fetch with leaks on failure
ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();
Future<String>  user  = exec.submit(() -> fetchUser(id));
Future<Integer> items = exec.submit(() -> fetchItemCount(id));
try {
    return new Order(user.get(), items.get()); // if user fails, items keeps running
} finally {
    exec.shutdown();
}

// Correct — structured, fail-fast
try (var scope = StructuredTaskScope.open(Joiner.<Object>awaitAllSuccessfulOrThrow())) {
    Subtask<String>  user  = scope.fork(() -> fetchUser(id));
    Subtask<Integer> items = scope.fork(() -> fetchItemCount(id));
    scope.join();                     // cancels siblings on first failure
    return new Order(user.get(), items.get());
}
```

The scope cannot be escaped: **the enclosing method cannot return while any forked subtask is still running.** Policy lives in the joiner: `Joiner.allSuccessfulOrThrow()` returns a stream of completed subtasks, `Joiner.anySuccessfulResultOrThrow()` races replicas and cancels the losers, `Joiner.awaitAll()` tolerates failures. Timeouts are a scope configuration (`open(joiner, cf -> cf.withTimeout(Duration.ofSeconds(2)))`), not per-subtask work.

See `StructuredConcurrency.java` for the parallel-fetch and replica-race demos. Because the feature is still preview, the sample file is annotated `@SuppressWarnings("preview")` and the project `pom.xml` passes `--enable-preview` at compile and run time.

In summary, **if two or more concurrent subtasks must complete before a method returns, put them in a `StructuredTaskScope` instead of juggling futures.** You get automatic cancellation, guaranteed join-before-exit, and a single place to set the timeout.

---

## Item 89: Prefer `ScopedValue` over `ThreadLocal` for one-way transmission

`ThreadLocal` has three well-known pitfalls: any callee can mutate the value, the value leaks past its "scope" until you remember to call `remove()`, and `InheritableThreadLocal` copies an entire map for every child thread. JEP 506 (final in JDK 25) introduces `ScopedValue`, which fixes all three.

```java
// Old — inheritable, mutable, leak-prone
static final InheritableThreadLocal<Principal> USER = new InheritableThreadLocal<>();
USER.set(principal);           // any callee can overwrite
try {
    handleRequest();
} finally {
    USER.remove();             // easy to forget
}

// Modern — immutable, bounded, O(1) inheritance into forked subtasks
static final ScopedValue<Principal> USER = ScopedValue.newInstance();

ScopedValue.where(USER, principal).run(() -> handleRequest());
// Outside the lambda, USER.isBound() is false — no cleanup required.
```

**A `ScopedValue` binding lives exactly as long as the `run` or `call` it introduces.** Code inside the scope reads the value with `USER.get()`; code outside gets an exception. Rebinding creates a nested scope whose lifetime is its own call — the outer binding is restored automatically.

Inheritance into child threads is handled by `StructuredTaskScope`: scope `open()` captures the enclosing bindings, and every `fork(...)` subtask inherits them via a pointer share rather than a map copy. **This means `ScopedValue` only flows into structured children — not arbitrary threads submitted to an `Executor`.** See `ScopedValue.java`, which demonstrates binding two values in a carrier chain, rebinding in a nested scope, and inheritance through `scope.fork(...)`.

In summary, **use `ScopedValue` to pass request-scoped context (principal, tenant, trace id) to callees and to forked subtasks; reserve `ThreadLocal` for the legacy cases it was never really suited to.**

---

## Item 90: Know the pinning story — `synchronized` is fine on JDK 24+

A virtual thread is said to be *pinned* when the JVM cannot unmount it from its carrier. A pinned blocking call therefore ties up one of the small number of carrier threads and defeats the throughput argument for virtual threads in the first place. Historically, any blocking operation inside a `synchronized` block pinned the carrier; `java.util.concurrent.locks` did not.

```java
// Pinned on JDK 21–23 during sleep; fine on JDK 24+ (JEP 491)
synchronized (monitor) {
    Thread.sleep(Duration.ofMillis(100));
}

// Always safe — parks without pinning
lock.lock();
try {
    Thread.sleep(Duration.ofMillis(100));
} finally {
    lock.unlock();
}
```

**JEP 491, shipped in JDK 24, removed monitor pinning.** On JDK 24 and later, the two idioms above are equivalent from a scheduling standpoint, and the intrinsic `synchronized` form is usually clearer. Library code that must support JDK 21–23 runtimes should still prefer `ReentrantLock` / `ReadWriteLock` for anything likely to block while the lock is held. Native frames and class initializers can still pin on any JDK version, but those are rare.

See `LocksVsSynchronized.java` for a side-by-side counter that exercises both idioms under 200 contending virtual threads.

In summary, **on JDK 24+, choose between `synchronized` and `ReentrantLock` on clarity grounds, not on pinning grounds. Portable library code targeting older LTS runtimes should still prefer explicit locks.**

---

## Item 91: Observe pinning with JFR, not with `-Djdk.tracePinnedThreads`

`-Djdk.tracePinnedThreads=full` is a debug switch — it prints a stack trace on every pin event and is noisy enough to distort the behavior it is trying to measure. The JDK also emits a structured JFR event, `jdk.VirtualThreadPinned`, on every pin. The event carries the operation name, duration, and stack trace, and is included in the default JFR profile.

```java
// Programmatic in-process observation
try (var rs = new RecordingStream()) {
    rs.enable("jdk.VirtualThreadPinned").withStackTrace();
    rs.onEvent("jdk.VirtualThreadPinned", e ->
        log.warn("pinned {} for {}", e.getString("operation"), e.getDuration()));
    rs.startAsync();
    runWorkload();
}
```

For production, collect a standard JFR recording (`jcmd <pid> JFR.start settings=profile ...`) and open it in JDK Mission Control — filter on `jdk.VirtualThreadPinned` and group by stack trace to find the offending call sites. **Expect zero events on JDK 24+ from `synchronized` blocks; what remains is usually native code or a file-system call you didn't know was blocking.** The emission site is in `java.base/java/lang/VirtualThread.java` around the `postPinnedEvent` native hook (L825).

See `JfrPinning.java` for the `RecordingStream` pattern.

In summary, **treat pinning as an observable, not a log line.** Collect it with JFR, dashboard the count, alert on regressions — just as you would any other runtime metric.

---

## Item 92: Drain completed futures with `state()` / `resultNow()` / `exceptionNow()`

Before JDK 19, inspecting a batch of completed futures meant wrapping each `get()` in a try/catch and inferring outcomes from exception types. JDK 19 added a `Future.State` enum — `RUNNING`, `SUCCESS`, `FAILED`, `CANCELLED` — plus two non-blocking accessors: `resultNow()` returns the value if successful (and throws `IllegalStateException` otherwise), `exceptionNow()` returns the `Throwable` if failed (likewise). The additions are overridden efficiently in `CompletableFuture`, `FutureTask`, and `ForkJoinTask`.

```java
// Old — exception-as-control-flow
for (Future<V> f : futures) {
    try {
        V v = f.get();
        handleSuccess(v);
    } catch (CancellationException ce) {
        handleCancelled();
    } catch (ExecutionException ee) {
        handleFailure(ee.getCause());
    }
}

// Modern — state-driven
for (Future<V> f : futures) {
    switch (f.state()) {
        case SUCCESS   -> handleSuccess(f.resultNow());
        case FAILED    -> handleFailure(f.exceptionNow());
        case CANCELLED -> handleCancelled();
        case RUNNING   -> throw new AssertionError("expected completed");
    }
}
```

**The switch form composes with exhaustiveness checking and pattern matching; the try/catch form does not.** It is also meaningfully faster in hot paths because no exception objects are thrown for normal completion or cancellation. See `FutureState.java`, which submits a mixed batch of successes, failures, and cancellations on a virtual-thread-per-task executor and drains them with a single switch.

In summary, **once a `Future` has completed, prefer state-based inspection over exception-driven inspection.** Keep `get()` for the call site that actually needs to block.

---

## Item 93: Use `Duration` overloads; prefer `threadId()`; forget `stop` / `suspend` / `resume`

The pre-JDK-19 `Thread` API used `(long millis, int nanos)` pairs, exposed a deprecated `getId()`, and kept three methods — `stop`, `suspend`, `resume` — that had been unsafe since the 1990s.

```java
// Modern — Duration overloads and threadId
Thread worker = Thread.ofVirtual().name("worker").start(() -> {
    try { Thread.sleep(Duration.ofMillis(250)); }
    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
});
worker.join(Duration.ofSeconds(5));
long id = worker.threadId();           // replaces the @Deprecated getId()
```

- `Thread.sleep(Duration)` and `Thread.join(Duration)` both carry `@since 19` in `java.base/java/lang/Thread.java` (L596, L1989). They read better than the primitive overloads and remove a whole class of unit-conversion bugs.
- `Thread.getId()` is `@Deprecated(since="19")`; it now delegates to `threadId()`, which is the method to call (L2293).
- `Thread.suspend()` and `Thread.resume()` **no longer exist in JDK 25** — they were removed outright. `Thread.stop()` remains only as an `@Deprecated(forRemoval=true)` stub that throws `UnsupportedOperationException`. **The cooperative cancellation patterns from Bloch's Item 78 (poll a `volatile boolean`, or call `Thread.interrupt()`) are still the right approach.**

See `DurationAndThreadId.java` for a minimal demonstration, including the `UnsupportedOperationException` thrown by the residual `stop()` stub.

In summary, **use `Duration` overloads for all new code, replace `getId()` with `threadId()`, and treat `stop`/`suspend`/`resume` as if they are gone** — because, in JDK 25, two of them are.

---

## Item 94: Pick a splittable `RandomGenerator` for parallel streams

`ThreadLocalRandom` is thread-safe but contends globally under load; `SplittableRandom` is statistically weaker than the modern LXM family. JDK 17's JEP 356 introduced a service-loader-style registry, `RandomGeneratorFactory`, and a trait-based interface hierarchy: `RandomGenerator`, `SplittableGenerator`, `JumpableGenerator`, `LeapableGenerator`, `ArbitrarilyJumpableGenerator`.

```java
// Old — contended and statistically weak choices
double v = ThreadLocalRandom.current().nextDouble();   // contends on shared seed
var weak = new SplittableRandom(seed);                 // worse statistical profile

// Modern — pick an LXM generator that matches the axis of parallelism you need
RandomGenerator rng = RandomGeneratorFactory.all()
        .filter(f -> f.isSplittable() && f.isStatistical())
        .findFirst().orElseThrow()
        .create(seed);

// Parallel streams — split once, then let each worker own its sub-stream
var root = (SplittableGenerator) RandomGeneratorFactory
        .of("L64X128MixRandom").create(seed);
long hits = root.splits(8).parallel()
        .mapToLong(g -> monteCarloHits(g, 1_000_000))
        .sum();
```

**The capability predicates on `RandomGeneratorFactory` are the point.** `isSplittable()` is the one you want for parallel streams and for `StructuredTaskScope` subtasks — each child gets an independent sub-stream with no coordination. `isJumpable()` gives reproducible skips for simulations; `isLeapable()` gives larger, cheaper skips. See `RandomGenerator.java` for a Monte Carlo π estimate using `L64X128MixRandom` across eight splits.

In summary, **pick the weakest generator trait that fits your access pattern, and let the factory hand you a concrete implementation.** For parallel streams that means a splittable, statistically strong LXM generator — not `ThreadLocalRandom`, not `SplittableRandom`.

---

## Honorable mentions

A few JDK 13+ additions did not make the top ten but are worth knowing:

- **`ForkJoinPool` is schedulable (JDK 25).** `schedule`, `scheduleAtFixedRate`, `scheduleWithFixedDelay`, and `submitWithTimeout(Callable, long, TimeUnit, Consumer<ForkJoinTask<V>>)` live on `ForkJoinPool` directly — the cleanest stdlib API for per-task deadlines with a custom timeout action.
- **`ForkJoinTask.adaptInterruptible(Callable)` (JDK 19).** Unlike `adapt`, `cancel(true)` on the adapted task actually interrupts the running thread.
- **`Executors.newThreadPerTaskExecutor(ThreadFactory)` (JDK 21).** The non-virtual half of JEP 444; useful when you want thread-per-task semantics with a custom platform-thread factory (named threads for debugging, for example).
- **`CompletableFuture.orTimeout` / `completeOnTimeout`.** Older than this chapter (JDK 9) but still underused — prefer them over your own `ScheduledExecutorService`.

## Reading the source yourself

Every code anchor in this chapter is in the JDK 25 standard library source, shipped as `lib/src.zip` inside `$JAVA_HOME`. Extract once:

```bash
mkdir -p /tmp/jdk25-src
unzip -q /opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home/lib/src.zip -d /tmp/jdk25-src
```

Then grep for `@since 19` upward in `java.base/java/util/concurrent/` and `java.base/java/lang/` to see the full surface area added in the virtual-thread era. The same files live online at [github.com/openjdk/jdk](https://github.com/openjdk/jdk) under the `jdk-25-ga` tag — paths there are `src/java.base/share/classes/...`, the on-disk `src.zip` simply flattens the `share/classes` segment.
