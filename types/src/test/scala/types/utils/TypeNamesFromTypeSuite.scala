package types.utils

import org.specs2.mutable.Specification

/**
  * Tests for [[TypeNameUtils#typeNameWithoutPackagePredecessors]] method.
  */
class TypeNamesFromTypeSuite extends Specification {

  "TypeNameUtils.typeNameWithoutPackagePredecessors()" should {

    val typeName = "SomeRandomType"
    val typeNameWithPredecessors = s"org.predecessors.$typeName"

    "handle with required inputs" should {

      def checkExpectations(result: TypeNameUtils.TypeNameParsingResult): Boolean = {
        val e1 = result.isRequired must beTrue
        val e2 = result.typeName must beEqualTo(typeName)
        e1 && e2
      }

      "Handle type names without its package predecessors" in {
        checkExpectations(TypeNameUtils.parseTypeName(typeName))
      }
      "Handle type names with package predecessors" in {
        checkExpectations(TypeNameUtils.parseTypeName(typeNameWithPredecessors))
      }
    }
    "handle optional inputs" should {

      def checkExpectations(result: TypeNameUtils.TypeNameParsingResult): Boolean = {
        val e1 = result.isRequired must beFalse
        val e2 = result.typeName must beEqualTo(typeName)
        e1 && e2
      }

      "Handle type names without any package predecessors" in {
        val optionalTypeWithoutPredecessors = s"Option[$typeName]"
        checkExpectations(TypeNameUtils.parseTypeName(optionalTypeWithoutPredecessors))
      }
      "Handle type names with predecessors in the inner type name" in {
        val optionalTypeWithoutPredecessors = s"Option[$typeNameWithPredecessors]"
        checkExpectations(TypeNameUtils.parseTypeName(optionalTypeWithoutPredecessors))
      }
      "Handle type names with predecessors both in the Option monad and in the inner type name" in {
        val optionalTypeWithoutPredecessors = s"scala.Option[$typeNameWithPredecessors]"
        checkExpectations(TypeNameUtils.parseTypeName(optionalTypeWithoutPredecessors))
      }
    }
  }
}
