package types.custom

import types.custom.helpers._

import scala.annotation.{compileTimeOnly, tailrec}
import scala.collection.immutable.Seq
import scala.meta.Term.Block
import scala.meta.Type.Name
import scala.meta._

//Before:
//
//@TypeDefinition
//case class Test(a: Int, b: String, c: Float)
//
//After:
//
//case class Test(a: Int, b: String, c: Float) extends CustomType {
//  def toMap: Map[String, Any] =
//    _root_.scala.collection.Map[String, Any](("a", a), ("b", b), ("c", c))
//}
//
//object Test extends CustomTypeCompanion[CustomType] {
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
  * [[Class2TypeMap]], [[FromMapApply]] and [[StructureDefinitionGenerator]] generated methods
  * to the input class and its companion object. The class will implement the Trait [[CustomType]]
  * and the companion object the trait [[CustomTypeCompanion]].
  */
@compileTimeOnly("@TypeDefinition not expanded")
class TypeDefinition extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta(TypeDefinitionImpl.impl(defn))
}

private object TypeDefinitionImpl {

  /**
    * Implementation of the [[TypeDefinition]] macro annotation expansion.
    */
  private[types] def impl(defn: Stat): Stat = {
    addCustomTypeInterfaces(
      addMethodsLoop(defn, methodsToExpand)
    )
  }

  /**
    * Expands the class
    */
  @tailrec
  private[this] def addMethodsLoop(classToExpand: Stat,
                                   toExpand: Seq[(Stat) => Stat with Scope]): Stat = {
    toExpand.headOption match {
      case Some(methodToExpand) =>
        addMethodsLoop(methodToExpand(classToExpand), toExpand.drop(1))
      case _ => classToExpand
    }
  }

  /**
    * Macros implementing
    */
  private[this] val methodsToExpand: Seq[(Stat) => Stat with Scope] = {
    Seq(
      Class2Map.impl,
      Class2TypeMap.impl,
      FromMapApply.impl,
      StructureDefinitionGenerator.impl
    )
  }

  /**
    * Add [[CustomType]] and [[CustomTypeCompanion]] as parents of the
    * case class being expanded.
    */
  private[this] val addCustomTypeInterfaces: (Stat) => Block = {

    case Term.Block(Seq(cls: Defn.Class, companion: Defn.Object)) =>

      // Adds to the class the 'CustomType' interface as parent.
      val Defn.Class(_, _, _, _, classTemplate) = cls
      val Template(_, classParents, _, _) = classTemplate
      val newCustomTypeParents = ctor"_root_.types.custom.CustomType"
      val newClassTemplate = classTemplate.copy(parents = classParents :+ newCustomTypeParents)
      val newClass = cls.copy(templ = newClassTemplate)

      // Adds to the companion object the interface 'CustomTypeCompanion'
      val Defn.Object(_, _, companionTemplate) = companion
      val Template(_, companionParents, _, _) = companionTemplate
      val classTypeName: Name = Type.Name(cls.name.value)
      val companionConstructor = ctor"_root_.types.custom.CustomTypeCompanion[$classTypeName]"
      val newCompanionParents = companionParents :+ companionConstructor
      val newCompanionTemplate = companionTemplate.copy(parents = newCompanionParents)
      val newCompanion = companion.copy(templ = newCompanionTemplate)

      // Returns the class with the added interfaces.
      Term.Block(Seq(newClass, newCompanion))

    case inputDefinition =>
      // Annotating a class or case class with parameters is forbidden
      println(inputDefinition.structure)
      abort("@TypeDefinition must have a companion object.")
  }
}
