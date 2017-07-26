package types.primitives.text

import java.nio.charset.Charset

import types.primitives.PrimitiveType

/**
  * Represents a Babel primitive type representing a text [[String]] in the AST.
  */
case class TextType private[types] (override val typeName: String,
                                    override val constraints: Seq[TextTypeConstraint])
    extends PrimitiveType[TextTypeConstraint] {

  /**
    * Copies the type adding/replacing the minimum length constraint
    * from the [[TextType]].
    */
  def withMinLength(minLength: Long): TextType =
    withConstraint(TextTypeConstraints.minLength(minLength))

  /**
    * Copies the type adding/replacing the maximum length constraint
    * from the [[TextType]].
    */
  def withMaxLength(maxLength: Long): TextType =
    withConstraint(TextTypeConstraints.maxLength(maxLength))

  /**
    * Copies the type adding/replacing the encoding constraint
    * from the [[TextType]].
    */
  def withEncoding(charset: Charset): TextType =
    withConstraint(TextTypeConstraints.encoding(charset))

  /**
    * Adds a new constraint to [[TextType]]. If the
    * constraint is repeated replaces it with the new one.
    */
  private[this] def withConstraint(constraint: TextTypeConstraint): TextType = {
    @inline val otherConstraints = this.constraints.filterNot(_.name == constraint.name)
    TextType(otherConstraints :+ constraint: _*)
  }
}

object TextType {

  /**
    * Name of the 'Text' types in the AST.
    */
  val typeName: String = "Text"

  /**
    * Apply method needed for generating a [[TextType]]
    * from the given constraints.
    */
  private[types] def apply(constraints: TextTypeConstraint*): TextType =
    TextType(typeName, constraints.toSeq)
}
