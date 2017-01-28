package macros

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Term.Param
import scala.meta._

/**
  * Adds a method with name `toTypeMap` that returns the parameters and its
  * types as a [[scala.collection.immutable.Map]].
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
  *   def toTypeMap: _root_.scala.collection.Map[String, Any] =
  *     _root_.scala.collection.Map(("a", Int), ("b", String))
  * }}}
  */
@compileTimeOnly("@Class2TypeMap not expanded")
class Class2TypeMap extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(Class2TypeMap.impl(defn))
}

object Class2TypeMap {

  /**
    * Implementation of the [[Class2TypeMap]] macro annotation.
    */
  def impl(defn: Stat): Stat = {
    defn match {
      case cls@Defn.Class(_, _, Nil, Ctor.Primary(_, _, paramss), template) =>

        val namesToValues: Seq[Term.Tuple] = paramss.flatten.map {
          (param: Param) =>
            val valueType: String = "\"".concat(param.decltpe.map(_.toString()).get).concat("\"")
            q"(${param.name.syntax}, ${Term.Name(valueType)})"
        }
        val methodImp: Term =
          q"_root_.scala.collection.Map[String, String](..$namesToValues)"

        val method = {
          q"def toTypeMap: _root_.scala.collection.Map[String, String] = $methodImp"
        }
        val templateStats: Seq[Stat] = method +: template.stats.getOrElse(Nil)
        cls.copy(templ = template.copy(stats = Some(templateStats)))

      case Defn.Class(_, _, tParams, _, _) if tParams.nonEmpty =>
        abort("@Class2TypeMap is not compatible with classes with type parameters")

      case _ =>
        println(defn.structure)
        abort("@Class2TypeMap must annotate a class.")
    }
  }
}
