package ground.learning.scala.fpinscala.chapter2

object fib {
  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    def _isSorted(n: Int): Boolean = {
      if (n == 0) true
      else ordered(as(n), as(n - 1)) && _isSorted(n - 1)
    }
    _isSorted(as.length - 1)
  }

  def fib(n: Int): Int = {
    if (n <= 1) n
    else
      fib(n - 1) + fib(n - 2)
  }
  def main(args: Array[String]) = {
    println(fib(15))
    println(isSorted(Array(1, 2, 3), (x: Int, y: Int) => x > y))
    println(isSorted(Array(1), (x: Int, y: Int) => x > y))
    println(isSorted(Array(2, 1), (x: Int, y: Int) => x > y))
  }

}