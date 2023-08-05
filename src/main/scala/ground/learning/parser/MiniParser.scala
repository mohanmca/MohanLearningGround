package ground.learning.parser

import scala.util.parsing.combinator.RegexParsers
import scala.collection.mutable.Map

object MiniParser {

  def main(args: Array[String]): Unit = {
    val expr = """
  def mod(x, y) ={
    if(x < y){
      x
    }else{
      mod(x - y, y)
    }
};

def fizzbuzz(x) ={
    def impl(y)={
      if(y < x + 1){
        if(mod(y,3) < 1){
          if(mod(y,5) < 1){
            println("fizzbuzz")
          }else{
            println("fizz")
          }
        }else if(mod(y,5)< 1){
          println("buzz")
        }else{
          println(y)
        };
        impl(y + 1)
      }else{
        message("Finish" + x);
        0
      }
    };
    impl(1)
};
fizzbuzz(20);
"""
    val parser = new MiniParser
    val ast = parser.parse(expr).get

    val visitor = new ExprVisitor
    val env = new Environment(None)
    env.set("println", PrintLineFunc)
    env.set("message", MessageFunc)
    var result = visitor.eval(env, ast);
  }
}

trait EmbeddedFunc{
  def exec(params:List[Any]):Any
}

object PrintLineFunc extends EmbeddedFunc{
  def exec(params:List[Any])={
    params match{
      case Nil =>{
        println()
        Nil
      }
      case v::_ =>{
          println(v.toString)
        v
      }
    }
  }
}

object MessageFunc extends EmbeddedFunc{
  def exec(params:List[Any])={
    params match{
      case Nil => {}
      case v::_ =>{
          javax.swing.JOptionPane.showMessageDialog(null, v.toString)
          v
      }
    }
  }
}

class Environment(parent:Option[Environment]){
  val variables = Map[String, Any]()
  def get(key:String):Any = {
    if(variables.contains(key)){
      variables(key)
    }else{
      parent match{
        case Some(p) => p.get(key)
        case None => throw new Exception("symbol'%s' not found".format(key))
      }
    }
    
  }
  def set(key:String, value:Any){
    variables(key) = value
  }
}


class ExprVisitor{
  def eval(env:Environment, ast:AST):Any = {
    def visit(ast:AST):Any = {
      ast match{
        case Lines(exprs) =>{
            val local = new Environment(Some(env))
            exprs.foldLeft(()){(result, x) => eval(local, x)}
        }
        case IfExpr(cond, pos, neg) =>{
            visit(cond) match{
              case true => visit(pos)
              case false => visit(neg)
            }
        }
        case LessOp(left, right) =>{
            (visit(left), visit(right)) match{
              case (lval:Int, rval:Int) => lval < rval
            }
        }
        case AddOp(left, right) =>{
            (visit(left), visit(right)) match{
              case (lval:Int, rval:Int) => lval + rval
              case (lval:String, rval) => lval + rval
              case (lval, rval:String) => lval + rval
            }
        }
        case SubOp(left, right) =>{
            (visit(left), visit(right)) match{
              case (lval:Int, rval:Int) => lval - rval
            }
        }
        case MulOp(left, right) =>{
            (visit(left), visit(right)) match{
              case (lval:Int, rval:Int) => lval * rval
            }
        }
        case IntVal(value) => value
        case StringVal(value) => value
        case Ident(name) => {
            env.get(name)
        }
        case Assignment(vr, value) =>{
            val v = visit(value)
            env.set(vr, v)
        }
        case FuncDef(name, func) =>{
            env.set(name, Closure(env, func))
        }
        case FuncCall(func, params) =>{
            visit(func) match{
              case f:Closure => {
                  val local = new Environment(Some(f.env))
                  (f.func.params zip params).foreach{case(pn, a) =>
                    local.set(pn, visit(a))
                  }
                  eval(local, f.func.proc)
              }
              case f:EmbeddedFunc => {
                  f.exec(params.map(visit))
              }
            }
        }
        case f:Func =>{
            Closure(env, f)
        }
      }
    }
    visit(ast)
  }
}

