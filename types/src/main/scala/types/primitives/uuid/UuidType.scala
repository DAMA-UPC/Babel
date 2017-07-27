package types
package primitives.uuid

import types.primitives.PrimitiveType
import java.util.UUID

/**
  * Represents a Babel primitive type representing a [[UUID]] in the AST.
  */
case class UuidType private[types] (override val typeName: String,
                                    override val constraints: Seq[UuidTypeConstraint])
  extends PrimitiveType[UuidTypeConstraint] {

  /**
    * Copies the type adding/replacing the minimum date constraint
    * from the [[UuidType]].
    */
  def withTimeBasedUuid(isTimeBased: Boolean): UuidType =
    withConstraint(UuidTypeConstraints.isTimeUuid(isTimeBased))

  /**
    * Adds a new constraint to [[UuidType]]. If the
    * constraint is repeated replaces it with the new one.
    */
  private[this] def withConstraint(constraint: UuidTypeConstraint): UuidType = {
    @inline val otherConstraints = this.constraints.filterNot(_.name == constraint.name)
    UuidType(otherConstraints :+ constraint: _*)
  }
}

/**
  * @see [[UuidType]]
  */
object UuidType {

  /**
    * Name of the 'Uuid' types in the AST.
    */
  val typeName: String = "UUID"

  /**
    * Apply method needed for generating a [[UuidType]]
    * from the given constraints.
    */
  private[types] def apply(constraints: UuidTypeConstraint*): UuidType =
    UuidType(typeName, constraints.toSeq)
}
