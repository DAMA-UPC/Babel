package types.primitives

import cats.data.NonEmptyList
import types.Type

/**
  * Represents the primitive types base class. (Primitives are the
  * types which usually can be represented as a simple value in
  * all programming languages, like the numeric literals or the Strings.
  */
trait PrimitiveType[T <: Constraint] extends Type {

  /**
    * Name of the primitive type in the AST.
    */
  val name: String

  /**
    * Primitive types constraints.
    */
  val constraints: NonEmptyList[T]
}
