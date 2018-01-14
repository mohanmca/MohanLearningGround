package ground.learning.fp.Monad.State2
import scalaz.Scalaz

//http://scalaz.github.io/scalaz/scalaz-2.9.1-6.0.2/doc.sxr/scalaz/example/ExampleState.scala.html#772357
object StateMonadExample2 {
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

  val increment = (x : Int) => (x, x + 1)         //> increment  : Int => (Int, Int) = <function1>

  def number[A](tree : Tree[A]) : State[Int, Tree[(A, Int)]] = tree match {
    case Leaf(x) => State { s => (s + 1, Leaf(x, s)) }
    case Branch(l, r) => State { s : Int =>
      {
        val t = number(l).run(s);
        val u = number(r).run(t._1)
        (u._1, Branch(t._2, u._2))
      }
    }
  }                                               //> number: [A](tree: StateMonadExample2.Tree[A])StateMonadExample2.State[Int,S
                                                  //| tateMonadExample2.Tree[(A, Int)]]

  val tree = Branch(Leaf("one"), Branch(Leaf("two"), Branch(Leaf("three"), Branch(Leaf("four"), Leaf("five")))))
                                                  //> tree  : StateMonadExample2.Branch[String] = Branch(Leaf(one),Branch(Leaf(tw
                                                  //| o),Branch(Leaf(three),Branch(Leaf(four),Leaf(five)))))

  number(tree).run(1)                             //> res0: (Int, StateMonadExample2.Tree[(String, Int)]) = (6,Branch(Leaf((one,1
                                                  //| )),Branch(Leaf((two,2)),Branch(Leaf((three,3)),Branch(Leaf((four,4)),Leaf((
                                                  //| five,5)))))))
}