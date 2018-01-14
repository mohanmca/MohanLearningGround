package ground.learning.fp.Monad.State2
//http://patterngazer.blogspot.sg/2012/01/changing-my-state-of-mind-with-monad-in.html

object state {
  trait StateMonad[+T, S] {
    owner =>
    def apply(state : S) : (T, S)

    def flatMap[U](f : T => StateMonad[U, S]) = new StateMonad[U, S] {
      override def apply(state : S) = {
        val (a, y) = owner.apply(state)
        f(a)(y)
      }
    }

    def map[U](f : T => U) = new StateMonad[U, S] {
      def apply(state : S) = {
        val (a, y) = owner.apply(state)
        (f(a), y)
      }
    }
  }

  object Stack {
    def push[A](x : A) = new StateMonad[Unit, List[A]] {
      def apply(state : List[A]) = ((), x :: state)
    }

    def pop[A] = new StateMonad[Option[A], List[A]] {
      def apply(state : List[A]) =
        state match {
          case x :: xs => (Some(x), xs)
          case _ => (None, state)
        }
    }
  }

  import Stack._

  val result = for {
    _ <- push(3)
    _ <- push(5)
    _ <- push(7)
    _ <- push(9)
    _ <- pop
  } yield ()

  println(result(List(1))._2)

  val otherResult = push(3).flatMap { _ =>
    push(5).flatMap { _ =>
      push(7).flatMap { _ =>
        push(9).flatMap { _ =>
          pop.map { _ => () }
        }
      }
    }
  }

  println(otherResult(List(1))._2)

}