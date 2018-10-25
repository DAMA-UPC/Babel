package babel
package primitives.numeric

import babel.primitives.numeric.NumericTypesConstraints.{maxNumberDecimals, maxValue, minValue}
import babel.utils.TypeNameUtils

import scala.language.implicitConversions

/**
  * Trait containing all [[NumericType]] to Babel [[Type]]s implicit conversions.
  */
trait NumericTypes {

  /**
    * Represents a 8 bit signed integer on the Babel AST.
    */
  private[numeric] val astByteType: NumericType =
    NumericType(minValue(Byte.MinValue), maxValue(Byte.MaxValue), maxNumberDecimals(0))

  /**
    * Implicit conversion from [[scala.Byte]] to [[astByteType]].
    */
  implicit def byteToBabelType(typ: Byte.type): NumericType = astByteType

  /**
    * Represents an optional 8 bit signed integer on the Babel AST.
    */
  private[numeric] val optionalAstByteType: NumericType =
    new NumericType(isRequired = false, astByteType.constraints)

  /**
    * Implicit conversion from an optional [[scala.Byte]] to [[optionalAstByteType]].
    */
  implicit def optionalByteToBabelType(typ: Option[Byte.type]): NumericType = optionalAstByteType

  /**
    * Represents a 16 bit signed integer on the Babel AST.
    */
  private[numeric] val astShortType: NumericType =
    NumericType(minValue(Short.MinValue), maxValue(Short.MaxValue), maxNumberDecimals(0))

  /**
    * Implicit conversion from [[scala.Short]] to [[astShortType]].
    */
  implicit def shortToBabelType(typ: Short.type): NumericType = astShortType

  /**
    * Represents an optional 16 bit signed integer on the Babel AST.
    */
  private[numeric] val optionalAstShortType: NumericType =
    new NumericType(isRequired = false, astShortType.constraints)

  /**
    * Implicit conversion from an optional [[Short]] to [[optionalAstShortType]].
    */
  implicit def optionalShortToBabelType(typ: Option[Short.type]): NumericType = optionalAstShortType

  /**
    * Represents a 32 bit signed integer on the Babel AST.
    */
  private[numeric] val astIntType: NumericType =
    NumericType(minValue(Int.MinValue), maxValue(Int.MaxValue), maxNumberDecimals(0))

  /**
    * Implicit conversion from [[scala.Int]] to [[astIntType]].
    */
  implicit def intToBabelType(typ: Int.type): NumericType = astIntType

  /**
    * Represents an optional 32 bit signed integer on the Babel AST.
    */
  private[numeric] val optionalAstIntType: NumericType =
    new NumericType(isRequired = false, astIntType.constraints)

  /**
    * Implicit conversion from an optional [[Int]] to [[optionalAstIntType]].
    */
  implicit def optionalIntToBabelType(typ: Option[Int.type]): NumericType = optionalAstIntType

  /**
    * Represents a 64 bit signed integer on the Babel AST.
    */
  private[numeric] val astLongType: NumericType =
    NumericType(minValue(Long.MinValue), maxValue(Long.MaxValue), maxNumberDecimals(0))

  /**
    * Implicit conversion from [[scala.Int]] to [[astLongType]].
    */
  implicit def longToBabelType(typ: Long.type): NumericType = astLongType

  /**
    * Represents an optional 64 bit signed integer on the Babel AST.
    */
  private[numeric] val optionalAstLongType: NumericType =
    new NumericType(isRequired = false, astLongType.constraints)

  /**
    * Implicit conversion from an optional [[Long]] to [[optionalAstLongType]].
    */
  implicit def optionalLongToBabelType(typ: Option[Long.type]): NumericType = optionalAstLongType

  /**
    * Represents an integer with an arbitrary precision.
    */
  private[numeric] val astBigIntType: NumericType =
    NumericType(maxNumberDecimals(0))

  /**
    * Implicit conversion from [[scala.BigInt]] to [[astBigIntType]].
    */
  implicit def bigIntToBabelType(typ: BigInt.type): NumericType = astBigIntType

  /**
    * Represents an optional signed integer with arbitrary precision on the Babel AST.
    */
  private[numeric] val optionalAstBigIntType: NumericType =
    new NumericType(isRequired = false, astBigIntType.constraints)

