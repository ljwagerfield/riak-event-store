import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * Specifies the behaviour of the ordering algorithm for event log shards.
 */
class EventLogShardOrderingSpec extends Specification {

   "event log shard ordering" should {

     "rank parents over children" in new Scope {
       ???
     }

     "rank children under parents" in new Scope {
       ???
     }

     "rank identical shards as equals" in new Scope {
       ???
     }

     "not rank sibling shards" in new Scope {
       ???
     }
   }
 }
