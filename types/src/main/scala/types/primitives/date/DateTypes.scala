package types.primitives.date

import java.util.Date
import java.time.LocalDate

import types.utils.TypeNameUtils

import scala.language.implicitConversions

/**
  * Trait containing all [[DateTypes]]s to Babel [[types.Type]]s implicit conversions.
  */
trait DateTypes {

  @inline private[this] val defaultDateOnAST: LocalDate = {
    @inline val defaultMinYearOnAST: Int = 1900
    @inline val defaultMinYearDayOnAst: Int = 1
    LocalDate.ofYearDay(defaultMinYearOnAST, defaultMinYearDayOnAst)
  }

  /**
    * Represents the default date format on the AST.
    */
  private[date] val astDateType: DateType =
    DateType(DateTypeConstraints.minDate(defaultDateOnAST), DateTypeConstraints.maxDate(types.Now))

  /**
    * Represents the default date format on the AST in case it is not required.
    */
  private[date] val optionalAstDateType: DateType =
    new DateType(isRequired = false, astDateType.constraints)

  /**
    * Implicit conversion from [[Date]] to [[astDateType]].
    */
  implicit def dateToBabelType(typ: Class[Date]): DateType = astDateType

  /**
    * Implicit conversion from an optional [[Date]] to [[optionalAstDateType]]
    */
  implicit def optionalDateTypeToType(typ: Option[Class[Date]]): DateType = optionalAstDateType

  /**
    * Implicit conversion from [[LocalDate]] to [[astDateType]].
    */
  implicit def localDateToBabelType(typ: Class[LocalDate]): DateType = astDateType

  /**
    * Implicit conversion from an optional [[LocalDate]] to [[optionalAstDateType]]
    */
  implicit def optionalLocalDateToType(typ: Option[Class[LocalDate]]): DateType =
    optionalAstDateType

}

/**
  * @see [[DateTypes]]
  */
object DateTypes extends DateTypes {

  private[types] def typeNameToBabelType(typeName: String): Option[DateType] = {

    val parsedTypeName = TypeNameUtils.parseTypeName(typeName)
    val isRequired = parsedTypeName.isRequired

    parsedTypeName.typeName match {
      case "Date" | "LocalDate" => Some(if (isRequired) astDateType else optionalAstDateType)
      case _ => None
    }
  }
}