  /**
    * Implicit conversion from an optional [[BigInt]] to [[optionalAstBigIntType]].
    */
  implicit def optionalBigIntToBabelType(typ: Option[BigInt.type]): NumericType =
    optionalAstBigIntType

  /**
    * Represents a 32 bit float value on the Babel AST.
    */
  private[numeric] val astFloatType: NumericType = {
    @inline val maxNumberDecimalsInScalaFloat = 8
    NumericType(minValue(Float.MinValue),
                maxValue(Float.MaxValue),
                maxNumberDecimals(maxNumberDecimalsInScalaFloat))
  }

  /**
    * Implicit conversion from [[scala.Float]] to [[astFloatType]].
    */
  implicit def floatToBabelType(typ: Float.type): NumericType = astFloatType

  /**
    * Represents an optional 32 bit float value on the Babel AST.
    */
  private[numeric] val optionalAstFloatType: NumericType =
    new NumericType(isRequired = false, astFloatType.constraints)

  /**
    * Implicit conversion from an optional [[Float]] to [[optionalAstFloatType]].
    */
  implicit def optionalFloatToBabelType(typ: Option[Float.type]): NumericType = optionalAstFloatType

  /**
    * Represents a 64 bit float value on the Babel AST.
    */
  private[numeric] val astDoubleType: NumericType = {
    @inline val maxNumberDecimalsInScalaDouble = 16
    NumericType(minValue(Double.MinValue),
                maxValue(Double.MaxValue),
                maxNumberDecimals(maxNumberDecimalsInScalaDouble))
  }

  /**
    * Implicit conversion from [[scala.Double]] to [[astDoubleType]].
    */
  implicit def doubleToBabelType(typ: Double.type): NumericType = astDoubleType

  /**
    * Represents an optional 64 bit float value on the Babel AST.
    */
  private[numeric] val optionalAstDoubleType: NumericType =
    new NumericType(isRequired = false, astDoubleType.constraints)

  /**
    * Implicit conversion from an optional [[Float]] to [[optionalAstDoubleType]].
    */
  implicit def optionalDoubleToBabelType(typ: Option[Double.type]): NumericType =
    optionalAstDoubleType

  /**
    * Represents an arbitrary precision float value on the Babel AST.
    */
  private[numeric] val astBigDecimalType: NumericType = NumericType()

  /**
    * Implicit conversion from [[scala.BigDecimal]] to [[astBigDecimalType]].
    */
  implicit def bigDecimalToBabelType(typ: BigDecimal.type): NumericType = astBigDecimalType

  /**
    * Represents an optional signed integer with arbitrary precision on the Babel AST.
    */
  private[numeric] val optionalAstBigDecimalType: NumericType =
    new NumericType(isRequired = false, astBigDecimalType.constraints)

  /**
    * Implicit conversion from an optional [[BigInt]] to [[optionalAstBigDecimalType]].
    */
  implicit def optionalBigDecimalToBabelType(typ: Option[BigDecimal.type]): NumericType =
    optionalAstBigDecimalType

}

object NumericTypes extends NumericTypes {

  private[babel] def typeNameToBabelType(typeName: String): Option[NumericType] = {

    val parsedTypeName = TypeNameUtils.parseTypeName(typeName)
    val isRequired = parsedTypeName.isRequired

    parsedTypeName.typeName match {
      case "Byte" => Some(if (isRequired) astByteType else optionalAstByteType)
      case "Short" => Some(if (isRequired) astShortType else optionalAstShortType)
      case "Int" => Some(if (isRequired) astIntType else optionalAstIntType)
      case "Long" => Some(if (isRequired) astLongType else optionalAstLongType)
      case "BigInt" => Some(if (isRequired) astBigIntType else optionalAstBigIntType)
      case "Float" => Some(if (isRequired) astFloatType else optionalAstFloatType)
      case "Double" => Some(if (isRequired) astDoubleType else optionalAstDoubleType)
      case "BigDecimal" => Some(if (isRequired) astBigDecimalType else optionalAstBigDecimalType)
      case _ => None
    }
  }
}
