import io.circe.Encoder
import babel.Type
import babel.primitives.date.DateTypes
import babel.primitives.numeric.NumericTypes
import babel.primitives.text.TextTypes
import babel.primitives.timestamp.TimestampTypes
import babel.primitives.uuid.UuidTypes

/**
  * Package object containing the API for managing types in Babel.
  */
package object babel extends types

trait types extends NumericTypes with TimestampTypes with TextTypes with DateTypes with UuidTypes {

  /**
    * Implicit [[Encoder]] for [[Type]].
    */
  implicit val typeEncoder: Encoder[babel.Type] =
    (a: Type) => a.intermediateLanguage

  /**
    * Class representing a 'Now' in the whole framework implementation.
    * Can also be used for constraining the time to the current time when
    * working with the [[TimestampTypes]] and the [[DateTypes]].
    */
  @inline case object Now {
    val astName = "NOW()"
  }
}
