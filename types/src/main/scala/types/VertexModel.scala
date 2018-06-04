package types

import scala.annotation.compileTimeOnly
import scala.meta._

/**
  * Trait specifying that this class can be serialized
  * as a Vertex model on the Intermediate Language.
  */
trait VertexModel

/**
  * Represents a [[VertexModel]] custom type annotation.
  * Basically it does the macro expansion from [[VertexModel]]
  * but extending also the [[VertexModel]] trait.
  */
@compileTimeOnly("@VertexModelDefinition not expanded")
class VertexModelDefinition extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = true, isEdge = false))
}

/**
  * Alias of [[VertexModelDefinition]].
  */
@compileTimeOnly("@VertexModelDefinition not expanded")
class vertex extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = true, isEdge = false))
}
