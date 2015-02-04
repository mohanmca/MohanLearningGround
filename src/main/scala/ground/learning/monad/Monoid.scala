package ground.learning.scala.monad

import language.postfixOps
import scala.language.implicitConversions
import scala.language.higherKinds

object Application {

  trait Monoid[A] {
    val mzero: A
    def mappend(a: A, b: A): A
  }

  trait Each[M[_]] {
    def each[A](xs: M[A])(f: (A => Unit)): Unit
  }

  trait MA[M[_], A] {
    val value: M[A]
    def sum2(implicit monoid: Monoid[A], each: Each[M]): A = {
      var result = monoid.mzero
      each.each(value) { elem => result = monoid.mappend(result, elem) }
      result
    }
  }

  trait Identity[A] {
    val value: A
    def println = System.out.println(value)
  }

  implicit def makeMyIdentity[A](_value: A) = new Identity[A] { val value = _value }

  implicit def makeMa[M[_], A](v: M[A]): MA[M, A] = new MA[M, A] {
    val value = v
  }

  implicit val listEach: Each[List] = new Each[List] {
    def each[A](xs: List[A])(f: (A => Unit)): Unit = {
      xs foreach f
    }
  }

  implicit val optionEach: Each[Option] = new Each[Option] {
    def each[A](op: Option[A])(f: (A => Unit)): Unit = {
      op foreach f
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
   * BootStrap function
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

  val optInt: Option[Int] = Some(5)
  val optString: Option[String] = Some("Some")

  def main(args: Array[String]) {
    List(1, 2, 3).sum2.println
    List[String]("1", "2", "3").sum2.println
    optInt.sum2.println
    optString.sum2.println
  }
}
