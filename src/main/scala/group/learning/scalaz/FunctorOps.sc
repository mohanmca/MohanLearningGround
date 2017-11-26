package group.learning.scalaz
import scalaz.Functor
import scalaz.std.option._;
import scalaz.std.list._
import scalaz.Bifunctor
import scalaz.syntax.functor._

//https://tpolecat.github.io/2014/03/21/functor.html
object FunctorOps {

  case class Box2[A](fst : A, snd : A)

  implicit val boxFunctor = new Functor[Box2] {
    def map[A, B](fa : Box2[A])(f : A => B) : Box2[B] = Box2(f(fa.fst), f(fa.snd))
  }                                               //> boxFunctor  : scalaz.Functor[group.learning.scalaz.FunctorOps.Box2] = group.
                                                  //| learning.scalaz.FunctorOps$$anon$1@6f75e721

  val F = Functor[Box2]                           //> F  : scalaz.Functor[group.learning.scalaz.FunctorOps.Box2] = group.learning.
                                                  //| scalaz.FunctorOps$$anon$1@6f75e721

  F.map(Box2("123", "x"))(_.length)               //> res0: group.learning.scalaz.FunctorOps.Box2[Int] = Box2(3,1)

  F.apply(Box2("123", "x"))(_.length)             //> res1: group.learning.scalaz.FunctorOps.Box2[Int] = Box2(3,1)

  F(Box2("123", "x"))(_.length)                   //> res2: group.learning.scalaz.FunctorOps.Box2[Int] = Box2(3,1)

  //We can use lift to take a function A => B to F[A] => F[B]
  F.lift((s : String) => s.length)(Box2("123", "x"))
                                                  //> res3: group.learning.scalaz.FunctorOps.Box2[Int] = Box2(3,1)

  def add1(n : Int) = n + 1                       //> add1: (n: Int)Int

  def times2(n : Int) = n * 2                     //> times2: (n: Int)Int

  //mapply takes an F[A => B] and applies it to a value of type A to produce an F[B].
  F.mapply(10)(Box2(add1 _, times2 _))            //> res4: group.learning.scalaz.FunctorOps.Box2[Int] = Box2(11,20)

  F.fpair(Box2(true, false))                      //> res5: group.learning.scalaz.FunctorOps.Box2[(Boolean, Boolean)] = Box2((true
                                                  //| ,true),(false,false))
  //We can turn A into (A,A).
  F.fpair(Box2(true, false))                      //> res6: group.learning.scalaz.FunctorOps.Box2[(Boolean, Boolean)] = Box2((true
                                                  //| ,true),(false,false))

  //injecting a constant of any type B on the right or left.
  F.strengthL(1, Box2("abc", "x"))                //> res7: group.learning.scalaz.FunctorOps.Box2[(Int, String)] = Box2((1,abc),(
                                                  //| 1,x))

  F.strengthR(Box2("abc", "x"), 1)                //> res8: group.learning.scalaz.FunctorOps.Box2[(String, Int)] = Box2((abc,1),(
                                                  //| x,1))

  //pair each element with the result of applying a function.
  F.fproduct(Box2("foo", "x"))(_.length)          //> res9: group.learning.scalaz.FunctorOps.Box2[(String, Int)] = Box2((foo,3),(
                                                  //| x,1))
  //We can empty our value of any information, retaining only structure:
  F.void(Box2("foo", "x"))                        //> res10: group.learning.scalaz.FunctorOps.Box2[Unit] = Box2((),())

  val f = Functor[List] compose Functor[Option]   //> f  : scalaz.Functor[<error>] = scalaz.Functor$$anon$1@1e6d1014

  f.map(List(Some(1), None, Some(3)))(_ + 1)      //> res11: <error> = List(Some(2), None, Some(4))

  import scalaz.std.either._
  val f2 = Functor[List] bicompose Bifunctor[Either]
                                                  //> f2  : scalaz.Bifunctor[<error>] = scalaz.Functor$$anon$2@32d2fa64
  f2.bimap(List(Left(1), Right(2), Left(3)))(_ + 1, _ + 2)
                                                  //> res12: <error> = List(Left(2), Right(4), Left(4))

  //To prove that scalaz functor provides additional power
  val b2 = Box2("foo", "x")                       //> b2  : group.learning.scalaz.FunctorOps.Box2[String] = Box2(foo,x)
  b2.strengthL(true)                              //> res13: group.learning.scalaz.FunctorOps.Box2[(Boolean, String)] = Box2((tru
                                                  //| e,foo),(true,x))
  // The as operation (also called >|) replaces all elements with the given constant, preserving structure.
  // Note the similarity to the void operation.
  b2 as 123                                       //> res14: group.learning.scalaz.FunctorOps.Box2[Int] = Box2(123,123)
  b2 >| false                                     //> res15: group.learning.scalaz.FunctorOps.Box2[Boolean] = Box2(false,false)
  
   //The fpoint operation lifts the parameterized type into a given Applicative.
   b2.fpoint[List]                                //> res16: group.learning.scalaz.FunctorOps.Box2[List[String]] = Box2(List(foo)
                                                  //| ,List(x))
   b2.fpoint[Option]                              //> res17: group.learning.scalaz.FunctorOps.Box2[Option[String]] = Box2(Some(fo
                                                  //| o),Some(x))
   // Way to make existing types into Functor types
   import scalaz._
   import Scalaz._
   val fc = Functor[java.util.concurrent.Callable]//> fc  : scalaz.Functor[java.util.concurrent.Callable] = scalaz.std.java.util.
                                                  //| concurrent.CallableInstances$$anon$1@37e547da
                                                  
}