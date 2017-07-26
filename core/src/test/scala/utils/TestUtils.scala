package utils

import java.util.UUID

import org.scalacheck.{Arbitrary, Gen}

/**
  * Set of utilities for Unit testing.
  */
object TestUtils {

  /**
    * Scalacheck arbitrary function for [[java.util.UUID]].
    */
  implicit def arbUUID: Arbitrary[UUID] = {
    Arbitrary(Gen.delay(UUID.randomUUID))
  }
}
