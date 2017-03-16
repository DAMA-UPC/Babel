package types.primitives

/**
  * Represents a Primitive type constraint.
  *
  * For instance, if we want a constraint that forces the Integers
  * to be positive, we can use:
  *
  * {{{
  *   val constraint = Constraint("MIN_VALUE", 0)
  * }}}
  */
case class Constraint[T] private[primitives](name: String, value: T)
