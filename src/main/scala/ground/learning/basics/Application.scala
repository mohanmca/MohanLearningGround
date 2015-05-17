package ground.learning.basics

object Application extends App {
  var a = 45
  val b = 55
  def c = a + b
  println(c)
  a = 100
  println(c)

}