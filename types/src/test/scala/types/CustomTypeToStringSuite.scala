package types

import org.specs2.mutable.Specification

/**
  * Test that the [[CustomType]] macro generates a proper override
  * of its [[toString()]] method.
  */
class CustomTypeToStringSuite extends Specification {

  "Macro annotation expansion" should {
    "toString() override must correspond to a pretty printed structure JSON with 2 spaces" in {
      @CustomType class SingleParameterClass(value: Int)
      SingleParameterClass.toString must beEqualTo(SingleParameterClass.structureJson.spaces2)
    }
  }
}
