package ground.learning.fp.Monad

object TreeNumberV1 {
  sealed trait Tree[+A]
  final case class Leaf[A](a : A) extends Tree[A]
  final case class Branch[A](left : Tree[A], right : Tree[A]) extends Tree[A]

  case class State[S, A](runState : S => (S, A)) {

    def run(s : S) : (S, A) = runState(s)

    def map[B](g : A => B) : State[S, B] = State { s =>
      {
        val v = runState(s)
        (s, g(v._2))
      }
    }

    def flatMap[B](g : A => State[S, B]) : State[S, B] = State { s : S =>
      {
        val v = runState(s)
        g(v._2).runState(v._1)
      }
    }
  }

  val increment = (x : Int) => (x, x + 1)

  def number[A](tree : Tree[A]) : State[Int, Tree[(A, Int)]] = tree match {
    case Leaf(x) => State { s => (s + 1, Leaf(x, s)) }
    case Branch(l, r) => State { s : Int =>
      {
        val t = number(l).run(s);
        val u = number(r).run(t._1)
        (u._1, Branch(t._2, u._2))
      }
    }
  }
  val tree = Branch(Leaf("one"), Branch(Leaf("two"), Branch(Leaf("three"), Branch(Leaf("four"), Leaf("five")))))

  number(tree).run(1)

}