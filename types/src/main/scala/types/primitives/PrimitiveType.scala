package types.primitives

import cats.data.NonEmptyList
import types.Type

/**
  * Represents the primitive types base class. (Primitives are the
  * types which usually can be represented as a simple value in
  * all programming languages, like the numeric literals or the Strings.
  *
  * For instance, if we want to represent a positive number which values
  * are between '0' and '1000':
  *
  * {{{
  *   val newType = PrimitiveType(Constraint("MIN_VALUE", 0))
  * }}}
  */
abstract case class PrimitiveType(constraints : NonEmptyList[Constraint[_]]) extends Type
