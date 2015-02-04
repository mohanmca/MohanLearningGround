package ground.learning.scala.parser
import scala.util.parsing.combinator._

class ReversePolishCalculator1 extends JavaTokenParsers {
  def term: Parser[List[Float]] = rep(num)
  def num: Parser[Float] = floatingPointNumber ^^ (_.toFloat)
  def operator: Parser[(Float, Float) => Float] = ("*" | "/" | "+" | "-") ^^ {
    case "+" => (x, y) => x + y
    case "-" => (x, y) => x - y
    case "*" => (x, y) => x * y
    case "/" => (x, y) => x / y
  }

  def expr: Parser[Float] = rep(term ~ operator) ^^ {
    case terms =>
      var stack = List.empty[Float]
      var lastOp: (Float, Float) => Float = (x, y) => x + y
      terms.foreach(t =>
        // match on the operator to perform the appropriate calculation
        t match {
          // append the operands to the stack, and reduce the pair at the top using the current operator
          case nums ~ op => lastOp = op; stack = reduce(stack ++ nums, op)
        })
      stack.reduceRight((x, y) => lastOp(y, x))
  }

  // Reduces a stack of numbers by popping the last pair off the stack, applying op, and pushing the result
  def reduce(nums: List[Float], op: (Float, Float) => Float): List[Float] = {
    // Reversing the list lets us use pattern matching to destructure the list safely
    val result = nums.reverse match {
      // Has at least two numbers at the end
      case x :: y :: xs => xs ++ List(op(y, x))
      // List of only one number
      case List(x)      => List(x)
      // Empty list
      case _            => List.empty[Float]
    }
    result
  }

}

///**
// * This trait provides the mathematical operations which the calculator can perform.
// */
//trait Maths {
//  def add(x: Float, y: Float) = x + y
//  def sub(x: Float, y: Float) = x - y
//  def mul(x: Float, y: Float) = x * y
//  def div(x: Float, y: Float) = if (y > 0) (x / y) else 0.0f
//}
//
//object Calculator extends ReversePolishCalculator {
//  def main(args: Array[String]) {
//    val result = parseAll(num, args(0))
//    println(s"Argument was parsed \t\t\t\n $result")
//  }
//}