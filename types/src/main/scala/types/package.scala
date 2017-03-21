import types.primitives.numeric.NumericTypes
import types.primitives.timestamp.TimestampTypes

/**
  * Package object containing the API for managing types in Babel.
  */
package object types extends types

trait types
  extends NumericTypes
    with TimestampTypes {

  /**
    * Class representing a 'Now' in the whole framework implementation.
    * Can also be used for constraining the time to the current time when
    * working with timestamp types.
    */
  @inline case object Now {
    val astName = "NOW()"
  }

}
