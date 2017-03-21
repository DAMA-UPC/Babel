package generators

/**
  * Represents a generator with a custom seed type in the AST.
  * @tparam ResultType of the generator result.
  * @tparam SeedType representing the type of generated seed.
  */
trait CustomSeedGenerator[ResultType, SeedType] {

  /**
    * Generates an element from a Seed.
    */
  def next(seed: SeedType): ResultType

  /**
    * Generates an element from a Skip-Seed ignoring
    * the first `seed` elements.
    */
  def next(seed: SeedType, skip: Long)
}
