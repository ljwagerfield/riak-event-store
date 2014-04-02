Riak Event Store
================

**Work in progress**

A Riak event store implemented as a linked-list of size-bounded sequences.

Data structure
--------------

An event log is represented as a tree of 'log shards'. A log shard is a convergent replicated data type (CvRDT) with
the following structure:

    -------------------------------
    1 GUID | Log Shard ID
    -------|-----------------------
    0-1000 | Previous Log Shard IDs
    GUIDs  |-----------------------
           | Event IDs
    -------------------------------

Log shard ID
------------

Log shards are classified as either `head` or `tail` shards. A single event log has one `head` shard and
zero-to-many `tail` shards. The `head` shard ID is a hash of the log name (e.g. `f('my log') = 0x8fb9...`), whereas the
`tail` shard IDs are a hash of their payload (event IDs + previous log IDs).

Conflicts
---------

`tail` shards are immutable so cannot yield conflicts.

`head` shards are mutable so must yield conflicts (aka siblings) for concurrent writes.
