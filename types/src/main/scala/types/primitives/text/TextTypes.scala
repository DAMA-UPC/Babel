package types.primitives.text

import java.nio.charset.Charset

import types.Type

import scala.language.implicitConversions

/**
  * @see [[TextTypes]]
  */
object TextTypes extends TextTypes

/**
  * Trait containing all [[TextType]]s to Babel [[Type]]s implicit conversions.
  */
trait TextTypes {

  /**
    * Represents a UTF-8 [[String]] in the AST.
    */
  private[types] val astStringTextType: TextType =
    TextType(
      TextTypeConstraints.minLength(0),
      TextTypeConstraints.encoding(Charset.forName("UTF-8"))
    )

  /**
    * Implicit conversion from [[String]] to [[astStringTextType]].
    */
  implicit def stringToBabelType(typ: Class[String]): TextType = astStringTextType

  /**
    * Represents a UTF-8 [[Char]] in the AST.
    */
  private[types] val astCharacterTextType: TextType =
    astStringTextType.withMaxLength(1).withMinLength(1)

  /**
    * Implicit conversion from [[Char]] to [[astCharacterTextType]].
    */
  implicit def charToBabelType(typ: Char.type): TextType = astCharacterTextType

}
