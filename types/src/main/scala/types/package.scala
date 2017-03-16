import types.primitives.numeric.{NumericTypesConstraints, NumericTypes}

/**
  * Package object containing the API for managing types in Babel.
  */
package object types extends types

trait types
// ** NUMERIC TYPES **/
  extends NumericTypes
  with NumericTypesConstraints
