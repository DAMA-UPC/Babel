package types.utils

/**
  * Contains a set of methods useful when working with type names.
  */
object TypeNameUtils {

  /**
    * Removes if necessary the package predecessors from `java.time.LocalDateTime`
    * to its type name, like `LocalDateTime`.
    */
  def typeNameWithoutPackagePredecessors(typeName: String): String =
    typeName.toCharArray.reverse.takeWhile(_ != '.').reverse.foldLeft("")(_ + _)

}
