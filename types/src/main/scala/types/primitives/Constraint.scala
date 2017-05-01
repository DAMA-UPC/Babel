package types.primitives

import io.circe.Json
import io.circe.syntax._

/**
  * Represents a Primitive type constraint.
  *
  * For instance, if we want a constraint that forces the Integers
  * to be positive, we can set the 'Name' to 'MinValue', and its value to '0'.
  */
abstract class Constraint(val name: String, valueJson: Json) {

  def this(name: String, value: String) = this(name, value.asJson)
  def this(name: String, value: Number) = this(name, value.toString)

  /**
    * Serializes the constraint as a [[Json]] object.
    */
  def asJson: Json =
    Map("name" -> name).asJsonObject.add("value", valueJson).asJson

}
