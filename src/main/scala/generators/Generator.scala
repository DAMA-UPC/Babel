package generators

trait Generator[T, SeedType] {
  def next(seed : SeedType) : T
}
