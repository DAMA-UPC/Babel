package types.custom

import types.custom.helpers._

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Term.Block
import scala.meta.Type.Name
import scala.meta._

//Before:
//
//@CustomType
//case class Test(a: Int, b: String, c: Float)
//
//After:
//
//case class Test(a: Int, b: String, c: Float) extends CustomTypeImpl {
//
//  override val typeName: String = "Test"
//
//  def toMap: Map[String, Any] =
//    _root_.scala.collection.Map[String, Any](("a", a), ("b", b), ("c", c))
//}
//
//object Test extends CustomTypeCompanion[CustomType] {
//
//  override val typeName: String = "Test"
//
//  def structureJson: _root_.io.circe.Json = {
//    val typeMap = _root_.scala.collection.immutable.Map[String, String](
//      ("a", "Int"),
//      ("b", "String"),
//      ("c", "Float")
//    )
//    import _root_.io.circe._
//    import _root_.io.circe.syntax._
//    val objectTypesJson = JsonObject.fromMap(typeMap.mapValues(_.asJson)).asJson
//    val objectPropertiesJson = JsonObject.fromMap(
//      _root_.scala.collection.immutable.Map(
//        "type" -> "object".asJson, "properties" -> objectTypesJson)
//    ).asJson
//    JsonObject.fromMap(
//      _root_.scala.collection.immutable.Map(
//        "Test" -> objectPropertiesJson)
//    ).asJson
//  }
//
//  def apply(m: Map[String, Any]): Test = {
//    new Test(
//      m("a").asInstanceOf[Int],
//      m("b").asInstanceOf[String],
//      m("c").asInstanceOf[Float]
//    )
//  }
//
//    _root_.scala.collection.immutable.Map[String, String](
//      ("a", "Int"),
//      ("b", "String"),
//      ("c", "Float")
//    )
//}
//

/**
  * Represents a Babel custom type class. This class aggregates the macro [[Class2Map]],
  * [[Class2TypeMap]], [[FromMapApply]] and [[CustomTypeDefinitionJsonGenerator]] generated methods
  * to the input class and its companion object. The class will implement the Trait [[CustomTypeImpl]]
  * and the companion object the trait [[CustomTypeCompanion]].
  */
@compileTimeOnly("@CustomType not expanded")
class CustomType extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn))
}

private object MacroImpl {

  /**
    * Implementation of the [[CustomType]] macro annotation expansion.
    */
  private[types] def impl(defn: Stat): Stat = {
    addCustomTypeInterfaces(
      addCustomTypesMethods(defn)
    )
  }

  /**
    * Methods that needs to be included in the expanded class.
    */
  private[this] val methodsToExpand: Seq[(Stat) => Stat with Scope] = {
    Seq(
      Class2Map.impl,
      Class2TypeMap.impl,
      FromMapApply.impl,
      CustomTypeDefinitionJsonGenerator.impl
    )
  }

  /**
    * Add [[CustomTypeImpl]] and [[CustomTypeCompanion]] as parents of the
    * case class being expanded.
    */
  private[this] def addCustomTypeInterfaces(defn: Stat): Block = {
    defn match {
      case Term.Block(Seq(cls: Defn.Class, companion: Defn.Object)) =>

        val Defn.Class(_, name, _, _, classTemplate) = cls

        // Generates the methods for obtaining the type name either in the type or
        // in the type companion.
        val className : String = "\"".concat(name.toString()).concat("\"")
        val nameMethod = q"override val typeName: String = ${Term.Name(className)}"

        // Adds to the class the 'CustomType' interface as parent.
        val Template(_, classParents, _, _) = classTemplate
        val newCustomTypeParent = ctor"types.custom.CustomTypeImpl"
        val newClassTemplate = classTemplate.copy(parents = classParents :+ newCustomTypeParent)

        // Implements the 'typeName' method in the type class and generates the new class.
        val classTemplateStats = nameMethod +: newClassTemplate.stats.getOrElse(Nil)
        val newClass = cls.copy(
          templ = newClassTemplate.copy(stats = Some(classTemplateStats))
        )

        // Adds to the companion object the interface 'CustomTypeCompanion'
        val Defn.Object(_, _, companionTemplate) = companion
        val Template(_, companionParents, _, _) = companionTemplate
        val classTypeName: Name = Type.Name(cls.name.value)
        val newCompanionParent = ctor"_root_.types.custom.CustomTypeCompanion[$classTypeName]"

        val newCompanionTemplate = companionTemplate.copy(
          parents = companionParents :+ newCompanionParent
        )

        // Implements the 'typeName' method in the companion object and updates it.
        val companionTemplateStats = nameMethod +: newCompanionTemplate.stats.getOrElse(Nil)
        val newCompanion = companion.copy(
          templ = newCompanionTemplate.copy(stats = Some(companionTemplateStats))
        )
        // Returns the class with the added interfaces.
        Term.Block(Seq(newClass, newCompanion))

      case inputDefinition =>
        // Annotating a class or case class with parameters is forbidden
        println(inputDefinition.structure)
        abort("@CustomType must have a companion object.")
    }
  }

  /**
    * Transforms the class adding the required macro methods.
    */
  private[this] def addCustomTypesMethods(classToExpand: Stat): Stat =
    methodsToExpand.foldLeft(classToExpand)((clazz, method) => method(clazz))
}
