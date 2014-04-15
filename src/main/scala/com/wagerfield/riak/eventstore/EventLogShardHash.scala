package com.wagerfield.riak.eventstore

/**
 * Uniquely identifies an event log shard.
 * @param value Hash value.
 */
case class EventLogShardHash(value: Sha1Hash)

/**
 * Event log shard hash globals.
 */
object EventLogShardHash {

  /**
   * Provides ordering to event log hash collections.
   */
  implicit val Ordering: scala.Ordering[EventLogShardHash] = scala.Ordering[Sha1Hash].on[EventLogShardHash](_.value)
}
