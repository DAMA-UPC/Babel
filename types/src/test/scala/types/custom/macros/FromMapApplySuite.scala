package types.custom.macros

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[FromMapApply]].
  */
class FromMapApplySuite extends Specification with ScalaCheck {

  "@Macro annotation expansion" should {
    "work with single parameter classes" in {
      @FromMapApply case class Test(value: Int)
      prop {
        (expectedValue: Int) =>
          Test.apply(Map("value" -> expectedValue)) must beEqualTo(Test(expectedValue))
      }
    }
    "work with multiple parameter classes" in {
      @FromMapApply case class Test(intValue: Int, stringValue: String, floatValue: Float)
      prop {
        (intValue: Int, stringValue: String, floatValue: Float) =>
          Test(
            intValue = intValue,
            stringValue = stringValue,
            floatValue = floatValue
          ) must beEqualTo(
            Test.apply(
              Map(
                "intValue" -> intValue,
                "stringValue" -> stringValue,
                "floatValue" -> floatValue
              )
            )
          )
      }
    }
  }
}
