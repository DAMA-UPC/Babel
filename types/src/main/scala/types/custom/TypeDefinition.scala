package types.custom

import types.custom.helpers.{Class2Map, Class2TypeMap, StructureDefinitionGenerator, FromMapApply}

import scala.annotation.{compileTimeOnly, tailrec}
import scala.collection.immutable.Seq
import scala.meta._

//Before:
//
//@TypeDefinition
//case class Test(a: Int, b: String, c: Float) {
//  def toMap: _root_.scala.collection.Map[String, Any] =
//    _root_.scala.collection.Map[String, Any](("a", a), ("b", b), ("c", c))
//}
//
//After:
//
//object Test {
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
//  def apply(m: _root_.scala.collection.Map[String, Any]): Test = {
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
  * [[Class2TypeMap]], [[FromMapApply]] and [[StructureDefinitionGenerator]] generated methods to the input
  * class and its companion object.
  */
@compileTimeOnly("@CustomTypeDefinition not expanded")
class TypeDefinition extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta(CustomTypeImpl.impl(defn))
}

private object CustomTypeImpl {

  private[this] val methodsToExpand: Seq[(Stat) => Stat with Scope] = {
    Seq(
      Class2Map.impl,
      Class2TypeMap.impl,
      FromMapApply.impl,
      StructureDefinitionGenerator.impl
    )
  }

  private[types] def impl(defn: Stat): Stat = {
    @tailrec
    def loopTransform(objectInExpansion: Stat, toExpand: Seq[(Stat) => Stat with Scope]): Stat = {
      toExpand.headOption match {
        case Some(methodToExpand) =>
          loopTransform(methodToExpand(objectInExpansion), toExpand.drop(1))
        case _ => objectInExpansion
      }
    }

    loopTransform(defn, methodsToExpand)
  }
}
