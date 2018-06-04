package types

import scala.annotation.compileTimeOnly
import scala.meta._

/**
  * Trait specifying that this class can be serialized
  * as an Edge model on the Intermediate Language.
  */
trait EdgeModel

/**
  * Represents an [[EdgeModel]] custom type annotation.
  * Basically it does the macro expansion from [[CustomType]]
  * but extending also the [[EdgeModel]] trait.
  */
@compileTimeOnly("@EdgeModelDefinition not expanded")
class EdgeModelDefinition extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = false, isEdge = true))
}

/**
  * Alias of [[EdgeModelDefinition]].
  */
@compileTimeOnly("@edge not expanded")
class edge extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(MacroImpl.impl(defn, isVertex = false, isEdge = true))
}

