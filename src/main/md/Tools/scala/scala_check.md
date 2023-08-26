# ScalaCheck Property-based software testing
* Specifications and tests and ScalaCheck vs Junit
* A specification is a definition of a program's abstract behavior, provides complete picture and informal
* Tests are concrete examples of how a program should behave in particular situations and it is formal.
* In TDD and BDD,  tests as specification is to make your specification more test-centered. It is also known as executable specification.
* Property-based testing goes in the opposite direction by making your tests more specification-like
* Every system has structure and behaviour. In BDD we test behaviour of the system, Test cases are coarse grained level or use-case level.

* Sample scala check
```Scala
    /** Dependency -- "org.scalacheck" %% "scalacheck" % "1.13.5" **/
    import org.scalacheck.Properties
    import org.scalacheck.Prop.forAll
  
    object MathProps extends Properties("Math") {
  
      property("max") = forAll { (x: Int, y: Int) =>
        val z = java.lang.Math.max(x, y)
        (z == x || z == y) && (z >= x && z >= y)
      }
  
    }
``` 
* Properties are sometimes called parameterized tests
* Benefits of properties based testing
  * Increased - Test coverage due to randomized input
  * Specification completeness - due to abstract test
  * Maintenance - less code to maintain and refactor
  * Test case simplification  - Finding the smallest set for which test case fails
* When property based test case fails.
  * Handle it in the implementation code, and repercussion may impact test case, hence handle it in testcase
  * Handle the exception in the property-based test case
  * Ignore the particular case, by filtering out possibility in the test-case
* ScalaCheck  == Properties ++ Generators
* Properties
  * A single property in ScalaCheck is the smallest testable unit. It is represented by an instance of the org.scalacheck.Prop class.   
  * org.scalacheck.Properties is a class with lot of operators to compose multiple Property. 
  * org.scalacheck.Properties.property method is used to add named properties to the set.
* Generator
  * Generator can generate for any type, Implicit used instead of reflection to find the type T.
  ```Scala
    class Gen[+T] {
      def apply(prms: Gen.Params): Option[T]
    }
  ```
  * Generator might sometime fail, That is why return type is Option[T]
  * org.scalacheck.Gen has function to accept function that expects Generated value, normally we don't need to deal with Generator including custom generator.
  * Ensure instance of Arbitrary[T]  is in path would be enough to supply T for any testcase

* Sample custom generator.
```Scala
  import org.scalacheck.Gen.{choose, oneOf}
  
  case class Person (firstName: String,    lastName: String,    age: Int) {    def isTeenager = age >= 13 && age <= 19  }
  
  val genPerson = for {
    firstName <- oneOf("Alan", "Ada", "Alonzo")
    lastName <- oneOf("Lovelace", "Turing", "Church")
    age <- choose(1,100)
  } yield Person(firstName, lastName, age)

  import org.scalacheck.Arbitrary  
  implicit val arbPerson = Arbitrary(genPerson)
  val propPerson = forAll { p: Person =>  p.isTeenager == (p.age >= 13 && p.age <= 19)}
```


# Designing properties
* It can be hard to immediately come up with a ScalaCheck property that completely describes all behavioral aspects that you want to specify.
* Don't be afraid of leaving a property incomplete if it starts to get too involved.
* Strive to make your properties straightforward, and try to make them as different as possible from the tested code, even if it means you lose specification completeness.
* Relation properties
  * A special form of incomplete properties are relation properties. 
  * Instead of specifying a unit of code against one input instance at a time, you can use two or more test cases in the same property
  * Base your specification on the relation between the inputs.
* Reference implementations
  * Example: Write property for a specialized integer map behaves exactly like the generic hash map.
  * Can be used only when direct specifications are hard to define or when it is very simple to define a correct reference implementation that can be used to test a more complex one
  * Can be used in an iterative development process, testing new and improved implementations against old and stable ones
