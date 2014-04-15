package com.wagerfield.riak.eventstore

import scala.math.Ordering.Implicits.seqDerivedOrdering

/**
 * Uniquely identifies an arbitrarily large block of data.
 * @param bytes The SHA-1 hash value.
 */
case class Sha1Hash(bytes: List[Byte]) {
  require(bytes.length == Sha1Hash.Size, "Value must be a valid SHA-1 hash.")
}

/**
 * SHA-1 hash globals.
 */
object Sha1Hash {

  /**
   * Provides ordering to hash collections.
   */
  implicit val Ordering: scala.Ordering[Sha1Hash] = scala.Ordering[Seq[Byte]].on[Sha1Hash](_.bytes)

  /**
   * Number of bytes in hash.
   */
  val Size = 20

  /**
   * Empty hash. Whilst SHA-1 does not reserve this value, producing a zero-valued hash is equally as improbable to any
   * other collision.
   */
  val Empty = Sha1Hash(List.fill(Size)(0.toByte))
}
