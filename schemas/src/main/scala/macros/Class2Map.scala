package macros

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
  inline def apply(defn: Any): Any = meta(Class2MapImpl.impl(defn)._1)
}

/**
  * Companion object of [[Class2Map]].
  *
  * Contains the methods used by other macros implemented in this class.
  */
private[macros] object Class2Map {

  /**
    * Obtains the method inserted by the [[Class2Map]] macro.
    */
  def macroMethod(defn: Stat): Defn.Def = Class2MapImpl.impl(defn)._2
}

/**
  * Object containing the [[Class2Map]] macro annotation expansion implementation.
  */
private object Class2MapImpl {

  /**
    * Implementation of the [[Class2Map]] macro expansion.
    */
  def impl(defn: Stat): (Defn.Class, Defn.Def) = {
    defn match {
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
        val implClass: Class = cls.copy(templ = template.copy(stats = Some(templateStats)))
        (implClass, method)
      case Defn.Class(_, _, tParams, _, _) if tParams.nonEmpty =>
        abort("@Class2Map is not compatible with classes with type parameters")

      case _ =>
        println(defn.structure)
        abort("@Class2Map must annotate a class.")
    }
  }
}
