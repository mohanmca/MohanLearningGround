package ground.learning.fp.fib
//https://gist.github.com/mpilquist/5025874
//http://blog.tmorris.net/posts/memoisation-with-state-using-scala/
trait State[S, A] {
  val run : S => (S, A)

  def apply(s : S) : (S, A) =
    run(s)

  def eval(s : S) : A =
    apply(s)._2

  def map[B](f : A => B) : State[S, B] = State { s : S =>
    val (s1, a) = run(s)
    (s1, f(a))
  }

  def flatMap[B](f : A => State[S, B]) : State[S, B] = State { s : S =>
    val (s1, a) = run(s)
    f(a)(s1)
  }
}

object State {

  def apply[S, A](f : S => (S, A)) : State[S, A] = new State[S, A] {
    final val run = f
  }

  def state[S, A](a : A) : State[S, A] = State { s : S => (s, a) }
  def get[S] : State[S, S] = State { s : S => (s, s) }
  def gets[S, A](f : S => A) : State[S, A] = State { s : S => (s, f(s)) }
  def modify[S](f : S => S) : State[S, Unit] = State { s : S => (f(s), ()) }
}

object Fib {

  type Memo = Map[Int, Int]
  def stFib(n : Int) : State[Memo, Int] = n match {
    case 0 => State.state(0)
    case 1 => State.state(1)
    case n =>
      for {
        memoed <- State.gets { m : Memo => m get n }
        res <- memoed match {
          case Some(fibN) => State.state[Memo, Int](fibN)
          case None => for {
            a <- stFib(n - 2)
            b <- stFib(n - 1)
            fibN = { println(s"Calculated fib($n)"); a + b }
            _ <- State.modify { memo : Memo => memo + (n -> fibN) }
          } yield fibN
        }
      } yield res
  }

  def fib(n : Int) : Int = stFib(n).eval(Map.empty)
}

object FibApp extends App {

  println(Fib.fib(10))
}