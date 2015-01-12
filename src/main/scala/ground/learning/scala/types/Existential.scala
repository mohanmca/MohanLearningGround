package ground.learning.scala.types

object existential {

  def print(values: Array[Any]) = println(values.length)

  def print(values: Array[T] forSome { type T }) = println(values.length)

  print(Array("Test"))
  print(Array[String]("Test")) //won't compile without existential

  //http://www.drmaciver.com/2008/03/existential-types-in-scala/
  val t: Map[Class[T] forSome { type T; }, String] = ???;
  
  val l: List[Class[_]] = List(classOf[Int], classOf[String])
  val l2: List[Class[T] forSome {type T;}] = List(classOf[Int], classOf[String])
  
  //Below won't compile, as it is List[Class[Any]]
  //val l3: List[Class[T]] forSome {type T;} = List(classOf[Int], classOf[String]) 
  
}