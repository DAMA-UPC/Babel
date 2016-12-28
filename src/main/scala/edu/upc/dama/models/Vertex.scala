package edu.upc.dama.models

/**
  * Represents a Vertex of the generated graph.
  * @tparam ID of the [[Vertex#id]] in the generated graph.
  */
trait Vertex[ID] {

  /**
    * Unique identifier for the [[Vertex]] in the generated graph.
    */
  val id : ID
}
