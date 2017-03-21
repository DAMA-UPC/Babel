package generators

/**
  * Represents a generator with a custom seed type in the AST.
  * @tparam ResultType of the generator result.
  * @tparam SeedType representing the type of generated seed.
  */
trait CustomSeedGenerator[IdType, ResultType, SeedType] {

  /**
    * Generates an element from a Seed.
    */
  def next(id: IdType, seed: SeedType): ResultType

}
