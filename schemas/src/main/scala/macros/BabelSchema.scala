package macros

import scala.annotation.{compileTimeOnly, tailrec}
import scala.collection.immutable.Seq
import scala.meta._

/**
  * Represents a Babel Schema class. This class aggregates the macros [[Class2Map]]
  * and [[ClassTypeMap]] methods to the input class.
  *
  * Before:
  * {{{
  * @BabelSchema
  * case class Test(a: Int, b: String, c: Float)
  * }}}
  *
  * After:
  * {{{
  * case class Test(a: Int, b: String, c: Float) {
  *   def toMap: _root_.scala.collection.Map[String, Any] =
  *     _root_.scala.collection.Map[String, Any](("a", a), ("b", b), ("c", c))
  * }
  *
  * object Test {
  *   def apply(m: _root_.scala.collection.Map[String, Any]): Test = {
  *      new Test(m("a").asInstanceOf[Int], m("b").asInstanceOf[String], m("c").asInstanceOf[Float])
  *
  *   def typeMap: _root_.scala.collection.Map[String, String] =
  *     _root_.scala.collection.Map[String, String](("a", "Int"), ("b", "String"), ("c", "Float"))
  * }
  * }}}
  */
@compileTimeOnly("@BabelSchema not expanded")
class BabelSchema extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta(BabelSchemaImpl.impl(defn))
}

object BabelSchemaImpl {

  private[this] val methodsToExpand: Seq[(Stat) => Stat with Scope] = {
    Seq(
      Class2Map.impl,
      ClassTypeMap.impl,
      FromMapApply.impl
    )
  }

  private[macros] def impl(defn: Stat): Stat = {
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
