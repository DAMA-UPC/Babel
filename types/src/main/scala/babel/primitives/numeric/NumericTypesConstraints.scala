package babel.primitives.numeric

import babel.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
private[babel] sealed case class NumericTypeConstraint private (override val name: String,
                                                                value: Number)
    extends Constraint(name, value)

/**
  * List of all the valid numeric constraints.
  */
private[babel] object NumericTypesConstraints {

  @inline private[this] val minValueNameInAST = "MinValue"
  @inline private[this] val maxValueNameInAST = "MaxValue"
  @inline private[this] val maxNumberDecimalsNameInAST = "MaxNumberDecimals"

  /**
    * Sets the minimum possible value of a Number.
    */
  def minValue(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(minValueNameInAST, value)

  /**
    * Sets the minimum possible value of a Number.
    */
  def maxValue(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(maxValueNameInAST, value)

  /**
    * Sets the maximum number of decimals of a Number.
    */
  def maxNumberDecimals(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(maxNumberDecimalsNameInAST, value)
}
