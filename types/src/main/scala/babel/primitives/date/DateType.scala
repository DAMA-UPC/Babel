package babel
package primitives.date

import java.time.LocalDate
import java.util.Date

import babel.primitives.PrimitiveType
import babel.primitives.text.TextType

/**
  * Represents a Babel primitive type representing a text [[String]] in the AST.
  */
case class DateType private[babel] (override val isRequired: Boolean,
                                    override val constraints: Seq[DateTypeConstraint])
    extends PrimitiveType[DateTypeConstraint] {

  /**
    * @inheritdoc
    */
  override val typeName: String = DateType.typeName

  /**
    * Copies the type adding/replacing the minimum date constraint
    * from the [[DateType]].
    */
  def withMinDate(minDate: Date): DateType =
    withConstraint(DateTypeConstraints.minDate(minDate))

  /**
    * Copies the type adding/replacing the minimum date constraint
    * from the [[DateType]].
    */
  def withMinDate(minDate: LocalDate): DateType =
    withConstraint(DateTypeConstraints.minDate(minDate))

  /**
    * Copies the type adding/replacing the minimum date constraint
    * from the [[DateType]] with the current time.
    */
  def withMinDate(now: Now.type): DateType =
    withConstraint(DateTypeConstraints.minDate(now))

  /**
    * Copies the type adding/replacing the maximum date constraint
    * from the [[DateType]].
    */
  def withMaxDate(minDate: Date): DateType =
    withConstraint(DateTypeConstraints.maxDate(minDate))

  /**
    * Copies the type adding/replacing the maximum date constraint
    * from the [[DateType]].
    */
  def withMaxDate(minDate: LocalDate): DateType =
    withConstraint(DateTypeConstraints.maxDate(minDate))

  /**
    * Copies the type adding/replacing the maximum date constraint
    * from the [[DateType]] with the current time.
    */
  def withMaxDate(now: Now.type): DateType =
    withConstraint(DateTypeConstraints.maxDate(now))

  /**
    * Adds a new constraint to [[DateType]]. If the
    * constraint is repeated replaces it with the new one.
    */
  private[this] def withConstraint(constraint: DateTypeConstraint): DateType = {
    @inline val otherConstraints = this.constraints.filterNot(_.name == constraint.name)
    copy(constraints = otherConstraints :+ constraint)
  }
}

/**
  * @see [[DateType]]
  */
object DateType {

  /**
    * Name of the 'Date' types in the AST.
    */
  val typeName: String = "Date"

  /**
    * Apply method needed for generating a [[TextType]] from the
    * given constraints, supposing that the the [[DateType]] is required.
    */
  private[babel] def apply(constraints: DateTypeConstraint*): DateType =
    DateType(isRequired = true, constraints = constraints.toSeq)
}
