package ground.learning.scala.whyfp;

object fp {
  sealed trait List[+A] {
  }
  case object Nil extends List[Nothing] {
    //    def foldr[A,B](z: B)(op: (A, B) => B): B = z
  }
  case class Cons[A](val first: A, val rest: List[A]) extends List[A] {
    //    def foldr[B](z: B)(op: (A, B) => B): B = {
    //      op(first, rest.foldr(z)(op))
    //    }
  }

  val ints: List[Int] = Cons(4, Cons(3, Cons(2, Cons(1, Nil))))
  val xs = Cons("ABC", ints)

  val sum: (Int, Int) => Int = (x, y) => x + y

  val count: List[Any] => Int = xs => xs match {
    case Nil => 0
    case Cons(first, rest) => sum(1, count(rest))
  }

  val double: List[Int] => List[Int] = xs => xs match {
    case Nil => Nil
    case Cons(first, rest) => Cons(2 * first, double(rest))
  }

  /**
   * val composed_function: Input => Output = argument
   * base_case => replace_for_base_case
   * others => apply(microFunction)  ++ invoke composed_function(for successive element)
   */
  /*
  val foldr: ((Any, Any) => Any, Any) => Any = (f, iv) => xs match {
    case Nil: Any => iv
    case Cons(first, rest) => f(first, foldr(f, rest))
  }
*/
  def main(args: Array[String]) {
    println(count(xs))
    println(double(ints))
  }
}