package babel
package primitives.text

import org.specs2.mutable.Specification

/**
  * Test the [[TextType]] and the [[TextTypes]] classes.
  */
class TextTypeSuites extends Specification {

  "Scala types implicit conversion to Babel types" should {
    "Perform automatically with 'String'" in {
      val value: TextType = classOf[String]
      value must beEqualTo(TextTypes.astStringTextType)
    }
    "Perform automatically with 'Option[String]'" in {
      val value: TextType = Option(classOf[String])
      value must beEqualTo(TextTypes.optionalAstStringTextType)
    }
    "Perform automatically with 'Char'" in {
      val value: TextType = Char
      value must beEqualTo(TextTypes.astCharacterTextType)
    }
    "Perform automatically with 'Option[Char]'" in {
      val value: TextType = Option(Char)
      value must beEqualTo(TextTypes.optionalAstCharTextType)
    }
  }

  "ConstraintOverloads" should {
    "Adds a single constraint as expected" in {
      // No value overlapping
      val minLength: Long = 6L
      val constraint = TextTypeConstraints.minLength(minLength)

      // Test the MinValue constraint
      val noOverlapValue = TextType().withMinLength(minLength)
      val expectation1 = noOverlapValue.constraints must beEqualTo(Seq(constraint))

      // Value overlapping
      val maxLength: Long = 6L
      val newConstraint = TextTypeConstraints.maxLength(maxLength)
      val valueOverlapping = noOverlapValue.withMaxLength(maxLength)
      val expectation2 = valueOverlapping.constraints must beEqualTo(Seq(constraint, newConstraint))

      expectation1 && expectation2
    }
    "Replaces a constraint as expected" in {
      val initialValue: Long = 42L
      val initialConstraint = TextTypeConstraints.minLength(initialValue)
      val initialType = TextType().withMinLength(initialValue)

      val endValue: Long = 15923L
      val endConstraint = TextTypeConstraints.minLength(endValue)
      val endType = initialType.withMinLength(endValue)

      val expectation1 =
        endType.constraints.exists(_.equals(endConstraint)) must beTrue

      val expectation2 =
        endType.constraints.exists(_.equals(initialConstraint)) must beFalse

      expectation1 && expectation2
    }
  }
}
