package types

import io.circe.Json

/**
  * Represents a Babel Type companion object.
  */
trait TypeCompanion {

  /**
    * This method returns the custom type name.
    */
  def typeName: String

  /**
    * This method generates a JSON containing the definition
    * from the case class.
    */
  def structureJson: Json

}
