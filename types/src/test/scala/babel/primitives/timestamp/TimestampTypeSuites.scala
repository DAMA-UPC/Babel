package babel
package primitives.timestamp

import java.time.{Instant, LocalDateTime, OffsetDateTime, ZonedDateTime}

import org.specs2.mutable.Specification

/**
  * Test the [[TimestampType]] and the [[TimestampTypes]] classes.
  */
class TimestampTypeSuites extends Specification {

  "Scala primitive types implicit conversion to Babel types" should {
    "Perform automatically with 'LocalDateTime'" in {
      val value: TimestampType = classOf[LocalDateTime]
      value must beEqualTo(astLocalDateTime)
    }
    "Perform automatically with 'Option[LocalDateTime]'" in {
      val value: TimestampType = Option(classOf[LocalDateTime])
      value must beEqualTo(optionalAstLocalDateTime)
    }
    "Perform automatically with 'Instant'" in {
      val value: TimestampType = classOf[Instant]
      value must beEqualTo(astOffsetDateTime)
    }
    "Perform automatically with 'Option[Instant]'" in {
      val value: TimestampType = Option(classOf[Instant])
      value must beEqualTo(optionalAstOffsetDateTime)
    }
    "Perform automatically with 'OffsetDateTime'" in {
      val value: TimestampType = classOf[OffsetDateTime]
      value must beEqualTo(astOffsetDateTime)
    }
    "Perform automatically with 'Option[OffsetDateTime]'" in {
      val value: TimestampType = Option(classOf[OffsetDateTime])
      value must beEqualTo(optionalAstOffsetDateTime)
    }
    "Perform automatically with 'ZonedDateTime'" in {
      val value: TimestampType = classOf[ZonedDateTime]
      value must beEqualTo(astZonedDateTime)
    }
    "Perform automatically with 'Option[ZonedDateTime]'" in {
      val value: TimestampType = Option(classOf[ZonedDateTime])
      value must beEqualTo(optionalAstZonedDateTime)
    }
  }

  "ConstraintOverloads" should {
    "Adds a constraint using 'Now' as expected" in {
      // No value overlapping
      val constraint = TimestampTypesConstraints.minTimestamp(Now)
      val noOverlapValue = TimestampType().withMinTimestamp(Now)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = TimestampTypesConstraints.maxTimestamp(Now)
      val valueOverlapping = noOverlapValue.withMaxTimestamp(Now)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Adds a constraint using 'Instant' as expected" in {
      val value = Instant.now()

      // No value overlapping
      val constraint = TimestampTypesConstraints.minTimestamp(value)
      val noOverlapValue = TimestampType().withMinTimestamp(value)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = TimestampTypesConstraints.maxTimestamp(value)
      val valueOverlapping = noOverlapValue.withMaxTimestamp(value)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Adds a constraint using 'LocalDateTime' as expected" in {
      val value = LocalDateTime.now()

      // No value overlapping
      val constraint = TimestampTypesConstraints.minTimestamp(value)
      val noOverlapValue = TimestampType().withMinTimestamp(value)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = TimestampTypesConstraints.maxTimestamp(value)
      val valueOverlapping = noOverlapValue.withMaxTimestamp(value)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Adds a constraint using 'OffsetDateTime' as expected" in {
      val value = OffsetDateTime.now()

      // No value overlapping
      val constraint = TimestampTypesConstraints.minTimestamp(value)
      val noOverlapValue = TimestampType().withMinTimestamp(value)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = TimestampTypesConstraints.maxTimestamp(value)
      val valueOverlapping = noOverlapValue.withMaxTimestamp(value)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Adds a constraint using 'ZonedDateTime' as expected" in {
      val value = ZonedDateTime.now()

      // No value overlapping
      val constraint = TimestampTypesConstraints.minTimestamp(value)
      val noOverlapValue = TimestampType().withMinTimestamp(value)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val newConstraint = TimestampTypesConstraints.maxTimestamp(value)
      val valueOverlapping = noOverlapValue.withMaxTimestamp(value)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Replaces a constraint as expected" in {
      val initialType = classOf[LocalDateTime].withMinTimestamp(Now)
      val newTimestamp = LocalDateTime.now()
      val endState = initialType.withMinTimestamp(newTimestamp)

      val expectation1 =
        endState.constraints.exists(_.value == newTimestamp.toString) must beTrue

      val expectation2 =
        endState.constraints.exists(_.value == Now.astName) must beFalse

      expectation1 && expectation2
    }
    "Primitive values Implicit conversions constraint overloads must compile as expected" in {
      val minConstraint: TimestampTypeConstraint =
        TimestampTypesConstraints.minTimestamp(Now)

      classOf[LocalDateTime]
        .withMinTimestamp(Now)
        .constraints
        .exists(_.equals(minConstraint)) must beTrue
    }
  }
}