* Methods that has pre-condition (Restricting TestCases)
  * Example don't invoke with null, and class should be in certain state (precondition)
  * This is equivalent to saying that if the precondition is false, then the method behaves correctly no matter what it does.
  * Generally, it is never a bad idea to write out the preconditions in your properties, since it also works as documentation of the code under test.
  * When ScalaCheck tests property, it skips over the cases where the precondition is not fulfilled, and regards them as discarded tests.
  * ScalaCheck's test case simplification feature can cause troubles when methods with preconditions are tested. 
    * In such cases, you need to specify the complete precondition in your property even though you are using custom generators for the method input
* Round-Trip Properties
  * Encoder-Decoder  :: decode(encode(x)) == x
  * Serializer-Deserializer  :: deserialize(serialize(x)) == x
  * xs: List[Int] => xs.reverse.reverse == xs
  * parse(prettyPrint(ast)) == ast
* Constructing Optimal output
 * A variation on the round-trip theme is a case when it is easy to synthesize an optimal output for the method you're testing
 * But not equally trivial to check whether a given output is optimal.
 * Produce some output and calculate input, and test method with calculated input.
 
 # Properties in Details
  * We can label property and test arguments using String or Symbol. We need to use Operator |:. (| is always facing label, : Always facing property) or :|
  * Instead of label feature, we could also use ScalaTest PropertyChecks style, it would automatically give clear message when test fails. IDE friendly line number also presented.
  * Smart equivalence checks using ?= or =?. When boolean expression fails, print both side values.
  * The value closest to the question mark will be presented as the actual value and the value closest to the equal sign will be presented as the expected one.
  * Collecting test statistics using classify (and nested classify).
  * Prop.forAll, Prop.throws, Prop.exists, Prop.all and Prop.atLeastOne. Alternatively && and || could also be used instead of forAll and atLeastOne.
  * Know about usage of  org.scalacheck.Prop.{    undecided, proved, passed, exception, falsified  }
  * Sample
  ```scala
        import org.scalacheck.Prop.{AnyOperators, forAll, classify}  
  
        forAll(choose(0,100) :| "pos", choose(-10,0) :| "neg")(_ * _ < 0)
        forAll('prime |: oneOf(2,3,5,7)) { prime =>    prime % 2 != 0  }
  
          val propInterleave =
          forAll { (xs: List[Int], ys: List[Int]) =>
            val res = interleave(xs,ys)
            val is = (0 to math.min(xs.length, ys.length)-1).toList
            all(
              "length" |:
                xs.length+ys.length =? res.length,
              "zip xs" |:
                xs =? is.map(i => res(2*i)) ++ res.drop(2*ys.length),
              "zip ys" |:
                ys =? is.map(i => res(2*i+1)) ++ res.drop(2*xs.length)
            )
          }
  
        val p = forAll { n:Int =>
          classify(n % 2 == 0, "even", "odd") {
            classify(n < 0, "neg", "pos") {
              classify(math.abs(n) > 50, "large") {
                n+n == 2*n
              }
            }
          }
        }
  
          val propSorted = forAll { xs: List[Int] =>
          val r = xs.sorted
        
          val isSorted = r.indices.tail.forall(i => r(i) >= r(i-1))
          val containsAll = xs.forall(r.contains)
          val correctSize = xs.size == r.size
        
          isSorted    :| "sorted" &&
          containsAll :| "all elements" &&
          correctSize :| "size"
        }
  ```
# Generators in Details
* Give up and discarded
  * One way of creating a new generator is to attach a filter to an existing one, by using the Gen.suchThat method
  * If you use this generator in a property, each filtered generator value results in a **discarded property** evaluation.
  * If you add a filter that is too narrow, too many values will be discarded and ScalaCheck will **give up on checking the property**. In those cases using  Gen.retryUntil with caution.
* Size and resize for custom generators
  * Gen.sized and Gen.resize When ScalaCheck produces data with a generator, it tells the generator what data size it wants. 
  * Size lets you test a property with increasingly larger data sets. A generator may interpret the data size parameter freely, or even ignore it if it doesn't make sense to use it.
  * When you implement your own generator, you can use the size variable by utilizing the Gen.sized or Gen.resize methods. 
  * The Gen.size method takes an anonymous function as its only parameter, and this function in turn takes an integer value as its parameter.
* Gen.const, Gen.fail
* Generators could be composed to create Higher-order generators or recursive Generators
* Higher-order generators
  * Gen.sequence
  * Gen.frequency
