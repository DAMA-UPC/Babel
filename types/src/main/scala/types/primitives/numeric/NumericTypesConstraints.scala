package types.primitives.numeric

import types.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
private[types]
sealed case class NumericTypeConstraint private(override val name: String,
                                                override val value: String
                                               ) extends Constraint(name, value)

/**
  * List of all the valid numeric constraints.
  */
private[types] object NumericTypesConstraints {

  @inline private[this] val minValueNameInAST = "minValue"
  @inline private[this] val maxValueNameInAST = "maxValue"
  @inline private[this] val maxNumberDecimalsNameInAST = "MaxNumberDecimals"

  /**
    * Sets the minimum possible value of a Number.
    */
  def minValue(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(minValueNameInAST, value.toString)

  /**
    * Sets the minimum possible value of a Number.
    */
  def maxValue(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(maxValueNameInAST, value.toString)

  /**
    * Sets the maximum number of decimals of a Number.
    */
  def maxNumberDecimals(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(maxNumberDecimalsNameInAST, value.toString)
}
