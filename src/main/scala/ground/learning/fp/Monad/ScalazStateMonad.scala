package ground.learning.fp.Monad

import scala.collection.immutable.List

object ScalazStateMonad extends App {

  def counter = State[Int, Int](counter => (counter + 1, counter + 1))
  def dice = State[java.util.Random, Int](r => (r, r.nextInt(1000) + 1))

  val _list : List[State[java.util.Random, Int]] = List.fill(10)(dice)
  val _counterList : List[State[Int, Int]] = List.fill(10)(counter)

  def example2() {
    import java.util.Random

    def TwoDice() = for {
      r1 <- dice
      r2 <- dice
    } yield (r1, r2)

  }

  /*
   * type mismatch; found : ((List[Int], ground.learning.fp.Monad.State[java.util.Random,Int])) ⇒ List[Int] required: (List[Int], ground.learning.fp.Monad.State[java.util.Random,Int]) ⇒ List[Int]
   */
  def fill(list : List[State[java.util.Random, Int]], random : java.util.Random) : List[Int] = {
    val fop : (List[Int], State[java.util.Random, Int]) => List[Int] = (accu, state) => state.runState(random)._2 :: accu
    list.foldLeft(List[Int]())(fop)
  }

  println(fill(_list, new java.util.Random()))

  /**
   * type mismatch;
   * found : (ground.learning.fp.Monad.State[Int,Int], ground.learning.fp.Monad.State[Int,List[Int]]) ⇒ ground.learning.fp.Monad.State[Int,List[Int]]
   * required: (ground.learning.fp.Monad.State[Int, _ >: Int with List[Int]], ground.learning.fp.Monad.State[Int, _ >: Int with List[Int]]) ⇒ ground.learning.fp.Monad.State[Int, _ >: Int with List[Int]]
   */

  def fill(list : List[State[Int, Int]], counter : Int) : List[Int] = {
    val op : (State[Int, List[Int]], State[Int, Int]) => State[Int, List[Int]] = (y, x) => x.flatMap(counter => y.map(accumulator => counter :: accumulator))
    val idState = State[Int, List[Int]](r => (r, List[Int]()))
    val t2 = list.foldLeft(idState)(op)
    t2.runState(counter)._2
  }

  println(fill(_counterList, 11))

}



 