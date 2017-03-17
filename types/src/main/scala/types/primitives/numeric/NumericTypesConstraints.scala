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

  /**
    * Sets the minimum possible value of a Number.
    */
  def minValue(value : Number) : NumericTypeConstraint =
    NumericTypeConstraint("MinValue", value)

  /**
    * Sets the minimum possible value of a Number.
    */
  def maxValue(value: Number) : NumericTypeConstraint =
    NumericTypeConstraint("MaxValue", value)

  /**
    * Sets the maximum number of decimals of a Number.
    */
  def maxNumberDecimals(value: Number) : NumericTypeConstraint =
    NumericTypeConstraint("MaxNumberDecimals", value)
}
