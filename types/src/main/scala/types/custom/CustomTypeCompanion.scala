package types.custom

import types.Type

import scala.collection.Map

/**
  * Represents a custom type companion.
  *
  * @tparam T representing the assigned [[CustomTypeImpl]].
  */
private[types] trait CustomTypeCompanion[T <: CustomTypeImpl] extends Type {

  /**
    * Apply override used for de-serializing a map of variables
    * into the assigned class.
    */
  def apply(content: Map[String, Any]): T

}
