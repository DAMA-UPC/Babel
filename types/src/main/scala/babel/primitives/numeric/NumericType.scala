package babel
package primitives.numeric

import babel.primitives.PrimitiveType

/**
  * Represents a Numeric type, which represents a primitive type number.
  */
case class NumericType private[babel] (override val isRequired: Boolean,
                                       override val constraints: Seq[NumericTypeConstraint])
    extends PrimitiveType[NumericTypeConstraint] {

  /**
    * @inheritdoc
    */
  override val typeName: String = NumericType.typeName

  /**
    * Copies the type adding/replacing the minimum value constraint
    * from the [[NumericType]].
    */
  def withMinValue(v: Number): NumericType =
    withConstraint(NumericTypesConstraints.minValue(v))

  /**
    * Copies the type adding/replacing the minimum value constraint
    * from the [[NumericType]].
    */
  def withMaxValue(v: Number): NumericType =
    withConstraint(NumericTypesConstraints.maxValue(v))

  /**
    * Copies the type adding/replacing the minimum value constraint
    * from the [[NumericType]].
    */
  def withMaxNumberDecimals(v: Number): NumericType =
    withConstraint(NumericTypesConstraints.maxNumberDecimals(v))

  /**
    * Adds a new constraint to the [[NumericType]]. If the
    * constraint is repeated replaces it with the new one.
    */
  private[this] def withConstraint(constraint: NumericTypeConstraint): NumericType = {
    @inline val otherConstraints = this.constraints.filterNot(_.name == constraint.name)
    copy(constraints = otherConstraints :+ constraint)
  }
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
    * Apply method needed for generating a required [[NumericType]]
    * from the given constraints.
    */
  def apply(constraints: NumericTypeConstraint*): NumericType =
    NumericType(isRequired = true, constraints)

}
