# Monad pragmatic rules (not algebric)

* Method signature should not lie
* Method signature should be acurate
* Method signature should reflect non-determinism
* Monad helps to achieve referential transparency.
  * By returning computation, side-effect or non-determinism are passed to dependent
  * For example, if below method is invoked twice, both the time results are equal since it does represent computation, not values  
    * val readTemperaturer = (fileName) => IO(File.read(fileName)) => IO[A]  
* Return type should emphasize the computation in context, not actual values
  * Result may not be available due to failure, lack of result
  * There could be one or more result
  * Computation may never return result and still in computation mode
* No one can peek into the result from outside, rather they could pass the dependent computation to the monad. (Hollywood principle, Monad type would call dependent method)
* Monad also gives a way to invoke sequence dependent operation using flatMap or bind operation


# Implementation details
* Wrap the problem into case class and define map, and flatMap
* When one of the function dealts with multiple parameter, using curry treat them as if they are simple Function1 (A =>B)
  * val nFunc: A => B => C ~~~> val nFunc2: A => (B => C)
  * In above declare new type for (B=>C)  

F[A] ~~~> Functor of A
* It may return result of type A
* It shows lack of result
* It may return multiple values of type A
* It represents computation, not result
