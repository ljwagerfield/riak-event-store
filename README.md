Riak Event Store
================

**Work in progress**

The `riak-event-store` library provides data structures and algorithms for persisting [unrolled linked lists][unrolled-linked-list]
in [Riak][riak] using size-bounded nodes to provide basic [event sourcing][event-sourcing].

The library can be used as a Riak-backed alternative to more feature-rich frameworks like [Axon Framework][axon] and
[Event Store][event-store].

Log shards
----------

The `riak-event-store` represents an entire event log as multiple KVPs within Riak. Each of these entries is referred to
as a 'log shard': a size-bounded object that overflows into a new shard when it reaches its maximum size (1000 items).
When this occurs, the full shard retains a reference to the new shard, allowing the complete sequence of events to
be traversed as an unrolled linked-list.

Log shards are classified as either `head` or `tail` shards. An event log has one `head` shard and zero-to-many `tail`
shards. The `head` shard key is a hash of the log name (e.g. `f('my log') = 0x8fb9...`), whereas the `tail` shard keys
match the hash defined in their payloads. This provides a fixed address for the `head` and deterministic non-conflicting
addresses for `tail` shards.

Guarantees and trade-offs
-------------------------

-   Guarantee 1: The earliest occurrence of an event within the log is guaranteed to be in correct causal order.

-   Guarantee 2: All values are size-bounded.

-   Guarantee 3: All `tail` values have a fixed size; these represent the vast majority.

-   Trade-off: Event duplicates may occur. Later events must be ignored when replaying the log; only the earliest event has
the correct order guarantee.

Payload
-------

A log shard has the following size-bounded structure:

    Field   Type    Cardinality
    -----------------------------------
    Hash  | SHA-1 | 1
    ------|-------|--------------------
    Event | SHA-1 | 1 to 1000 - |Child|
    ------|-------|--------------------
    Child | SHA-1 | 1 to 1000 - |Event|
    -----------------------------------

-   `hash` is a function of the entire payload.

-   `event` is a sequence of unique event references in ascending causal order (earliest first, events are appended).

-   `child` is a *set* of references to shards containing previously observed state.

The `event` and `child` hashes occupy a shared quota, where `event`s are allocated before `child`s, with the exception
of at least one child to provide continuity. 

Children imply state that precedes the current shard, although children do not imply causality between themselves: they are siblings and represent event branches. Prioritising the allocation of events before referencing existing children retains the intention from each shard; events from the current payload succeeds those from its children.

### Payload size

Shard sizes have an upper-bound of 20KB. All `tail` shards are guaranteed to be exactly this size, whereas `head` shards
can be anything up to this size.

Unfolding shards
----------------

Unfolding shards refers to the process of producing a causally ordered sequence of events given the `head` shard. The
process is as follows:

1.  `unfold` the linked-list into a partially sorted set:

        a > b = a.child ⊇ b.hash

2.  `sort` unsorted items by `hash` to ensure deterministic ordering of siblings.

3.  `flatmap` over each `event` sub-sequence to produce a sequence of events, with duplicates, in ascending causal order.

4.  `filter` the resulting sequence, keeping only the first occurrence of each element.

Conflicts
---------

`tail` shards are immutable; they will never exhibit conflicts.

`head` shards are mutable; concurrent writes will yield conflicts that must resolve.

Conflict detection is provided through Riak by persisting `head` shards to buckets with vector clocks enabled
(`allow_mult=true`). Conversely, `tail` shards are committed to buckets with this feature optimally disabled.

Riak will automatically raise conflicts without reevaluating merge results against their inputs. This removes the
need to implement shards as complete [CvRDTs][crdt], but still requires payloads to support partial re-merging to allow
concurrent writes post-merge.

[unrolled-linked-list]: http://en.wikipedia.org/wiki/Unrolled_linked_list  "Unrolled linked list"
[riak]: http://basho.com/riak/  "Riak"
[event-sourcing]: http://martinfowler.com/eaaDev/EventSourcing.html  "Event Sourcing"
[axon]: http://www.axonframework.org/  "Axon Framework"
[event-store]: http://geteventstore.com/  "Event Store"
[crdt]: https://github.com/ljwagerfield/crdt  "Conflict-free Replicated Data Types"
