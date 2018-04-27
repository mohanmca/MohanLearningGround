object Excercise {

  def reduce[F[_],A](nums: F[A]): Int = {
   ???
  }                                               //> reduce: [F[_], A](nums: F[A])Int

  Some(5).map(_ + 1)                              //> res0: Option[Int] = Some(6)

  val studentMarks = List(120, 230, 340, 540, 560)//> studentMarks  : List[Int] = List(120, 230, 340, 540, 560)

  val revaluationFun: Option[Int => Int] = Some({ mark: Int =>
    java.lang.Math.ceil(mark * 1.05).toInt
  })                                              //> revaluationFun  : Option[Int => Int] = Some(Excercise$$$Lambda$12/1757293506
                                                  //| @28feb3fa)

  //val revaluation = (marks: Seq[Int], fn: Int => Int) => marks.map(fn)
  val revaluation = (marks: Seq[Int], fn: Option[Int => Int]) => if (fn.isDefined) marks.map(fn.get) else marks.map(x => x)
                                                  //> revaluation  : (Seq[Int], Option[Int => Int]) => Seq[Int] = Excercise$$$Lamb
                                                  //| da$13/459296537@7823a2f9

  //val revisedMarks = revaluation(studentMarks, revaluationFun)

  val revisedMarks = revaluation(studentMarks, revaluationFun)
                                                  //> revisedMarks  : Seq[Int] = List(126, 242, 357, 567, 588)
  /**
   * class {
   * static [
   * }
   * var int = 5
   *
   * func intVarDecl = {}
   * func charVarDecl = {}
   * func VarDecl = inv
   *
   * }
   *
   * interace {
   * }
   *
   */

}