package com.wagerfield.riak.eventstore

/**
 * Uniquely identifies an event.
 * @param value Hash value.
 */
case class EventHash(value: Sha1Hash)
