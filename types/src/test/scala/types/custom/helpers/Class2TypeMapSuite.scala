package types.custom.helpers

import org.specs2.mutable.Specification
import types._

/**
  * Test the macro [[Class2TypeMap]].
  */
class Class2TypeMapSuite extends Specification {

  "Macro annotation expansion" should {
    "work with single parameter classes" in {
      @Class2TypeMap class TestWithInt(value: Int)
      TestWithInt.typeMap must beEqualTo(Map[String, Type]("value" -> Int))

      @Class2TypeMap class TestWithString(value: String)
      TestWithString.typeMap must beEqualTo(Map[String, Type]("value" -> Byte)) // FIXME

      @Class2TypeMap case class TestCaseClass(value: String)
      TestCaseClass.typeMap must beEqualTo(Map[String, Type]("value" -> Byte)) // FIXME
    }
    "work with multiple parameter classes" in {
      @Class2TypeMap class TestWithStandardClass(stringValue: String, floatValue: Float)
      @Class2TypeMap case class TestWithCaseClass(stringValue: String, floatValue: Float)

      (TestWithStandardClass.typeMap must beEqualTo(
        Map[String, Type](
          "stringValue" -> Byte, // FIXME
          "floatValue" -> Float
        )
      )) && (TestWithCaseClass.typeMap must beEqualTo(
        Map[String, Type](
          "stringValue" -> Byte, // FIXME
          "floatValue" -> Float
        )
      ))
    }
    "work when already having a companion object" in {

      val expectedMethodResult: Int = 42

      @Class2TypeMap class TestWithCompanionObject(stringValue: String, floatValue: Float)
      object TestWithCompanionObject {
        def testMethod: Int = expectedMethodResult
      }
      (TestWithCompanionObject.typeMap must beEqualTo(
        Map(
          "stringValue" -> Byte, // FIXME
          "floatValue" -> Float
        )
      )) && (TestWithCompanionObject.testMethod must beEqualTo(expectedMethodResult))
    }
  }
}

