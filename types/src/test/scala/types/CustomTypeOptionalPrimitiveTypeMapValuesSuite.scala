package types

import java.time._
import java.util.{Date, UUID}

import org.specs2.mutable.Specification

import scala.collection.immutable.SortedMap

/**
  * Test that the macro [[CustomType]] generates handles the
  * generation of the `typeMap: SortedMap[String, Type]` method
  * as expected when working with nullable AST values.
  */
class CustomTypeOptionalPrimitiveTypeMapValuesSuite extends Specification {

  "Macro annotation expansion" should {

    "work with all optional numeric types" in {

      @CustomType class ByteTest(value: Option[Byte])
      val testByte = ByteTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Byte)))

      @CustomType class ShortTest(value: Option[Short])
      val testShort = ShortTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Short)))

      @CustomType class IntTest(value: Option[Int])
      val testInt = IntTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Int)))

      @CustomType class LongTest(value: Option[Long])
      val testLong = LongTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Long)))

      @CustomType class BigIntTest(value: Option[BigInt])
      val testBigInt =
        BigIntTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(BigInt)))

      @CustomType class FloatTest(value: Option[Float])
      val testFloat = FloatTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Float)))

      @CustomType class DoubleTest(value: Option[Double])
      val testDouble =
        DoubleTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Double)))

      @CustomType class BigDecimalTest(value: Option[BigDecimal])
      val testBigDecimal =
        BigDecimalTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(BigDecimal)))

      testByte && testShort && testInt && testLong &&
      testBigInt && testFloat && testDouble && testBigDecimal
    }
    "work with all optional timestamp types" in {

      @CustomType class LocalDateTimeTest(value: Option[LocalDateTime])
      val testLocalDateTimeType =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> Option(classOf[LocalDateTime]))
        )

      @CustomType class OffsetDateTimeTest(value: Option[OffsetDateTime])
      val testOffsetDateTimeType =
        OffsetDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> Option(classOf[OffsetDateTime]))
        )

      @CustomType class InstantDateTest(value: Option[Instant])
      val testInstantType =
        InstantDateTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> Option(classOf[Instant]))
        )

      @CustomType class ZonedDateTimeTest(value: Option[ZonedDateTime])
      val testZonedDateTimeType =
        ZonedDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> Option(classOf[ZonedDateTime]))
        )

      testLocalDateTimeType && testOffsetDateTimeType && testInstantType && testZonedDateTimeType
    }
    "work with all optional text types" in {

      @CustomType class StringTypeTest(value: Option[String])
      val testStringType =
        StringTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(classOf[String])))

      @CustomType class CharTypeTest(value: Option[Char])
      val testCharType =
        CharTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Char)))

      testStringType && testCharType
    }
    "work with all optional date types" in {

      @CustomType class JavaDateTest(value: Option[Date])
      val testJavaDateType =
        JavaDateTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(classOf[Date])))

      @CustomType class LocalDateTest(value: Option[LocalDate])
      val testLocalDateType =
        LocalDateTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> Option(classOf[LocalDate]))
        )

      testJavaDateType && testLocalDateType
    }
    "work with optional UUID classes" in {
      @CustomType case class TestUuidClass(value: Option[UUID])
      TestUuidClass.typeMap must beEqualTo(Map[String, Type]("value" -> Option(classOf[UUID])))
    }
    "work when using the fully qualified primitive types (Such as 'Option[java.time.LocalDate]')" in {

      @CustomType class LocalDateTimeTest(value: Option[java.time.LocalDateTime])
      val testLocalDateTimeType =
        LocalDateTimeTest.typeMap must beEqualTo(
          Map[String, Type]("value" -> Option(classOf[LocalDateTime]))
        )

      @CustomType class ByteTest(value: Option[scala.Byte])
      val testByteType = ByteTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Byte)))

      @CustomType class CharTypeTest(value: Option[scala.Char])
      val testCharType =
        CharTypeTest.typeMap must beEqualTo(Map[String, Type]("value" -> Option(Char)))

      testLocalDateTimeType && testByteType && testCharType
    }
  }
}
