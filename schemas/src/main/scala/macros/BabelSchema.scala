package macros

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta._

/**
  * Represents a Babel Schema class. This class aggregates the macros [[Class2Map]]
  * and [[Class2TypeMap]] methods to the input class.
  *
  * Before:
  * {{{
  * @Class2TypeMap
  * class Example(a: Int, b: String)
  * }}}
  *
  * After:
  * {{{
  * class Example(a: Int, b: String) {
  *   def toTypeMap: _root_.scala.collection.Map[String, Any] = {
  *     _root_.scala.collection.Map(("a", Int), ("b", String))
  *   }
  *   def toMap: _root_.scala.collection.Map[String, Any] = {
  *     _root_.scala.collection.Map(("a", a), ("b", b))
  *   }
  * }}}
  */
@compileTimeOnly("@Class2TypeMap not expanded")
class BabelSchema extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = {
    meta {
      defn match {
        case cls@Defn.Class(_, _, _, _, template) =>
          val methodsToExpand = Seq(Class2Map.macroMethod(defn), Class2TypeMap.macroMethod(defn))
          val templateStats: Seq[Stat] = template.stats.getOrElse(Nil) ++ methodsToExpand
          cls.copy(templ = template.copy(stats = Some(templateStats)))
        case _ =>
          println(defn.structure)
          abort("@BabelSchema must annotate a class.")
      }
    }
  }
}
