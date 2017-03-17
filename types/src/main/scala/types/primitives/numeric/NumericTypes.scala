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
  private[types] val BYTE: NumericType =
    NumericType(
      minValue(Byte.MinValue),
      maxValue(Byte.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Byte]] to [[BYTE]].
    */
  implicit def byteToBabelByte(typ: Byte.type): NumericType = BYTE

  /**
    * Represents a 16 bit signed integer on the Babel AST.
    */
  private[types] val SHORT: NumericType =
    NumericType(
      minValue(Short.MinValue),
      maxValue(Short.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Short]] to [[SHORT]].
    */
  implicit def shortToBabelShort(typ: Short.type): NumericType = SHORT

  /**
    * Represents a 32 bit signed integer on the Babel AST.
    */
  private[types] val INTEGER: NumericType =
    NumericType(
      minValue(Int.MinValue),
      maxValue(Int.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Int]] to [[INTEGER]].
    */
  implicit def intToBabelInteger(typ: Int.type): NumericType = INTEGER

  /**
    * Represents a 64 bit signed integer on the Babel AST.
    */
  private[types] val LONG: NumericType =
    NumericType(
      minValue(Long.MinValue),
      maxValue(Long.MaxValue),
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.Int]] to [[LONG]].
    */
  implicit def longToBabelLong(typ: Long.type): NumericType = LONG

  /**
    * Represents an integer with an arbitrary precision.
    */
  private[types] val BIG_INT: NumericType =
    NumericType(
      maxNumberDecimals(0)
    )

  /**
    * Implicit conversion from [[scala.BigInt]] to [[BIG_INT]].
    */
  implicit def bigIntToBabelBigInt(typ: BigInt.type): NumericType = BIG_INT

  /**
    * Represents a 32 bit float value on the Babel AST.
    */
  private[types] val FLOAT: NumericType = {
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
  implicit def floatToBabelFloat(typ: Float.type): NumericType = FLOAT

  /**
    * Represents a 64 bit float value on the Babel AST.
    */
  private[types] val DOUBLE: NumericType = {
    @inline val maxNumberDecimalsInScalaDouble = 16
    NumericType(
      minValue(Double.MinValue),
      maxValue(Double.MaxValue),
      maxNumberDecimals(maxNumberDecimalsInScalaDouble)
    )
  }

  /**
    * Implicit conversion from [[scala.Double]] to [[DOUBLE]].
    */
  implicit def doubleToBabelDouble(typ: Double.type): NumericType = DOUBLE

  /**
    * Represents an arbitrary precision float value on the Babel AST.
    */
  private[types] val BIG_DECIMAL: NumericType = NumericType()

  /**
    * Implicit conversion from [[scala.BigDecimal]] to [[BIG_DECIMAL]].
    */
  implicit def bigDecimalToBabelBigDecimal(typ: BigDecimal.type): NumericType = BIG_DECIMAL

}
