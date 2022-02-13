##FSync

## Durability and Concept of checkpoint
  * Every time check point is invoked
    * Shared buffers (Managed by PGSQL) - WAL Buffers (Top layer)
      * Page Cache (Kernel Managed) (mid layer)
        * [Data Files] and [WAL - TransactonLog]

## Shared buffers
* Are written to page cache

## Page Cache (Kernel Managed) (mid layer)
* It invokes fsync to store to underlying file (DataFiles and WAL)

## When IO error happens
* We expect fsync to tries to rewrite the same data
* fsync may write data on network storage, even tiny network failure might cause fsync to fail
* But fsync won't retry next time, but marks the actual buffer as clean (kind of insync with io) (ext4)

## One file might have multile FILE descriptor
* When failure reported in one process the should be reported to other process, but only first process gets the error


## Reference
*[PostgreSQL vs. fsync How is it possible that PostgreSQL used fsync incorrectly for 20 years, and wh…](https://www.youtube.com/watch?v=1VWIGBQLtxo)
* Write-Ahead Logging (WAL)