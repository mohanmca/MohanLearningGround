//package ground.learning.parser
//
//import scala.util.Try
//
//object ParserCombinator {
//
//  val syntax = """
//    (3 + 4)
//    (3 * 4)
//    assignment := variable = addExpr || multExpr 
//  """
//
//  type Parser[+T] = String => T
//  
//  trait Parser2[+T] {
//    def parse(input: String): T 
//  }
//
//  def numberParser1(input: String): (Option[Int], String) = {
//    val digit = input.toList.takeWhile(_.isDigit).mkString
//    (Try(Integer.valueOf(digit).toInt).toOption, input.replaceFirst(digit, ""))
//  }
//  
//  def numberParser(): Parser[(Option[Int], String)] = { input: String =>
//    val digit = input.toList.takeWhile(_.isDigit).mkString
//    (Try(Integer.valueOf(digit).toInt).toOption, input.replaceFirst(digit, ""))
//  }
//  
//
//  def opParser(): Parser[(Option[Char], String)] = { input: String =>
//    if (input.charAt(0) == '+' || input.charAt(0) == '*') {
//      (Some(input.charAt(0)), input.substring(1))
//    } else {
//      (None, input)
//    }
//  }
//
//  def expParser(input: String): (List[Int], Char) = {
//    val number = numberParser(input)
//    val operator = opParser(number._2)
//    val number2 = numberParser(operator._2)
//    (List(number._1.get, number2._1.get), operator._1.get)
//  }
//
//  def main(args: Array[String]) {
//    println(expParser("3+2"))
//  }
//
//}