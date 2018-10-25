package babel

import java.time._
import java.util.{Date, UUID}

import org.specs2.mutable.Specification

import scala.collection.immutable.SortedMap

/**
  * Test that the macro [[CustomType]] generates handles the
  * generation of the `typeMap: SortedMap[String, Type]` method
  * as expected when working that have a 'Non-Null' constraint
  * on them in the AST.
  */
class CustomTypeRequiredTypeMapSuite extends Specification {

  "Macro annotation expansion" should {

    "work with all numeric types" in {

      @CustomType class ByteTest(value: Byte)
      val testByte = ByteTest.typeMap must beEqualTo(Map[String, Type]("value" -> Byte))

      @CustomType class ShortTest(value: Short)
      val testShort = ShortTest.typeMap must beEqualTo(Map[String, Type]("value" -> Short))

      @CustomType class IntTest(value: Int)
      val testInt = IntTest.typeMap must beEqualTo(Map[String, Type]("value" -> Int))

      @CustomType class LongTest(value: Long)
      val testLong = LongTest.typeMap must beEqualTo(Map[String, Type]("value" -> Long))

      @CustomType class BigIntTest(value: BigInt)
      val testBigInt =
        BigIntTest.typeMap must beEqualTo(Map[String, Type]("value" -> BigInt))

      @CustomType class FloatTest(value: Float)
      val testFloat = FloatTest.typeMap must beEqualTo(Map[String, Type]("value" -> Float))

      @CustomType class DoubleTest(value: Double)
      val testDouble =
        DoubleTest.typeMap must beEqualTo(Map[String, Type]("value" -> Double))

      @CustomType class BigDecimalTest(value: BigDecimal)
      val testBigDecimal =
        BigDecimalTest.typeMap must beEqualTo(Map[String, Type]("value" -> BigDecimal))

      testByte && testShort && testInt && testLong &&
      testBigInt && testFloat && testDouble && testBigDecimal
    }
    "work with all timestamp types" in {

      @CustomType class LocalDateTimeTest(value: LocalDateTime)
      val testLocalDateTimeType =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[LocalDateTime])
        )

      @CustomType class OffsetDateTimeTest(value: OffsetDateTime)
      val testOffsetDateTimeType =
        OffsetDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[OffsetDateTime])
        )

      @CustomType class InstantDateTest(value: Instant)
      val testInstantType =
        InstantDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[Instant]))

      @CustomType class ZonedDateTimeTest(value: ZonedDateTime)
      val testZonedDateTimeType =
        ZonedDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[ZonedDateTime])
        )

      testLocalDateTimeType && testOffsetDateTimeType && testInstantType && testZonedDateTimeType
    }
    "work with all text types" in {

      @CustomType class StringTypeTest(value: String)
      val testStringType =
        StringTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[String]))

      @CustomType class CharTypeTest(value: Char)
      val testCharType =
        CharTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Char))

      testStringType && testCharType
    }
    "work with all date types" in {

      @CustomType class JavaDateTest(value: Date)
      val testJavaDateType =
        JavaDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[Date]))

      @CustomType class LocalDateTest(value: LocalDate)
      val testLocalDateType =
        LocalDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[LocalDate]))

      testJavaDateType && testLocalDateType
    }
    "work with UUID classes" in {
      @CustomType case class TestUuidClass(value: UUID)
      TestUuidClass.typeMap must beEqualTo(Map[String, Type]("value" -> classOf[UUID]))
    }
    "work with case classes" in {
      @CustomType case class TestCaseClass(value: Int)
      TestCaseClass.typeMap must beEqualTo(Map[String, Type]("value" -> Int))
    }
    "work with multiple parameter classes" in {
      @CustomType class TestWithStandardClass(stringValue: Byte, floatValue: Float)
      @CustomType case class TestWithCaseClass(stringValue: Byte, floatValue: Float)

      (TestWithStandardClass.typeMap must beEqualTo(
        SortedMap[String, Type]("stringValue" -> Byte, "floatValue" -> Float)
      )) && (TestWithCaseClass.typeMap must beEqualTo(
        SortedMap[String, Type]("stringValue" -> Byte, "floatValue" -> Float)
      ))
    }
    "work when using the fully qualified primitive types (Such as 'java.time.LocalDate')" in {

      @CustomType class LocalDateTimeTest(value: java.time.LocalDateTime)
      val testLocalDateTimeType =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> classOf[LocalDateTime])
        )

      @CustomType class ByteTest(value: scala.Byte)
      val testByteType = ByteTest.typeMap must beEqualTo(Map[String, Type]("value" -> Byte))

      @CustomType class CharTypeTest(value: scala.Char)
      val testCharType =
        CharTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Char))

      testLocalDateTimeType && testByteType && testCharType
    }
    "work with nested custom types" should {
      @CustomType class NestedClass(v: Byte)
      @CustomType class Test(nestedClass: NestedClass)
      Test.typeMap must beEqualTo(SortedMap[String, Type]("nestedClass" -> NestedClass))
    }
  }
}
