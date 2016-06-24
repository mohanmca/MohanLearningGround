package ground.learning.scala.fpinscala.chapter2

object pow {

  def pow(n: Int): Int = {
    if (n % 2 != 0) 0
    else
      1 + pow(n % 2)
  }
  def main(args: Array[String]) = {
    println(pow(15))
  }

}