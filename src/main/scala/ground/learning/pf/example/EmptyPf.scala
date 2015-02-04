package ground.learning.scala.pf.example

object EmptyPf {
  def emptyPfOne(value: AnyRef): Unit = value match {
    case _ if false => ()
  }

  def emptyPfTwo(value: AnyRef): PartialFunction[AnyRef, Unit] = {
    case _ if false => ()
  }

  val emptyPf: PartialFunction[AnyRef, Unit] = { case _ if false => () }

  val emptyPfWinner: PartialFunction[AnyRef, Unit] = Map()
}