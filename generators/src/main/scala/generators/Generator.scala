package generators

/**
  * Represents the default Babel generator, using [[scala.Long]]
  * as the data generation seed.
  */
trait Generator[IdType, ResultType] extends CustomSeedGenerator[IdType, ResultType, Long]
