package macros

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[Class2Map]].
  */
class Class2MapTest extends Specification with ScalaCheck {

  "Macro annotation expansion" should {
    "work with single parameter classes without generics" in {
      @Class2Map class Test(value: Int)
      prop {
        (expectedValue: Int) =>
          new Test(expectedValue).toMap must beEqualTo(Map("value" -> expectedValue))
      }
    }
    "work with single parameter classes with generics" in {
      @Class2Map class Test[T](value: T)
      prop {
        (expectedValue: Int) =>
          new Test(expectedValue).toMap must beEqualTo(Map("value" -> expectedValue))
      }
      prop {
        (expectedValue: String) =>
          new Test(expectedValue).toMap must beEqualTo(Map("value" -> expectedValue))
      }
      prop {
        (expectedValue: Boolean) =>
          new Test(expectedValue).toMap must beEqualTo(Map("value" -> expectedValue))
      }
    }
    "work with multiple parameter classes without generics" in {
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
    "work with multiple parameter classes with generics" in {
      @Class2Map class Test[A, B, C](value1: A, value2: B, value3: C, booleanValue: Boolean)
      prop {
        (intValue: Int, stringValue: String, floatValue: Float, booleanValue: Boolean) =>
          new Test(
            value1 = intValue,
            value2 = stringValue,
            value3 = floatValue,
            booleanValue = booleanValue
          ).toMap must beEqualTo(
            Map(
              "value1" -> intValue,
              "value2" -> stringValue,
              "value3" -> floatValue,
              "booleanValue" -> booleanValue
            )
          )
      }
    }
  }
}
