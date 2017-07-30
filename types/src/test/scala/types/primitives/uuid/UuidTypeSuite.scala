package types
package primitives.uuid

import java.util.UUID

import org.specs2.mutable.Specification

/**
  * Test the [[UuidType]] and the [[UuidTypes]] classes.
  */
class UuidTypeSuite extends Specification {

  "Scala primitive types implicit conversion to Babel types" should {
    "Perform automatically with 'UUID'" in {
      val value: UuidType = classOf[UUID]
      value must beEqualTo(astUuidType)
    }
    "Perform automatically with 'Option[UUID]'" in {
      val value: UuidType = Option(classOf[UUID])
      value must beEqualTo(optionalAstUuidType)
    }
  }

  "ConstraintOverloads" should {
    "Replaces a constraint as expected" in {

      val notTimeBasedConstraint = UuidTypeConstraints.isTimeUuid(false)
      val timeBasedConstraint = UuidTypeConstraints.isTimeUuid(true)

      val initialType = classOf[UUID]

      val preRequirement =
        initialType.constraints.exists(_.equals(notTimeBasedConstraint)) must beTrue

      val endType = initialType.withTimeBasedUuid(true)

      val postRequirement1 =
        endType.constraints.exists(_.equals(notTimeBasedConstraint)) must beFalse

      val postRequirement2 =
        endType.constraints.exists(_.equals(timeBasedConstraint)) must beTrue

      preRequirement && postRequirement1 && postRequirement2
    }
  }
}
