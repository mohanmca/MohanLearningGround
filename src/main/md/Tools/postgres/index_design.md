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
Here’s a compact, battle-tested playbook for PostgreSQL index design—split into **first principles**, **practical guidance**, and a **pre-deployment checklist**.

# First principles

* **Indexes trade read speed for write cost + space.** Only add them to serve real, recurring queries. Measure first. ([PostgreSQL][1])
* **Pick the index *type* for the operators you use.**

  * **B-tree**: equality, ranges, sorting; the *only* type that can satisfy `ORDER BY` directly.
  * **GIN/GiST/SP-GiST/BRIN/Hash**: specialized cases (arrays/JSONB, ranges/geo, tries, huge time-correlated tables, equality-only). ([PostgreSQL][2])
* **Multicolumn order matters (leftmost rule).** Put the most selective / most-filtered column first; order also drives whether `ORDER BY` is satisfied from the index. Mixed ASC/DESC must match the query. ([PostgreSQL][3])
* **Index-only scans** are great, but only work when the **visibility map** says heap pages are “all visible”. Use `INCLUDE` to cover select-list columns; expect occasional heap hits when pages aren’t all-visible. ([PostgreSQL][4])
* **Partial indexes** target a hot slice (e.g., `WHERE status = 'active'`) to cut size and maintenance. ([PostgreSQL][5])
* **Expression indexes** make predicates like `WHERE lower(email) = …` indexable. ([PostgreSQL][6])
* **NULLs & uniqueness:** by default, `UNIQUE` treats NULLs as *distinct*; use `NULLS NOT DISTINCT` to treat them as equal. ([PostgreSQL][7])
* **Partitioned tables:** unique constraints (and PKs) must include the partition key; there are no global indexes. ([PostgreSQL][8])
* **Foreign keys:** Postgres auto-indexes the *referenced* side (via the PK/UNIQUE), **not** the referencing column—index FKs yourself if you join on them or delete/update parents. ([Stack Overflow][9])
* **Online builds:** prefer `CREATE INDEX CONCURRENTLY` (longer, but doesn’t block writes). ([PostgreSQL][10])

# Practical guidance (what to build, when)

* **Classic OLTP lookups / ranges / sorts → B-tree.** If queries also sort, align index order and even mix ASC/DESC to match `ORDER BY`. ([PostgreSQL][11])
* **Hot subset filters → Partial B-tree.** Example: only “open” tickets or “recent” rows. ([PostgreSQL][5])
* **Covering reads → B-tree with `INCLUDE`.** Put filter and join keys in the index *keys*; put purely-selected columns in `INCLUDE` to enable index-only scans. ([PostgreSQL][10])
* **Case-insensitive / computed predicates → Expression index.** e.g., `CREATE INDEX ON users ((lower(email)))`. ([PostgreSQL][6])
* **LIKE/ILIKE on text with non-C collation → B-tree + pattern opclass.** Use `text_pattern_ops`/`varchar_pattern_ops` so `LIKE 'abc%'` can use the index. For fuzzy/substring search, use **pg\_trgm** with **GIN/GiST**. ([PostgreSQL][12])
* **JSONB containment / path queries → GIN.** Choose `jsonb_path_ops` (narrower, often smaller/faster for `@>` containment) vs default `jsonb_ops` (supports more operators). ([PostgreSQL][13])
* **Full-text search → GIN on `tsvector`.** It’s the preferred index for text search. ([PostgreSQL][14])
* **Time-series / very large append-only tables → BRIN.** Tiny, write-friendly indexes that exploit physical correlation (e.g., by timestamp). ([PostgreSQL][15], [Crunchy Data][16])
* **Range / geometry / “overlap” logic or exclusion constraints → GiST (often with `btree_gist`).** ([PostgreSQL][17])

# Pre-deployment checklist

**Understand the workload**

* Enable and read **`pg_stat_statements`** to find top queries by time/calls; normalize query shapes. ([PostgreSQL][18])
* Baseline with **`EXPLAIN (ANALYZE, BUFFERS)`** for representative queries. ([PostgreSQL][19])

**Design the index**

