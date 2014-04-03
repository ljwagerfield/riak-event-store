Riak Event Store
================

**Work in progress**

An event log implemented on Riak as a tree of size-bounded sequences, referred to as 'log shards'.

Log shards
----------

An event log is represented as multiple KVPs within Riak. Each of these entries is referred to as a 'log shard': a
size-bounded object that overflows into a new shard when it reaches its maximum size (1000 items). When this occurs, the
full shard retains a reference to the new shard, allowing the complete sequence of events to be traversed as a linked-list.

Log shards are classified as either `head` or `tail` shards. An event log has one `head` shard and zero-to-many `tail`
shards. The `head` shard key is a hash of the log name (e.g. `f('my log') = 0x8fb9...`), whereas the `tail` shard keys
match the hash defined in their payloads. This provides a fixed address for the `head` and deterministic non-conflicting
addresses for `tail` shards.

Payload
-------

A log shard has the following size-bounded structure:

    ---------------------
    1 GUID | Hash
    -------|-------------
    0-1000 | Child Hashes
    GUIDs  |-------------
           | Event IDs
    ---------------------

Log shard hashes are a function of all event IDs and child shard hashes.

Child shard hashes reference zero-to-many child shards within the tree. Multiple children are a result of merging
multiple conflicting `head` shards (discussed later). Systems that do not experience concurrent writes will simply
produce a linked-list (i.e. shards will have up to one child).

Event IDs represent the 'useful payload': the sequence of events we are maintaining causal order for.

Conflicts
---------

`tail` shards are immutable; they will never exhibit conflicts.

`head` shards are mutable; concurrent writes will yield conflicts that must resolve.

Conflict detection is provided through Riak by persisting `head` shards to buckets with vector clocks enabled
(`allow_mult=true`). Conversely, `tail` shards are committed to buckets with this feature optimally disabled.

Riak will automatically raise conflicts without reevaluating merge results against their inputs. This removes the
need to implement shards as complete CvRDTs, but still requires payloads to support partial re-merging to allow
concurrent writes post-merge.

The event store achieves this by implementing GUID lists as sorted sets, making appends idempotent.

### Trade-off: duplicate events

Due to their size-bounded design, shards will adsorb duplicate events during re-merges with shards that contain
previously-truncated events. This must be considered when replaying the event log.
