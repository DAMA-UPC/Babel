package babel.primitives.uuid

import babel.primitives.Constraint

/**
  * Represents a [[UuidType]] [[Constraint]].
  */
private[babel] sealed case class UuidTypeConstraint private (typeName: String, value: String)
    extends Constraint(typeName, value)

/**
  * List of all the valid [[UuidTypeConstraint]]'s.
  */
private[babel] object UuidTypeConstraints {

  @inline private[this] val isTimeUuidAstName = "isTimeUuid"

  /**
    * Sets the minimum date of the [[UuidType]].
    */
  def isTimeUuid(isTimeUuid: Boolean): UuidTypeConstraint =
    UuidTypeConstraint(isTimeUuidAstName, isTimeUuid.toString)

}
