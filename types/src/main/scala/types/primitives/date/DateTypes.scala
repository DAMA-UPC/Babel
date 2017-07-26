package types
package primitives.date

import java.util.Date
import java.time.LocalDate

import types.utils.TypeNameUtils

import scala.language.implicitConversions

/**
  * Trait containing all [[DateTypes]]s to Babel [[Type]]s implicit conversions.
  */
trait DateTypes {

  @inline private[this] val defaultDateOnAST: LocalDate = {
    @inline val defaultMinYearOnAST: Int = 1900
    @inline val defaultMinYearDayOnAst: Int = 1
    LocalDate.ofYearDay(defaultMinYearOnAST, defaultMinYearDayOnAst)
  }

  /**
    * Represents the default date on the AST.
    */
  private[types] val astDateType: DateType =
    DateType(DateTypeConstraints.minDate(defaultDateOnAST), DateTypeConstraints.maxDate(Now))

  /**
    * Implicit conversion from [[Date]] to [[astDateType]].
    */
  implicit def dateToBabelType(typ: Class[Date]): DateType = astDateType

  /**
    * Implicit conversion from [[LocalDate]] to [[astDateType]].
    */
  implicit def localDateToBabelType(typ: Class[LocalDate]): DateType = astDateType

}

/**
  * @see [[DateTypes]]
  */
object DateTypes extends DateTypes {

  private[types] def typeNameToBabelType(typeName: String): Option[DateType] = {
    TypeNameUtils.typeNameWithoutPackagePredecessors(typeName) match {
      case "Date" | "LocalDate" => Some(astDateType)
      case _ => None
    }
  }
}
