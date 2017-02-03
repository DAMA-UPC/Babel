package macros

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[Class2Map]].
  */
class BabelSchemaSpec extends Specification with ScalaCheck {

  "Macro annotation expansion" should {
    @BabelSchema class Test(value: Int)
    "Must expand the macro '@Class2Map'" in {
      new Test(1).toMap must haveSize(1)
    }
    "Must expand the macro '@ClassTypeMap'" in {
      Test.typeMap must haveSize(1)
    }
    "Must expand the macro '@FromMapApply'" in {
      Test(Map("value" -> 1)) must beAnInstanceOf[Test]
    }
  }
}
