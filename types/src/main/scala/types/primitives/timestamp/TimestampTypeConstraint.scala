package types
package primitives.timestamp

import java.time._

import types.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
private[types] case class TimestampTypeConstraint(override val name: String,
                                                  value: String
                                                 ) extends Constraint(name, value)

/**
  * List of all the valid timestamp constraints.
  */
private[types] object TimestampTypesConstraints {

  @inline private[this] val minTimestampNameInAST: String = "MinTimestamp"
  @inline private[this] val maxTimestampNameInAST: String = "MaxTimestamp"
  @inline private[this] val formatNameInAST: String = "Format"

  @inline private[this] val isoLocalTimestampNameInAST: String = "ISO-8601"
  @inline private[this] val isoTimestampWithOffsetNameInAST: String = "ISO-8601 WITH OFFSET"
  @inline private[this] val isoTimestampWithTimeZoneInAST: String = "ISO-8601 WITH TIMEZONE"

  /**
    * Represents the constraint indicating how to persist a local timestamp
    * using the ISO-8601 convention.
    *
    * Will be serialized as: '2017-03-20T10:50:22.730'
    */
  def formatLocalTimestamp: TimestampTypeConstraint =
    TimestampTypeConstraint(formatNameInAST, isoLocalTimestampNameInAST)

  /**
    * Represents the constraint indicating how to persist a timestamp
    * with its offset on the AST, using the ISO-8601 convention.
    *
    * Will be serialized as: '2017-03-20T10:50:22.730Z' and
    * '2017-03-20T11:33:51.435+01:00'.
    */
  def formatTimestampWithOffset: TimestampTypeConstraint =
    TimestampTypeConstraint(formatNameInAST, isoTimestampWithOffsetNameInAST)

  /**
    * Represents the constraint indicating how to persist a timestamp
    * with its offset on the AST, using the ISO-8601 convention.
    *
    * Will be serialized as: '2017-03-20T11:34:56.581+01:00[Europe/Andorra]'
    */
  def formatTimestampWithTimeZone: TimestampTypeConstraint =
    TimestampTypeConstraint(formatNameInAST, isoTimestampWithTimeZoneInAST)

  /**
    * Sets the minimum possible timestamp to the current timestamp.
    */
  def minTimestamp(literalType: Now.type): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, Now.astName)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[Instant]]: '2017-03-17T18:56:34.468Z'
    */
  def minTimestamp(minTime: Instant): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using a [[LocalDateTime]]: '2017-03-17T18:56:34.468'
    */
  def minTimestamp(minTime: LocalDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[OffsetDateTime]]: '2017-03-17T19:00:47.743+01:00'
    */
  def minTimestamp(minTime: OffsetDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format when using
    * a [[ZonedDateTime]]: '2017-03-17T19:02:18.426+01:00[Europe/Andorra]'
    */
  def minTimestamp(minTime: ZonedDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[Instant]]: '2017-03-17T18:56:34.468Z'
    */
  def maxTimestamp(maxTime: Instant): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using a [[LocalDateTime]]: '2017-03-17T18:56:34.468'
    */
  def maxTimestamp(maxTime: LocalDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[OffsetDateTime]]: '2017-03-17T19:00:47.743+01:00'
    */
  def maxTimestamp(maxTime: OffsetDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format when using
    * a [[ZonedDateTime]]: '2017-03-17T19:02:18.426+01:00[Europe/Andorra]'
    */
  def maxTimestamp(maxTime: ZonedDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the minimum possible timestamp to the current timestamp.
    */
  def maxTimestamp(literalType: Now.type): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, Now.astName)

}
