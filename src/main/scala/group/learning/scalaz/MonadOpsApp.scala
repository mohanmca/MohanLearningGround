package group.learning.scalaz
import scalaz.Monad

object MonadOpsApp extends App {
  println("Welcome to the Scala worksheet")

  case class Box2[A](fst : A)

  implicit val boxFunctor = new Monad[Box2] {
    def point[A](a : => A) : Box2[A] = Box2(a)
    def bind[A, B](fa : Box2[A])(fb : A => Box2[B]) : Box2[B] = fb(fa.fst)
  }

  val M = Monad[Box2]

  val box = M.bind(Box2("123", "x"))(_ => new Box2("234"))
  
  println(box)

}

