package types.custom.helpers

import org.specs2.mutable.Specification

/**
  * Test the macro [[Class2TypeMap]].
  */
class DefinitionGeneratorSuite extends Specification {

  "Macro annotation expansion" should {

    "of the method: 'structureJson : Json'" should {
      "work with single parameter classes" in {
        @StructureDefinitionGenerator class SingleParameterClass(value: Int)
        SingleParameterClass.structureJson.noSpaces must beEqualTo(
          """{"SingleParameterClass":{"type":"object","properties":{"value":"Int"}}}"""
        )
      }
      "work with multiple parameter classes" in {
        @StructureDefinitionGenerator class MultipleParameterClass(stringValue: String, floatValue: Float)

        MultipleParameterClass.structureJson.noSpaces must beEqualTo(
          """
          {"MultipleParameterClass":{"type":"object","properties":{"stringValue":"String","floatValue":"Float"}}}
          """.trim
        )
      }
      "work when already having a companion object" in {
        val expectation: Int = 42
        @StructureDefinitionGenerator class ClassWithCompanion(stringValue: String, intValue: Int)
        object ClassWithCompanion {
          def testValue: Int = expectation
        }

        (ClassWithCompanion.structureJson.noSpaces must beEqualTo(
          """
          {"ClassWithCompanion":{"type":"object","properties":{"stringValue":"String","intValue":"Int"}}}
          """.trim
        )) && (ClassWithCompanion.testValue must beEqualTo(expectation))
      }
    }
  }
}
