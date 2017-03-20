package types.primitives.timestamp

import java.time._

import types.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
sealed case class TimestampTypeConstraint private(name: String,
                                                  value: String) extends Constraint[String]

/**
  * List of all the valid timestamp constraints.
  */
trait TimestampTypesConstraints {

  @inline private[this] val minTimestampNameInAST: String = "MinTimestamp"
  @inline private[this] val maxTimestampNameInAST: String = "MaxTimestamp"
  @inline private[this] val formatNameInAST: String = "Format"

  @inline private[this] val isoLocalTimestampNameInAST: String = "ISO-8601"
  @inline private[this] val isoTimestampWithOffsetNameInAST: String = "ISO-8601 WITH OFFSET"
  @inline private[this] val isoTimestampWithTimeZoneInAST: String = "ISO-8601 WITH TIMEZONE"

  /**
    * Object that can be used for constraining the limit timestamp
    * as the current time on the framework implementation.
    */
  @inline case object Now {
    val astName = "NOW()"
  }

  /**
    * Represents the constraint indicating how to persist a local timestamp
    * using the ISO-8601 convention.
    *
    * Will be serialized as: '2017-03-20T10:50:22.730'
    */
  def constraintFormatLocalTimestamp: TimestampTypeConstraint =
    TimestampTypeConstraint(formatNameInAST, isoLocalTimestampNameInAST)

  /**
    * Represents the constraint indicating how to persist a timestamp
    * with its offset on the AST, using the ISO-8601 convention.
    *
    * Will be serialized as: '2017-03-20T10:50:22.730Z' and
    * '2017-03-20T11:33:51.435+01:00'.
    */
  def constraintFormatTimestampWithOffset: TimestampTypeConstraint =
    TimestampTypeConstraint(formatNameInAST, isoTimestampWithOffsetNameInAST)

  /**
    * Represents the constraint indicating how to persist a timestamp
    * with its offset on the AST, using the ISO-8601 convention.
    *
    * Will be serialized as: '2017-03-20T11:34:56.581+01:00[Europe/Andorra]'
    */
  def constraintFormatTimestampWithTimeZone: TimestampTypeConstraint =
    TimestampTypeConstraint(formatNameInAST, isoTimestampWithTimeZoneInAST)

  /**
    * Sets the minimum possible timestamp to the current timestamp.
    */
  def constraintMinTimestamp(literalType: Now.type): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, Now.astName)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[Instant]]: '2017-03-17T18:56:34.468Z'
    */
  def constraintMinTimestamp(minTime: Instant): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using a [[LocalDateTime]]: '2017-03-17T18:56:34.468'
    */
  def constraintMinTimestamp(minTime: LocalDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[OffsetDateTime]]: '2017-03-17T19:00:47.743+01:00'
    */
  def constraintMinTimestamp(minTime: OffsetDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format when using
    * a [[ZonedDateTime]]: '2017-03-17T19:02:18.426+01:00[Europe/Andorra]'
    */
  def constraintMinTimestamp(minTime: ZonedDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(minTimestampNameInAST, minTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[Instant]]: '2017-03-17T18:56:34.468Z'
    */
  def constraintMaxTimestamp(maxTime: Instant): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using a [[LocalDateTime]]: '2017-03-17T18:56:34.468'
    */
  def constraintMaxTimestamp(maxTime: LocalDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[OffsetDateTime]]: '2017-03-17T19:00:47.743+01:00'
    */
  def constraintMaxTimestamp(maxTime: OffsetDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format when using
    * a [[ZonedDateTime]]: '2017-03-17T19:02:18.426+01:00[Europe/Andorra]'
    */
  def constraintMaxTimestamp(maxTime: ZonedDateTime): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, maxTime.toString)

  /**
    * Sets the minimum possible timestamp to the current timestamp.
    */
  def constraintMaxTimestamp(literalType: Now.type): TimestampTypeConstraint =
    TimestampTypeConstraint(maxTimestampNameInAST, Now.astName)

}
