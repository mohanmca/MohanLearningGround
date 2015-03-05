package ground.worksheet

import scala.reflect.runtime.universe._
import scala.reflect.runtime.universe._
import org.apache.commons.lang.builder.ToStringBuilder

object def_Test {

  case class Reader[A, B](run: A => B) {
    def apply(x: A): B = run(x)
    def map[C](g: B => C): A => C = run andThen g
    def flatMap[C](g: B => Reader[A, C]): Reader[A, C] = Reader(x => map(g)(x)(x))
    
  }

}
