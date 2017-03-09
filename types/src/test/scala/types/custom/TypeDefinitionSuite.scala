package types.custom

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

/**
  * Test the macro [[TypeDefinition]].
  */
class TypeDefinitionSuite extends Specification with ScalaCheck {

  "Macro annotation expansion" should {
    @TypeDefinition class Test(value: Int)
    "Must expand the macro '@Class2Map'" in {
      new Test(1).toMap must haveSize(1)
    }
    "Must expand the macro '@Class2TypeMap'" in {
      Test.typeMap must haveSize(1)
    }
    "Must expand the macro '@FromMapApply'" in {
      Test(Map("value" -> 1)) must beAnInstanceOf[Test]
    }
    "Must expand the macro '@TypeDefinition'" in {
      Test.structureJson.noSpaces must beEqualTo(
        """{"Test":{"type":"object","properties":{"value":"Int"}}}"""
      )
    }
    "The expanded class companion must implement the interface 'CustomType'" in {
      new Test(1) must beAnInstanceOf[CustomType]
    }
    "The class companion must implement the interface 'CustomTypeCompanion[CustomType]'" in {
      Test must beAnInstanceOf[CustomTypeCompanion[Test]]
    }
  }
}
