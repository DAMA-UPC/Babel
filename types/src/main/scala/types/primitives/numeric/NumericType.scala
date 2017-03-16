package types.primitives.numeric

import cats.data.NonEmptyList
import types.primitives.PrimitiveType

/**
  * Represents a Numeric type.
  */
sealed trait NumericType extends PrimitiveType[NumericTypeConstraint]

object NumericType {

  val typeName : String = "Number"

  def apply(constraint : NumericTypeConstraint,
            otherConstraints: NumericTypeConstraint*) : NumericType = {

    new NumericType {
      override val name : String = typeName
      override val constraints: NonEmptyList[NumericTypeConstraint] =
        NonEmptyList.of(constraint, otherConstraints : _*)
    }
  }
}
