package babel
package primitives.date

import java.time.LocalDate
import java.util.Date

import org.specs2.mutable.Specification

/**
  * Test the [[DateType]] and the [[DateTypes]] classes.
  */
class DateTypeSuites extends Specification {

  "Scala types implicit conversion to Babel types" should {
    "Perform automatically with 'Date'" in {
      val value: DateType = classOf[Date]
      value must beEqualTo(DateTypes.astDateType)
    }
    "Perform automatically with 'LocalDate'" in {
      val value: DateType = classOf[LocalDate]
      value must beEqualTo(DateTypes.astDateType)
    }
    "Perform automatically with 'Option[Date]'" in {
      val value: DateType = Option(classOf[Date])
      value must beEqualTo(DateTypes.optionalAstDateType)
    }
    "Perform automatically with 'Option[LocalDate]'" in {
      val value: DateType = Option(classOf[LocalDate])
      value must beEqualTo(DateTypes.optionalAstDateType)
    }
  }

  "ConstraintOverloads" should {
    "Adds a single constraint as expected" in {
      // No value overlapping
      val minDate = Now
      val constraint = DateTypeConstraints.minDate(minDate)

      // Test the MinValue constraint
      val noOverlapValue = DateType().withMinDate(minDate)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val maxDate = Now
      val newConstraint = DateTypeConstraints.maxDate(maxDate)
      val valueOverlapping = noOverlapValue.withMaxDate(maxDate)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Replaces a constraint as expected" in {

      // Max Value
      val maxValueInitialValue = new Date()
      val maxValueInitialConstraint = DateTypeConstraints.maxDate(maxValueInitialValue)
      val maxValueInitialType = DateType().withMaxDate(maxValueInitialValue)

      val maxValueEndValue = LocalDate.now()
      val maxValueEndConstraint = DateTypeConstraints.maxDate(maxValueEndValue)
      val maxValueEndType = maxValueInitialType.withMaxDate(maxValueEndValue)

      val expectation1 =
        maxValueEndType.constraints.exists(_.equals(maxValueEndConstraint)) must beTrue

      val expectation2 =
        maxValueEndType.constraints.exists(_.equals(maxValueInitialConstraint)) must beFalse

      // Min Value
      val minValueInitialValue = new Date()
      val minValueInitialConstraint = DateTypeConstraints.minDate(minValueInitialValue)
      val minValueInitialType = DateType().withMinDate(minValueInitialValue)

      val minValueEndValue = LocalDate.now()
      val minValueEndConstraint = DateTypeConstraints.minDate(minValueEndValue)
      val minValueEndType = minValueInitialType.withMinDate(minValueEndValue)

      val expectation3 =
        minValueEndType.constraints.exists(_.equals(minValueEndConstraint)) must beTrue

      val expectation4 =
        minValueEndType.constraints.exists(_.equals(minValueInitialConstraint)) must beFalse

      expectation1 && expectation2 && expectation3 && expectation4
    }
  }
}
