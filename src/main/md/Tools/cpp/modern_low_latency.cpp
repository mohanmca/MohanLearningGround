# Here’s a **tight, no-fluff list** of **ultra-modern C++ (C++17 → C++23)** features that directly help **low-latency + elegant code**

## **Memory & Allocation (latency critical)**

* **`std::pmr::*`** – allocator control, arena / pool allocation
* **`std::byte`** – type-safe raw memory
* **`constexpr` allocation (C++20/23)** – precompute data structures at compile time
* **Custom allocators + monotonic_buffer_resource** – zero-free fast paths

---

## **Object Construction & Ownership**

* **Move semantics (guaranteed elision)** – no copies
* **`std::optional`** – avoid sentinel values
* **`std::variant`** – tagged unions, branch-predictable
* **`std::unique_ptr` (no refcount)** – deterministic destruction

---

## **Zero-Cost Abstractions**

* **`if constexpr`** – compile-time branching
* **Concepts (`requires`)** – zero-overhead constraints
* **CRTP** – static polymorphism
* **`constexpr` algorithms** – no runtime cost

---

## **Concurrency & Atomics**

* **`std::atomic_ref`** – atomic ops on existing memory
* **`memory_order_*`** – fine-grained ordering control
* **`std::jthread`** – RAII threads, safer teardown
* **Lock-free data structures** (with atomics)

---

## **Data Layout & Performance**

* **`[[no_unique_address]]`** – empty base optimization without inheritance
* **`alignas` / `alignof`** – cache-line control
* **`std::span`** – zero-copy views
* **`std::bit_cast`** – safe type punning

---

## **Compile-Time Power**

* **`consteval`** – force compile-time execution
* **`constinit`** – static init without order issues
* **NTTPs with class types** – encode config in types

---

## **Branching & Control**

* **`[[likely]] / [[unlikely]]`** – branch prediction hints
* **`std::assume` (C++23)** – optimizer guidance
* **`std::unreachable()`** – dead-path elimination

---

## **I/O & Parsing (hot paths)**

* **`std::from_chars` / `to_chars`** – allocation-free parsing
* **`string_view`** – zero-copy string handling
* **`std::format` (compile-time checked)** – safer formatting

---

## **Error Handling (no exceptions path)**

* **`std::expected` (C++23)** – explicit, branch-friendly errors
* **`noexcept` correctness** – enables optimizer wins

---

## **Modern Elegance (readability without cost)**

* **Structured bindings** – cleaner code
* **Ranges (selectively)** – composable, inlineable
* **Designated initializers** – cache-friendly clarity

---

## **Practical Low-Latency Combo**

> **PMR + span + from_chars + expected + atomics + constexpr**
> = **predictable latency, no allocations, clean APIs**

---

## Few more items...

* **Modules (`import`) (C++20)** – big build/ODR hygiene win (indirectly helps latency work via faster iteration).
* **Coroutines (`co_await`, `co_yield`) (C++20)** – efficient async without OS-thread context switching.
* **`std::flat_map` / `std::flat_set` (C++23)** – sorted-vector associative containers; great cache locality vs node-based `map/set`.
* **Deducing `this` (C++23)** – cleaner static polymorphism / mixins; can help remove `virtual` and enable inlining.
* **`std::print` (C++23)** – simpler/faster than iostream-style output (still: avoid in hot paths).
* **Static reflection (targeting C++26 / future)** – compile-time code inspection; can generate zero-overhead serialization/meta code.

One quick nuance: **`std::format` isn’t “compile-time formatting”**; it *can* be compile-time checked/optimized in some cases, but formatting work still happens at runtime.
