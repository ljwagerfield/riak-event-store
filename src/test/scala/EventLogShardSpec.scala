import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * Event log shard specification.
 */
class EventLogShardSpec extends Specification {

  "event log shard" should {

    "produce a hash from its content" in new Scope {
      failure
    }

    "produce a hash that distinguishes between events and children" in new Scope {
      failure // Same hashes for children must be different to same hashes for events.
    }

    "support deep equality checking" in new Scope {
      failure
    }

    "be monotonic" in new Scope {
      failure // Append operations should produce a greater value.
    }

    "be idempotent" in new Scope {
      failure // Append operations should have no effect with same value.
    }

    "be bounded in size" in new Scope {
      failure
    }

    "retain a link to the child shard" in new Scope {
      failure
    }

    "not retain a link to the parent shard" in new Scope {
      failure
    }
  }

  "event log shard tails" should {

    "have a constant size" in new Scope {
      failure
    }
  }

  "event log shard ordering" should {

    "rank parents under children" in new Scope {
      failure
    }

    "rank children over parents" in new Scope {
      failure
    }

    "rank identical shards as equal" in new Scope {
      failure
    }

    "rank sibling shards as equal" in new Scope {
      failure
    }
  }
}
