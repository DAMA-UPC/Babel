package types.primitives.numeric

import types.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
sealed case class NumericTypeConstraint private(name: String,
                                                value: Number) extends Constraint[Number]

/**
  * List of all the valid numeric constraints.
  */
trait NumericTypesConstraints {

  @inline private[this] val minValueNameInAST = "minValue"
  @inline private[this] val maxValueNameInAST = "maxValue"
  @inline private[this] val maxNumberDecimalsNameInAST = "MaxNumberDecimals"

  /**
    * Sets the minimum possible value of a Number.
    */
  def constraintMinValue(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(minValueNameInAST, value)

  /**
    * Sets the minimum possible value of a Number.
    */
  def constraintMaxValue(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(maxValueNameInAST, value)

  /**
    * Sets the maximum number of decimals of a Number.
    */
  def constraintMaxNumberDecimals(value: Number): NumericTypeConstraint =
    NumericTypeConstraint(maxNumberDecimalsNameInAST, value)
}
