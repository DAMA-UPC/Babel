package types.primitives

import io.circe.Json
import io.circe.syntax._
import types.Type

/**
  * Represents the primitive types base class. (Primitives are the
  * types which usually can be represented as a simple value in
  * all programming languages, like the numeric literals or the Strings.
  */
trait PrimitiveType[C <: Constraint] extends Type {

  override lazy val structureJson: Json =
    Map("typeName" -> typeName).asJsonObject
      .add("isRequired", isRequired.asJson)
      .add("constraints", constraints.map(_.asJson).asJson)
      .asJson

  /**
    * Primitive types constraints.
    */
  val constraints: Seq[C]

}
