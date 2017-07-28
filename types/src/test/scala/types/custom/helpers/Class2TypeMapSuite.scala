package types.custom.helpers

import java.time._
import java.util.{Date, UUID}

import org.specs2.mutable.Specification
import types._

import scala.collection.immutable.SortedMap

/**
  * Test the macro [[Class2TypeMap]].
  */
class Class2TypeMapSuite extends Specification {

  // TODO: Test the macro when using optinal values as soon as it is implemented.
  // TODO: Test the macro when pointing to other type-mapped classes.

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
      val testBigInt =
        BigIntTest.typeMap must beEqualTo(Map[String, Type]("value" -> BigInt))

      @Class2TypeMap class FloatTest(value: Float)
      val testFloat = FloatTest.typeMap must beEqualTo(Map[String, Type]("value" -> Float))

      @Class2TypeMap class DoubleTest(value: Double)
      val testDouble =
        DoubleTest.typeMap must beEqualTo(Map[String, Type]("value" -> Double))

      @Class2TypeMap class BigDecimalTest(value: BigDecimal)
      val testBigDecimal =
        BigDecimalTest.typeMap must beEqualTo(Map[String, Type]("value" -> BigDecimal))

      testByte && testShort && testInt && testLong &&
      testBigInt && testFloat && testDouble && testBigDecimal
    }
    "work with all timestamp types" in {

      @Class2TypeMap class LocalDateTimeTest(value: LocalDateTime)
      val testLocalDateTimeType =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[LocalDateTime])
        )

      @Class2TypeMap class OffsetDateTimeTest(value: OffsetDateTime)
      val testOffsetDateTimeType =
        OffsetDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[OffsetDateTime])
        )

      @Class2TypeMap class InstantDateTest(value: Instant)
      val testInstantType =
        InstantDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[Instant]))

      @Class2TypeMap class ZonedDateTimeTest(value: ZonedDateTime)
      val testZonedDateTimeType =
        ZonedDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[ZonedDateTime])
        )

      testLocalDateTimeType && testOffsetDateTimeType && testInstantType && testZonedDateTimeType
    }
    "work with all text types" in {

      @Class2TypeMap class StringTypeTest(value: String)
      val testStringType =
        StringTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[String]))

      @Class2TypeMap class CharTypeTest(value: Char)
      val testCharType =
        CharTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Char))

      testStringType && testCharType
    }
    "work with all date types" in {

      @Class2TypeMap class JavaDateTest(value: Date)
      val testJavaDateType =
        JavaDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[Date]))

      @Class2TypeMap class LocalDateTest(value: LocalDate)
      val testLocalDateType =
        LocalDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[LocalDate]))

      testJavaDateType && testLocalDateType
    }
    "work with UUID classes" in {
      @Class2TypeMap case class TestUuidClass(value: UUID)
      TestUuidClass.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[UUID]))
    }
    "work with case classes" in {
      @Class2TypeMap case class TestCaseClass(value: Int)
      TestCaseClass.typeMap must beEqualTo(Map[String, Type]("value" -> Int))
    }
    "work with multiple parameter classes" in {
      @Class2TypeMap class TestWithStandardClass(stringValue: Byte, floatValue: Float)
      @Class2TypeMap case class TestWithCaseClass(stringValue: Byte, floatValue: Float)

      (TestWithStandardClass.typeMap must beEqualTo(
        SortedMap[String, Type]("stringValue" -> Byte, "floatValue" -> Float)
      )) && (TestWithCaseClass.typeMap must beEqualTo(
        SortedMap[String, Type]("stringValue" -> Byte, "floatValue" -> Float)
      ))
    }
    "work when using the fully qualified primitive types (Such as 'java.time.LocalDate')" in {

      @Class2TypeMap class LocalDateTimeTest(value: java.time.LocalDateTime)
      val testLocalDateTimeType =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[LocalDateTime])
        )

      @Class2TypeMap class ByteTest(value: scala.Byte)
      val testByteType = ByteTest.typeMap must beEqualTo(Map[String, Type]("value" -> Byte))

      @Class2TypeMap class CharTypeTest(value: scala.Char)
      val testCharType =
        CharTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Char))

      testLocalDateTimeType && testByteType && testCharType
    }
    "work when already having a companion object" in {

      val expectedMethodResult: Int = 42

      @Class2TypeMap class TestWithCompanionObject(stringValue: Byte, floatValue: Float)
      object TestWithCompanionObject {
        def testMethod: Int = expectedMethodResult
      }
      (TestWithCompanionObject.typeMap must beEqualTo(
        SortedMap[String, Type]("stringValue" -> Byte, "floatValue" -> Float)
      )) && (TestWithCompanionObject.testMethod must beEqualTo(expectedMethodResult))
    }
  }
}
