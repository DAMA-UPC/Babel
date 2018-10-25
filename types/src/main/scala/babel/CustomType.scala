package babel

import babel.primitives.date.DateTypes
import babel.primitives.numeric.NumericTypes
import babel.primitives.text.TextTypes
import babel.primitives.timestamp.TimestampTypes
import babel.primitives.uuid.UuidTypes
import babel.utils.TypeNameUtils

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Ctor.Ref
import scala.meta.Term.{Block, Param}
import scala.meta.Type.Name
import scala.meta._

//Before:
//
//@CustomType
//case class Test(a: Int, b: String, c: Float, d: Option[java.time.LocalDate])
//
//After:
//
//case class Test(a: Int, b: String, c: Float)
//
//object Test extends TestCompanion(isRequired = true)
//object OptionalTest extends TestCompanion(isRequired = false)
//
//private sealed abstract class TestCompanion(override val isRequired: Boolean)
//  extends _root_.babel.Type {
//
//  override val typeName: String = "Test"
//  override val isRequired: Boolean = true
//
//  override def intermediateLanguage: _root_.io.circe.Json = {
//    import _root_.babel._
//    import _root_.io.circe._
//    import _root_.io.circe.syntax._
//    val objectTypesJson = JsonObject.fromMap(typeMap.mapValues(_.asJson)).asJson
//    val objectPropertiesJson =
//        JsonObject.fromMap(
//          _root_.scala.collection.immutable.ListMap(
//             "type" -> "object".asJson,
//             "properties" -> objectTypesJson
//          )
//        ).asJson
//    JsonObject.fromMap(
//     _root_.scala.collection.immutable.Map("Test" -> objectPropertiesJson)
//    ).asJson
//  }
//
//  override def toString: String = intermediateLanguage.spaces2
//
//  def typeMap: _root_.scala.collection.immutable.SortedMap[String, babel.Type] = {
//     import _root_.babel._
//     _root_.scala.collection.immutable.ListMap[String, babel.Type](
//          ("a", Int),
//          ("b", classOf[String]),
//          ("c", Float),
//          ("d", Option(classOf[java.time.LocalDate]))
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
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = false, isEdge = false))
}

