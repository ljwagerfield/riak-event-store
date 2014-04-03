import com.wagerfield.riak.eventstore.Sha256Hash
import org.specs2.mutable.Specification

/**
 * SHA-256 hash specification.
 */
class Sha256HashSpec extends Specification {

  "SHA-256 hash" should {

    "contain 256 bits" in {
      val value = List.fill(32)(0.toByte)
      val hash = Sha256Hash(value)
      hash.bytes must beEqualTo(value)
    }

    "not contain more than 256 bits" in {
      val value = List.fill(33)(0.toByte)
      Sha256Hash(value) must throwA[Exception]
    }

    "not contain fewer than 256 bits" in {
      val value = List.fill(31)(0.toByte)
      Sha256Hash(value) must throwA[Exception]
    }

    "not be empty" in {
      val value = Nil
      Sha256Hash(value) must throwA[Exception]
    }
  }
}
