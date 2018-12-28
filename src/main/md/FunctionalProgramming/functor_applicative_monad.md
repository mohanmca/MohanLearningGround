# Functor, Mondad and applicative
* Functor   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;  fmap :: Functor f => (a -> b) -> f a -> f b
* Applicative   |&nbsp; (<*>) :: Applicative f => f (a -> b) -> f a -> f b
* Monad m &nbsp;&nbsp;|&nbsp; (>>=) :: Monad m => m a -> (a -> m b) -> m b
* Monoid m &nbsp;|&nbsp; (mappend) :: a -> a -> a ; mempty :: a
* *lower case variables represents generic type, not variable*

# Monoid laws
* (x <> y) <> z = x <> (y <> z) -- associativity
* mempty <> x = x               -- left identity
* x <> mempty = x               -- right identity


# Applicative laws
* pure id <*> v = v                            -- Identity
* pure f <*> pure x = pure (f x)               -- Homomorphism
* u <\*> pure y = pure ($ y) <\*> u              -- Interchange
* pure (.) <\*> u <\*> v <\*> w = u <\*> (v <\*> w) -- Composition
* * *pure as a way to inject values into the functor in a default,*
* * *The interchange law says that applying a morphism to a "pure" value pure y is the same as applying pure ($ y) to the morphism. 
* * *($ y) is the function that supplies y as argument to another function.*
* * *The composition law says that pure (.) composes morphisms similarly to how (.) composes functions: applying the composed morphism pure (.) <*> u <*> v to w gives the same result as applying u to the result of applying v to w*

# Monad laws
* m >>= return     =  m                        -- right unit
* return x >>= f   =  f x                      -- left unit
* (m >>= f) >>= g  =  m >>= (\x -> f x >>= g)  -- associativity


# Applicative usage

```scala
object Applicative extends App {
  case class User(userFirstName: String, userLastName: String, userEmail: String)

  val userFactory: (String, String, String) => User = (a: String, b: String, c: String) => User(a, b, c)

  val dynamicUserFactory: (Map[String, String]) => Option[User] = { userprops =>
    for {
      userFirstName <- userprops.get("userFirstName")
      userLastName <- userprops.get("userLastName")
      userEmail <- userprops.get("userEmail")
    } yield userFactory(userFirstName, userLastName, userEmail)
  }

  /**
   * Now using applicative, we are able to acheive without monad
   */
  val applicativeUserFactory: (Map[String, String]) => Option[User] = { props =>
    //Here Apply method takes Type parameter, it should matching with Functor type of the Applicative arguments
    val applicativeFactory = Apply[Option].lift3(userFactory)
    applicativeFactory(props.get("userFirstName"), props.get("userFirstName"), props.get("userFirstName"))

  }

}
```


# References
* [Application Practical](https://pbrisbin.com/posts/applicative_functors/)