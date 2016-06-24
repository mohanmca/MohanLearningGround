package ground.learning.fp.Monad

case class Reader[Cx, A](rd : Cx => A) {
  def map[B](f : A => B) : Reader[Cx, B] = Reader(f compose rd)
  val s : Cx => A = { c : Cx => rd(c) }
  def flatMap[B](f : A => Reader[Cx, B]) : Reader[Cx, B] = Reader.apply({ c : Cx => f(rd(c)).rd(c) })
}

case class Identity[A](a : A) {
  def map[B](f : A => B) : Identity[B] = Identity(f(a))
  def flatMap[B](f : A => Identity[B]) : Identity[B] = f(a)
}