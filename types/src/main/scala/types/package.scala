import types.primitives.numeric.{NumericTypes, NumericTypesConstraints}
import types.primitives.timestamp.{TimestampTypesConstraints, TimestampTypes}

/**
  * Package object containing the API for managing types in Babel.
  */
package object types extends types

trait types
// ** NUMERIC TYPES **/
  extends NumericTypes
  with NumericTypesConstraints
// ** TIMESTAMP TYPES **/
  with TimestampTypes
  with TimestampTypesConstraints