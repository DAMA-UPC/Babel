package types.custom

import io.circe.Json

import scala.collection.Map

/**
  * Represents a custom type companion.
  *
  * @tparam T representing the assigned [[CustomType]].
  */
trait CustomTypeCompanion[T <: CustomType] {

  /**
    * When calling this method over a custom type it will generate a
    * the schema definition.
    */
  def structureJson: Json

  /**
    * Apply override used for de-serializing a map of variables
    * into the assigned class.
    */
  def apply(m: Map[String, Any]): T

}