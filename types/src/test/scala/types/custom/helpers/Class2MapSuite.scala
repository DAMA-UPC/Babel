package types.custom.helpers

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[Class2Map]].
  */
class Class2MapSuite extends Specification with ScalaCheck {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      @Class2Map class Test(value: Int)
      prop {
        (expectedValue: Int) =>
          new Test(expectedValue).toMap must beEqualTo(Map("value" -> expectedValue))
      }
    }
    "work with multiple parameter classes" in {
      @Class2Map class Test(intValue: Int, stringValue: String, floatValue: Float)
      prop {
        (intValue: Int, stringValue: String, floatValue: Float) =>
          new Test(
            intValue = intValue,
            stringValue = stringValue,
            floatValue = floatValue
          ).toMap must beEqualTo(
            Map(
              "intValue" -> intValue,
              "stringValue" -> stringValue,
              "floatValue" -> floatValue
            )
          )
      }
    }
  }
}
