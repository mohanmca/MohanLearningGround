## Foldable
* Definition: Foldable abstracts over containers which can be “folded” into a summary value.  To make a Foldable instance you only need to implement one method: your choice of foldMap or foldr.

```Haskell
class Foldable t where
  foldr   :: (a -> b -> b) -> b -> t a -> b
  fold    :: Monoid m => t m -> m
  foldMap :: Monoid m => (a -> m) -> t a -> m
```

```Scala
trait Foldable[F[_]] {

  def foldMap[A,B](fa: F[A])(f: A => B): B
  
   /** Map each element of the structure to a [[scalaz.Monoid]], and combine the results. */
  def foldMap[A,B](fa: F[A])(f: A => B)(implicit F: Monoid[B]): B
}  
```  

* Instances: Map, Set, Tree, and Sequence


## Functor

* Definition:  A Functor represents a “container” of some sort, along with the ability to apply a function uniformly to every element in the container. It can also be considered like boxed type (Java boxed type lik Integer). Important API signature is   ```fmap :: (a -> b) -> f a -> f b```

Functor can also be considered as "Function Lifter", such that functor takes `a -> b` function and returns a function `f a -> f b`. This is called lifting a function.

* Known instances List, Option, Either, Function1

```Haskell
class Functor f where
  fmap :: (a -> b) -> f a -> f b
  (<$) :: a -> f b -> f a
  (<$) = fmap . const
```


## Functor laws
```Haskell
fmap id = id  -- fmap preserves identity
fmap (g . h) = (fmap g) . (fmap h)   -- fmap distributes over composition
```

* Any Functor instance satisfying the first law (fmap id = id) will automatically satisfy the second law as well.


## Applicative

 * Definition: Functor doesn’t allow to apply a function which is itself in a context to a value in a context. Applicative gives us function  "apply" (<*>, "app", or "splat") to do that. It encapsulates certain sorts of “effectful” computations in a functionally pure way, and encourages an “applicative” programming style. It also provides a method, pure, for embedding values in a default, “effect free” context. 

```Haskell
class Functor f => Applicative f where
  pure  :: a -> f a
  (<*>) :: f (a -> b) -> f a -> f b
```


## Applicative laws
```Haskell
pure id <*> v = v  -- The identity law:
pure f <*> pure x = pure (f x)   -- Homomorphism
u <*> pure y = pure ($ y) <*> u -- Interchange
u <*> (v <*> w) = pure (.) <*> u <*> v <*> w -- Composition
fmap g x = pure g <*> x --Not law, relationship with functor and applicative
```

* Uses - Applicative functors allow you to take a "normal" function (taking non-functorial arguments) use it to operate on several values that are in functor contexts. As a corollary, this gives you effectful programming without monads. Applicative functors are useful when you need sequencing of actions, but don't need to name any intermediate results

* A common example is parsing, where you need to run a number of actions that read parts of a data structure in order, then glue all the results together. This is like a general form of function composition: where you can think of a, b and so on as the arbitrary actions to run, and f as the functor to apply to the result.

```f a b c d``` => ```f <$> a <*> b <*> c <*> d```

* The only thing a functor can do is to alter the end result of a computation via some pure function a -> b, it could have effects but can't sequence effects. Whereas application function (*>) :: f a -> f b -> f b, chains two computations and discarding the end result from the first one. it's the ability to chain computations which is the minimum requirement for effects such as mutable state in computations.

## Traversable

* Definition: Traversable is a Foldable Functor, traverse function is unique for Traversable. Traversable as a generalization of Functor with traverse as "effectful fmap". The map (traverse) applications in the Traversable instance take place within an Applicative context.  Doing two traversals in sequence can be collapsed to a single traversal.

```Haskell
class (Functor t, Foldable t) => Traversable t where
  traverse  :: Applicative f => (a -> f b) -> t a -> f (t b)
  sequenceA :: Applicative f => t (f a) -> f (t a)
```

```Scala @ Future.scala
def traverse[A, B](in: List[A])(fn: (A) ⇒ Future[B]): Future[List[B]]
```


