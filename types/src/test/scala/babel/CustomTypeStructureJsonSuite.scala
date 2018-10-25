package babel

import org.specs2.mutable.Specification
import babel.utils.JsonUglyfier

/**
  * Test that the macro [[CustomType]] method
  * 'intermediateLanguage: Json' works as expected.
  */
class CustomTypeStructureJsonSuite extends Specification {

  "Macro annotation expansion of the method: 'intermediateLanguage : Json'" should {

    "work with single parameter classes" in {
      @CustomType class SingleParameterClass(value: Int)
      SingleParameterClass.intermediateLanguage.noSpaces must beEqualTo(
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

      MultipleParameterClass.intermediateLanguage.noSpaces must
        beEqualTo(JsonUglyfier.uglyfy("""
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
              |}""".stripMargin))
    }
  }
}
