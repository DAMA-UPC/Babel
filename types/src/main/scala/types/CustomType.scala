package types

import types.primitives.date.DateTypes
import types.primitives.numeric.NumericTypes
import types.primitives.text.TextTypes
import types.primitives.timestamp.TimestampTypes
import types.primitives.uuid.UuidTypes

import scala.annotation.{compileTimeOnly, tailrec}
import scala.collection.immutable.Seq
import scala.meta.Term.{Block, Param}
import scala.meta.Type.Name
import scala.meta._

//Before:
//
//@CustomType
//case class Test(a: Int, b: String, c: Float)
//
//After:
//
//case class Test(a: Int, b: String, c: Float)
//
//object Test extends _root_.types.Type {
//  override val typeName: String = "Test"
//
//  override def structureJson: _root_.io.circe.Json = {
//    import _root_.types._
//    import _root_.io.circe._
//    import _root_.io.circe.syntax._
//    val objectTypesJson = JsonObject.fromMap(typeMap.mapValues(_.asJson)).asJson
//    val objectPropertiesJson =
//        JsonObject.fromMap(
//          _root_.scala.collection.immutable.ListMap(
//             "type" -> "object".asJson
//            ) + ("properties" -> objectTypesJson)
//        ).asJson
//    JsonObject.fromMap(_root_.scala.collection.immutable.Map("Test" -> objectPropertiesJson)).asJson
//  }
//
//  override def toString: String = structureJson.spaces2
//
//  def typeMap: _root_.scala.collection.immutable.SortedMap[String, types.Type] = {
//     import _root_.types._
//     _root_.scala.collection.immutable.ListMap[String, types.Type](
//          ("a", Int),
//          ("b", classOf[String]),
//          ("c", Float)
//     )
//  }
//}
//

/**
  * This macro annotation is used on Babel custom type classes. It generates
  * a companion class implementing all the required methods from [[Type]]
  * inherating this interface, so this companion class can be used as a
  * Babel [[Type]].
  */
@compileTimeOnly("@CustomType not expanded")
class CustomType extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn))
}

private object MacroImpl {

  /**
    * [[CustomType]] macro implementation.
    */
  @tailrec
  def impl(defn: Stat): Block = {
    defn match {

      case cls@Defn.Class(_, name, _, _, _) if cls.tparams.isEmpty =>
        // The class doesn't have a companion, since this macro need a companion it
        // creates it and calls the method recursively again.
        val companion = q"object ${Term.Name(name.value)} { }"
        impl(Term.Block(Seq(cls, companion)))

      case Term.Block(Seq(cls: Defn.Class, companion: Defn.Object)) if cls.tparams.isEmpty =>
        // Its already a block, start adding the required methods/interfaces.

        val Defn.Class(_, name, _, ctor, _) = cls

        val className = "\"" + name.value +"\""

        // Adds to the companion object the interface 'Type' and its newly generated methods.
        val Defn.Object(_, _, companionTemplate) = companion
        val Template(_, companionParents, _, _) = companionTemplate

        val newCompanionObjectParents = companionTemplate.copy(
          parents = companionParents :+ typeParent
        )
        val newCompanionClassMethods = Seq(
          typeNameMethod(className),
          structureJsonMethod(name, ctor),
          toStringMethodOverride,
          typeMapMethod(ctor)
        )

        val companionClassMethods = newCompanionObjectParents.stats.getOrElse(Nil) ++ newCompanionClassMethods

        val newCompanion = companion.copy(
          templ = newCompanionObjectParents.copy(stats = Some(companionClassMethods))
        )
        // Returns the class with the added interfaces.
        Term.Block(Seq(cls, newCompanion))

      case inputDefinition =>

        // Annotating a class or case class with parameters is forbidden
        println(inputDefinition.structure)
        abort("@CustomType must have a companion object.")
    }
  }

  /**
    * Common parent for all custom types.
    */
  private[this] val typeParent = ctor"_root_.types.Type"

  /**
    * Generates the method
    */
  private[this] def typeNameMethod(className: String): Defn.Val =
    q"override val typeName: String = ${Term.Name(className)}"

  /**
    * Method for serializing the [[Type]] structure as a JSON.
    */
  private[this] def structureJsonMethod(name: Name,
                                        ctor: Ctor.Primary): Defn.Def = {

    val className = Term.Name("\"" + name.value + "\"")

    q"""
        override def structureJson : _root_.io.circe.Json = {

          import _root_.types._

          import _root_.io.circe._
          import _root_.io.circe.syntax._

          val objectTypesJson =
              JsonObject
              .fromMap(
                typeMap.mapValues(_.asJson)
              ).asJson

          val objectPropertiesJson =
              JsonObject.fromMap(
                 _root_.scala.collection.immutable.ListMap(
                     "type" -> "object".asJson,
                     "properties" -> objectTypesJson
                  )
              ).asJson

          JsonObject.fromMap(
            _root_.scala.collection.immutable.Map(
              $className -> objectPropertiesJson
            )
          ).asJson
        }
      """
  }

  /**
    * Contains the toString method override.
    */
  private[this] val toStringMethodOverride: Defn.Def =
    q"override def toString: String = structureJson.spaces2"

  private[this] def typeMapMethod(ctor: Ctor.Primary): Defn.Def = {

    val namesToValues: Seq[Term.Tuple] = ctor.paramss.flatten.map {
      (param: Param) => {

        val paramName: String = param.name.syntax
        val typeName: String = param.decltpe.get.toString()

        val isNumericType = NumericTypes.typeNameToBabelType(typeName).isDefined
        val isTimeType = TimestampTypes.typeNameToBabelType(typeName).isDefined
        val isTextType = TextTypes.typeNameToBabelType(typeName).isDefined
        val isUuidType = UuidTypes.typeNameToBabelType(typeName).isDefined
        val isCharType = if (!isTextType) false else typeName.endsWith("Char")
        val isDateType = DateTypes.typeNameToBabelType(typeName).isDefined

        if (isNumericType || isCharType) {
          q"($paramName, ${Term.Name(typeName)})"
        } else if (isTimeType || isTextType || isUuidType || isDateType) {
          q"($paramName, classOf[${Type.Name(typeName)}])"
        } else {
          abort(s"Type '$typeName' is unsupported")
        }
      }
    }
    q"""
       def typeMap: _root_.scala.collection.immutable.ListMap[String, _root_.types.Type] = {
         import _root_.types._
         _root_.scala.collection.immutable.ListMap[String, types.Type](..$namesToValues)
       }
     """
  }
}
