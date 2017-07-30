package types

import org.specs2.mutable.Specification
import types.utils.JsonUglyfier

/**
  * Test that the macro [[CustomType]] method
  * 'structureJson: Json' works as expected.
  */
class CustomTypeStructureJsonSuite extends Specification {

  "Macro annotation expansion of the method: 'structureJson : Json'" should {

    "work with single parameter classes" in {
      @CustomType class SingleParameterClass(value: Int)
      SingleParameterClass.structureJson.noSpaces must beEqualTo(
        JsonUglyfier.uglyfy("""
              |{
              |  "SingleParameterClass": {
              |    "type": "object",
              |    "properties": {
              |      "value": {
              |        "typeName": "Number",
              |        "isRequired": true,
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
      @CustomType class MultipleParameterClass(stringValue: String, floatValue: Float)

      MultipleParameterClass.structureJson.noSpaces must beEqualTo(
        JsonUglyfier.uglyfy("""
              |{
              | "MultipleParameterClass": {
              |   "type": "object",
              |   "properties": {
              |     "stringValue": {
              |       "typeName": "Text",
              |       "isRequired": true,
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
              |     },
              |     "floatValue": {
              |       "typeName": "Number",
              |       "isRequired": true,
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
              |     }
              |   }
              | }
              |}""".stripMargin)
      )
    }
    "work when already having a companion object" in {
      val expectation: Int = 42

      @CustomType class ClassWithCompanion(value: Int)

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
              |       "isRequired": true,
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
