package schema

import org.specs2.mutable.Specification

/**
  * Test the macro [[ClassTypeMap]].
  */
class SchemaDefinitionSpec extends Specification {

  "Macro annotation expansion" should {

    "of the method: 'definitionJson : Json'" should {
      "work with single parameter classes" in {
        @SchemaDefinition class SingleParameterClass(value: Int)
        SingleParameterClass.definitionJson.noSpaces must beEqualTo(
          """{"SingleParameterClass":{"type":"object","properties":{"value":"Int"}}}"""
        )
      }
      "work with multiple parameter classes" in {
        @SchemaDefinition class MultipleParameterClass(stringValue: String, floatValue: Float)

        MultipleParameterClass.definitionJson.noSpaces must beEqualTo(
          """
          {"MultipleParameterClass":{"type":"object","properties":{"stringValue":"String","floatValue":"Float"}}}
          """.trim
        )
      }
      "work when already having a companion object" in {
        val expectation: Int = 42
        @SchemaDefinition class ClassWithCompanion(stringValue: String, intValue: Int)
        object ClassWithCompanion {
          def testValue: Int = expectation
        }

        (ClassWithCompanion.definitionJson.noSpaces must beEqualTo(
          """
          {"ClassWithCompanion":{"type":"object","properties":{"stringValue":"String","intValue":"Int"}}}
          """.trim
        )) && (ClassWithCompanion.testValue must beEqualTo(expectation))
      }
    }
    "of the method: 'definitionYaml : YamlSyntax'" should {
      "work with single parameter classes" in {
        @SchemaDefinition class SingleParameterClass(value: Int)
        SingleParameterClass.definitionYaml.spaces2.trim must beEqualTo(
          """SingleParameterClass:
            |  type: object
            |  properties:
            |    value: Int
          """.stripMargin.trim
        )
      }
      "work with multiple parameter classes" in {
        @SchemaDefinition class MultipleParameterClass(stringValue: String, floatValue: Float)

        MultipleParameterClass.definitionYaml.spaces2.trim must beEqualTo(
          """MultipleParameterClass:
            |  type: object
            |  properties:
            |    stringValue: String
            |    floatValue: Float
          """.stripMargin.trim
        )
      }
      "work when already having a companion object" in {
        val expectation: Int = 42
        @SchemaDefinition class ClassWithCompanion(stringValue: String, intValue: Int)
        object ClassWithCompanion {
          def testValue: Int = expectation
        }

        (ClassWithCompanion.definitionYaml.spaces2.trim must beEqualTo(
          """ClassWithCompanion:
            |  type: object
            |  properties:
            |    stringValue: String
            |    intValue: Int
          """.stripMargin.trim
        )) && (ClassWithCompanion.testValue must beEqualTo(expectation))
      }
    }
  }
}
