package ground.learning.scala.types.typeclass

object TypeClass extends App {

  import greeting._

  def printGreetMessaage[A](implicit a: greeting[A]): String = "Hello! " + a.greet

  println(printGreetMessaage(3))
  println(printGreetMessaage(3.0))
}

trait greeting[A] {
  val value: A
  def greet: String
}
object greeting {
  case class IntGreeting(value: Int) extends greeting[Int] {
    def greet = value + " From IntGreeting"
  }
  case class DoubleGreeting(value: Double) extends greeting[Double] {
    def greet = value + " From DoubleGreeting"
  }
  implicit def pimpInt[T <% Int](input: Int): IntGreeting = IntGreeting(input)
  implicit def pimpDouble[T <% Double](input: Double): DoubleGreeting = DoubleGreeting(input)
}

