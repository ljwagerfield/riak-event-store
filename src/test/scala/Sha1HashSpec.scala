import com.wagerfield.riak.eventstore.Sha1Hash
import org.specs2.mutable.Specification

/**
 * SHA-1 hash specification.
 */
class Sha1HashSpec extends Specification {

  "SHA-1 hash" should {

    "contain 160 bits" in {
      val value = List.fill(20)(0.toByte)
      val hash = Sha1Hash(value)
      hash.bytes must beEqualTo(value)
    }

    "not contain more than 160 bits" in {
      val value = List.fill(21)(0.toByte)
      Sha1Hash(value) must throwA[Exception]
      Nil.distinct
    }

    "not contain fewer than 160 bits" in {
      val value = List.fill(19)(0.toByte)
      Sha1Hash(value) must throwA[Exception]
    }

    "not be empty" in {
      val value = Nil
      Sha1Hash(value) must throwA[Exception]
    }
  }

  "SHA-1 hashes" should {

    "be comparable" in {
      val lowestA = Sha1Hash(List.fill(20)(0.toByte))
      val lowestB = Sha1Hash(List.fill(20)(0.toByte))
      val greatest = Sha1Hash(List.fill(20)(9.toByte))

      lowestA mustEqual lowestB
      lowestA mustNotEqual greatest

      Sha1Hash.Ordering.compare(lowestA, lowestB) must beEqualTo(0)
      Sha1Hash.Ordering.compare(lowestA, greatest) must beLessThan(0)
      Sha1Hash.Ordering.compare(greatest, lowestA) must beGreaterThan(0)
    }
  }
}
