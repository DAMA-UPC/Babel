package types.primitives.timestamp

import types.primitives.PrimitiveType

/**
  * Represents a Timestamp type, which represents the way of
  * representing an specific time moment.
  */
case class TimestampType private (override val name: String,
                                  override val constraints: Seq[TimestampTypeConstraint]
                                 ) extends PrimitiveType[TimestampType, TimestampTypeConstraint] {

  /**
    * Adds a new constraint to the timestamp type. If the
    * constraint is repeated replaces it with the new one.
    */
  override def withConstraint(constraint: TimestampTypeConstraint): TimestampType =
    this.constraints match {
      case Nil => TimestampType(constraint)
      case c => TimestampType(c :+ constraint: _*)
    }
}

/**
  * Numeric type companion object.
  */
object TimestampType {

  /**
    * Name of the 'Timestamp' types in the AST.
    */
  val typeName: String = "Timestamp"

  /**
    * Apply method needed for generating a [[TimestampType]]
    * from the given constraints.
    */
  def apply(constraints: TimestampTypeConstraint*): TimestampType =
    TimestampType(typeName, if (constraints.isEmpty) Nil else constraints.toList)

}
