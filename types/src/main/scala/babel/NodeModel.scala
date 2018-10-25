package babel

import scala.annotation.compileTimeOnly
import scala.meta._

/**
  * Trait specifying that this class can be serialized
  * as a Vertex model on the Intermediate Language.
  */
trait NodeModel

/**
  * Represents a [[NodeModel]] custom type annotation.
  * Basically it does the macro expansion from [[NodeModel]]
  * but extending also the [[NodeModel]] trait.
  */
@compileTimeOnly("@VertexModelDefinition not expanded")
class VertexModelDefinition extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = true, isEdge = false))
}

/**
  * Alias of [[VertexModelDefinition]].
  */
@compileTimeOnly("@vertex not expanded")
class vertex extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = true, isEdge = false))
}

/**
  * Alias of [[VertexModelDefinition]].
  */
@compileTimeOnly("@NodeVertexDefinition not expanded")
class NodeVertexDefinition extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = true, isEdge = false))
}

/**
  * Alias of [[VertexModelDefinition]].
  */
@compileTimeOnly("@node not expanded")
class node extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = true, isEdge = false))
}
