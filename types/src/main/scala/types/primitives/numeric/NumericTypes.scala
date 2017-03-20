package types
package primitives.numeric

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
      constraintMinValue(Byte.MinValue),
      constraintMaxValue(Byte.MaxValue),
      constraintMaxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Byte]] to [[astByte]].
    */
  implicit def byteToBabelByte(typ: Byte.type): NumericType = astByte

  /**
    * Represents a 16 bit signed integer on the Babel AST.
    */
  private[types] val astShort: NumericType =
    NumericType(
      constraintMinValue(Short.MinValue),
      constraintMaxValue(Short.MaxValue),
      constraintMaxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Short]] to [[astShort]].
    */
  implicit def shortToBabelShort(typ: Short.type): NumericType = astShort

  /**
    * Represents a 32 bit signed integer on the Babel AST.
    */
  private[types] val astInt: NumericType =
    NumericType(
      constraintMinValue(Int.MinValue),
      constraintMaxValue(Int.MaxValue),
      constraintMaxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Int]] to [[astInt]].
    */
  implicit def intToBabelInteger(typ: Int.type): NumericType = astInt

  /**
    * Represents a 64 bit signed integer on the Babel AST.
    */
  private[types] val astLong: NumericType =
    NumericType(
      constraintMinValue(Long.MinValue),
      constraintMaxValue(Long.MaxValue),
      constraintMaxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Int]] to [[astLong]].
    */
  implicit def longToBabelLong(typ: Long.type): NumericType = astLong

  /**
    * Represents an integer with an arbitrary precision.
    */
  private[types] val astBigInt: NumericType =
    NumericType(
      constraintMaxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.BigInt]] to [[astBigInt]].
    */
  implicit def bigIntToBabelBigInt(typ: BigInt.type): NumericType = astBigInt

  /**
    * Represents a 32 bit float value on the Babel AST.
    */
  private[types] val astFloat: NumericType = {
    @inline val maxNumberDecimalsInScalaFloat = 8
    NumericType(
      constraintMinValue(Float.MinValue),
      constraintMaxValue(Float.MaxValue),
      constraintMaxNumberDecimals(maxNumberDecimalsInScalaFloat)
    )
  }

  /**
    * Implicit conversion from [[scala.Float]] to [[Float]].
    */
  implicit def floatToBabelFloat(typ: Float.type): NumericType = astFloat

  /**
    * Represents a 64 bit float value on the Babel AST.
    */
  private[types] val astDouble: NumericType = {
    @inline val maxNumberDecimalsInScalaDouble = 16
    NumericType(
      constraintMinValue(Double.MinValue),
      constraintMaxValue(Double.MaxValue),
      constraintMaxNumberDecimals(maxNumberDecimalsInScalaDouble)
    )
  }

  /**
    * Implicit conversion from [[scala.Double]] to [[astDouble]].
    */
  implicit def doubleToBabelDouble(typ: Double.type): NumericType = astDouble

  /**
    * Represents an arbitrary precision float value on the Babel AST.
    */
  private[types] val astBigDecimal: NumericType = NumericType()

  /**
    * Implicit conversion from [[scala.BigDecimal]] to [[astBigDecimal]].
    */
  implicit def bigDecimalToBabelBigDecimal(typ: BigDecimal.type): NumericType = astBigDecimal

}
