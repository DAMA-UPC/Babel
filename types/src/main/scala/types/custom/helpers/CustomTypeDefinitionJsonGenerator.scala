package types.custom.helpers

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Term.Block
import scala.meta._

/**
  * Before:
  * {{{
  * @CustomTypeDefinitionJsonGenerator
  * case class Test(a: Int, b: String, c: Float)
  * }}}
  *
  * After:
  *
  * {{{
  * case class Test(a: Int, b: String, c: Float) {
  *   def structureJson: _root_.io.circe.Json = Test.structureJson
  * }
  *
  * object Test {
  *   def structureJson: _root_.io.circe.Json = {
  *     import types._
  *     val typeMap =
  *       scala.collection.immutable.SortedMap[String, types.Type](
  *            ("a", Int), ("b", classOf[String]), ("c", Float)
  *       )
  *     import _root_.io.circe._
  *     import _root_.io.circe.syntax._
  *     val objectTypesJson = JsonObject.fromMap(typeMap.mapValues(_.asJson)).asJson
  *     val objectPropertiesJson = JsonObject.fromMap(
  *         _root_.scala.collection.immutable.Map(
  *            "type" -> "object".asJson, "properties" -> objectTypesJson
  *         )
  *       ).asJson
  *     JsonObject.fromMap(
  *       _root_.scala.collection.immutable.Map("Test" -> objectPropertiesJson)
  *     ).asJson
  *  }
  *
  *   override def toString: String = structureJson.spaces2
  * }
  * }}}
  */
@compileTimeOnly("@CustomTypeDefinitionJsonGenerator not expanded")
class CustomTypeDefinitionJsonGenerator extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(CustomTypeDefinitionJsonGenerator.impl(defn))
}

/**
  * Object containing the [[CustomTypeDefinitionJsonGenerator]] macro annotation expansion implementation.
  */
object CustomTypeDefinitionJsonGenerator {

  /**
    * Implementation of the [[CustomTypeDefinitionJsonGenerator]] macro expansion.
    */
  val impl: (Stat) => Block = {
    (defn: Stat) => {
      defn match {
        // Companion object exist
        case Term.Block(Seq(cls@Defn.Class(_, name, _, ctor, template), companion: Defn.Object)) =>

          val methodToAdd = definitionJsonCompanionObjectMethod(name, ctor)

          // Adds the method to the class.
          val newClsMethod = definitionJsonClassMethod(name)
          val clsMethods: Seq[Stat] = template.stats.getOrElse(Nil) :+ newClsMethod
          val newClass = cls.copy(templ = template.copy(stats = Some(clsMethods)))

          // Adds the method to the companion object
          val companionMethods: Seq[Stat] =
            companion.templ.stats.getOrElse(Nil) :+ methodToAdd :+ toStringMethodOverride

          val newCompanion = companion.copy(
            templ = companion.templ.copy(stats = Some(companionMethods))
          )
          Term.Block(Seq(newClass, newCompanion))

        // Companion object does not exist yet.
        case cls@Defn.Class(_, name, _, ctor, template) =>

          val methodToAdd = definitionJsonCompanionObjectMethod(name, ctor)

          // Adds the method to the class.
          val newClsMethod = definitionJsonClassMethod(name)
          val clsMethods: Seq[Stat] = template.stats.getOrElse(Nil) :+ newClsMethod
          val newClass = cls.copy(templ = template.copy(stats = Some(clsMethods)))

          // companion object does not exists
          val companion =
            q"""
               object ${Term.Name(name.value)} {
                  $methodToAdd
                  $toStringMethodOverride
               }
             """
          Term.Block(Seq(newClass, companion))
        case _ =>
          println(defn.structure)
          abort("@StructureDefinitionGenerator must annotate a class.")
      }
    }
  }

  /**
    * Method for serializing the [[Type]] structure as a JSON.
    */
  private[this] def definitionJsonCompanionObjectMethod(name: Name,
                                                        ctor: Ctor.Primary): Defn.Def = {

    val className = Term.Name("\"" + name.value + "\"")

    q"""
        def structureJson : _root_.io.circe.Json = {

          import types._

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

  /**
    * Convenience method in the main class for calling directly from the class:
    * [[CustomTypeDefinitionJsonGenerator#definitionJsonCompanionObjectMethod]]
    */
  private[this] def definitionJsonClassMethod(name: Name): Defn.Def = {
    val className = Term.Name(name.value)
    q"def structureJson : _root_.io.circe.Json = $className.structureJson"
  }

  /**
    * Contains the toString method override.
    */
  private[this] val toStringMethodOverride: Defn.Def =
    q"override def toString: String = structureJson.spaces2"
}
