package types
package primitives.numeric

import org.specs2.mutable.Specification

/**
  * Test the [[NumericTypeSuites]] class.
  */
class NumericTypeSuites extends Specification {

  "Scala primitive types implicit conversion to Babel types" should {

    "Perform automatically with 'scala.Byte'" in {
      val value: NumericType = Byte
      value must beEqualTo(astByte)
    }
    "Perform automatically with 'scala.Short'" in {
      val value: NumericType = Short
      value must beEqualTo(astShort)
    }
    "Perform automatically with 'scala.Int'" in {
      val value: NumericType = Int
      value must beEqualTo(astInt)
    }
    "Perform automatically with 'scala.Long'" in {
      val value: NumericType = Long
      value must beEqualTo(astLong)
    }
    "Perform automatically with 'scala.BigInt'" in {
      val value: NumericType = BigInt
      value must beEqualTo(astBigInt)
    }
    "Perform automatically with 'scala.Float" in {
      val value: NumericType = Float
      value must beEqualTo(astFloat)
    }
    "Perform automatically with 'scala.Double'" in {
      val value: NumericType = Double
      value must beEqualTo(astDouble)
    }
    "Perform automatically with 'scala.BigDecimal" in {
      val value: NumericType = BigDecimal
      value must beEqualTo(astBigDecimal)
    }
  }
}