```Scala
  import org.scalacheck.Gen.{choose, negNum, posNum} /** show few more generatos **/
  /** AlphaChar, String Generator **/
  val genString = for {
    c1 <- Gen.numChar
    c2 <- Gen.alphaUpperChar
    c3 <- Gen.alphaLowerChar
    c4 <- Gen.alphaChar
    c5 <- Gen.alphaNumChar
  } yield List(c1,c2,c3,c4,c5).mkString  

    alpha <- Gen.alphaStr
    num <- Gen.numStr
    id <- Gen.identifier
``` 

* Some reusable template
```Scala
    Gen.sequence(List(choose(1,10), const(20), const(30)))
    Gen.frequency((1, oddNumberGen),    (2, evenNumberGen),    (4, 0)  )
    val genNotZero = Gen.oneOf(choose(-10,-1), choose(1,10))
    val users: List[User] = db.getUsers
    val genUsers = Gen.oneOf(users)
    val genIntList = Gen.listOf(choose(0,10))
    val genEightBytes = Gen.listOfN(8, arbitrary[Byte])
    val genIntList =    Gen.containerOf[List,Int](choose(0,10))
    val oddInt = arbitrary[Int] suchThat (_ % 2 != 0) (Potential discarded property)
    val oddInt = arbitrary[Int] retryUntil (_ % 2 != 0) (Discarded property will be zero)
    val numbers = Gen.someOf(List(1,2,3,4))
    val twoStrings =  Gen.pick(2, List("red", "blue", "green", "pink"))


  def genList[T](genElem: Gen[T]): Gen[List[T]] = {
    sized { sz: Int =>
      for {
        listSize <- Gen.choose(0, sz)
        list <- Gen.listOfN(listSize, genElem)
      } yield list
    }
  }
```
# Shrink framework
* Shrink allows to find a case that fails for 100s parameters into a case `as simple as` possible.
* Shrink framework would try to narrow down failed cases.
* Shrink framework use Stream type to evaluate all elements lazily, which makes the process of simplifying test cases more performant.
* Sometime shrink framework would try to minimize and would end-up other problem.
* The shrink method takes a value and returns a stream of simpler variants of that value.
  * You are free to implement the shrink method in a way that makes sense for your particular type. 
  * Shrink method must converge towards an empty stream.
  * if you run shrink on elements in its output, the original value is not allowed to re-appear.
  * org.scalacheck.Shrink.shrink(10).check => 0, 5, -5, 8, -8, 9, -9, empty  
```Scala
  trait Shrink[T] {
    def shrink(x: T): scala.collection.immutable.Stream[T]
  }
```
# Runtime considerations
* Prefer using PropertyChecks style when using scalaTest
* ScalaCheck runtime parameters
   * maxDiscardedRatio - determines how hard ScalaCheck will try before giving up on a property
   * minSuccessfulTests - parameter, default value is 100. - Default is 5.
     * if the minimum number of successful tests is 100, then 500 discarded attempts are allowed before property evaluation is aborted.
   * maxDiscardedRatio - ScalaCheck will always try at least as many evaluations as the specified minimum number of successful tests, even if the maximum discarded ratio is exceeded at some point during the evaluation iteration
   * Size
     * minSize and maxSize - ScalaCheck can control the size of the generated test data by providing the generator with a size parameter, a hint about how large the generated value should be.
     * The size makes most sense for generators that produce some kind of collection.
   * workers  - ScalaCheck can use several threads in parallel when checking a property. The thread count is controlled by the workers parameter.  
     * Test.Parameters.default.withMinSuccessfulTests(5000).withWorkers(2)
   * Test.check(oneWorker, p).time - Can find time take to test a property
   * org.scalacheck.Test.TestCallback. This object will receive callbacks during the test execution.
   * The main difference compared with Checkers is that you need not use ScalaCheck labels with PropertyChecks.
* When using with SBT
```Scala
     testOptions in Test +=
       Tests.Argument(
         TestFrameworks.ScalaCheck,
         "-maxDiscardRatio", "10",
         "-minSuccessfulTests", "1000"
     )
```
* scala -cp scalacheck.jar:. ListSpec --help
