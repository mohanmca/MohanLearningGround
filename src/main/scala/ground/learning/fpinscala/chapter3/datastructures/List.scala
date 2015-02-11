package ground.learning.fpinscala.chapter3.datastructures

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def ~~:[A](f: A => Unit, a: A): A = {
    f(a)
    a
  }

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

  def head[A](xs: List[A]): A = xs match {
    case Cons(head, tail) => head
    case Nil => throw new RuntimeException("head in null list")
  }

  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  }

  def foldRight[A, B](xs: List[A], z: B)(f: (A, B) => B): B = xs match {
    case Nil => z
    case Cons(x, ys) => f(x, foldRight(ys, z)(f))
  }

  def tail[A](xs: List[A]): List[A] = xs match {
    case Nil => throw new NoSuchElementException("Nil")
    case Cons(head, tail) => tail
  }

  def setHead[A](xs: List[A], newHead: A) = xs match {
    case Nil => Cons(newHead, Nil)
    case other => Cons(newHead, other)
  }

  def drop[A](xs: List[A], n: Int): List[A] = n match {
    case 0 => xs
    case n => xs match {
      case Nil => throw new NoSuchElementException("No element at " + n)
      case _ => drop(tail(xs), n - 1)
    }
  }

  def dropWhile[A](xs: List[A], f: A => Boolean): List[A] = xs match {
    case Cons(head, tail) if f(head) => dropWhile(tail, f)
    case _ => xs
  }

  def init[A](xs: List[A]): List[A] = xs match {
    case Nil => throw new UnsupportedOperationException("empty.init")
    case Cons(head, Cons(tail, Nil)) => Cons(head, Nil)
    case Cons(head, tail) => Cons(head, init(tail))
  }

  def sum2(xs: List[Int]): Int = foldRight(xs, 0)((x, y) => x + y)
  def product2(xs: List[Double]): Double = foldRight(xs, 1.0)((x, y) => x * y)
  def concat(xs: List[Int]): List[Int] = foldRight(xs, (Nil: List[Int]))((x, y) => Cons(x, y))
  def length[A](xs: List[A]): Int = foldRight(xs, 0)((x, y) => 1 + y)

  def foldLeft[A, B](xs: List[A], z: B)(f: (B, A) => B): B = xs match {
    case Nil => z
    case Cons(x, ys) => foldLeft(ys, f(z, x))(f)
  }

  def sum3(xs: List[Int]): Int = foldLeft(xs, 0)(_ + _)

  def product3(xs: List[Double]): Double = foldLeft(xs, 1.0)(_ * _)

  def length3[A](xs: List[A]): Int = foldLeft(xs, 0)((x, y) => x + 1)

  def foldRight2[A, B](xs: List[A], z: B)(f: (A, B) => B): B = foldLeft(xs, z)(altFun(f))

  def altFun[A, B](f: (A, B) => B): (B, A) => B = (b: B, a: A) => f(a, b)

  //3.14 -- append using foldRight
  def append[A](xs: List[A], x: A): List[A] = foldLeft(xs, Nil: List[A])((b, a) => Cons(a, b))

  //3.15 -- combine
  def combine[A](xs: List[A], ys: List[A]): List[A] = xs match {
    case Nil => ys
    case Cons(head, tail) => Cons(head, combine(tail, ys))
  }

  //3.16 -- increment each element by 1 with foldLeft
  def incrementEach(xs: List[Int]): List[Int] = foldLeft(xs, Nil: List[Int])((b, a) => Cons(a + 1, b))

  def doubleToString(xs: List[Double]): List[String] = foldLeft(xs, Nil: List[String])((b, a) => Cons(a.toString(), b))

  //3.18 map using foldLeft/foldRight
  def map[A, B](xs: List[A])(f: A => B): List[B] = foldLeft(xs, Nil: List[B])((b, a) => Cons(f(a), b))
  def map2[A, B](xs: List[A])(f: A => B): List[B] = foldRight(xs, Nil: List[B])((a, b) => Cons(f(a), b))

  // 3.19
  def filter[A](xs: List[A], f: A => Boolean): List[A] = foldLeft(xs, Nil: List[A])((b, a) => if (f(a)) Cons(a, b) else b)
  def filter2[A](xs: List[A], f: A => Boolean): List[A] = foldRight(xs, Nil: List[A])((a, b) => if (f(a)) Cons(a, b) else b)

  //From blog excercise
  def last[A](xs: List[A]): A = foldLeft(xs, head(xs))((b, a) => b)

  //3.20
  def flatMap[A, B](xs: List[A], f: A => List[B]): List[B] = {
    val listOfxs: List[List[B]] = map(xs)(f)
    foldRight(listOfxs, Nil: List[B])((ys, b) => foldLeft(b, ys)((a1, b1) => Cons(b1, a1)))
  }

  def rangeToList(x: Int): List[Int] = x match {
    case 0 => Nil
    case n => Cons(n, rangeToList(n - 1))
  }

}

object ListApp extends App {
  import List._
  val items: List[String] = List("1", "2", "3", "4", "5", "6")
  //println(init(items))
  //println(foldLeft(items, Nil: List[Int])((b, a) => Cons(a.toInt, b)))
  val intItems = foldLeft(items, Nil: List[Int])((b, a) => Cons(a.toInt, b))
  //println(intItems)
  //println("Map using foldLeft => " + map(items)(item => item.toInt))
  //println(combine(intItems, intItems))
  //println(filter(intItems, (x: Int) => x > 3))
  //  println(last(intItems))

 // val explosive = map(intItems)(rangeToList)

  println(flatMap(intItems, rangeToList))

}