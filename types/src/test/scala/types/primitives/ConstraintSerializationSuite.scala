package types.primitives

import org.specs2.mutable.Specification

/**
  * Tests for [[Constraint#structureJson]] method.
  */
class ConstraintSerializationSuite extends Specification {

  "Constraints" should {
    "Generate an intermediate language JSON properly" in {

      case class TestConstraint(override val name: String,
                                override val value: String
                               ) extends Constraint(name, value)

      val name = "constraint name"
      val value = "value"

      val expectation = TestConstraint(name, value).asJson.noSpaces

      expectation must beEqualTo(
        "{\"name\":\"" + name + "\",\"value\":\"" + value + "\"}"
      )
    }
  }
}
