package types.custom.helpers

import org.specs2.mutable.Specification
import utils.JsonUglyfier

/**
  * Test the macro [[Class2TypeMap]].
  */
class DefinitionGeneratorSuite extends Specification {

  "Macro annotation expansion" should {

    "of the method: 'structureJson : Json'" should {
      "work with single parameter classes" in {
        @StructureDefinitionGenerator class SingleParameterClass(value: Int)
        SingleParameterClass.structureJson.noSpaces must beEqualTo(
          JsonUglyfier.uglyfy("""
              |{
              |  "SingleParameterClass": {
              |    "type": "object",
              |    "properties": {
              |      "value": {
              |        "typeName": "Number",
              |        "constraints": [
              |          {
              |            "name": "MinValue",
              |            "value": "-2147483648"
              |          },
              |          {
              |            "name": "MaxValue",
              |            "value": "2147483647"
              |          },
              |          {
              |            "name": "MaxNumberDecimals",
              |            "value": "0"
              |          }
              |        ]
              |      }
              |    }
              |  }
              |}
            """.stripMargin)
        )
      }
      "work with multiple parameter classes" in {
        @StructureDefinitionGenerator class MultipleParameterClass(stringValue: String,
                                                                   floatValue: Float)

        MultipleParameterClass.structureJson.noSpaces must beEqualTo(
          JsonUglyfier.uglyfy("""
              |{
              | "MultipleParameterClass": {
              |   "type": "object",
              |   "properties": {
              |     "floatValue": {
              |       "typeName": "Number",
              |       "constraints": [
              |         {
              |           "name": "MinValue",
              |           "value": "-3.4028235E38"
              |         },
              |         {
              |           "name": "MaxValue",
              |           "value": "3.4028235E38"
              |         },
              |         {
              |           "name": "MaxNumberDecimals",
              |           "value": "8"
              |         }
              |       ]
              |     },
              |     "stringValue": {
              |       "typeName": "Text",
              |       "constraints": [
              |         {
              |           "name": "MinLength",
              |           "value": "0"
              |         },
              |         {
              |           "name": "Encoding",
              |           "value": "UTF-8"
              |         }
              |       ]
              |     }
              |   }
              | }
              |}""".stripMargin)
        )
      }
      "work when already having a companion object" in {
        val expectation: Int = 42

        @StructureDefinitionGenerator class ClassWithCompanion(value: Int)

        object ClassWithCompanion {
          def testValue: Int = expectation
        }

        (ClassWithCompanion.structureJson.noSpaces must beEqualTo(
          JsonUglyfier.uglyfy("""
              |{
              | "ClassWithCompanion": {
              |   "type": "object",
              |   "properties": {
              |     "value": {
              |       "typeName": "Number",
              |       "constraints": [
              |         {
              |           "name": "MinValue",
              |           "value": "-2147483648"
              |         },
              |         {
              |           "name": "MaxValue",
              |           "value": "2147483647"
              |         },
              |         {
              |            "name": "MaxNumberDecimals",
              |            "value": "0"
              |          }
              |        ]
              |      }
              |    }
              |  }
              |}
            """.stripMargin)
        )) && (ClassWithCompanion.testValue must beEqualTo(expectation))
      }
    }
  }
}
