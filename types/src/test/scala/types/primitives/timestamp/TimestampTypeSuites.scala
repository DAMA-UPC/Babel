package types
package primitives.timestamp

import java.time.{Instant, LocalDateTime, OffsetDateTime, ZonedDateTime}

import org.specs2.mutable.Specification

/**
  * Test the [[TimestampType]] and the [[TimestampTypes]] classes.
  */
class TimestampTypeSuites extends Specification {

  "Scala primitive types implicit conversion to Babel types" should {
    "Perform automatically with 'Instant'" in {
      val value: TimestampType = classOf[Instant]
      value must beEqualTo(astOffsetDateTime)
    }
    "Perform automatically with 'LocalDateTime'" in {
      val value: TimestampType = classOf[LocalDateTime]
      value must beEqualTo(astLocalDateTime)
    }
    "Perform automatically with 'OffsetDateTime'" in {
      val value: TimestampType = classOf[OffsetDateTime]
      value must beEqualTo(astOffsetDateTime)
    }
    "Perform automatically with 'ZonedDateTime'" in {
      val value: TimestampType = classOf[ZonedDateTime]
      value must beEqualTo(astZonedDateTime)
    }
  }

  "ConstraintOverloads" should {
    "Adds a single constraint as expected" in {
      // No value overlapping
      val constraint = constraintMinTimestamp(Now)
      val noOverlapValue = TimestampType().withConstraint(constraint)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = constraintMaxTimestamp(LocalDateTime.now())
      val valueOverlapping = noOverlapValue.withConstraint(newConstraint)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Primitive values Implicit conversions constraint overloads must compile as expected" in {
      val newConstraint: TimestampTypeConstraint = constraintMaxTimestamp(Now)
      classOf[LocalDateTime]
        .withConstraint(newConstraint)
        .constraints
        .exists(_.equals(newConstraint)) must beTrue
    }
  }
}
