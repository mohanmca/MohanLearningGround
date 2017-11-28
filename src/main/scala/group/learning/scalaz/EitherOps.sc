package group.learning.scalaz
import scalaz._
import Scalaz._

object EitherOps {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  1.right[String]                                 //> res0: String \/ Int = \/-(1)
  "error".left[Int]                               //> res1: String \/ Int = -\/(error)
  Left[String, Int]("boom") flatMap { x => Right[String, Int](x + 1) }
                                                  //> res2: scala.util.Either[String,Int] = Left(boom)
  Left[String, Int]("boom").right flatMap { x => Right[String, Int](x + 1) }
                                                  //> res3: scala.util.Either[String,Int] = Left(boom)
  "boom".left[Int] >>= { x => (x + 1).right }     //> res4: <error> = -\/(boom)

  val result = for {
    e1 <- "event 1 ok".right
    e2 <- "event 2 failed!".left[String]
    e3 <- "event 3 failed!".left[String]
  } yield (e1 |+| e2 |+| e3)                      //> result  : String \/ Nothing = -\/(event 2 failed!)
  
  result.right.getOrElse("something bad")         //> res5: java.io.Serializable = -\/(event 2 failed!)
  result.swap.right.getOrElse("something good!")  //> res6: java.io.Serializable = \/-(event 2 failed!)
}