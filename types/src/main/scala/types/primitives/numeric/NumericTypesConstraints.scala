package types.primitives.numeric

import types.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
sealed case class NumericTypeConstraint private (name: String,
                                                 value: Number) extends Constraint[Number]

/**
  * List of all the valid numeric constraints.
  */
trait NumericTypesConstraints {

  def minValue(value : Number) : NumericTypeConstraint =
    NumericTypeConstraint("MinValue", value)

  def maxValue(value: Number) : NumericTypeConstraint =
    NumericTypeConstraint("MaxValue", value)

  def maxNumberDecimals(value: Number) : NumericTypeConstraint =
    NumericTypeConstraint("MaxNumberDecimals", value)
}
