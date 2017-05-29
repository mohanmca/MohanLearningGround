/**
  * http://like-a-boss.net/2014/09/27/type-lambda-in-scala.html
  */
object typelambda {


  trait Functor[A, F[_]] {
    def map[B](x: F[A])(f: A => B): F[B]
  }

  implicit def OptionIsFunctor[A]: Functor[A, Option] = new Functor[A, Option] {
    override def map[B](x: Option[A])(f: A => B): Option[B] = x map f
  }

  implicitly[Functor[Int, Option]].map(Option(5))(_ + 1)

  def Tuple2FunctorTest[X, A](x: Tuple2[X, A]) = {
    type Alias[A] = Tuple2[X, A]
    new Functor[A, Alias] {
      override def map[B](x: Alias[A])(f: A => B): Alias[B] = (x._1, f(x._2))
    }
  }

  implicit def Tuple2IsFunctor[X, A]: Functor[A, ({ type Alias[A] = Tuple2[X, A]})#Alias] =
    new Functor[A, ({ type Alias[A] = Tuple2[X, A]})#Alias] {
      override def map[B](x: Tuple2[X, A])(f: A => B): Tuple2[X, B] = (x._1, f(x._2))
    }

//  implicit def Tuple2IsFunctor2[X, A]: Functor[A, ({ type λ[α] = Tuple2[X, A] })#λ)] =
//  new Functor[A, ({ type Alias[A] = Tuple2[X, A]})#Alias] {
//    override def map[B](x: Tuple2[X, A])(f: A => B): Tuple2[X, B] = (x._1, f(x._2))
//  }

}
