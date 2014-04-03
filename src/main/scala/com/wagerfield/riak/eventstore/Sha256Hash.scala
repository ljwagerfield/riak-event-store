package com.wagerfield.riak.eventstore

/**
 * Uniquely identifies an arbitrarily large block of data.
 * @param bytes The SHA-256 hash value.
 */
case class Sha256Hash(bytes: List[Byte]) {
  require(bytes.length == Sha256Hash.size, "Value must be a valid SHA-256 hash.")
}

/**
 * Contains an empty hash value.
 */
object Sha256Hash {
  val size = 32
  val empty = Sha256Hash(List.fill(size)(0.toByte))
}
