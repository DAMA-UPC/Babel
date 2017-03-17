package types.primitives

import types.Type

/**
  * Represents the primitive types base class. (Primitives are the
  * types which usually can be represented as a simple value in
  * all programming languages, like the numeric literals or the Strings.
  */
trait PrimitiveType[T <: PrimitiveType[T, _], C <: Constraint[_]] extends Type {

  /**
    * Name of the primitive type in the AST.
    */
  val name: String

  /**
    * Primitive types constraints.
    */
  val constraints: Seq[C]

  /**
    * Adds a new constraint to a type. If the constraint
    * is repeated replaces it with the new one.
    */
  def withConstraint(constraint: C): T

}
