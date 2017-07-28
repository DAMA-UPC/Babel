package types

import org.specs2.mutable.Specification

/**
  * Test that macro [[CustomType]] generates a method named
  * 'typeName:String' returning the custom type name.
  */
class CustomTypeTypeNameMethodSuite extends Specification {

  "Macro annotation expansion" should {
    @CustomType class Test(value: Int)

    "Must generate in the companion object the method 'typeName: String'" in {
      Test.typeName must beEqualTo("Test")
    }
  }
}
