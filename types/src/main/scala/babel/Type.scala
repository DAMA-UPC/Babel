package babel

import io.circe.Json

/**
  * Represents a Babel Type.
  */
trait Type {

  /**
    * Returns the type name as written in the generated meta-language.
    */
  def typeName: String

  /**
    * Returns if the type is required or if it is optional.
    */
  def isRequired: Boolean

  /**
    * This method generates a JSON containing the
    * type structure definition.
    */
  def intermediateLanguage: Json
}
