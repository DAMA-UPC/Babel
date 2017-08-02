package babel.graph

/**
  * Object containing the different types of graph edges available.
  *
  * @tparam ID of the generated [[Vertex]] unique identifiers.
  */
sealed trait Edge[ID] {

  /**
    * Source [[Vertex]]
    */
  val source: Vertex[ID]

  /**
    * Target [[Vertex]]
    */
  val target: Vertex[ID]
}

/**
  * Companion Object for [[Edge]].
  *
  * Contains two default implementations for directed and undirected Edges.
  */
object Edge {

  /**
    * Edge representation for Undirected Edges.
    *
    * @param source @see [[Edge.source]]
    * @param target @see [[Edge.target]]
    * @tparam ID with the unique Vertex identifier type.
    */
  case class Undirected[ID](override val source: Vertex[ID],
                            override val target: Vertex[ID]) extends Edge[ID] {

    override def toString: String = s"${source.id} - ${target.id}"
  }


  /**
    * Edge representation for Directed Edges.
    *
    * @param source @see [[Edge.source]]
    * @param target @see [[Edge.target]]
    * @tparam ID with the unique Vertex identifier type.
    */
  case class Directed[ID](override val source: Vertex[ID],
                          override val target: Vertex[ID]) extends Edge[ID] {

    override def toString: String = s"${source.id} -> ${target.id}"
  }

}
