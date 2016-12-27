package edu.upc.dama.generators

trait Generator[T, SeedType] {
  def next(seed : SeedType) : T
}
