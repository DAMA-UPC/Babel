package babel.utils

object JsonUglyfier {

  /**
    * Converts a space2 / space4 / tab-separated JSON into a
    * no space one.
    *
    * WARNING: The original JSON cannot contain any space within its contain.
    */
  def uglyfy(space4Json: String): String =
    space4Json.replaceAll("\t", "").replaceAll("\n", "").replaceAll(" ", "")

}
