package babel

import org.specs2.mutable.Specification

/**
  * Test that macro [[CustomType]] generates a method named
  * 'typeName:String' returning the custom type name.
  */
class CustomTypeIsRequiredMethodSuite extends Specification {

  "Macro annotation expansion" should {
    @CustomType class Test(value: Int)

    "Must generate in the companion object the method 'isRequired: Boolean' returning a true" in {
      // Custom Types are required by default.
      Test.isRequired must beTrue
    }
  }
}
