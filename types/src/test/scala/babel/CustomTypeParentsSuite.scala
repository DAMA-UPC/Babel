package babel

import org.specs2.mutable.Specification

/**
  * Test that macro [[CustomType]] generated class and its
  * companion object implement the interface [[Type]].
  */
class CustomTypeParentsSuite extends Specification {

  "Macro annotation expansion" should {
    @CustomType class Test(value: Int)
    "must implement the interface 'Type' on the companion object" in {
      Test must beAnInstanceOf[Type]
    }
  }
}
