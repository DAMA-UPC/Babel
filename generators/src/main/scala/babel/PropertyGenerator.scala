package babel

/**
  * Trait that needs to be extended by all property generator implementations.
  * @tparam ID representing the type of the node/edge ID accepted by the property generator.
  * @tparam R with the return type of the property generator.
  */
trait PropertyGenerator[ID, R] {
  def run(id: ID, r: ID => R, dependencies: Any*): R
}