package types.primitives

import types.Type

/**
  * Represents the primitive types base class. (Primitives are the
  * types which usually can be represented as a simple value in
  * all programming languages, like the numeric literals or the Strings.
  */
trait PrimitiveType[C <: Constraint] extends Type {

  /**
    * Primitive types constraints.
    */
  val constraints: Seq[C]

}
