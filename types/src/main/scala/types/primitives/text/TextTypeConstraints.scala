package types
package primitives.text

import java.nio.charset.Charset

import types.primitives.Constraint

/**
  * Represents a [[TextType]] constraint.
  */
private[types]
sealed case class TextTypeConstraint private(typeName: String,
                                             value: String) extends Constraint(typeName, value)

/**
  * List of all the valid [[TextTypeConstraint]].
  */
private[types] object TextTypeConstraints {

  @inline private[this] val minLengthNameInAST = "MinLength"
  @inline private[this] val maxLengthNameInAST = "MaxLength"
  @inline private[this] val encodingNameInAST = "Encoding"

  /**
    * Sets the minimum length of the text.
    */
  def minLength(value: Long): TextTypeConstraint =
    TextTypeConstraint(minLengthNameInAST, value.toString)

  /**
    * Sets the maximum length of the text.
    */
  def maxLength(value: Long): TextTypeConstraint =
    TextTypeConstraint(maxLengthNameInAST, value.toString)

  /**
    * Sets the encoding of the text (UTF-8, Unicode...)
    */
  def encoding(value: Charset): TextTypeConstraint = {
    TextTypeConstraint(encodingNameInAST, value.toString)
  }
}
