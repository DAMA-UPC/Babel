package types.custom

import types.TypeCompanion

import scala.collection.Map

/**
  * Represents a custom type companion.
  *
  * @tparam T representing the assigned [[CustomType]].
  */
trait CustomTypeCompanion[T <: CustomType] extends TypeCompanion {

  /**
    * Apply override used for de-serializing a map of variables
    * into the assigned class.
    */
  def apply(content: Map[String, Any]): T

}
