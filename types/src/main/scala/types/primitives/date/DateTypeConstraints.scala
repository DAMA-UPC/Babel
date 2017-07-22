package types
package primitives.date

import java.time.LocalDate
import java.util.Date

import types.primitives.Constraint

/**
  * Represents a [[DateType]] constraint.
  */
private[types]
sealed case class DateTypeConstraint private(typeName: String,
                                             value: String) extends Constraint(typeName, value)

/**
  * List of all the valid [[DateTypeConstraint]].
  */
private[types] object DateTypeConstraints {

  @inline private[this] val minDateInAST = "MinDate"
  @inline private[this] val maxDateInAST = "MaxDate"

  /**
    * Sets the minimum date of the [[DateType]].
    */
  def minDate(value: LocalDate): DateTypeConstraint =
    DateTypeConstraint(minDateInAST, value.toString)

  /**
    * Sets the minimum date of the [[DateType]].
    */
  def minDate(value: Date): DateTypeConstraint =
    DateTypeConstraint(minDateInAST, value.toString)

  /**
    * Sets the minimum date of the [[DateType]] to the current
    * day on the application execution.
    */
  def minDate(now: Now.type): DateTypeConstraint =
    DateTypeConstraint(minDateInAST, now.toString)

  /**
    * Sets the maximum date of the [[DateType]].
    */
  def maxDate(value: LocalDate): DateTypeConstraint =
    DateTypeConstraint(maxDateInAST, value.toString)

  /**
    * Sets the maximum date of the [[DateType]].
    */
  def maxDate(value: Date): DateTypeConstraint =
    DateTypeConstraint(maxDateInAST, value.toString)

  /**
    * Sets the maximum date of the [[DateType]] to the current
    * day on the application execution.
    */
  def maxDate(now: Now.type): DateTypeConstraint =
    DateTypeConstraint(maxDateInAST, now.toString)

}