* Traversable uses, turn a tree of lists into a list of trees. Travers functor and covert into another functor.

Notably, Set is not Traversable
* Known instances of traversable are List, ZipList, Maybe, ((,) e), Sum, Product, Either e, Map, Tree, and Sequence. Notably, Set is not Traversable, although it is Foldable.


## Traversable laws
```Haskell
  traverse Identity = Identity
  traverse (Compose . fmap g . f) = Compose . fmap (traverse g) . traverse f --Doing two traversals in sequence can be collapsed to a single traversal
```

## Category

* Definition: Category := "identity + function composition", Category generalizes the notion of function composition to general “morphisms”.

```Haskell
-- Category with the infix function type constructor (->)
class Category arr where
  id  :: a `arr` a
  (.) :: (b `arr` c) -> (a `arr` b) -> (a `arr` c)
 
-- Category with a normal (prefix) type constructor
class Category cat where
  id  :: cat a a
  (.) :: cat b c -> cat a b -> cat a c
```

* Category instances are of type  * -> * -> *, i.e, which takes two type arguments.

## Monad

* Definition: A Monad is just a monoid in the category of endofunctors.

```Haskell
  class Monad m where
    return :: a -> m a
    (>>=)  :: m a -> (a -> m b) -> m b
```


## Arrow

* Definition:  Arrow class represents another abstraction of computation, In Monad and Applicative, types only reflect on their output, the type of an Arrow computation reflects both its input and output. Arrows generalize functions: if arr is an instance of Arrow, a value of type "b `arr` c" can be thought of as a computation which takes values of type b as input, and produces values of type c as output. 

```Haskell
class Category arr => Arrow arr where
  arr :: (b -> c) -> (b `arr` c)
  first :: (b `arr` c) -> ((b, d) `arr` (c, d))
```


* Conjunction: The condition of being joined; Compound statement that uses the word AND.
* Disjunction: The condition of being disjoined; separation, disunion 'either/or'.
* Homomorphism: The most important functions between two groups are those that “preserve” the group operations, and they are called homomorphisms. (Homo=same)
  A function f : G → H between two groups is a homomorphism when f(x*y) = f(x)*f(y) for all x and y in G.
  * Here the multiplication in x*y is in G and the multiplication in f(x)*f(y) is in H, so a homomorphism from G to H is a function that transforms the operation in G to the operation in H.
  * Homemorphic examples : Examples: c(x + y) = cx + cy, |xy| = |x||y|, (xy)^2 = x^2*y^2
* Isomorphisms: Isomorphisms are formalized using category theory. A morphism f : X → Y in a category is an isomorphism if it admits a two-sided inverse, meaning that there is another morphism g : Y → X in that category such that gf = 1X and fg = 1Y, where 1X and 1Y are the identity morphisms of X and Y, respectively.
  * An isomorphism is a pair of morphisms (i.e. functions), f and g, such that: f . g = id and g . f = id
* Adjoint functors: Adjunction is a possible relationship between two functors. A pair of adjoint functors from C to D and from D to C is what is needed to make two categories C and D compatible in their objects and morphisms.
  * An adjunction between categories C and D is a pair of functors, F: D => C, G: C => D
  * The functor F is called a left adjoint functor, while G is called a right adjoint functor. “F is left adjoint to G” (or equivalently, “G is right adjoint to F”)
  * Endofunctors : An endofunctor is a functor from one category back to the same category. It maps objects of the category to objects of the same category. The simplest example is the identity functor which maps every object inside a category back to itself; more interesting examples map objects to other objects in the same category.  


### Reference
* [Second functor law is redundant](https://github.com/quchen/articles/blob/master/second_functor_law.md)
* [McBride and Paterson. The title of their classic paper, Applicative Programming with Effects](http://www.soi.city.ac.uk/~ross/papers/Applicative.html)
* [The Essence of the Iterator Pattern](http://www.comlab.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf)
* [A taste of category theory for computer scientists](http://repository.cmu.edu/cgi/viewcontent.cgi?article=2846&context=compsci)

