object lamdbda {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val x = () => 3                                 //> x  : () => Int = lamdbda$$$Lambda$8/1641808846@13a57a3b

  val add : Int => Int = { x =>
    x + 3                                         //> add  : Int => Int = lamdbda$$$Lambda$9/972765878@6276ae34
  }

  val someInt =  () =>     3                      //> someInt  : () => Int = lamdbda$$$Lambda$10/2034688500@3c09711b
  someInt()                                       //> res0: Int = 3
  
  //val someInt :=> Int =

}