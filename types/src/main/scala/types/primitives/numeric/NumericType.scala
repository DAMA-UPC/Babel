package types.primitives.numeric

import types.primitives.PrimitiveType

/**
  * Represents a Numeric type, which represents a primitive type number.
  */
sealed trait NumericType extends PrimitiveType[NumericTypeConstraint]

object NumericType {

  val typeName: String = "Number"

  def apply(constraints: NumericTypeConstraint*): NumericType = {

    new NumericType {
      override val name: String = typeName
      override val constraints: Seq[NumericTypeConstraint] = constraints
    }
  }
}
