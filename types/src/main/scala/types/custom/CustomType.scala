package types.custom

import scala.collection.Map

/**
  * Trait representing a custom type base class.
  */
trait CustomType {

  /**
    * This method can be used for serializing the
    * case class elements as a serializable map.
    */
  def toMap: Map[String, Any]
}
