package types.utils

/**
  * Contains a set of methods useful when working with type names.
  */
object TypeNameUtils {

  private[this] val optionalTypeNameRegex = """Option\[[(a-zA-Z.|.)]+\]""".r

  /**
    * Contains the parsing result of [[TypeNameUtils.parseTypeName()]]
    */
  case class TypeNameParsingResult(typeName: String, isRequired: Boolean)

  /**
    * Removes if necessary the package predecessors from `java.time.LocalDateTime`
    * to its type name, like `LocalDateTime`. In case the value corresponds to an
    * [[Option]] type, it removes the predecessor from inside.
    */
  def parseTypeName(typeName: String): TypeNameParsingResult =
    parseTypeName(typeName, removePredecessorsInOption = true)

  /**
    * Removes if necessary the package predecessors from `java.time.LocalDateTime`
    * to its type name, like `LocalDateTime`.
    *
    * @param removePredecessorsInOption if true removes the predecessors in case the value is optional
    */
  def parseTypeName(typeName: String, removePredecessorsInOption: Boolean): TypeNameParsingResult = {

    def removePredecessors(name: String) =
      name
        .replaceAll(" ", "")
        .toCharArray
        .reverse
        .takeWhile(_ != '.')
        .reverse
        .foldLeft("")(_ + _)

    optionalTypeNameRegex.findFirstIn(typeName) match {
      case Some(name) if removePredecessorsInOption =>
        TypeNameParsingResult(typeName = removePredecessors(name.drop(7).dropRight(1)),
                              isRequired = false)
      case Some(name) =>
        TypeNameParsingResult(typeName = name.drop(7).dropRight(1), isRequired = false)
      case _ =>
        TypeNameParsingResult(typeName = removePredecessors(typeName), isRequired = true)
    }
  }
}
