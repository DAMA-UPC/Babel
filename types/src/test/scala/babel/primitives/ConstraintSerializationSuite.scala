package babel.primitives

import io.circe.Json
import io.circe.syntax._
import org.specs2.mutable.Specification

/**
  * Tests for [[Constraint#intermediateLanguage]] method.
  */
class ConstraintSerializationSuite extends Specification {

  "Constraints" should {
    "Generate an intermediate language JSON properly" should {
      "When the value corresponds to a String value" in {

        case class TestConstraint(override val name: String, value: String)
            extends Constraint(name, value)

        val name = "constraint name"
        val value = "value"

        val expectation = TestConstraint(name, value).asJson.noSpaces

        expectation must beEqualTo("{\"name\":\"" + name + "\",\"value\":\"" + value + "\"}")
      }
      "When the value corresponds to a Number value" in {

        case class TestConstraint(override val name: String, value: Number)
            extends Constraint(name, value)

        val name = "constraint name"
        val value = 412

        val expectation = TestConstraint(name, value).asJson.noSpaces

        expectation must beEqualTo(
          "{\"name\":\"" + name + "\",\"value\":\"" + value.toString + "\"}"
        )
      }
    }
    "When the value corresponds to a Json value" in {

      val name = "name"
      val value = Map("a" -> 1, "gm" -> 41).asJson

      case class TestConstraint(override val name: String, value: Json)
          extends Constraint(name, value)

      val expectation = TestConstraint(name, value).asJson.noSpaces

      expectation must beEqualTo("{\"name\":\"" + name + "\",\"value\":" + value.noSpaces + "}")
    }
  }
}
