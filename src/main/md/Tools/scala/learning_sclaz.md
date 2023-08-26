# Scalaz

* Confusing without training
* Method names are all stupid and mathy, becuase we expect to use without effort

# How to begin
* Assume - import scalaz._; import Scalaz._
* Start with following code reading
* https://github.com/scalaz/scalaz/blob/series/6.0.x/core/src/main/scala/scalaz/Identity.scala
* https://github.com/scalaz/scalaz/blob/series/6.0.x/core/src/main/scala/scalaz/MA.scala
* https://github.com/scalaz/scalaz/blob/series/6.0.x/core/src/main/scala/scalaz/Each.scala

# Before you begin
* Should be familiar with typeclasses
* typeclasses are orthoganal to types
* They describe common behvaiour for bunch of types
* They can inject behaviour on to a existing types
* Adapt existing types to our structures
* Scalaz provides MA for many existing types using implicit
* MohanLearningGround\src\main\scala\ground\learning\monoid\Monoid.scala
* Understand that by aligning with time tested Mathematical structure, It begets more operations, properties and processes. Example: Monoid begets monoids 


# Math
* Endmorphism is function from type to itself : fn: A=>A is endomorphism

# Invoke side effect for invoking each element of a container - Each[A]
```Scala

trait Each[-E[_]] {
    def each[A](e: E[A], f: A=>Unit): Unit
}

implicit val OptionEach: Each[Option] = new Each[Option] {
    def each[A](e: Option[A], f: A=>Unit): Unit = e.foreach f
}

```
 
## Monoid - Monoid[A]
* A Set
* With an associative operation (Ex: Integer (+,0) Add or Integer (*,1) Multiplication)
* And an identity under that operation
* Monoids are semigroups with identity.

## Monoid begets monoid
* fun1 is monoid if result type is monoid
* Natural monoid for boolean, under following law
* Boolean is monoid, if identity=true for conjunction is mappend (^ - AND) - |+| - Boolean disjunction
* Boolean is monoid, if identity=false for disjunction is mappend (v - OR) - |+| - Boolean disjunction
* By appending |^| - can create BooleanConjuction operation


##  Define monoid is scalaz
* To define new monoid for custom type, define mzero[type] and semiGroup[type]
* Monoids for Float and Double break the laws due to how they are represented, hence they are not monoids 
* Create monoid for Currency
```Scala
implicit def MoneyZero(implicy ccy: Currency): Zero[Money] = zero(Money.zero(ccy))
implicit val ccySemiGroup: SemiGroup[Money] =  semiGroup[Money] (_ add _)
```

```Scala
1 |+| 2
1.2 |+| 2.3
```

## Monoid begets monoid
* Option[A] is monoid iff A is monoid
* Tuple (A, B, C, D) is monoid iff A, B, C, D is monoid
* A => B is monoid iff B is monoid
* Map[A, B] is monoid iff B is monoid
* A => A is monoid under function composition
* val books: List[Map[String, Int]] = books.foldLeft(Map.empty)(|+|).asMA.sum //Sum of all maps
* Boolean Conjunction - |^|
*  

```Scala
none[Int] |+| Some(4) 
none[Int] |+| none[Int]
(1, "abc", 1) |+| (2, "cbd", 4)
Map("A" -> 3, "C" -> 4)  |+| Map("A" -> 4, "B" -> 9, "C" -> 8) //> Map(A -> 7, B -> 9, C ->12)

val posn = (sym -> (posn.get(trade.sym).getOrElse(0) + trade.qty)
val posn = (sym -> (~posn.get(trade.sym) |+| trade.qty) //Alternative way
                                                  
```


```Scala
type Filter = TradinPosition => BooleanConjunction
type londonFilter = (t: TradinPosition) => (t.sym.id.endsWith ".L")
type nyFilter = (t: TradinPosition) => (t.sym.id.endsWith ".NY")
type bigTrade = (t: TradinPosition) => (t.qty > 1000)
type inLnOrNy = londonFilter |+| nyFilter
type inLnANDBigTrade = (londonFilter |^|) |+| (bigTrade |^|)
val reverseFilter: A => Boolean = EndoTo((_:Filter).neg)
```
       
## Exercise
* combined(a: Map[A,B], b: Map[A,B]) => Map[A, (B,B)]
*  Map[A, (B,B).asMA.sum should produce MAP[A,B] (You added two maps)


* [Practical Scalaz: making your life easier the hard way] (https://skillsmatter.com/skillscasts/2518-practical-scalaz-2518)
* [Monad Transformers in Scalamachine & Scaliak] (https://www.youtube.com/watch?v=S_l95GIDCM0)
* [Scalaz - The Good Parts] (https://www.youtube.com/watch?v=jPdHQZnF56A)
* [Scalaz State Monad] (https://www.youtube.com/watch?v=Jg3Uv_YWJqI)
* [Scalaz for the Rest of Us at Yelp] (https://www.youtube.com/watch?v=kcfIH3GYXMI)
* [void-main-args] (http://voidmainargs.blogspot.sg/2012/02/having-fun-with-monoid-in-scalaz-seven.html)