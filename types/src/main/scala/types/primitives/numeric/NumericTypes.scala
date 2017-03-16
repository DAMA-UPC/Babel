package types
package primitives.numeric

/**
  * List of all numeric types.
  */
trait NumericTypes {

  @inline private[this] val maxNumberDecimalsInScalaDouble = 16

  val INTEGER : NumericType = {
    NumericType(
      minValue(Int.MinValue),
      maxValue(Int.MaxValue),
      maxNumberDecimals(0)
    )
  }

  val DOUBLE : NumericType = {
    NumericType(
      minValue(Double.MinValue),
      maxValue(Double.MaxValue),
      maxNumberDecimals(maxNumberDecimalsInScalaDouble)
    )
  }
}
