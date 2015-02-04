package ground.learning.scala.fpinscala.chapter3.datastructures

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def sum(xs: List[Int]): Int = {
    xs match {
      case Nil => 0
      case Cons(x, ys) => x + sum(ys)
    }
  }

  def product(xs: List[Double]): Double = {
    xs match {
      case Nil => 1.0
      case Cons(0, y) => 0
      case Cons(h, ys) => h * product(ys)
    }
  }

  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  }

  def foldRight[A, B](xs: List[A], z: B)(f: (A, B) => B): B = xs match {
    case Nil => z
    case Cons(x, ys) => f(x, foldRight(ys, z)(f))
  }

  def tail[A](xs: List[A]): List[A] = ???

  def setHead[A](xs: List[A], newHead: A) = ???

  def drop[A](xs: List[A], n: Int): List[A] = ???

  def dropWhile[A](xs: List[A], f: A => Boolean): List[A] = ???

  def init[A](xs: List[A]): List[A] = ???
  def sum2(xs: List[Int]): Int = foldRight(xs, 0)((x, y) => x + y)
  def product2(xs: List[Double]): Double = foldRight(xs, 1.0)((x, y) => x * y)
  def concat(xs: List[Int]): List[Int] = foldRight(xs, (Nil: List[Int]))((x, y) => Cons(x, y))
  def length[A](xs: List[A]): Int = foldRight(xs, 0)((x, y) => 1 + y )
  
  def foldLeft[A,B](xs: List[A], z: B)(f: (B,A) => B) : B = xs match {
    case Nil => z
//    case Cons(x, Cons(y, ys)) => f(x,y)  
  }

  //  val x = List(1, 2, 3, 4, 5) match {
  //    case Cons(x, Cons(2, Cons(4, _))) => x
  //    case Nil => 42
  //    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
  //    case Cons(h, t) => h + sum(t)
  //    case _ => 101
  //  }
}