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
      val minValue: Int = 1
      val constraint = NumericTypesConstraints.minValue(minValue)
      // Test the MinValue constraint
      val noOverlapValue = NumericType().withMinValue(minValue)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val maxNumberDecimals: Int = 4
      // Test the MaxNumberDecimals constraint
      val newConstraint = NumericTypesConstraints.maxNumberDecimals(maxNumberDecimals)
      val valueOverlapping = noOverlapValue.withMaxNumberDecimals(maxNumberDecimals)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Replaces a constraint as expected" in {
      val initialValue: Int = 42
      val initialConstraint = NumericTypesConstraints.minValue(initialValue)
      val initialType = Int.withMinValue(initialValue)

      val endValue: Int = 15923
      val endConstraint = NumericTypesConstraints.minValue(endValue)
      val endType = initialType.withMinValue(endValue)

      val expectation1 =
        endType.constraints.exists(_.equals(endConstraint)) must beTrue

      val expectation2 =
        endType.constraints.exists(_.equals(initialConstraint)) must beFalse

      expectation1 && expectation2
    }
    "Primitive values Implicit conversions constraint overloads must compile as expected" in {
      val maxValue = 4
      val newConstraint = NumericTypesConstraints.maxValue(maxValue) // Test the MaxValue constraint
      BigInt
        .withMaxValue(maxValue)
        .constraints
        .exists(_.equals(newConstraint)) must beTrue
    }
  }
}
