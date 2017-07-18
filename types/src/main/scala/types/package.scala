import io.circe.Encoder
import types.Type
import io.circe.syntax._
import types.primitives.numeric.NumericTypes
import types.primitives.text.TextTypes
import types.primitives.timestamp.TimestampTypes

/**
  * Package object containing the API for managing types in Babel.
  */
package object types extends types

trait types
  extends NumericTypes
    with TimestampTypes
    with TextTypes {

  /**
    * Implicit [[Encoder]] for [[Type]].
    */
  implicit final val typeEncoder: Encoder[types.Type] =
    (a: Type) => a.structureJson

  /**
    * Class representing a 'Now' in the whole framework implementation.
    * Can also be used for constraining the time to the current time when
    * working with timestamp types.
    */
  @inline case object Now {
    val astName = "NOW()"
  }
}
