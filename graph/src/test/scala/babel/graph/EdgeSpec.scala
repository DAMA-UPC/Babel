package babel.graph

import java.util.UUID

import org.scalacheck.Arbitrary._
import org.scalacheck.{Arbitrary, Gen}
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.specs2.scalacheck.ScalaCheckFunction2

/**
  * Unit tests for [[Edge]]
  */
class EdgeSpec extends Specification with ScalaCheck {

  /**
    * Class used for definining a test Vertex.
    */
  private[this] case class V[T](override val id: T) extends Vertex[T]

  /**
    * Scalacheck arbitrary function for [[java.util.UUID]].
    */
  private[this] def arbUUID: Arbitrary[UUID] = Arbitrary(Gen.delay(UUID.randomUUID))

  /**
    * [[Edge.toString]] method testSuite
    */
  private[this] def testToString[T](
    classGenerator: (T, T) => Edge[T],
    stringGenerator: (T, T) => String
  )(implicit arbA: Arbitrary[T]): ScalaCheckFunction2[T, T, Boolean] = {
    prop { (source: T, target: T) =>
      classGenerator(source, target).toString == stringGenerator(source, target).toString
    }
  }

  "Undirected Edges" should {
    "toString() method prints the elements separated by a '-' at Undirected edges" should {
      def classGenerator[T]: (T, T) => Edge.Undirected[T] =
        (a: T, b: T) => Edge.Undirected[T](source = V(a), target = V(b))

      def stringGenerator[T]: (T, T) => String = (a: T, b: T) => s"$a - $b"

      "Works with [Int] identifiers" in {
        testToString[Int](classGenerator, stringGenerator)
      }
      "Works with [Long] identifiers" in {
        testToString[Long](classGenerator, stringGenerator)
      }
      "Works with [String] identifiers" in {
        testToString[String](classGenerator, stringGenerator)
      }
      "Works with [UUID] identifiers" in {
        testToString[UUID](classGenerator, stringGenerator)(arbUUID)
      }
    }
  }

  "Directed Edges" should {
    "toString() method prints the elements separated by a '->' at Directed edges" should {
      def classGenerator[T]: (T, T) => Edge[T] =
        (a: T, b: T) => Edge.Directed[T](source = V(a), target = V(b))

      def stringGenerator[T]: (T, T) => String = (a: T, b: T) => s"$a -> $b"

      "Works with [Int] identifiers" in {
        testToString[Int](classGenerator, stringGenerator)
      }
      "Works with [Long] identifiers" in {
        testToString[Long](classGenerator, stringGenerator)
      }
      "Works with [String] identifiers" in {
        testToString[String](classGenerator, stringGenerator)
      }
      "Works with [UUID] identifiers" in {
        testToString[UUID](classGenerator, stringGenerator)(arbUUID)
      }
    }
  }
}
