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
    * Represents an standard UUID on the AST.
    */
  private[uuid] val astUuidType: UuidType =
    UuidType(UuidTypeConstraints.isTimeUuid(false))

  /**
    * Implicit conversion from [[UUID]] to [[astUuidType]].
    */
  implicit def uuidToBabelType(typ: Class[UUID]): UuidType = astUuidType

  /**
    * Represents an standard UUID on the AST that is not explicitly required.
    */
  private[uuid] val optionalAstUuidType: UuidType =
    UuidType(UuidTypeConstraints.isTimeUuid(false))

  /**
    * Implicit conversion from an optional [[UUID]] to [[optionalAstUuidType]].
    */
  implicit def optionalUuidToBabelType(typ: Option[Class[UUID]]): UuidType = optionalAstUuidType

}

/**
  * @see [[UuidTypes]]
  */
object UuidTypes extends UuidTypes {

  private[types] def typeNameToBabelType(typeName: String): Option[UuidType] = {

    val parsedTypeName = TypeNameUtils.parseTypeName(typeName)
    val isRequired = parsedTypeName.isRequired

    parsedTypeName.typeName match {
      case "UUID" => Some(if (isRequired) astUuidType else optionalAstUuidType)
      case _ => None
    }
  }
}
