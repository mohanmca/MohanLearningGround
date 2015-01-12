
object partialFunction {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(70); 
  println("Welcome to the Scala worksheet")
  import PartialFunction.cond;$skip(52); 
  val regex = "f.*o"r;System.out.println("""regex  : scala.util.matching.Regex = """ + $show(regex ));$skip(43); 
  val strings = List("foo", "boo", "foro");System.out.println("""strings  : List[String] = """ + $show(strings ));$skip(54); val res$0 = 
  strings filter (cond(_) { case regex(_*) => true });System.out.println("""res0: List[String] = """ + $show(res$0));$skip(46); val res$1 = 
  strings collect { case s @ regex(_*) => s };System.out.println("""res1: List[String] = """ + $show(res$1));$skip(57); val res$2 = 
  strings collect { case s @ regex(_*) => s+" :found " };System.out.println("""res2: List[String] = """ + $show(res$2));$skip(66); val res$3 = 

  List(Some(5), Some(3), None) collect { case s @ Some(x) => s};System.out.println("""res3: List[Some[Int]] = """ + $show(res$3))}

}
