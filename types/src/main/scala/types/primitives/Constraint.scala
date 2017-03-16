package types.primitives

/**
  * Represents a Primitive type constraint.
  *
  * For instance, if we want a constraint that forces the Integers
  * to be positive, we can set the 'Name' to 'MinValue', and the value to '0'.
  */
trait Constraint[T] {
  val name : String
  val value : T
}
