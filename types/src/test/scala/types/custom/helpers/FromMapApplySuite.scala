package types.custom.helpers

import org.specs2.mutable.Specification

/**
  * Test the macro [[FromMapApply]].
  */
class FromMapApplySuite extends Specification {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      val expectedValue: Int = 5
      @FromMapApply case class Test(value: Int)
      Test.apply(Map("value" -> expectedValue)) must beEqualTo(Test(expectedValue))
    }
    "work with multiple parameter classes" in {
      val intValue: Int = 5
      val stringValue: String = "Hello"
      val floatValue: Float = 5.0f
      @FromMapApply case class Test(intValue: Int, stringValue: String, floatValue: Float)
      Test(intValue = intValue, stringValue = stringValue, floatValue = floatValue) must beEqualTo(
        Test.apply(
          Map("intValue" -> intValue, "stringValue" -> stringValue, "floatValue" -> floatValue)
        )
      )
    }
  }
}
