Traits

Reference :

1) http://joelabrahamsson.com/learning-scala-part-seven-traits/

2) How trait implemented in Scala - http://stackoverflow.com/questions/7637752/using-scala-traits-with-implemented-methods-in-java

3) Self type, The compiler will check that any class in a hierarchy including SpellChecker is or extends RandomAccessSeq[char], so SpellChecker can now use the fields or methods of RandomAccessSeq[char]. (Ref : http://markthomas.info/blog/?p=92)

    trait SpellChecker { self: RandomAccessSeq[char] =>
      ...
    }

4) One can use type parameters within self-types:
    trait A[Self] {this: Self => } is legal,
   But we can't extend trat from type Parameter.
    trait A[Self] extends Self isn't.

5) http://stackoverflow.com/questions/1990948/what-is-the-difference-between-scala-self-types-and-trait-subclasses

6) self type doesn't conform to --- Error
   When a trait has self type, Every type that extending self type should conform to the parent signature.

7) http://stackoverflow.com/questions/7378901/scala-illegal-inheritance-self-type-y-does-not-conform-to-xs-selftype-self

    Self type is more akin to generic constraint than to inheritance. When you have class C[A <: B], the constraint must be repeated all along in subclasses : class D[A <: B] extends C[A]. The constraint must be repeated until it is satisfied, that is until you have chosen an actual parameter type which indeed satisfy <: B. Same for the self type. Writing self: A => does not makes your type extends A. It ensures that it will ultimately have to be mixed in with A before it is actually instantiated.
