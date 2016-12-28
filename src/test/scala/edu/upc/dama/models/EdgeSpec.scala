package edu.upc.dama.models

import java.util.UUID

import edu.upc.dama.TestUtils.arbUUID
import org.scalacheck.Arbitrary
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.specs2.scalacheck.ScalaCheckFunction2

/**
  * Test for @see [[Edge]]
  */
class EdgeSpec extends Specification with ScalaCheck {

  /**
    * [[Edge.toString]] method testSuite
    */
  private[this] def testToString[T](classGenerator: (T, T) => Edge[T],
                                    stringGenerator: (T, T) => String)
                                   (implicit arbA: Arbitrary[T]
                                   ): ScalaCheckFunction2[T, T, Boolean] = {
    prop {
      (source: T, target: T) =>
        classGenerator(source, target).toString == stringGenerator(source, target).toString
    }
  }

  "Default Edge apply() constructor" should {
    "Generate automatically an Undirected Edge by default" in {
      Edge(1, 2) must beAnInstanceOf[Edge.Undirected[Int]]
    }
  }

  "Undirected Edges" should {
    "toString() method prints the elements separated by a '-' at Undirected edges" should {
      def classGenerator[T]: (T, T) => Edge[T] = (a: T, b: T) => Edge.Undirected[T](a, b)

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
      def classGenerator[T]: (T, T) => Edge[T] = (a: T, b: T) => Edge.Directed[T](a, b)

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
