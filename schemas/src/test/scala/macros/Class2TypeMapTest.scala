package macros

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[Class2TypeMap]].
  */
class Class2TypeMapTest extends Specification with ScalaCheck {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      @Class2TypeMap class TestWithInt(value: Int)
      prop {
        (expectedValue: Int) =>
          new TestWithInt(expectedValue).toTypeMap must beEqualTo(Map("value" -> "Int"))
      }
      @Class2TypeMap class TestWithString(value: String)
      prop {
        (expectedValue: String) =>
          new TestWithString(expectedValue).toTypeMap must beEqualTo(Map("value" -> "String"))
      }
      @Class2TypeMap case class TestCaseClass(value: String)
      prop {
        (expectedValue: String) =>
          TestCaseClass(expectedValue).toTypeMap must beEqualTo(Map("value" -> "String"))
      }
    }
    "work with multiple parameter classes" in {
      @Class2TypeMap class TestWithStandardClass(stringValue: String, floatValue: Float)
      prop {
        (stringValue: String, floatValue: Float) =>
          new TestWithStandardClass(
            stringValue = stringValue,
            floatValue = floatValue
          ).toTypeMap must beEqualTo(
            Map(
              "stringValue" -> "String",
              "floatValue" -> "Float"
            )
          )
      }
      @Class2TypeMap case class TestWithCaseClass(stringValue: String, floatValue: Float)
      prop {
        (stringValue: String, floatValue: Float) =>
          TestWithCaseClass(
            stringValue = stringValue,
            floatValue = floatValue
          ).toTypeMap must beEqualTo(
            Map(
              "stringValue" -> "String",
              "floatValue" -> "Float"
            )
          )
      }
    }
  }
}
