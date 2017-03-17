package types
package custom

import scala.collection.Map

/**
  * Trait representing a custom type base class.
  */
private[types] trait CustomTypeImpl extends Type {

  /**
    * This method can be used for serializing the
    * case class elements as a serializable map.
    */
  def toMap: Map[String, Any]
}
