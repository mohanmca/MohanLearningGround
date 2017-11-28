package group.learning.scalaz

import scalaz.Tags.Multiplication

object MonoidOps {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  import scalaz._
  import Scalaz._
  1 |+| 2                                         //> res0: Int = 3
  none[Int] |+| none[Int]                         //> res1: Option[Int] = None
  none[Int] |+| Some(4)                           //> res2: Option[Int] = Some(4)
  
  val doubleRevers = Endo(-(_:Double))            //> doubleRevers  : scalaz.Endo[Double] = Endo(group.learning.scalaz.MonoidOps$$
                                                  //| $Lambda$12/1564984895@5ea434c8)
  
  (1, "abc", 1) |+| (2, "cbd", 4)                 //> res3: (Int, String, Int) = (3,abccbd,5)
  Map("A" -> 3, "C" -> 4)  |+| Map("A" -> 4, "B" -> 9, "C" -> 8)
                                                  //> res4: scala.collection.immutable.Map[String,Int] = Map(A -> 7, B -> 9, C -> 
                                                  //| 12)
  
  //val t = Tag[(String,Int), Multiplication](Map("A" -> 3, "C" -> 4))  |+| Tag[(String,Int), Multiplication] Map("A" -> 4, "B" -> 9, "C" -> 8)
}