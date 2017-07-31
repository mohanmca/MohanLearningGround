package ground.learning.fp.FreeMonad

object Free {
  def point[M[_], A](a : A) : M[A] = ???
  def flatMap[M[_], A, B](fa : M[A])(f : A => M[B]) : M[B] = ???
}