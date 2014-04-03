import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * Event log shard specification.
 */
class EventLogShardSpec extends Specification {

  "event log shard" should {

    "produce a hash from its content" in new Scope {
      ???
    }

    "support deep equality checking" in new Scope {
      ???
    }

    "be monotonic" in new Scope {
      ??? // Append operations should produce a greater value.
    }

    "be idempotent" in new Scope {
      ??? // Append operations should have no effect with same value.
    }

    "be bounded in size" in new Scope {
      ???
    }

    "retain a link to the child shard" in new Scope {
      ???
    }

    "not retain a link to the parent shard" in new Scope {
      ???
    }
  }
}
