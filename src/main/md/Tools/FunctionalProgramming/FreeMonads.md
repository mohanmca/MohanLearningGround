## [YOW! Lambda Jam 2014 - Run free with the monads: Free Monads for fun and profit - Ken Scrambler] (https://www.youtube.com/watch?v=fU8eKoakd6o)

## Why free monad
* Sepeartion of concerns
* Impreative and side-effect code
* Convert decisions as data


def regularAI() = {
    val enemy = findNearesTank()
    val angle = angleTo(enemy)
    aimToward(angle)
    fire()
}

## Problems
* How to compose side-effects
* Linking effectful inputs (sequencing)
* Gradual execution
  * Internally may need to manage state
  * State transition should be properly managed
  * May force to adopt custom scripting langauge
    * Ugly & hard, won't compose
* We need very close to imperative style, but should work purely functional way
* Monads to the rescue (for all the above problems)

## What is "Free" data structure
* Free is a data structure
* Represents tree of computatons
* Free[F[_], A]
  * Suspend(F[Free[F, A]]) 
  * Return(A)
* We can assume Suspend and Return like 
  * When we map Suspend, it produces Free[F[_]] such as ```Suspend(F[Free[F, A]]).map(fn) =>Free[F[_], A]```
  * Free[F[_], A] may turn to one of following type 
    * Suspend(F[Free[F, A]]) or Free[F[_],A]
* liftF : F[A] => Free[F,A]

## Why free Monad
* Sequential computations
* Elegant imperative-style syntax
* Lazy evaluation

## What do we need?
* Design our DSL ADT (Set of API specific to problem domain can be considered like DSL) 
* If we convert DSL ADT data-structure to a functor, then we get a Free Monad
* If your functor also happens to be a Monad, it is not that thing, it is a new thing




Steps
* 1 - Design DSL,  Create ADT to represent DSL (sealed trait and case class and objects)
* 2 - Create a type hole in the above DSL (to map over to other types)
* 3 - Continuation of statements -  3-a) void/unit is replaced with next continuation
* 3 - Continuation of expressions - 3-b) return value also replaced as function (rather returning, accept successive function that requires result of current computation) (hollywood principle)
  *        instead of ```def findNearestTank(): Tank```, write ```case class FindNearestTank(fn: Tank => Next) extends SuperTrait```
  *        instead of ```def angleTo(pos: Postion): Angle```, write ```case class AngleTo(pos: Position, fn: Angle => Next) extends SuperTrait```
* 4 Make above DSL a functor, implement fmap method for the super trait
  *  ```def map[B](f: Next => N): AIMOVE[B] = this match { case Fire(next) => {}. case AngleTo(x, y) => {} ...}```
  * above map method should cover entire ADT tree, For example if this is Option, map should cover both None, Some 
* 5 - 5a) Lifting Statements ``def aimToward(a: Angle) : AI[Unit] = Free.liftF(AimToward(a, ()))  
* 5 - 5b) Lifting expressions ``def aimToward(a: Angle) : AI[Unit] = Free.liftF(AimToward(a, ()))  
* Write interpreter that can covert F[A] into G[A] 

## References
[Free Monad Example] - (scalaz_examples\src\main\scala\scalaz\example\FreeUsage.scala)
[Slides] - (https://www.slideshare.net/kenbot/running-free-with-the-monads)
[Code] - (https://github.com/kenbot/free)
