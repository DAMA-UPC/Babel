package types.primitives

import io.circe.Json
import io.circe.syntax._

/**
  * Represents a Primitive type constraint.
  *
  * For instance, if we want a constraint that forces the Integers
  * to be positive, we can set the 'Name' to 'MinValue', and its value to '0'.
  */
abstract class Constraint(val name: String, val value: String) {

  /**
    * Serializes the constraint as a [[Json]] object.
    */
  def asJson: Json = Map("name" -> name, "value" -> value).asJson

}
