import io.circe.Encoder
import types.Type
import types.primitives.date.DateTypes
import types.primitives.numeric.NumericTypes
import types.primitives.text.TextTypes
import types.primitives.timestamp.TimestampTypes
import types.primitives.uuid.UuidTypes

/**
  * Package object containing the API for managing types in Babel.
  */
package object types extends types

trait types extends NumericTypes with TimestampTypes with TextTypes with DateTypes with UuidTypes {

  /**
    * Implicit [[Encoder]] for [[Type]].
    */
  implicit val typeEncoder: Encoder[types.Type] =
    (a: Type) => a.structureJson

  /**
    * Class representing a 'Now' in the whole framework implementation.
    * Can also be used for constraining the time to the current time when
    * working with the [[TimestampTypes]] and the [[DateTypes]].
    */
  @inline case object Now {
    val astName = "NOW()"
  }

}
