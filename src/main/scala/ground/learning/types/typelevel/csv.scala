package ground.learning.types.typelevel

import shapeless.{ HNil, ::, HList }
import shapeless.{ CNil, :+:, Coproduct, Inr, Inl }
import shapeless.{ Lazy, Generic }

trait CsvEncoder[A] {
  def encode(a : A) : List[String]
}

object CsvEncoder {

  def pure[A](func : A => List[String]) : CsvEncoder[A] = new CsvEncoder[A] {
    def encode(a : A) : List[String] = func(a)
  }

  implicit val intInstance : CsvEncoder[Int] = new CsvEncoder[Int] {
    def encode(a : Int) : List[String] = List(a.toString)
  }
  implicit val doubleInstance : CsvEncoder[Double] = new CsvEncoder[Double] {
    def encode(a : Double) : List[String] = List(a.toString)
  }

  implicit def optionInstance[A](implicit encoder : CsvEncoder[A]) : CsvEncoder[Option[A]] = new CsvEncoder[Option[A]] {
    def encode(opt : Option[A]) : List[String] = opt.map(v => encoder.encode(v)).getOrElse(List.empty)
  }

  implicit val hnilInstance : CsvEncoder[HNil] = new CsvEncoder[HNil] {
    def encode(a : HNil) : List[String] = List()
  }

  implicit def hlistInstance[H, T <: HList](
    implicit hinstance : Lazy[CsvEncoder[H]], tinstance : CsvEncoder[T]) : CsvEncoder[H :: T] = new CsvEncoder[H :: T] {
    def encode(list : H :: T) : List[String] = list match {
      case h :: t => hinstance.value.encode(h) ++ tinstance.encode(t)
    }
  }

  implicit val cnilInstance : CsvEncoder[CNil] = new CsvEncoder[CNil] {
    def encode(a : CNil) : List[String] = List()
  }

  implicit def coproductInstance[H, T <: Coproduct](
    implicit hinstance : Lazy[CsvEncoder[H]], tinstance : CsvEncoder[T]) : CsvEncoder[H :+: T] = new CsvEncoder[H :+: T] {
    def encode(list : H :+: T) : List[String] = list match {
      case Inl(h) => hinstance.value.encode(h)
      case Inr(t) => tinstance.encode(t)
    }
  }

  implicit def genInstance[A, R](
    implicit genInstance : Generic.Aux[A, R], rinstance : Lazy[CsvEncoder[R]]) : CsvEncoder[A] = new CsvEncoder[A] {
    def encode(a : A) : List[String] = {
      val r = genInstance.to(a)
      rinstance.value.encode(r)
    }
  }

}

object csv extends App {

  def encodeCsv[A](a : A)(implicit csvEncoder : CsvEncoder[A]) = csvEncoder.encode(a)
  def encodeCsv[A](as : List[A])(implicit csvEncoder : CsvEncoder[A]) = as.flatMap(a => csvEncoder.encode(a))

  sealed trait Shape
  case class Circle(radius : Double) extends Shape
  case class Rectangle(width : Double, height : Double) extends Shape

  println(encodeCsv[Int](5))
  println(encodeCsv[Shape](Circle(10.0)))

  val shapes = List[Shape](Circle(10.0), Rectangle(10.0, 10.0), Circle(20.0))
  println(encodeCsv[Shape](shapes))

  val optionalShapes = List[Option[Shape]](None, Some(Circle(10.0)), Some(Rectangle(10.0, 10.0)), Some(Circle(20.0)))
  println(encodeCsv[Shape](shapes))
  
}