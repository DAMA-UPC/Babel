package types

import io.circe.Json

/**
  * Represents a Babel Type.
  */
trait Type {

  /**
    * This method returns the type name.
    */
  def typeName: String

  /**
    * This method generates a JSON containing the
    * type structure definition.
    */
  def structureJson: Json
}
