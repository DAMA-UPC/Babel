package babel.primitives.text

import java.nio.charset.Charset

import babel.Type
import babel.utils.TypeNameUtils

import scala.language.implicitConversions

/**
  * Trait containing all [[TextType]]s to Babel [[Type]]s implicit conversions.
  */
trait TextTypes {

  /**
    * Represents a UTF-8 [[String]] in the AST.
    */
  private[text] val astStringTextType: TextType =
    TextType(TextTypeConstraints.minLength(0),
             TextTypeConstraints.encoding(Charset.forName("UTF-8")))

  /**
    * Implicit conversion from [[String]] to [[astStringTextType]].
    */
  implicit def stringToBabelType(typ: Class[String]): TextType = astStringTextType

  /**
    * Represents an optional UTF-8 [[String]] in the AST.
    */
  private[text] val optionalAstStringTextType: TextType =
    new TextType(isRequired = false, astStringTextType.constraints)

  /**
    * Implicit conversion from [[Option[String]]] to [[optionalAstStringTextType]].
    */
  implicit def optionalStringToBabelType(typ: Option[Class[String]]): TextType =
    optionalAstStringTextType

  /**
    * Represents a UTF-8 [[Char]] in the AST.
    */
  private[text] val astCharacterTextType: TextType =
    astStringTextType.withMaxLength(1).withMinLength(1)

  /**
    * Implicit conversion from [[Char]] to [[astCharacterTextType]].
    */
  implicit def charToBabelType(typ: Char.type): TextType = astCharacterTextType

  /**
    * Represents an optional UTF-8 [[Char]] in the AST.
    */
  private[text] val optionalAstCharTextType: TextType =
    new TextType(isRequired = false, astCharacterTextType.constraints)

  /**
    * Implicit conversion from [[Option[Char]]] to [[optionalAstCharTextType]].
    */
  implicit def optionalCharToBabelType(typ: Option[Char.type]): TextType = optionalAstCharTextType

}

/**
  * @see [[TextTypes]]
  */
object TextTypes extends TextTypes {

  private[babel] def typeNameToBabelType(typeName: String): Option[TextType] = {

    val parsedTypeName = TypeNameUtils.parseTypeName(typeName)
    val isRequired = parsedTypeName.isRequired

    parsedTypeName.typeName match {
      case "Char" => Some(if (isRequired) astCharacterTextType else optionalAstStringTextType)
      case "String" => Some(if (isRequired) astStringTextType else optionalAstCharTextType)
      case _ => None
    }
  }
}
