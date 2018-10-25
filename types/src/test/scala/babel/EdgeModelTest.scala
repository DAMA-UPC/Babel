package babel

import org.specs2.mutable.Specification

/**
  * Test that the Edge model annotation extends the required types.
  */
class EdgeModelTest extends Specification {

  "@edge must extend the type interface" in {
    @edge class TestEdge(name: String)
    "must extend the type interface" in {
      TestEdge must beAnInstanceOf[Type]
    }
    "must extend the EdgeModel interface" in {
      TestEdge must beAnInstanceOf[EdgeModel]
    }
    "must not extend the VertexModel interface" in {
      TestEdge must not(beAnInstanceOf[NodeModel])
    }
  }

  "The @EdgeModelDefinition annotation" should {
    @EdgeModelDefinition class TestEdge(name: String)
    "must extend the type interface" in {
      TestEdge must beAnInstanceOf[Type]
    }
    "must extend the EdgeModel interface" in {
      TestEdge must beAnInstanceOf[EdgeModel]
    }
    "must not extend the VertexModel interface" in {
      TestEdge must not(beAnInstanceOf[NodeModel])
    }
  }
}
