package types

import org.specs2.mutable.Specification

/**
  * Test that the Vertex model annotation extends the required types.
  */
class VertexModelTest extends Specification {

  "@vertex must extend the type interface" in {
    @vertex class TestVertex(name: String)
    "must extend the type interface" in {
      TestVertex must beAnInstanceOf[Type]
    }
    "must extend the VertexModel interface" in {
      TestVertex must beAnInstanceOf[VertexModel]
    }
    "must not extend the EdgeModel interface" in {
      TestVertex must not(beAnInstanceOf[EdgeModel])
    }
  }

  "The @VertexModelDefinition annotation" should {
    @VertexModelDefinition class TestVertex(name: String)
    "must extend the type interface" in {
      TestVertex must beAnInstanceOf[Type]
    }
    "must extend the VertexModel interface" in {
      TestVertex must beAnInstanceOf[VertexModel]
    }
    "must not extend the EdgeModel interface" in {
      TestVertex must not(beAnInstanceOf[EdgeModel])
    }
  }
}
