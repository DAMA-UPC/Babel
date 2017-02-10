package schema

import scala.annotation.{compileTimeOnly, tailrec}
import scala.collection.immutable.Seq
import scala.meta._

//Before:
//
//case class Test(a: Int, b: String, c: Float) {
//  def toMap: _root_.scala.collection.Map[String, Any] =
//    _root_.scala.collection.Map[String, Any](("a", a), ("b", b), ("c", c))
//}
//
//After:
//
//object Test {
//  def definitionJson: _root_.io.circe.Json = {
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
//  def definitionYaml: _root_.io.circe.yaml.syntax.YamlSyntax = {
//    import _root_.io.circe.yaml.syntax.AsYaml
//    definitionJson.asYaml
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
//  def typeMap: _root_.scala.collection.immutable.Map[String, String] =
//    _root_.scala.collection.immutable.Map[String, String](
//      ("a", "Int"),
//      ("b", "String"),
//      ("c", "Float")
//    )
//}
//

/**
  * Represents a Babel Schema class. This class aggregates the macros [[Class2Map]],
  * [[ClassTypeMap]], [[FromMapApply]] and [[SchemaDefinition]] methods to the input
  * class and its companion object.
  */
@compileTimeOnly("@BabelSchema not expanded")
class BabelSchema extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta(BabelSchemaImpl.impl(defn))
}

private object BabelSchemaImpl {

  private[this] val methodsToExpand: Seq[(Stat) => Stat with Scope] = {
    Seq(
      Class2Map.impl,
      ClassTypeMap.impl,
      FromMapApply.impl,
      SchemaDefinition.impl
    )
  }

  private[schema] def impl(defn: Stat): Stat = {
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
