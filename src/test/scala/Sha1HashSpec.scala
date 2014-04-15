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
      failure
    }
  }
}
