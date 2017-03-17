package types.primitives.numeric

import types.primitives.PrimitiveType

/**
  * Represents a Numeric type, which represents a primitive type number.
  */
case class NumericType (override val name : String,
                        override val constraints: Seq[NumericTypeConstraint]
                       ) extends PrimitiveType[NumericType, NumericTypeConstraint] {

  /**
    * Adds a new constraint to the numeric type. If the
    * constraint is repeated replaces it with the new one.
    */
  override def withConstraint(constraint: NumericTypeConstraint): NumericType =
    NumericType(
      this.constraints.filterNot(_.name == constraint.name) :+ constraint: _*
    )
}

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
  def apply(constraints: NumericTypeConstraint*): NumericType =
    NumericType(typeName, constraints)

}
