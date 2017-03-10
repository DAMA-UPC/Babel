package types.custom.helpers

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Term.Block
import scala.meta._

/**
  * Before:
  * {{{
  * @DefinitionGenerator
  * case class Test(a: Int, b: String, c: Float)
  * }}}
  *
  * After:
  *
  * {{{
  * case class Test(a: Int, b: String, c: Float)
  *
  * object Test {
  *   def structureJson: _root_.io.circe.Json = {
  *   val typeMap =
  *     _root_.scala.collection.immutable.Map[String, String](
  *       ("a", "Int"),
  *       ("b", "String"),
  *       ("c", "Float")
  *       )
  *   import _root_.io.circe._
  *   import _root_.io.circe.syntax._
  *   val objectTypesJson = JsonObject.fromMap(typeMap.mapValues(_.asJson)).asJson
  *   val objectPropertiesJson = {
  *     JsonObject
  *       .fromMap(
  *         _root_.scala.collection.immutable.Map(
  *           "type" -> "object".asJson,
  *           "properties" -> objectTypesJson
  *           )
  *       ).asJson
  *   }
  *   JsonObject.fromMap(_root_.scala.collection.immutable.Map("Test" -> objectPropertiesJson)).asJson
  *   }
  * }
  * }}}
  */
@compileTimeOnly("@DefinitionGenerator not expanded")
class StructureDefinitionGenerator extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(StructureDefinitionGenerator.impl(defn))
}

/**
  * Object containing the [[StructureDefinitionGenerator]] macro annotation expansion implementation.
  */
object StructureDefinitionGenerator {

  /**
    * Implementation of the [[StructureDefinitionGenerator]] macro expansion.
    */
  val impl: (Stat) => Block = {
    (defn: Stat) => {
      defn match {
        case Term.Block(Seq(cls@Defn.Class(_, name, _, ctor, _), companion: Defn.Object)) =>
          // companion object exists
          val methodsToAdd: Seq[Defn.Def] = {
            Seq(
              definitionJsonMethod(name, ctor)
            )
          }
          val templateStats: Seq[Stat] = {
            methodsToAdd ++ companion.templ.stats.getOrElse(Nil)
          }
          val newCompanion = companion.copy(
            templ = companion.templ.copy(stats = Some(templateStats))
          )
          Term.Block(Seq(cls, newCompanion))
        case cls@Defn.Class(_, name, _, ctor, _) =>
          // companion object does not exists
          val companion =
            q"""
                    object ${Term.Name(name.value)} {
                        ${definitionJsonMethod(name, ctor)}
                    }
                    """
          Term.Block(Seq(cls, companion))
        case _ =>
          println(defn.structure)
          abort("@StructureDefinitionGenerator must annotate a class.")
      }
    }
  }

  /**
    * Generates the JSON definition method.
    */
  private[this] def definitionJsonMethod(name: Name, ctor: Ctor.Primary): Defn.Def = {

    val className = Term.Name("\"" + name.value + "\"")

    q"""
       def structureJson : _root_.io.circe.Json = {

          val typeMap = ${Class2TypeMap.methodBody(ctor)}

          import _root_.io.circe._

          import _root_.io.circe.syntax._

          val objectTypesJson =
              JsonObject
              .fromMap(
                typeMap.mapValues(_.asJson)
              ).asJson

          val objectPropertiesJson =
              JsonObject.fromMap(
                 _root_.scala.collection.immutable.Map(
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
}
