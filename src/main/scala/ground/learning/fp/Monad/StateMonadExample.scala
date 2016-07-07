package ground.learning.fp.Monad

import scalaz.Scalaz

//http://scalaz.github.io/scalaz/scalaz-2.9.1-6.0.2/doc.sxr/scalaz/example/ExampleState.scala.html#772357
object StateMonadExample extends App {
  sealed trait Tree[+A]
  final case class Leaf[A](a : A) extends Tree[A]
  final case class Branch[A](left : Tree[A], right : Tree[A]) extends Tree[A]

  val increment = (x : Int) => (x, x + 1)

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

  val tree = Branch(Leaf("one"), Branch(Leaf("two"), Leaf("three")))
  println(number(1, tree))

}