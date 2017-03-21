package generators

/**
  * Represents the default Babel generator, using [[scala.Long]]
  * as the data generation seed.
  */
trait Generator[ResultType]
  extends CustomSeedGenerator[ResultType, Long]
