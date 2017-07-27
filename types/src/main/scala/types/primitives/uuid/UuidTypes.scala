package types.primitives.uuid

import java.util.UUID

import types.Type
import types.primitives.date.DateTypes
import types.utils.TypeNameUtils

import scala.language.implicitConversions

/**
  * Trait containing all [[DateTypes]]s to Babel [[Type]]s implicit conversions.
  */
trait UuidTypes {

  /**
    * Represents the default date on the AST.
    */
  private[types] val astUuidType: UuidType =
    UuidType(UuidTypeConstraints.isTimeUuid(false))

  /**
    * Implicit conversion from [[UUID]] to [[astUuidType]].
    */
  implicit def dateToBabelType(typ: Class[UUID]): UuidType = astUuidType

}

/**
  * @see [[UuidTypes]]
  */
object UuidTypes extends UuidTypes {

  private[types] def typeNameToBabelType(typeName: String): Option[UuidType] = {
    TypeNameUtils.typeNameWithoutPackagePredecessors(typeName) match {
      case "UUID" => Some(astUuidType)
      case _ => None
    }
  }
}