trait AST
case class Lines(exprs:List[AST]) extends AST
case class IfExpr(cond:AST, pos:AST, neg:AST) extends AST
case class LessOp(left: AST, right:AST) extends AST
case class AddOp(left: AST, right:AST) extends AST
case class SubOp(left: AST, right:AST) extends AST
case class MulOp(left: AST, right:AST) extends AST
case class StringVal(value: String) extends AST
case class IntVal(value: Int) extends AST
case class Ident(name: String) extends AST
case class Assignment(variable: String, value: AST) extends AST

case class Func(params:List[String], proc:AST) extends AST
case class FuncDef(name: String, func: Func) extends AST
case class FuncCall(func:AST, params:List[AST]) extends AST

case class Closure(env: Environment, func: Func)

class MiniParser extends RegexParsers{
  //lines ::= expr {";" expr} [";"]
  def lines: Parser[AST] = repsep(line, ";") <~ opt(";")^^Lines
  def line: Parser[AST] = expr | assignment | funcDef
  //expr ::= cond | if
  def expr: Parser[AST] = condOp|ifExpr
  //if ::= "if" "(" expr ")" expr "else" expr
  def ifExpr: Parser[AST] = "if"~"("~>expr~")"~expr~"else"~expr^^{
    case cond~_~pos~_~neg => IfExpr(cond, pos, neg)
  }
  //cond ::= add {"<" add}
  def condOp: Parser[AST] = chainl1(add, 
    "<"^^{_ => (left:AST, right:AST) => LessOp(left, right)})
  //add ::= term {"+" term | "-" term}.
  def add:  Parser[AST] = chainl1(term,
    "+"^^{_ => (left:AST, right:AST) => AddOp(left, right)}|
    "-"^^{_ => (left:AST, right:AST) => SubOp(left, right)})
  //term ::= factor {"*" factor}
  def term : Parser[AST] = chainl1(funcCall, 
    "*"^^{_ => (left:AST, right:AST) => MulOp(left, right)})
  def funcCall: Parser[AST] = factor~rep("("~>repsep(expr, ",")<~")")^^{
    case fac~Nil => fac
    case fac~params =>{
        //関数
        params.foldLeft(fac)(FuncCall(_,_))
    }
  }
  
  //factor ::= intLiteral | stringLiteral | "(" expr ")" |funcLiteral
  def factor: Parser[AST] = intLiteral | stringLiteral | ident |
    "("~>expr<~")" | funcLiteral
  //funcLiteral ::= "{" [[ident {"," ident}] "=>"] lines "}"
  def funcLiteral: Parser[AST] = "{"~>opt(repsep(ident, ",")<~"=>")~lines<~"}"^^{
    case p~x => {
        p match{
          case Some(param) => Func(param.map(_.name), x)
          case None => x
        }
    }
  }
  //intLiteral ::= ["1"-"9"] {"0"-"9"}
  def intLiteral : Parser[AST] = """[1-9][0-9]*|0""".r^^{
    value => IntVal(value.toInt)}
  //stringLiteral ::= "\"" {"a-zA-Z0-9.."}  "\""
  def stringLiteral : Parser[AST] = "\""~>"""[a-zA-Z0-9:*/+\- ]*""".r<~"\""^^StringVal
  def ident :Parser[Ident] = """[A-Za-z_][a-zA-Z0-9]*""".r^?{
    case n if n != "if" && n!= "val" && n != "def" => n}^^Ident
  def assignment:Parser[Assignment] = "val"~>ident~"="~expr^^{
    case v~_~value => Assignment(v.name, value)
  }
  def funcDef:Parser[FuncDef] = "def"~>ident~opt("("~>repsep(ident, ",")<~")")~"="~expr^^{
    case v~params~_~proc => {
        val p = params match{
          case Some(pr) => pr
          case None => Nil
        }
        FuncDef(v.name, Func(p.map(_.name), proc))
    }
  }

  def parse(str:String) = parseAll(lines, str)
}
