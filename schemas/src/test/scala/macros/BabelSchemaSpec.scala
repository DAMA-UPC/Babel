package macros

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[Class2Map]].
  */
class BabelSchemaSpec extends Specification with ScalaCheck {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      @BabelSchema class Test(value: Int)
      prop {
        (expectedValue: Int) =>
          val testClass = new Test(expectedValue)

          (testClass.toMap must beEqualTo(Map("value" -> expectedValue))) &&
            (testClass.toTypeMap must beEqualTo(Map("value" -> "Int")))
      }
    }
    "work with multiple parameter classes" in {
      @BabelSchema class Test(intValue: Int, stringValue: String, floatValue: Float)
      prop {
        (intValue: Int, stringValue: String, floatValue: Float) =>
          val testClass = new Test(intValue, stringValue, floatValue)

          (testClass.toMap must beEqualTo(
            Map(
              "intValue" -> intValue,
              "stringValue" -> stringValue,
              "floatValue" -> floatValue
            )
          )) && (
            testClass.toTypeMap must beEqualTo(
              Map(
                "intValue" -> "Int",
                "stringValue" -> "String",
                "floatValue" -> "Float"
              )
            ))
      }
    }
  }
}
