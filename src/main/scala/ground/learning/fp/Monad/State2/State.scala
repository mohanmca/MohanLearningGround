package ground.learning.fp.Monad.State2
//http://patricknoir.blogspot.sg/2014/12/demistify-state-monad-with-scala-22.html
object StackApp extends App {

  type Stack[A] = List[A]
  type State[S, A] = S => (A, S)

  def push[A](a : A) : State[Stack[A], Unit] = stack => ((), a :: stack)

  def map[S, A, B](sa : State[S, A])(f : A => B) : State[S, B] = s => {
    val t = sa.apply(s)
    (f(t._1), t._2)
  }

  def flatMap[S, A, B](sa : State[S, A])(f : A => State[S, B]) : State[S, B] = s => {
    val t = sa.apply(s)
    f(t._1).apply(t._2)
  }

  def pop[A] : State[Stack[A], Option[A]] = {
    case a :: tail => (Some(a), tail)
    case Nil => (None, Nil)
  }

  //  def popPairs[A] : State[Stack[A], (Option[A], Option[A])] = stack => {
  //    val (opt1, stack1) = pop(stack)
  //    val (opt2, stack2) = pop(stack1)
  //    ((opt1, opt2), stack2)
  //  }
  //  
  def popPairs[A] : State[Stack[A], (Option[A], Option[A])] =    flatMap(pop[A])(opt1 => map(pop[A])(opt2 => (opt1, opt2)))
}