package models

import cats.data.NonEmptyStream

/**
  * Represents a generated graph.
  *
  * @param vertices of the generated graph.
  * @param edges    of the generated graph.
  * @tparam ID of the generated [[Vertex]] unique identifiers.
  */
case class PropertyGraph[ID](vertices: NonEmptyStream[Vertex[ID]],
                             edges: NonEmptyStream[Edge[ID]])
