package ground.learning.scala.monad;

class ReaderMonad {

}

case class Reader[A, B](run: A => B) {
  def apply(x: A): B = run(x)
  def map[C](g: B => C): A => C = run andThen g
  
  def flatMapIntermediate[C](f: B => Reader[A, C]): Reader[A, Reader[A, C]] = Reader((x: A) => map(f)(x))

  def flatMap2[C](f: B => Reader[A, C]): Reader[A, C] = Reader((x: A) => map(f)(x)(x))
}