package com.wagerfield.riak.eventstore

/**
 * Uniquely identifies an arbitrarily large block of data.
 * @param bytes The SHA-1 hash value.
 */
case class Sha1Hash(bytes: List[Byte]) {
  require(bytes.length == Sha1Hash.size, "Value must be a valid SHA-1 hash.")
}

/**
 * Contains an empty hash value.
 */
object Sha1Hash {
  val size = 20
  val empty = Sha1Hash(List.fill(size)(0.toByte))
}
