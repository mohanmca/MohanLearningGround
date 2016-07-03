package ground.learning.fp.Monad

case class State[S, A](runState : S => (S, A)) {

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

object State {
  def init[S] : State[S, S] = State(s => (s, s))

  def modify[S](f : S => S) = init[S] flatMap (s => State(_ => (f(s), ())))
}

case class Reader[Cx, A](rd : Cx => A) {
  def map[B](f : A => B) : Reader[Cx, B] = Reader(f compose rd)
  val s : Cx => A = { c : Cx => rd(c) }
  def flatMap[B](f : A => Reader[Cx, B]) : Reader[Cx, B] = Reader.apply({ c : Cx => f(rd(c)).rd(c) })
}

case class Identity[A](a : A) {
  def map[B](f : A => B) : Identity[B] = Identity(f(a))
  def flatMap[B](f : A => Identity[B]) : Identity[B] = f(a)
}