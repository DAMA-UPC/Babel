package types
package primitives.numeric

import org.specs2.mutable.Specification

/**
  * Test the [[NumericType]] and the [[NumericTypes]] classes.
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
    "Perform automatically with 'scala.Float'" in {
      val value: NumericType = Float
      value must beEqualTo(astFloat)
    }
    "Perform automatically with 'scala.Double'" in {
      val value: NumericType = Double
      value must beEqualTo(astDouble)
    }
    "Perform automatically with 'scala.BigDecimal'" in {
      val value: NumericType = BigDecimal
      value must beEqualTo(astBigDecimal)
    }
  }

  "ConstraintOverloads" should {
    "Adds a single constraint as expected" in {
      // No value overlapping
      val constraint = maxNumberDecimals(1)
      val noOverlapValue = NumericType().withConstraint(constraint)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = maxNumberDecimals(0)
      val valueOverlapping = noOverlapValue.withConstraint(newConstraint)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(newConstraint))

      expectation1 && expectation2
    }
    "Primitive values Implicit conversions constraint overloads must compile as expected" in {
      val newConstraint = maxValue(0)
      BigInt
        .withConstraint(newConstraint)
        .constraints
        .exists(_.equals(newConstraint)) must beTrue
    }
  }
}
