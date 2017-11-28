package group.learning.scalaz
import scalaz.Monad

object MonadOps extends App {
  println("Welcome to the Scala worksheet")

  case class Box2[A](fst : A, snd : A)

  implicit val boxFunctor = new Monad[Box2] {
    def map[A, B](fa : Box2[A])(f : A => B) : Box2[B] = Box2(f(fa.fst), f(fa.snd))
  }

  val M = Monad[Box2]
  val box = M.bind(Box2("123", "x"))(_ => new Box2("234", "y"))

}