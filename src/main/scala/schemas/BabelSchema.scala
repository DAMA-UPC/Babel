package schemas

trait BabelSchema[T] {
  def projection : MappedProjection[T]
}

case class MappedProjection[T](projection : T)
