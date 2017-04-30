package types
package primitives.timestamp

import java.time.{Instant, LocalDateTime, OffsetDateTime, ZonedDateTime}
import types.primitives.PrimitiveType

/**
  * Represents a Timestamp type, which represents the way of
  * representing an specific time moment.
  */
case class TimestampType private (override val typeName: String,
                                  override val constraints: Seq[TimestampTypeConstraint]
                                 ) extends PrimitiveType[TimestampTypeConstraint] {

  /**
    * Sets the minimum possible timestamp to the current timestamp.
    */
  def withMinTimestamp(literalType: Now.type): TimestampType =
    withConstraint(
      TimestampTypesConstraints.minTimestamp(Now)
    )

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[Instant]]: '2017-03-17T18:56:34.468Z'
    */
  def withMinTimestamp(minTime: Instant): TimestampType =
    withConstraint(
      TimestampTypesConstraints.minTimestamp(minTime)
    )

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using a [[LocalDateTime]]: '2017-03-17T18:56:34.468'
    */
  def withMinTimestamp(minTime: LocalDateTime): TimestampType =
    withConstraint(
      TimestampTypesConstraints.minTimestamp(minTime)
    )

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[OffsetDateTime]]: '2017-03-17T19:00:47.743+01:00'
    */
  def withMinTimestamp(minTime: OffsetDateTime): TimestampType =
    withConstraint(
      TimestampTypesConstraints.minTimestamp(minTime)
    )

  /**
    * Sets the minimum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format when using
    * a [[ZonedDateTime]]: '2017-03-17T19:02:18.426+01:00[Europe/Andorra]'
    */
  def withMinTimestamp(minTime: ZonedDateTime): TimestampType =
    withConstraint(
      TimestampTypesConstraints.minTimestamp(minTime)
    )

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[Instant]]: '2017-03-17T18:56:34.468Z'
    */
  def withMaxTimestamp(maxTime: Instant): TimestampType =
    withConstraint(
      TimestampTypesConstraints.maxTimestamp(maxTime)
    )

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using a [[LocalDateTime]]: '2017-03-17T18:56:34.468'
    */
  def withMaxTimestamp(maxTime: LocalDateTime): TimestampType =
    withConstraint(
      TimestampTypesConstraints.maxTimestamp(maxTime)
    )

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format
    * when using an [[OffsetDateTime]]: '2017-03-17T19:00:47.743+01:00'
    */
  def withMaxTimestamp(maxTime: OffsetDateTime): TimestampType =
    withConstraint(
      TimestampTypesConstraints.maxTimestamp(maxTime)
    )

  /**
    * Sets the maximum possible timestamp of a value.
    *
    * Timestamp will be serialized on the following format when using
    * a [[ZonedDateTime]]: '2017-03-17T19:02:18.426+01:00[Europe/Andorra]'
    */
  def withMaxTimestamp(maxTime: ZonedDateTime): TimestampType =
    withConstraint(
      TimestampTypesConstraints.maxTimestamp(maxTime)
    )

  /**
    * Sets the minimum possible timestamp to the current timestamp.
    */
  def withMaxTimestamp(literalType: Now.type): TimestampType =
    withConstraint(
      TimestampTypesConstraints.maxTimestamp(Now)
    )

  /**
    * Adds a new constraint to the timestamp type. If the
    * constraint is repeated replaces it with the new one.
    */
  private[this] def withConstraint(constraint: TimestampTypeConstraint): TimestampType = {
    val otherConstraints = this.constraints.filterNot(_.name == constraint.name)
    TimestampType(otherConstraints :+ constraint: _*)
  }
}

/**
  * Numeric type companion object.
  */
object TimestampType {

  /**
    * Name of the 'Timestamp' types in the AST.
    */
  val typeName: String = "Timestamp"

  /**
    * Apply method needed for generating a [[TimestampType]]
    * from the given constraints.
    */
  def apply(constraints: TimestampTypeConstraint*): TimestampType =
    TimestampType(typeName, if (constraints.isEmpty) Nil else constraints.toList)

}
