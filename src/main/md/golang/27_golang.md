## Go-lang - Simple hello word!

```go
package main

import ("fmt";"time")

func main() {
	fmt.Println("Hello, 世界")
    fmt.Println("The time is", time.Now())
}
```

## Notes

* Package name is the same as the last element of the import path. For instance, the "math/rand" package comprises files that begin with the statement package rand
* Go recommends factored import statement
* Unexported name vs Exported name - fmt.Println(math.pi) vs fmt.Println(math.Pi)
* [type comes after the variable name.](https://blog.golang.org/gos-declaration-syntax)
    * func add(x int, y int) int
* Closures in golang
     * ```go sum := func(a, b int) int { return a+b } (3, 4) ```
* When two or more consecutive named function parameters share a type, you can omit the type from all but the last.
    * func add(x, y int) int
* A function can return any number of results.
    * ```go return y, x```
* Named return values 
    * ```go func split(sum int) (x, y int) ```
* package level variables can be declared
    * ```go 
        var c, python, java bool; 
        var i, j int = 1, 2
        var i, j = 1, 2
        var c, python, java = true, false, "no!"
        const Pi = 3.14
      ```
      ```
    * package level := is not available
* Assignment statement := (Alternative to var)
    * := short assignment statement can be used in place of a var
    * outside a function, every statement begins with a keyword
    * Constants cannot be declared using the := syntax. 
    * ```go 
        c, python, java := true, false, "no!" //only function level, not at package levelz      
        zAxis complex128 = cmplx.Sqrt(-5 + 12i)
        g := 0.867 + 0.5i
      ```
* basic types
    ```go
        bool
        string
        int  int8  int16  int32  int64
        uint uint8 uint16 uint32 uint64 uintptr
        byte // alias for uint8
        rune // alias for int32 - represents a Unicode code point
        float32 float64
        complex64 complex128    
    ``
* zero values
    * 0, false, ""
* The expression T(v) converts the value v to the type T. 
    1. ```go
            var i int = 42
            var f float64 = float64(i)
            var u uint = uint(f)
        ```
* Constants are declared like variables, but with the const keyword. 
* ```go
        const (
            // Create a huge number by shifting a 1 bit left 100 places.
            // In other words, the binary number that is 1 followed by 100 zeroes.
            Big = 1 << 100
            // Shift it right again 99 places, so we end up with 1<<1, or 2.
            Small = Big >> 99
        )
    ```
*  ```go
        func needInt(x int) int { return x*10 + 1 }
        func needFloat(x float64) float64 {
            return x * 0.1
        }

        func main() {
            fmt.Println(needInt(Small))
            fmt.Println(needFloat(Small))
            fmt.Println(needFloat(Big))
        }
    ```

## For and IF statements in Golang 
* [Go statements](https://go.dev/ref/spec#For_statements)
* for init; condition; post { <<block>> }
* the condition expression: evaluated before every iteration
* The init and post statements are optional.
* for and if looks similar in syntax
  * ```go
        for sum < 1000 {
          sum += sum
        }
        if x < 1000 {
          sum += sum
        }    
  ```
* if statement can start with a short statement to execute before the condition. (variable-'v' scope also within the if/else block)
  ```go
  func pow(x, n, lim float64) float64 {
	if v := math.Pow(x, n); v < lim {
		return v
	}
	return lim
  }
  ```
* Switch case, default break, and execute only one case, any type is supported (not necessary to be constant!)
    ```go
        switch os := runtime.GOOS; os {
        case "darwin":
            fmt.Println("OS X.")
        case "linux":
            fmt.Println("Linux.")
        default:
            // freebsd, openbsd,
            // plan9, windows...
            fmt.Printf("%s.\n", os)
        }
    ```

## Go defer

1. It is opposite concept of java Thread.join()
    1. Main thread.can join other thread and wait for other threds to complete
    1. In go, defered execution can complete later to the enlosed function
1. If there are multiple defer, they are stacked, one defered last will completed ahead!

## Go pointers

1. De-referencing or indirecting
    ```go
        var p *int // p is pointer to int - type
        i := 42;
        p = &i;
        fmt.Println(*p); //read value of i, through pointer p;
        *p = 21
    ```
1. Struct Literals
    ```go
        type Vertex struct {
            X, Y int
        }

        var (
            v1 = Vertex{1, 2}  // has type Vertex
            v2 = Vertex{X: 1}  // Y:0 is implicit
            v3 = Vertex{}      // X:0 and Y:0
            p  = &Vertex{1, 2} // has type *Vertex
        )    
    ```
1. Ananymous Structs
    ```go
       s:= []struct {
            i int
            b bool
        } {{2, true},{1, false}}
        fmt.Println(s)
    ```

## Arrays vs Slice (fixed vs dynamically-sized)

1. ```var a [10]int``` - declares a variable a as an array of ten integers.
1. The type ```[]T``` is a slice with elements of type T.
1. a[low : high] - This selects a half-open range which includes the first element, but excludes the last one.
    1. When slicing, you may omit the high or low bounds to use their defaults instead. The default is zero for the low bound and the length of the slice for the high bound.
1. s = s[2:] -- // Drop its first two values.
1. ```b := make([]int, 0, 5) // len(b)=0, cap(b)=5; a := make([]int, 5)  // len(a)=5```
1. ```go
        board := [][]string{
            []string{"_", "_", "_"},
            []string{"_", "_", "_"},
            []string{"_", "_", "_"},
        }
    ```
	
## Zero value

1. nil -- zero value ```var z []int;```


## Go programming langauge developers

1. Robert Griesemer, Rob Pike, and Ken Thompson.
1. Andrew Gerrand, Ian Lance Taylor - Type Parameter proposed
1. Russ Cox, Josh Bleecher Snyder, 

## Go programming specification
1. [Go language principles](https://talks.golang.org/2015/simplicity-is-complicated.slide#1)
1. [Type paramter](https://go.googlesource.com/proposal/+/refs/heads/master/design/43651-type-parameters.md)
1. [Members of the go](https://github.com/orgs/golang/people)
1. [Secure coding practices](https://github.com/OWASP/Go-SCP/blob/master/dist/go-webapp-scp.pdf)

## Follow-up questions

1. ```	v := Vertex{1, 2};p := &v;p.X = 1e9```
    1. struct pointers can be accessed via (*p).X, 
        1. why does go-lang allows p.X, does it mean go also support syntactic sugar? 
        1. is this only one?


## Golang errors

* 	c, python, java := true, false, "no!"; python := "false!"
    1. ./prog.go:9:9: no new variables on left side of :=
    1. ./prog.go:9:9: cannot use "false!" (type untyped string) as type bool in assignment