package babel
package primitives.timestamp

import java.time.{Instant, LocalDateTime, OffsetDateTime, ZonedDateTime}

import babel.utils.TypeNameUtils

import scala.language.implicitConversions

/**
  * Trait containing all [[TimestampType]] to Babel [[Type]]s implicit conversions.
  */
trait TimestampTypes {

  /**
    * Represents a local timestamp, without timezone in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T10:24:22.730'
    */
  private[timestamp] val astLocalDateTime: TimestampType =
    TimestampType(TimestampTypesConstraints.formatLocalTimestamp)

  /**
    * Implicit conversion from [[LocalDateTime]] to [[astLocalDateTime]].
    */
  implicit def localDateTimeToAstType(typ: Class[LocalDateTime]): TimestampType =
    astLocalDateTime

  /**
    * Represents an optional local timestamp, without timezone in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T10:24:22.730'
    */
  private[timestamp] val optionalAstLocalDateTime: TimestampType =
    TimestampType(isRequired = false, constraints = astLocalDateTime.constraints)

  /**
    * Implicit conversion from an optional [[LocalDateTime]] to [[optionalAstLocalDateTime]].
    */
  implicit def optionalLocalDateTimeToAstType(typ: Option[Class[LocalDateTime]]): TimestampType =
    optionalAstLocalDateTime

  /**
    * Represents a timestamp with an offset in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T10:24:22.730Z'
    * or '2017-03-20T10:24:22.730+10:00'
    */
  private[timestamp] val astOffsetDateTime: TimestampType =
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
    * Represents an optional timestamp with an offset in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T10:24:22.730Z'
    * or '2017-03-20T10:24:22.730+10:00'
    */
  private[timestamp] val optionalAstOffsetDateTime: TimestampType =
    TimestampType(isRequired = false, constraints = astOffsetDateTime.constraints)

  /**
    * Implicit conversion from an optional [[OffsetDateTime]] to [[optionalAstOffsetDateTime]].
    */
  implicit def optionalInstantDateTimeToAstType(typ: Option[Class[Instant]]): TimestampType =
    optionalAstOffsetDateTime

  /**
    * Implicit conversion from an optional [[java.time.OffsetDateTime]] class to [[optionalAstOffsetDateTime]].
    */
  implicit def optionalOffsetDateTimeToAstType(typ: Option[Class[OffsetDateTime]]): TimestampType =
    optionalAstOffsetDateTime

  /**
    * Represents a timestamp with its timezone in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T11:34:56.581+01:00[Europe/Andorra]'
    */
  private[timestamp] val astZonedDateTime: TimestampType =
    TimestampType(TimestampTypesConstraints.formatTimestampWithTimeZone)

  /**
    * Implicit conversion from [[ZonedDateTime]] to [[astZonedDateTime]].
    */
  implicit def zonedDateTimeToAstType(typ: Class[ZonedDateTime]): TimestampType =
    astZonedDateTime

  /**
    * Represents an optional timestamp with its timezone in the AST.
    *
    * Will be serialized in the AST as: '2017-03-20T11:34:56.581+01:00[Europe/Andorra]'
    */
  private[timestamp] val optionalAstZonedDateTime: TimestampType =
    TimestampType(isRequired = false, constraints = astZonedDateTime.constraints)

  /**
    * Implicit conversion from [[ZonedDateTime]] to [[optionalAstZonedDateTime]].
    */
  implicit def optionalZonedDateTimeToAstType(typ: Option[Class[ZonedDateTime]]): TimestampType =
    optionalAstZonedDateTime
}

/**
  * @see [[TimestampTypes]]
  */
object TimestampTypes extends TimestampTypes {

  private[babel] def typeNameToBabelType(typeName: String): Option[TimestampType] = {

    val parsedTypeName = TypeNameUtils.parseTypeName(typeName)
    val isRequired = parsedTypeName.isRequired

    parsedTypeName.typeName match {
      case "LocalDateTime" => Some(if (isRequired) astLocalDateTime else optionalAstLocalDateTime)
      case "OffsetDateTime" | "Instant" =>
        Some(if (isRequired) astOffsetDateTime else optionalAstOffsetDateTime)
      case "ZonedDateTime" => Some(if (isRequired) astZonedDateTime else optionalAstZonedDateTime)
      case _ => None
    }
  }
}
