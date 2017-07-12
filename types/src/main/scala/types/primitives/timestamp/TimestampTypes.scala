package types
package primitives.timestamp

import java.time.{Instant, LocalDateTime, OffsetDateTime, ZonedDateTime}

import scala.language.implicitConversions

/**
  * Trait containing all [[TimestampType]]s and all Babel
  * [[Type]]s implicit conversions.
  */
trait TimestampTypes {

  /**
    * Represents a local timestamp, without timezone in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T10:24:22.730'
    */
  private[types] val astLocalDateTime: TimestampType =
    TimestampType(TimestampTypesConstraints.formatLocalTimestamp)

  /**
    * Implicit conversion from [[LocalDateTime]] to [[astLocalDateTime]].
    */
  implicit def localDateTimeToAstType(typ: Class[LocalDateTime]): TimestampType =
    astLocalDateTime

  /**
    * Represents a timestamp with an offset in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T10:24:22.730Z'
    * or '2017-03-20T10:24:22.730+10:00'
    */
  private[types] val astOffsetDateTime: TimestampType =
    TimestampType(TimestampTypesConstraints.formatTimestampWithOffset)

  /**
    * Implicit conversion from [[OffsetDateTime]] to [[astOffsetDateTime]].
    */
  implicit def instantDateTimeToAstType(typ: Class[Instant]): TimestampType =
    astOffsetDateTime

  /**
    * Implicit conversion from [[java.time.OffsetDateTime]] class to [[astOffsetDateTime]].
    */
  implicit def offsetDateTimeToAstType(typ: Class[OffsetDateTime]): TimestampType =
    astOffsetDateTime

  /**
    * Represents a timestamp with its timezone in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T11:34:56.581+01:00[Europe/Andorra]'
    */
  private[types] val astZonedDateTime: TimestampType =
    TimestampType(TimestampTypesConstraints.formatTimestampWithTimeZone)

  /**
    * Implicit conversion from [[ZonedDateTime]] to [[astZonedDateTime]].
    */
  implicit def zonedDateTimeToAstType(typ: Class[ZonedDateTime]): TimestampType =
    astZonedDateTime

}

/**
  * @see [[TimestampTypes]]
  */
object TimestampTypes extends TimestampTypes {

  def typeNameToBabelType(typeName: String): Option[TimestampType] = {
    typeName match {
      case "LocalDateTime" => Some(classOf[LocalDateTime])
      case "OffsetDateTime" => Some(classOf[OffsetDateTime])
      case "Instant" => Some(classOf[Instant])
      case "ZonedDateTime" => Some(classOf[ZonedDateTime])
      case _ => None
    }
  }
}
