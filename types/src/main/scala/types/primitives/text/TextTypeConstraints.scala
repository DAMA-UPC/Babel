package types.primitives.text

import java.nio.charset.Charset

import types.primitives.Constraint

/**
  * Represents a numeric type constraint.
  */
private[types]
sealed case class TextTypeConstraints private(typeName: String,
                                              value: String) extends Constraint(typeName, value)

/**
  * List of all the valid numeric constraints.
  */
private[types] object TextTypeConstraints {

  @inline private[this] val minLengthNameInAST = "MinLength"
  @inline private[this] val maxLengthNameInAST = "MaxLength"
  @inline private[this] val encodingNameInAST = "Encoding"

  /**
    * Sets the minimum possible value of a Number.
    */
  def minLength(value: Long): TextTypeConstraints =
    TextTypeConstraints(minLengthNameInAST, value.toString)

  /**
    * Sets the minimum possible value of a Number.
    */
  def maxLength(value: Long): TextTypeConstraints =
    TextTypeConstraints(maxLengthNameInAST, value.toString)

  /**
    * Sets the maximum number of decimals of a Number.
    */
  def encoding(value: Charset): TextTypeConstraints = {
    TextTypeConstraints(encodingNameInAST, value.toString)
  }
}
