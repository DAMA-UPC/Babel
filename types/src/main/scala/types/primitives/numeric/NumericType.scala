package types.primitives.numeric

import types.primitives.PrimitiveType

/**
  * Represents a Numeric type, which represents a primitive type number.
  */
sealed trait NumericType extends PrimitiveType[NumericTypeConstraint]

/**
  * Numeric type companion object.
  */
object NumericType {

  /**
    * Name of the 'Number' types in the AST.
    */
  val typeName: String = "Number"

  /**
    * Apply method needed for generating a [[NumericType]]
    * from the given constraints.
    */
  def apply(constraints: NumericTypeConstraint*): NumericType = {

    new NumericType {
      override val name: String = typeName
      override val constraints: Seq[NumericTypeConstraint] = constraints
    }
  }
}
