package types
package custom.helpers

import types.primitives.numeric.NumericTypes
import types.primitives.text.TextTypes
import types.primitives.timestamp.TimestampTypes

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
  *   def typeMap: _root_.scala.collection.Map[String, Type] =
  *     _root_.scala.collection.Map(("a", Int.type), ("b", String.type))
  * }}}
  */
@compileTimeOnly("@Class2TypeMap not expanded")
class Class2TypeMap extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(Class2TypeMap.impl(defn))
}

/**
  * Object containing the [[Class2TypeMap]] macro annotation expansion implementation.
  */
object Class2TypeMap {

  /**
    * Implementation of the [[Class2TypeMap]] macro annotation expansion.
    */
  val impl: (Stat) => Block = {
    case Term.Block(Seq(cls@Defn.Class(_, _, Nil, ctor, methods), companion: Defn.Object)) =>
      // Annotating a class or case class without parameters which already haves
      // a companion object.
      val class2TypeMapMethod = createClass2MapMethod(ctor)

      // Adds the method to the class.
      val classMethods: Seq[Stat] = class2TypeMapMethod +: methods.stats.getOrElse(Nil)
      val newClass =
        cls.copy(templ = methods.copy(stats = Some(classMethods)))

      // Adds the method to the companion object.
      val companionObjectMethods: Seq[Stat] =
        class2TypeMapMethod +: companion.templ.stats.getOrElse(Nil)
      val newCompanion = companion.copy(
        templ = companion.templ.copy(stats = Some(companionObjectMethods)))

      // Returns the new class and the companion object with the new method.
      Term.Block(Seq(newClass, newCompanion))

    case Term.Block(_) =>
      // Annotating a class or case class with parameters is forbidden
      abort("@ClassTypeMap is not compatible with classes with type parameters")

    case cls@Defn.Class(_, name, Nil, ctor, methods) =>

      // Annotating a class or a case class without companion object.
      val class2TypeMapMethod = createClass2MapMethod(ctor)

      // Adds the method to the class.
      val classMethods: Seq[Stat] = class2TypeMapMethod +: methods.stats.getOrElse(Nil)
      val newClass =
        cls.copy(templ = methods.copy(stats = Some(classMethods)))

      // Adds the method to the companion object.
      val companion = q"object ${Term.Name(name.value)} { $class2TypeMapMethod }"
      Block(Seq(newClass, companion))

    case Defn.Class(_, _, tParams, _, _) if tParams.nonEmpty =>
      // Class with parameters
      abort("@ClassTypeMap is not compatible with classes with type parameters")
    case defn =>
      // Annotating a class or case class with parameters is forbidden
      println(defn.structure)
      abort("@ClassTypeMap must annotate a class.")
  }


  private[this] def createClass2MapMethod(ctor: Ctor.Primary): Defn.Def =
    q"""
       def typeMap: _root_.scala.collection.immutable.Map[String, types.Type] = {
          ${methodBody(ctor)}
       }
     """

  // TODO: Add Date type as soon as it is implemented.
  // TODO: Add UUID type as soon as it is implemented.
  private[custom] def methodBody(ctor: Ctor.Primary): Term.Apply = {

    val namesToValues: Seq[Term.Tuple] = ctor.paramss.flatten.map {
      (param: Param) => {

        val paramName: String = param.name.syntax
        val typeName: String = param.decltpe.get.toString()

        val isNumericType = NumericTypes.typeNameToBabelType(typeName).isDefined
        val isTimeType = TimestampTypes.typeNameToBabelType(typeName).isDefined
        val isTextType = TextTypes.typeNameToBabelType(typeName).isDefined
        val isCharType = if (!isTextType) false else typeName.endsWith("Char")

        if (isNumericType || isCharType) {
          q"($paramName, ${Term.Name(typeName)})"
        } else if (isTimeType || isTextType) {
          q"($paramName, classOf[${Type.Name(typeName)}])"
        } else {
          abort(s"Type '$typeName' is unsupported")
        }
      }
    }

    q"scala.collection.immutable.SortedMap[String, types.Type](..$namesToValues)"
  }
}
