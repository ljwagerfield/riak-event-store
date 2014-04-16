import com.wagerfield.riak.eventstore.{EventLogShardHash, Sha1Hash}
import org.specs2.mutable.Specification

/**
 * Event log shard hash specification.
 */
class EventLogShardHashSpec extends Specification {

  "event log shard hashes" should {

    "be comparable" in {
      val lowestA = EventLogShardHash(Sha1Hash(List.fill(20)(0.toByte)))
      val lowestB = EventLogShardHash(Sha1Hash(List.fill(20)(0.toByte)))
      val greatest = EventLogShardHash(Sha1Hash(List.fill(20)(9.toByte)))

      lowestA mustEqual lowestB
      lowestA mustNotEqual greatest

      EventLogShardHash.Ordering.compare(lowestA, lowestB) must beEqualTo(0)
      EventLogShardHash.Ordering.compare(lowestA, greatest) must beLessThan(0)
      EventLogShardHash.Ordering.compare(greatest, lowestA) must beGreaterThan(0)
    }
  }
}
