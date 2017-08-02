package babel.graph

/**
  * Represents a Vertex of the generated graph.
  * @tparam ID of the generated [[Vertex]] unique identifiers.
  */
trait Vertex[ID] {

  /**
    * Unique identifier for the [[Vertex]] in the generated graph.
    */
  val id: ID
}
