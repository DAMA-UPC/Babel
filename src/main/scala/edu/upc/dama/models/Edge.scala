package edu.upc.dama.models

/**
  * Represents a generated graph Edge.
  * @tparam ID with the unique Vertex identifier type.
  */
trait Edge[ID] {
  /**
    * Source [[Vertex.id]]
    */
  val source: ID
  /**
    * Target [[Vertex.id]]
    */
  val target: ID

  /**
    * An Edge [[toString]] method needs to be overridden, so they can be printed
    * once they are generated.
    *
    * @inheritdoc
    */
  override def toString: String
}

/**
  * Companion Object for [[Edge]].
  *
  * Contains two default implementations for directed and undirected Edges.
  */
object Edge {

  /**
    * Constructor for [[Edge]] generating an [[Edge.Undirected]] by default.
    *
    * This method can be called as: 'Edge(1,2)'.
    */
  def apply[T](source: T, target: T): Edge[T] = Undirected(source, target)

  /**
    * Edge representation for Undirected Edges.
    *
    * @param source @see [[Edge.source]]
    * @param target @see [[Edge.target]]
    * @tparam ID with the unique Vertex identifier type.
    */
  case class Undirected[ID](override val source: ID,
                            override val target: ID) extends Edge[ID] {

    /**
      * Undirected edges are represented on the following format: 'source - target'.
      * For instance: '14 - 41'
      */
    override def toString: String = s"$source - $target"
  }

  /**
    * Edge representation for Directed Edges.
    *
    * @param source @see [[Edge.source]]
    * @param target @see [[Edge.target]]
    * @tparam ID with the unique Vertex identifier type.
    */
  case class Directed[ID](override val source: ID,
                          override val target: ID) extends Edge[ID] {

    /**
      * Undirected edges are represented on the following format: 'source -> target'.
      * For instance: '14 -> 41'
      */
    override def toString: String = s"$source -> $target"
  }

}
