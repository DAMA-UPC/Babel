import scala.meta._

/**
  * Helper methods/structures used across all the [[macros]] package.
  */
package object macros {

  /**
    * Represents the output of a macro expansion, giving the
    * expanded class and the method that expanding it.
    */
  private[macros] case class MacroExpansionOutput(expandedClass : Defn.Class,
                                                  insertedMethod : Defn.Def)
}
