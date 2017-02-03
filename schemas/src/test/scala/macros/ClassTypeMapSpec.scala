package macros

import org.specs2.mutable.Specification

/**
  * Test the macro [[ClassTypeMap]].
  */
class ClassTypeMapSpec extends Specification {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      @ClassTypeMap class TestWithInt(value: Int)
      TestWithInt.typeMap must beEqualTo(Map("value" -> "Int"))

      @ClassTypeMap class TestWithString(value: String)
      TestWithString.typeMap must beEqualTo(Map("value" -> "String"))

      @ClassTypeMap case class TestCaseClass(value: String)
      TestCaseClass.typeMap must beEqualTo(Map("value" -> "String"))
    }
    "work with multiple parameter classes" in {
      @ClassTypeMap class TestWithStandardClass(stringValue: String, floatValue: Float)
      TestWithStandardClass.typeMap must beEqualTo(
        Map(
          "stringValue" -> "String",
          "floatValue" -> "Float"
        )
      )
    }
    @ClassTypeMap case class TestWithCaseClass(stringValue: String, floatValue: Float)
    TestWithCaseClass.typeMap must beEqualTo(
      Map(
        "stringValue" -> "String",
        "floatValue" -> "Float"
      )
    )
  }
}
