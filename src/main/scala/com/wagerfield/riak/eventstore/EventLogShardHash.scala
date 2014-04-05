package com.wagerfield.riak.eventstore

/**
 * Uniquely identifies an event log shard.
 * @param value Hash value.
 */
case class EventLogShardHash(value: Sha1Hash)
