# ScalaCheck Property-based software testing
* Specifications and tests and ScalaCheck vs Junit
* A specification is a definition of a program's abstract behavior, provides complete picture and informal
* Tests are concrete examples of how a program should behave in particular situations and it is formal.
* In TDD and BDD,  tests as specification is to make your specification more test-centered. It is also known as executable specification.
* Property-based testing goes in the opposite direction by making your tests more specification-like

* Sample scala check
```Scala
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
* Benefits
  * Increased - Test coverage due to randomized input
  * Specification completeness - due to abstract test
  * Maintenance - less code to maintain and refactor
  * Test case simplification  - Finding smallest set for which test case fails
  * When property based test case fails.
    * Handle it in the implementation code, and repurcusion may impact test case, hence handle it in testcase
    * Handle the exception in the propery-based test case
    * Ignore the particular case, by filtering out possibility in the test-case
* ScalaCheck  == Properties ++ Generators   