package types.custom.helpers

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Defn.Class
import scala.meta.Term.Param
import scala.meta._

/**
  * Adds a method with name `toMap` that returns the parameters and its
  * values as a [[scala.collection.immutable.Map]].
  *
  * Before:
  * {{{
  * @Class2Map
  * class Example(a: Int, b: String)
  * }}}
  *
  * After:
  * {{{
  * class Example(a: Int, b: String) {
  *   def toMap: _root_.scala.collection.Map[String, Any] =
  *     _root_.scala.collection.Map(("a", a), ("b", b))
  * }}}
  */
@compileTimeOnly("@Class2Map not expanded")
class Class2Map extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(Class2Map.impl(defn))
}

/**
  * Object containing the [[Class2Map]] macro annotation expansion implementation.
  */
object Class2Map {

  /**
    * Implementation of the [[Class2Map]] macro expansion.
    */
  val impl: (Stat) => Class = {
      case cls@Defn.Class(_, _, Nil, Ctor.Primary(_, _, paramss), template) =>
        val namesToValues: Seq[Term.Tuple] = paramss.flatten.map {
          (param: Param) =>
            q"(${param.name.syntax}, ${Term.Name(param.name.value)})"
        }
        val toMapImpl: Term =
          q"_root_.scala.collection.Map[String, Any](..$namesToValues)"
        val method =
          q"def toMap: _root_.scala.collection.Map[String, Any] = $toMapImpl"
        val templateStats: Seq[Stat] = method +: template.stats.getOrElse(Nil)
        cls.copy(templ = template.copy(stats = Some(templateStats)))
      case Defn.Class(_, _, tParams, _, _) if tParams.nonEmpty =>
        abort("@Class2Map is not compatible with classes with type parameters")
      case defn =>
        println(defn.structure)
        abort("@Class2Map must annotate a class.")
    }
}
