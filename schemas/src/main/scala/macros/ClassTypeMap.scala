package macros

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Term.{Block, Param}
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
  * class Example(a: Int, b: String)
  *
  * object Example {
  *   def typeMap: _root_.scala.collection.Map[String, Any] =
  *     _root_.scala.collection.Map(("a", Int), ("b", String))
  * }}}
  */
@compileTimeOnly("@Class2TypeMap not expanded")
class ClassTypeMap extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(ClassTypeMap.impl(defn))
}

/**
  * Object containing the [[ClassTypeMap]] macro annotation expansion implementation.
  */
object ClassTypeMap {

  /**
    * Implementation of the [[ClassTypeMap]] macro annotation expansion.
    */
  private[macros] val impl: (Stat) => Block = {
    case Term.Block(Seq(cls@Defn.Class(_, name, Nil, ctor, _), companion: Defn.Object)) =>
      // Annotating a class or case class without parameters which already haves
      // a companion object.
      val class2TypeMapMethod = createClass2MapMethod(ctor)
      val templateStats: Seq[Stat] =
        class2TypeMapMethod +: companion.templ.stats.getOrElse(Nil)
      val newCompanion = companion.copy(
        templ = companion.templ.copy(stats = Some(templateStats)))
      Term.Block(Seq(cls, newCompanion))

    case Term.Block(_) =>
      // Annotating a class or case class with parameters is forbidden
      abort("@ClassTypeMap is not compatible with classes with type parameters")

    case cls@Defn.Class(_, name, Nil, ctor, template) =>
      // Annotating a class or a case class without parameters.
      val class2TypeMapMethod = createClass2MapMethod(ctor)
      val companion = q"object ${Term.Name(name.value)} { $class2TypeMapMethod }"
      Block(Seq(cls, companion))

    case Defn.Class(_, _, tParams, _, _) if tParams.nonEmpty =>
      // Class with parameters
      abort("@ClassTypeMap is not compatible with classes with type parameters")
    case defn =>
      // Annotating a class or case class with parameters is forbidden
      println(defn.structure)
      abort("@ClassTypeMap must annotate a class.")
  }


  private[this] def createClass2MapMethod(ctor: Ctor.Primary): Defn.Def = {

    val namesToValues: Seq[Term.Tuple] = ctor.paramss.flatten.map {
      (param: Param) =>
        val valueType: String = "\"".concat(param.decltpe.map(_.toString()).get).concat("\"")
        q"(${param.name.syntax}, ${Term.Name(valueType)})"
    }
    val methodImp: Term = q"_root_.scala.collection.Map[String, String](..$namesToValues)"

    q"def typeMap: _root_.scala.collection.Map[String, String] = $methodImp"
  }
}
