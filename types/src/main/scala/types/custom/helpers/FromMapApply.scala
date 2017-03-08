package types.custom.helpers

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta.Term.Block
import scala.meta._

/**
  * Before:
  * {{{
  * @FromMapApply
  * case class Test(a: Int, b: String, c: Float)
  * }}}
  *
  * After:
  *
  * {{{
  * object Test {
  * def apply(m: _root_.scala.collection.Map[String, Any]): Test = {
  *  new Test(m("a").asInstanceOf[Int], m("b").asInstanceOf[String], m("c").asInstanceOf[Float])
  * }
  * }}}
  */
@compileTimeOnly("@FromMapApply not expanded")
class FromMapApply extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta(FromMapApply.impl(defn))
}

/**
  * Object containing the [[FromMapApply]] macro annotation expansion implementation.
  */
object FromMapApply {

  /**
    * Implementation of the [[FromMapApply]] macro expansion.
    */
  val impl: (Stat) => Block = {
    (defn: Stat) => {
      defn match {
        case Term.Block(Seq(cls@Defn.Class(_, name, _, ctor, _), companion: Defn.Object)) =>
          // companion object exists
          val applyMethod = createApply(name, ctor)
          val templateStats: Seq[Stat] =
            applyMethod +: companion.templ.stats.getOrElse(Nil)
          val newCompanion = companion.copy(
            templ = companion.templ.copy(stats = Some(templateStats)))
          Term.Block(Seq(cls, newCompanion))
        case cls@Defn.Class(_, name, _, ctor, _) =>
          // companion object does not exists
          val applyMethod = createApply(name, ctor)
          val companion = q"object ${Term.Name(name.value)} { $applyMethod }"
          Term.Block(Seq(cls, companion))
        case _ =>
          println(defn.structure)
          abort("@FromMapApply must annotate a class.")
      }
    }
  }

  /**
    * Generates the apply method with all the required assignations.
    */
  private[this] def createApply(name: Type.Name, ctor: Ctor.Primary): Defn.Def = {

    def argAssignations: Seq[Seq[Term.Name]] = {
      ctor.paramss.map(_.map(
        (param: Term.Param) => {
          val quotedArgName = Term.Name("\"" + param.name.syntax + "\"")
          val termType = Type.Name(param.decltpe.map(_.toString()).getOrElse("Any"))
          Term.Name(q"m($quotedArgName).asInstanceOf[$termType]".syntax)
        }
      )
      )
    }
    q"""def apply(m : _root_.scala.collection.Map[String, Any]): $name = {
            new ${Ctor.Ref.Name(name.value)}(...$argAssignations)
        }
        """
  }
}
