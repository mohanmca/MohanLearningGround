# 🧱 First Principles of Index Design

### 1. **Indexes exist to speed up reads, but slow down writes**

* Every insert, update, or delete must also update indexes.
* More indexes = faster reads (sometimes), but **heavier writes**.
* Balance based on workload (read-heavy vs write-heavy).

---

### 2. **Indexes work by ordering values**

* A B-Tree index (the default in most RDBMS) stores values in sorted order.
* This makes **point lookups, ranges, and ordered scans** efficient.
* Hash indexes (where supported) are only good for equality lookups.

---

### 3. **Selectivity matters**

* Indexes are only useful when they **reduce the search space a lot**.
* “Selective” = a predicate filters out many rows (e.g., `WHERE user_id = ?`).
* Indexing low-cardinality columns (e.g., `gender`, `is_active`) rarely helps unless combined with others.

---

### 4. **Leading column rule (composite indexes)**

* A composite index `(a, b, c)` is ordered by `a`, then `b`, then `c`.
* It supports:

  * Queries filtering by `a`
  * Queries filtering by `a, b`
  * Queries filtering by `a, b, c`
* ❌ But not queries filtering only by `b` or `c` (unless DB uses index skip scan, rare).

---

### 5. **Covering vs non-covering**

* If an index contains **all columns needed by a query**, the DB can serve it **without touching the table** (index-only scan).
* PostgreSQL: use `INCLUDE` for non-key columns.
* Other RDBMS: “covering index” = put all needed columns inside.

---

### 6. **Clustered vs secondary**

* In MySQL InnoDB, the **primary key is clustered**: data is physically ordered by PK.
* This means:

  * Secondary indexes store the PK inside their leaf nodes.
  * Short, stable PKs = more efficient.
* In PostgreSQL, there’s no true clustering except via `CLUSTER` or `BRIN`.

---

### 7. **Access paths**

* Indexes accelerate:

  * Point lookups (`=`)
  * Range scans (`BETWEEN`, `<`, `>`)
  * Joins on indexed columns
  * Sorting (`ORDER BY`) if order matches index
* Indexes do **not** help:

  * `LIKE '%foo'` (leading wildcard)
  * Highly volatile columns
  * Functions unless you use **functional indexes**

---

### 8. **Index size and memory**

* Indexes must fit in memory (buffer pool / shared buffers) for max benefit.
* Large indexes = more disk I/O.
* Sometimes **partial indexes** (Postgres) or **filtered indexes** (SQL Server) are better.

---

### 9. **Write amplification**

* Each index means extra maintenance during `INSERT/UPDATE/DELETE`.
* Avoid indexing every column “just in case”.

---

# ✅ Rules of Thumb

1. **Always index your primary key** (it’s usually clustered by default).
2. **Index foreign keys** — speeds up joins and `ON DELETE/UPDATE` checks.
3. **Index columns used in WHERE, JOIN, ORDER BY, GROUP BY** — especially if selective.
4. **Composite indexes:** put the **most selective / most frequently filtered column first**.
5. **Don’t over-index** — each index has storage + write cost.
6. **Prefer covering indexes** for frequently used queries.
7. **Avoid indexing low-cardinality columns** alone (`status = active` when 90% rows are active). Combine with other columns if needed.
8. **Use partial/filtered indexes** if only a subset of rows matter (e.g., `WHERE deleted_at IS NULL`).
9. **Cluster/index for range queries** — e.g., `(customer_id, created_at)` is great for "all orders for a customer in last 30 days".
10. **Measure with `EXPLAIN`/`ANALYZE`** — don’t guess. Query planners differ.
11. **Keep indexes small** — short keys, avoid indexing big text fields unless needed (then consider full-text or trigram indexes).
12. **Periodically monitor bloat & unused indexes** — indexes that aren’t used should be dropped.

---

# 🔮 Meta-Heuristic

👉 *Indexes are a contract between queries and storage*.
Design them by starting with **your queries**, not your schema.
Ask: “What’s the fastest way to find this row/range/order?” — then build only the minimum set of indexes to support that.