private[babel] object MacroImpl {

  /**
    * Common parent for all custom types.
    */
  private[this] val typeParent = ctor"_root_.babel.Type"

  private[this] val edgeTypeParent = ctor"_root_.babel.EdgeModel"

  private[this] val nodeTypeParent = ctor"_root_.babel.NodeModel"

  /**
    * [[CustomType]] macro implementation.
    */
  def impl(defn: Stat, isVertex: Boolean, isEdge: Boolean): Block = defn match {

    case Term.Block(_) =>
      abort("CustomType classes must be a class without companions.")

    case Defn.Class(_, _, typeParameters, _, _) if typeParameters.nonEmpty =>
      abort("CustomType classes must not have any type parameter")

    case cls@Defn.Class(_, name, _, _, _) if cls.tparams.isEmpty =>

      val classTermName = Term.Name(name.value)

      // Generates the base companion object.
      val baseCompanionClass = baseCompanionObject(cls, isVertex, isEdge)

      // Generates a companion abstract companion class containing the information from both companions.
      val companionObjectsParent: Ref.Name = Ctor.Ref.Name(s"${name}Companion")

      // object Test extends TestCompanion (isRequired = true)
      val companion = companionSignature(classTermName, companionObjectsParent, isVertex, isEdge)

      // object OptionalTest extends TestCompanion (isRequired = false)
      val optionalClassName = Term.Name(s"Optional${name.value}")

      val companionOptionalValues =
        q"object $optionalClassName extends $companionObjectsParent (isRequired = false)"

      Term.Block(Seq(cls, companion, companionOptionalValues, baseCompanionClass))

    case inputDefinition =>

      // Annotating a class or case class with parameters is forbidden
      println(inputDefinition.structure)
      abort("@CustomType must be a class with parameters.")
  }

  private[this] def companionSignature(className: Term.Name,
                                       companionObjectParent: Ref.Name,
                                       isVertex: Boolean,
                                       isEdge: Boolean): Defn.Object = {
    if (isVertex) {
      q"object $className extends $companionObjectParent (isRequired = true) with _root_.babel.NodeModel"
    } else if (isEdge) {
      q"object $className extends $companionObjectParent (isRequired = true) with _root_.babel.EdgeModel"
    } else {
      q"object $className extends $companionObjectParent (isRequired = true)"
    }
  }

  /**
    * Constructs the base companion class.
    */
  private[this] def baseCompanionObject(cls: Defn.Class,
                                        isVertex: Boolean,
                                        isEdge: Boolean): Defn.Class = {

    val abstractCompanionName = Type.Name(s"${cls.name.value}Companion")
    val baseCompanion: Defn.Class =
      q"private sealed abstract class $abstractCompanionName(override val isRequired: Boolean) { }"

    val companionTemplate: Template = baseCompanion.templ
    val companionParents = companionTemplate.parents

    val baseCompanionObjectWithParents: Template = companionTemplate.copy(
      parents = (companionParents :+ typeParent)
        ++ (if (isVertex) List(nodeTypeParent) else Nil)
        ++ (if (isEdge) List(edgeTypeParent) else Nil)
    )

    val newCompanionClassMethods = Seq(
      typeNameMethod(cls.name.value),
      intermediateLanguageMethod(cls.name, cls.ctor),
      toStringMethodOverride,
      typeMapMethod(cls.ctor)
    )

    val companionClassMethods =
      baseCompanionObjectWithParents.stats.getOrElse(Nil) ++ newCompanionClassMethods

    baseCompanion.copy(
      templ = baseCompanionObjectWithParents.copy(stats = Some(companionClassMethods))
    )
  }

  /**
    * Generates the method implementing [[Type.typeName]] on the expanded companion object.
    */
  private[this] def typeNameMethod(className: String): Defn.Val =
    q"override val typeName: String = ${Term.Name("\"" + className + "\"")}"

  /**
    * Method for serializing the [[Type]] structure as a JSON.
    */
  private[this] def intermediateLanguageMethod(name: Name,
                                               ctor: Ctor.Primary): Defn.Def = {

    val className = Term.Name("\"" + name.value + "\"")

    q"""
        override def intermediateLanguage : _root_.io.circe.Json = {

          import _root_.babel._

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
    q"override def toString: String = intermediateLanguage.spaces2"

  private[this] def typeMapMethod(ctor: Ctor.Primary): Defn.Def = {

    val namesToValues: Seq[Term.Tuple] = ctor.paramss.flatten.map {
      param: Param => {

        val paramName: String = param.name.syntax
        val typeName: String = param.decltpe.get.toString()

        val parsedTypeName =
          TypeNameUtils.parseTypeName(typeName, removePredecessorsInOption = false)

        val isNumericType = NumericTypes.typeNameToBabelType(typeName).isDefined
        val isTimeType = TimestampTypes.typeNameToBabelType(typeName).isDefined
        val isTextType = TextTypes.typeNameToBabelType(typeName).isDefined
        val isUuidType = UuidTypes.typeNameToBabelType(typeName).isDefined
        val isCharType = isTextType && parsedTypeName.typeName.endsWith("Char")
        val isDateType = DateTypes.typeNameToBabelType(typeName).isDefined

        val primitiveType = isNumericType || isCharType
        val supportedNonPrimitiveType = isTimeType || isTextType || isUuidType || isDateType

        if (primitiveType) {
          // Primitive types from scala, use implicit conversion directly.
          if (parsedTypeName.isRequired) {
            q"($paramName, ${Term.Name(typeName)})"
          } else {
            q"($paramName, Option(${Term.Name(parsedTypeName.typeName)}))"
          }
        } else if (supportedNonPrimitiveType) {
          // Supported non-primitive type, use 'classOf' for forcing implicit conversion
          if (parsedTypeName.isRequired) {
            q"($paramName, classOf[${Type.Name(typeName)}])"
          } else {
            q"($paramName, Option(classOf[${Type.Name(parsedTypeName.typeName)}]))"
          }
        } else {
          if (parsedTypeName.isRequired) {
            q"($paramName, ${Term.Name(typeName)})"
          } else {
            val optionalTypeName = s"Optional${parsedTypeName.typeName}"
            q"($paramName, ${Term.Name(optionalTypeName)})"
          }
        }
      }
    }
    q"""
       def typeMap: _root_.scala.collection.immutable.ListMap[String, _root_.babel.Type] = {
         import _root_.babel._
         _root_.scala.collection.immutable.ListMap[String, babel.Type](..$namesToValues)
       }
     """
  }
}
