| Typeclass	| Method |	From	| Given	| To |
| ------------- |:-------------:|:-------------:| -----:|:-------------:| 
|InvariantFunctor	| xmap	| F[A]	| A => B, B => A |	F[B] |
|Contravariant	 |contramap | F[A]	| B => A  |	F[B] |
|Functor	| map	| F[A]	| A => B	| F[B] |
|Apply	| ap / <*>	| F[A]	| F[A => B]	| F[B] |
| | 	apply2 | 	F[A], F[B]	| 	(A, B) => C	| 	F[C] |
|Divide	 | divide2	 | F[A], F[B]	 | C => (A, B) | 	F[C] |
|Bind	 | bind / >>=	 | F[A]	 | A => F[B]	 | F[B] |
 | | 	join	 | F[F[A]]	 |  |  	F[A] |
|Cobind	 | cobind	 | F[A]	 | F[A] => B	 | F[B] |
|  | 	cojoin	 | F[A]	 |  |  	F[F[A]] |
|Applicative	 | point | 	A | 	  | 	F[A] |
|Comonad	 | copoint	 | F[A]	 |  |  	A |
|Semigroup	 | append | 	A, A	 |   | 	A |
|Plus	 | plus / <+>	 | F[A], F[A]	  |  | 	F[A] |
|MonadPlus	 | withFilter | 	F[A]	 | A => Boolean | 	F[A] |
|Align	 | align	 | F[A], F[B]	 |  |  	F[A \&/ B] |
| 	| merge	 | F[A], F[A]	  |  | 	F[A] |
|Zip	 | zip	 | F[A], F[B]	  | 	 | F[(A, B)] |
|Unzip	 | unzip	 | F(A, B)	 |  	 | (F[A], F[B]) |
|Cozip	 | cozip	 | F[A \/ B]	 |   | 	F[A] \/ F[B] |
|Foldable	 | foldMap	 | F[A]	 | A => B	 | B |
 | | 	foldMapM	 | F[A]	 | A => G[B] | 	G[B] |
|Traverse	 | traverse	F[A]	A => G[B]	G[F[B]] |
| | 	sequence	 | F[G[A]]	 |  	G[F[A]] |
|Equal | 	equal /  | ===	A, A	 |  	 | Boolean |
|Show | 	shows	 | A	 |  	String |
|Bifunctor | 	bimap	 | F[A, B]	 | A => C, B => D	 | F[C, D] |
| | 	leftMap	 | F[A, B]	 | A => C	 | F[C, B] |
| | 	rightMap | 	F[A, B]	 | B => C	 | F[A, C] |
|Bifoldable	 | bifoldMap	 | F[A, B]	 | A => C, B => C | C |
|(with MonadPlus) | 	separate	 | F[G[A, B]]	 |  |  	(F[A], F[B]) |
|Bitraverse	 | bitraverse	 | F[A, B]	 | A => G[C], B => G[D]	 | G[F[C, D]] |
| | 	bisequence	 | F[G[A], G[B]] |  | G[F[A, B]] |

* [scalaz typeclass](http://arosien.github.io/scalaz-cheatsheets/typeclasses.pdf)