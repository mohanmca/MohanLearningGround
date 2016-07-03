package ground.learning.fp.Monad.State2
//http://patricknoir.blogspot.sg/2014/12/demistify-state-monad-with-scala-22.html
object state extends App {
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
  } yield ()                                      //> result  : state.StateMonad[Unit,List[Int]] = state$StateMonad$$anon$1@6a38e5
                                                  //| 7f

  println(result(List(1))._2)                     //> List(7, 5, 3, 1)

  val otherResult = push(3).flatMap { _ =>
    push(5).flatMap { _ =>
      push(7).flatMap { _ =>
        push(9).flatMap { _ =>
          pop.map { _ => () }
        }
      }
    }
  }                                               //> otherResult  : state.StateMonad[Unit,List[Int]] = state$StateMonad$$anon$1@
                                                  //| 5c3bd550

  println(otherResult(List(1))._2)                //> List(7, 5, 3, 1)

}