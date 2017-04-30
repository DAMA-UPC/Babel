package types
package primitives.numeric

import types.primitives.numeric.NumericTypesConstraints.{maxNumberDecimals, maxValue, minValue}

import scala.language.implicitConversions

/**
  * List of all numeric types.
  */
trait NumericTypes {

  /**
    * Represents a 8 bit signed integer on the Babel AST.
    */
  private[types] val astByte: NumericType =
    NumericType(
      minValue(Byte.MinValue),
      maxValue(Byte.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Byte]] to [[astByte]].
    */
  implicit def toBabelType(typ: Byte.type): NumericType = astByte

  /**
    * Represents a 16 bit signed integer on the Babel AST.
    */
  private[types] val astShort: NumericType =
    NumericType(
      minValue(Short.MinValue),
      maxValue(Short.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Short]] to [[astShort]].
    */
  implicit def toBabelType(typ: Short.type): NumericType = astShort

  /**
    * Represents a 32 bit signed integer on the Babel AST.
    */
  private[types] val astInt: NumericType =
    NumericType(
      minValue(Int.MinValue),
      maxValue(Int.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Int]] to [[astInt]].
    */
  implicit def toBabelType(typ: Int.type): NumericType = astInt

  /**
    * Represents a 64 bit signed integer on the Babel AST.
    */
  private[types] val astLong: NumericType =
    NumericType(
      minValue(Long.MinValue),
      maxValue(Long.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Int]] to [[astLong]].
    */
  implicit def toBabelType(typ: Long.type): NumericType = astLong

  /**
    * Represents an integer with an arbitrary precision.
    */
  private[types] val astBigInt: NumericType =
    NumericType(
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.BigInt]] to [[astBigInt]].
    */
  implicit def toBabelType(typ: BigInt.type): NumericType = astBigInt

  /**
    * Represents a 32 bit float value on the Babel AST.
    */
  private[types] val astFloat: NumericType = {
    @inline val maxNumberDecimalsInScalaFloat = 8
    NumericType(
      minValue(Float.MinValue),
      maxValue(Float.MaxValue),
      maxNumberDecimals(maxNumberDecimalsInScalaFloat)
    )
  }

  /**
    * Implicit conversion from [[scala.Float]] to [[Float]].
    */
  implicit def toBabelType(typ: Float.type): NumericType = astFloat

  /**
    * Represents a 64 bit float value on the Babel AST.
    */
  private[types] val astDouble: NumericType = {
    @inline val maxNumberDecimalsInScalaDouble = 16
    NumericType(
      minValue(Double.MinValue),
      maxValue(Double.MaxValue),
      maxNumberDecimals(maxNumberDecimalsInScalaDouble)
    )
  }

  /**
    * Implicit conversion from [[scala.Double]] to [[astDouble]].
    */
  implicit def toBabelType(typ: Double.type): NumericType = astDouble

  /**
    * Represents an arbitrary precision float value on the Babel AST.
    */
  private[types] val astBigDecimal: NumericType = NumericType()

  /**
    * Implicit conversion from [[scala.BigDecimal]] to [[astBigDecimal]].
    */
  implicit def toBabelType(typ: BigDecimal.type): NumericType = astBigDecimal
}

object NumericTypes extends NumericTypes {

  def typeNameToBabelType(typeName: String): Option[NumericType] = {
    typeName match {
      case "Byte" => Some(Byte)
      case "Short" => Some(Short)
      case "Int" => Some(Int)
      case "BigInt" => Some(BigInt)
      case "Float" => Some(Float)
      case "Double" => Some(Double)
      case "BigDecimal" => Some(BigDecimal)
      case _ => None
    }
  }
}
