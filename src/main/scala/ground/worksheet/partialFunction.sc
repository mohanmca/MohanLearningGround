
object partialFunction {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  import PartialFunction.cond
  val regex = "f.*o"r                             //> regex  : scala.util.matching.Regex = f.*o
  val strings = List("foo", "boo", "foro")        //> strings  : List[String] = List(foo, boo, foro)
  strings filter (cond(_) { case regex(_*) => true })
                                                  //> res0: List[String] = List(foo, foro)
  strings collect { case s @ regex(_*) => s }     //> res1: List[String] = List(foo, foro)
  strings collect { case s @ regex(_*) => s+" :found " }
                                                  //> res2: List[String] = List("foo :found ", "foro :found ")

  List(Some(5), Some(3), None) collect { case s @ Some(x) => s}
                                                  //> res3: List[Some[Int]] = List(Some(5), Some(3))

}