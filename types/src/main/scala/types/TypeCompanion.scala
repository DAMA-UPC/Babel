package types

import io.circe.Json

/**
  * Represents a Babel Type companion object.
  */
trait TypeCompanion {

  /**
    * This method generates a JSON containing the definition
    * from the case class.
    */
  def structureJson: Json

}
