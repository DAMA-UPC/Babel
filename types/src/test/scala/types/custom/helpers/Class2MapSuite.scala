package types.custom.helpers

import org.specs2.mutable.Specification

import scala.collection.SortedMap

/**
  * Test the macro [[Class2Map]].
  */
class Class2MapSuite extends Specification {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      val expectedValue: Int = 4
      @Class2Map class Test(value: Int)
      new Test(expectedValue).toMap must beEqualTo(SortedMap("value" -> expectedValue))
    }
    "work with multiple parameter classes" in {
      val intValue: Int = 1
      val stringValue: String = "abc"
      val floatValue: Float = 5f
      @Class2Map class Test(intValue: Int, stringValue: String, floatValue: Float)
      new Test(intValue, stringValue, floatValue).toMap must beEqualTo(
        SortedMap("intValue" -> intValue, "stringValue" -> stringValue, "floatValue" -> floatValue)
      )
    }
  }
}
