## What is Covariant relationship?
1. Given some ```class Cov[+T]``, then if A is a subtype of B, ```Cov[A] is a subtype of Cov[B]```.
2. This allows us to make very useful and intuitive subtyping relationships using generics.
3. Covariant relationship is useful for reading

## Give an example for covariance?
1. printAnimalNames - would work for both List[Cat] and List[Dog]
```scala
def printAnimalNames(animals: List[Animal]): Unit =
  animals.foreach {
    animal => println(animal.name)
  }

val cats: List[Cat] = List(Cat("Whiskers"), Cat("Tom"))
val dogs: List[Dog] = List(Dog("Fido"), Dog("Rex"))

printAnimalNames(cats)
printAnimalNames(dogs
```

## What is ContraVariance?
1. Given some class Contra[-T], then if A is a subtype of B, Contra[B] is a subtype of Contra[A].
2. Serializer is an example for Contravaiance usage

## Give an example for contravariance
```scala
   abstract class Serializer[-A] {
  def serialize(a: A): String
}

val animalSerializer: Serializer[Animal] = new Serializer[Animal] {
  def serialize(animal: Animal): String = s"""{ "name": "${animal.name}" }"""
}
val catSerializer: Serializer[Cat] = animalSerializer
catSerializer.serialize(Cat("Felix"))
```

## Example for Invariant and Variant usage
1. scala.collection.mutable.ListBuffer is invariant and mutable
2. scala.collection.immutable.List is covariant and immutable


## What is abstract type members?

1. Abstract types, such as traits and abstract classes, can in turn have abstract type members.
2. This means that the concrete implementations define the actual types.

## Give an example for abstract type member using Buffer?
```scala
abstract class SeqBuffer extends Buffer {
  type U
  type T <: Seq[U]
  def length = element.length
}
abstract class IntSeqBuffer extends SeqBuffer {
  type U = Int
}

def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer =
  new IntSeqBuffer {
    type T = List[U]
    val element = List(elem1, elem2)
  }
val buf = newIntSeqBuf(7, 8)
println("length = " + buf.length)
println("content = " + buf.element)
```

## What is upper type bound?
1.  An upper type bound T <: A declares that type variable T refers to a subtype of type A.
2.  Upper type bound limits a type to a subtype of another type.
3.  It ensure every-type is minimum of that upper bound

## Give an example for upper bound type constraint usage

```scala
abstract class Animal {
 def name: String
}

abstract class Pet extends Animal {}

class Cat extends Pet {
  override def name: String = "Cat"
}

class Dog extends Pet {
  override def name: String = "Dog"
}

class Lion extends Animal {
  override def name: String = "Lion"
}

class PetContainer[P <: Pet](p: P) {
  def pet: P = p
}

val dogContainer = new PetContainer[Dog](new Dog)
val catContainer = new PetContainer[Cat](new Cat)
// this would not compile
val lionContainer = new PetContainer[Lion](new Lion)
```

## What is lower type bound?
1.  An lower type bound B >: A declares that type variable B refers to a supertype of type A.
2.  new type parameter B has A as a lower type bound.
3.  B itself could be abstract-type
4.  In most cases, A will be the type parameter of the class and B will be the type parameter of a method.


## Give an example of "functions are contravariant in their parameter types and covariant in their result types."

```scala
trait List[+A] {
  def prepend(elem: A): NonEmptyList[A] = NonEmptyList(elem, this)
}

case class NonEmptyList[+A](head: A, tail: List[A]) extends List[A]

object Nil extends List[Nothing]
--
trait List[+A] {
  def prepend[B >: A](elem: B): NonEmptyList[B] = NonEmptyList(elem, this)
}

case class NonEmptyList[+A](head: A, tail: List[A]) extends List[A]

object Nil extends List[Nothing]
```

## What is the alternative for AbstractTypeMember

1. Abstract type members can be converted into type parameters of classes and vice versa

## Can you show an example of converting ATM into TM

```scala
abstract class Buffer[+T] {
  val element: T
}
abstract class SeqBuffer[U, +T <: Seq[U]] extends Buffer[T] {
  def length = element.length
}

def newIntSeqBuf(e1: Int, e2: Int): SeqBuffer[Int, Seq[Int]] =
  new SeqBuffer[Int, List[Int]] {
    val element = List(e1, e2)
  }

val buf = newIntSeqBuf(7, 8)
println("length = " + buf.length)
println("content = " + buf.element)
```
## Reference
1. [Lower bounds](https://docs.scala-lang.org/tour/lower-type-bounds.html)
