package ground.learning.fp.Monad

import scalaz.Scalaz

//http://scalaz.github.io/scalaz/scalaz-2.9.1-6.0.2/doc.sxr/scalaz/example/ExampleState.scala.html#772357
object StateMonadExample extends App {
  sealed trait Tree[+A]
  final case class Leaf[A](a : A) extends Tree[A]
  final case class Branch[A](left : Tree[A], right : Tree[A]) extends Tree[A]

  val increment = (x : Int) => (x, x + 1)

//  implicit def toStateMonad[S, A](fn : S => (S, A)) : State[S, A] = State.apply { fn }

  def number[A](seed : Int, tree : Tree[A]) : (Tree[(A, Int)], Int) = tree match {
    case Leaf(x) => (Leaf(x, seed), seed + 1)
    case Branch(left, right) => number(seed, left) match {
      case (l, ls) => {
        number(ls, left) match {
          case (r, rs) => (Branch(l, r), rs)
        }
      }
    }
  }


//  import Scalaz._
//  /**
//   * Use the State Monad to implicitly thread the current value
//   * of the label through the recursion.
//   */
//  def numberSM[A](seed : Int, tree : Tree[A]) : (Tree[(A, Int)], Int) = tree match {
//    case Leaf(x) => for {
//      s <- init[Int];
//      _ <- modify((_ : Int) + 1)
//    } yield Leaf((x, s))
//    case Branch(left, right) => for {
//      l <- left.numberSM
//      r <- right.numberSM
//    } yield Branch(l, r)
//  }

  val tree = Branch(Leaf("one"), Branch(Leaf("two"), Leaf("three")))

}