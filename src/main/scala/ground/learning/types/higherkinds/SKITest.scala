//package ground.learning.types.higherkinds
//
///**
// * @author Mohan
// */
//object SKITest {
//  case class Equals[A >: B <: B, B]()
//
//  Equals[Int, Int] // compiles fine
//  //  Equals[String, Int] // won't compile
//
//  /// Ic -> c
//  Equals[I#ap[c]#eval, c]
//
//  // Kcd -> c
//  Equals[K#ap[c]#ap[d]#eval, c]
//
//  // KKcde -> d
//  Equals[K#ap[K]#ap[c]#ap[d]#ap[e]#eval, d]
//
//  // SIIIc -> Ic
//  Equals[S#ap[I]#ap[I]#ap[I]#ap[c]#eval, c]
//
//  // SKKc -> Ic
//  Equals[S#ap[K]#ap[K]#ap[c]#eval, c]
//
//  // SIIKc -> KKc
//  Equals[S#ap[I]#ap[I]#ap[K]#ap[c]#eval, K#ap[K]#ap[c]#eval]
//
//  // SIKKc -> K(KK)c
//  Equals[S#ap[I]#ap[K]#ap[K]#ap[c]#eval, K#ap[K#ap[K]]#ap[c]#eval]
//
//  // SIKIc -> KIc
//  Equals[S#ap[I]#ap[K]#ap[I]#ap[c]#eval, K#ap[I]#ap[c]#eval]
//
//  // SKIc -> Ic
//  Equals[S#ap[K]#ap[I]#ap[c]#eval, c]
//
//  // R = S(K(SI))K  (reverse)
//  type R = S#ap[K#ap[S#ap[I]]]#ap[K]
//  Equals[R#ap[c]#ap[d]#eval, d#ap[c]#eval]
//
//  type b[a <: Term] = S#ap[K#ap[a]]#ap[S#ap[I]#ap[I]]
//
//  //Lets define some of the A_ns from above.
//
//  trait A0 extends Term {
//    type ap[x <: Term] = c
//    type eval = A0
//  }
//  trait A1 extends Term {
//    type ap[x <: Term] = x#ap[A0]#eval
//    type eval = A1
//  }
//  trait A2 extends Term {
//    type ap[x <: Term] = x#ap[A1]#eval
//    type eval = A2
//  }
//
//  //Now we can do iteration on the type level using a fixed point combinator:
//  // Single iteration
//  type NN1 = b[R]#ap[b[R]]#ap[A0]
//  Equals[NN1#eval, c]
//
//  // Double iteration
//  type NN2 = b[R]#ap[b[R]]#ap[A1]
//  Equals[NN2#eval, c]
//
//  // Triple iteration
//  type NN3 = b[R]#ap[b[R]]#ap[A2]
//  Equals[NN3#eval, c]
//
//  trait An extends Term {
//    type ap[x <: Term] = x#ap[An]#eval
//    type eval = An
//  }
//  // Infinite iteration: Smashes scalac's stack
//  type NNn = b[R]#ap[b[R]]#ap[An]
//  Equals[NNn#eval, c]
//}