* For each high-value query:

  * Pick index type that supports its operators. ([PostgreSQL][2])
  * Choose multicolumn order to satisfy `WHERE` and `ORDER BY` (direction included). ([PostgreSQL][11])
  * Consider **partial** or **expression** indexes where applicable. ([PostgreSQL][5])
  * For covering reads, add non-key columns via **`INCLUDE`** and verify index-only feasibility (visibility map). ([PostgreSQL][10])
  * On partitioned tables, include the partition key in any `UNIQUE`/PK index. ([PostgreSQL][8])
  * Add indexes on **foreign keys** used in joins or when parent rows are updated/deleted. ([Stack Overflow][9])

**Rollout safely**

* Build with **`CREATE INDEX CONCURRENTLY`** on busy tables; ensure disk headroom for index + write-amplification during build. ([PostgreSQL][10])

**Verify & maintain**

* Re-run **`EXPLAIN (ANALYZE, BUFFERS)`**; confirm expected scans and sort elimination. ([PostgreSQL][19])
* Track usage in **`pg_stat_user_indexes`** / **`pg_stat_all_indexes`** (look for low/zero `idx_scan`). Clean up unused/duplicate indexes. ([PostgreSQL][20])
* Periodically VACUUM/ANALYZE as needed so planner estimates and visibility map stay healthy (improves index-only scans). ([PostgreSQL][21])

---

If you want, share a couple of your heaviest queries (text + `EXPLAIN (ANALYZE, BUFFERS)` output), and I’ll propose the exact indexes and key orderings.

[1]: https://www.postgresql.org/docs/current/indexes.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: Chapter 11. Indexes"
[2]: https://www.postgresql.org/docs/current/indexes-types.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.2. Index Types"
[3]: https://www.postgresql.org/docs/current/indexes-multicolumn.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.3. Multicolumn Indexes"
[4]: https://www.postgresql.org/docs/current/indexes-index-only-scans.html?utm_source=chatgpt.com "11.9. Index-Only Scans and Covering Indexes - PostgreSQL"
[5]: https://www.postgresql.org/docs/current/indexes-partial.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.8. Partial Indexes"
[6]: https://www.postgresql.org/docs/current/indexes-expressional.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.7. Indexes on Expressions"
[7]: https://www.postgresql.org/docs/current/indexes-unique.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.6. Unique Indexes"
[8]: https://www.postgresql.org/docs/current/ddl-partitioning.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 5.12. Table Partitioning"
[9]: https://stackoverflow.com/questions/970562/postgres-and-indexes-on-foreign-keys-and-primary-keys?utm_source=chatgpt.com "Postgres and indexes on foreign keys and primary keys"
[10]: https://www.postgresql.org/docs/current/sql-createindex.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: CREATE INDEX"
[11]: https://www.postgresql.org/docs/current/indexes-ordering.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.4. Indexes and ORDER BY"
[12]: https://www.postgresql.org/docs/current/indexes-opclass.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.10. Operator Classes and Operator ..."
[13]: https://www.postgresql.org/docs/current/gin.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 64.4. GIN Indexes"
[14]: https://www.postgresql.org/docs/current/textsearch-indexes.html?utm_source=chatgpt.com "12.9. Preferred Index Types for Text Search - PostgreSQL"
[15]: https://www.postgresql.org/docs/current/brin.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 64.5. BRIN Indexes"
[16]: https://www.crunchydata.com/blog/postgres-indexing-when-does-brin-win?utm_source=chatgpt.com "Postgres Indexing: When Does BRIN Win? | Crunchy Data Blog"
[17]: https://www.postgresql.org/docs/current/ddl-constraints.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 5.5. Constraints"
[18]: https://www.postgresql.org/docs/current/pgstatstatements.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: F.30. pg_stat_statements — track ..."
[19]: https://www.postgresql.org/docs/current/sql-explain.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: EXPLAIN"
[20]: https://www.postgresql.org/docs/current/indexes-examine.html?utm_source=chatgpt.com "PostgreSQL: Documentation: 17: 11.12. Examining Index Usage"
[21]: https://www.postgresql.org/docs/16//indexes-index-only-scans.html?utm_source=chatgpt.com "11.9. Index-Only Scans and Covering Indexes - PostgreSQL"

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
