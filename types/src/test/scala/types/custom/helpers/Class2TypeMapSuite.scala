package types.custom.helpers

import java.time.LocalDateTime

import org.specs2.mutable.Specification
import types._

import scala.collection.immutable.SortedMap

/**
  * Test the macro [[Class2TypeMap]].
  */
class Class2TypeMapSuite extends Specification {

  // TODO: Add Date type as soon as it is implemented
  // TODO: Add Identifier type as soon as it is implemented
  // TODO: Implement tests for time types

  "Macro annotation expansion" should {

    "work with all numeric types" in {

      @Class2TypeMap class ByteTest(value: Byte)
      val testByte = ByteTest.typeMap must beEqualTo(Map[String, Type]("value" -> Byte))

      @Class2TypeMap class ShortTest(value: Short)
      val testShort = ShortTest.typeMap must beEqualTo(Map[String, Type]("value" -> Short))

      @Class2TypeMap class IntTest(value: Int)
      val testInt = IntTest.typeMap must beEqualTo(Map[String, Type]("value" -> Int))

      @Class2TypeMap class LongTest(value: Long)
      val testLong = LongTest.typeMap must beEqualTo(Map[String, Type]("value" -> Long))

      @Class2TypeMap class BigIntTest(value: BigInt)
      val testBigInt = BigIntTest.typeMap must beEqualTo(Map[String, Type]("value" -> BigInt))

      @Class2TypeMap class FloatTest(value: Float)
      val testFloat = FloatTest.typeMap must beEqualTo(Map[String, Type]("value" -> Float))

      @Class2TypeMap class DoubleTest(value: Double)
      val testDouble = DoubleTest.typeMap must beEqualTo(Map[String, Type]("value" -> Double))

      @Class2TypeMap class BigDecimalTest(value: BigDecimal)
      val testBigDecimal =
        BigDecimalTest.typeMap must beEqualTo(Map[String, Type]("value" -> BigDecimal))

      testByte && testShort && testInt && testLong &&
        testBigInt && testFloat && testDouble && testBigDecimal
    }

    "work with all time types" in {

    /*  @Class2TypeMap class LocalDateTest(value: LocalDateTime)
      val testLocalDateTimeTest =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[LocalDateTime])
        )
      testLocalDateTimeTest
    */
    }

    "work with case classes" in {
      @Class2TypeMap case class TestCaseClass(value: Int)
      TestCaseClass.typeMap must beEqualTo(Map[String, Type]("value" -> Int))
    }
    "work with multiple parameter classes" in {
      @Class2TypeMap class TestWithStandardClass(stringValue: Byte, floatValue: Float)
      @Class2TypeMap case class TestWithCaseClass(stringValue: Byte, floatValue: Float)

      (TestWithStandardClass.typeMap must beEqualTo(
        SortedMap[String, Type](
          "stringValue" -> Byte,
          "floatValue" -> Float
        )
      )) && (TestWithCaseClass.typeMap must beEqualTo(
        SortedMap[String, Type](
          "stringValue" -> Byte,
          "floatValue" -> Float
        )
      ))
    }
    "work when already having a companion object" in {

      val expectedMethodResult: Int = 42

      @Class2TypeMap class TestWithCompanionObject(stringValue: Byte, floatValue: Float)
      object TestWithCompanionObject {
        def testMethod: Int = expectedMethodResult
      }
      (TestWithCompanionObject.typeMap must beEqualTo(
        SortedMap[String, Type](
          "stringValue" -> Byte,
          "floatValue" -> Float
        )
      )) && (TestWithCompanionObject.testMethod must beEqualTo(expectedMethodResult))
    }
  }
}

