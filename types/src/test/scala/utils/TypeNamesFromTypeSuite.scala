package utils

import org.specs2.mutable.Specification
import types.utils.TypeNameUtils

/**
  * Tests for [[TypeNameUtils#typeNameWithoutPackagePredecessors]] method.
  */
class TypeNamesFromTypeSuite extends Specification {

  "TypeNameUtils.typeNameWithoutPackagePredecessors()" should {

    "Handle type names without its package predecessors" in {
      val typeName = "AlreadyAType"
      TypeNameUtils.typeNameWithoutPackagePredecessors(typeName) must beEqualTo(typeName)
    }
    "Handle type names with package predecessors" in {
      val typeName = "Type"
      val classNameWithPredecessors = s"org.predecessors.$typeName"
      val result = TypeNameUtils.typeNameWithoutPackagePredecessors(classNameWithPredecessors)
      result must beEqualTo(typeName)
    }
  }
}
