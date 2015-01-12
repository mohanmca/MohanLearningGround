package ground.learning.scala.monad

object Application {

  trait Monoid[A] {
    val mzero: A
    def mappend(a: A, b: A): A
  }

  trait Each[M[_]] {
    def each[A](xs: M[A])(f: (A => Unit)): Unit
  }

  implicit val optionEach: Each[Option] = new Each[Option] {
    def each[A](op: Option[A])(f: (A => Unit)): Unit = {
      op foreach f
    }
  }

  implicit val listEach: Each[List] = new Each[List] {
    def each[A](xs: List[A])(f: (A => Unit)): Unit = {
      xs foreach f
    }
  }

  implicit object intMonoid extends Monoid[Int] {
    val mzero = 0
    def mappend(a: Int, b: Int): Int = a + b
  }
  implicit object strMonoid extends Monoid[String] {
    val mzero = ""
    def mappend(a: String, b: String): String = a + b
  }
  /*
  def sum[A](xs: List[A])(implicit monoid: Monoid[A]): A = {
    xs.foldLeft(monoid.mzero)(monoid.mappend)
  }
*/
  def sum[M[_], A](xs: M[A])(implicit monoid: Monoid[A], each: Each[M]): A = {
    var result = monoid.mzero
    each.each(xs) { elem => result = monoid.mappend(result, elem) }
    result
  }

  //val t: Each[Option] = Some(5)
  //val t: Each[List] = List(5)

  def main(args: Array[String]) {
    println(sum(List(1, 2, 3)))
    println(sum(List("1", "2", "3")))
    //  println(sum(Some(5)))
  }
}
