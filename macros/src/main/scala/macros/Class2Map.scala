package macros

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta._

// Before:
// @Class2Map
// class Class2MapExample(a: Int, b: String)(c: List[Int]) {
// After:
// class Class2MapExample(a: Int, b: String)(c: List[Int]) {
//   def toMap: _root_.scala.collection.Map[String, Any] =
//     _root_.scala.collection.Map(("a", a), ("b", b), ("c", c))
// }
@compileTimeOnly("@Class2Map not expanded")
class Class2Map extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case cls @ Defn.Class(_, _, _, Ctor.Primary(_, _, paramss), template) =>
        val namesToValues: Seq[Term.Tuple] = paramss.flatten.map { param =>
          q"(${param.name.syntax}, ${Term.Name(param.name.value)})"
        }
        val toMapImpl: Term =
          q"_root_.scala.collection.Map[String, Any](..$namesToValues)"
        val toMap =
          q"def toMap: _root_.scala.collection.Map[String, Any] = $toMapImpl"
        val templateStats: Seq[Stat] = toMap +: template.stats.getOrElse(Nil)
        cls.copy(templ = template.copy(stats = Some(templateStats)))
      case _ =>
        println(defn.structure)
        abort("@Class2Map must annotate a class.")
    }
  }
}
