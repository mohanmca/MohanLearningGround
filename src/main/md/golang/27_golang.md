## Go-lang - Simple hello word!

```go
package main

import ("fmt";"time")

func main() {
	fmt.Println("Hello, 世界")
    fmt.Println("The time is", time.Now())
}
```

## Golang setups

```bash
#/Users/alpha/env.sh or in ~/.zhrc
export GOPATH=~/go
export GOROOT='/opt/homebrew/Cellar/go/1.17.5/'
export PATH="$GOROOT/bin/:$PATH"
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
        for i := 0; i < 10; i++ {
            sum += i
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

## Make
1. make channels
    1. ch := make(chan int)
1. maps
    1.  m := make(map[string]int)
1. slices
    1. a := make([]int, 5)

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
1. ```go
        a := make([]int, 5)
        var s []int
        s = append(s, 0)
        s = append(s, 1)
        s = append(s, 2, 3, 4)
        fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
    ```
1. ```go
    pow := []int{1, 2, 4, 8, 16, 32, 64, 128}
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
    ```
1. Two dimensional list
    ```go
    func Pic(dx, dy int) [][]uint8 {
        pic := make([][]uint8, dy)
        
        for i := 0; i < dy ; i++ {
            pic[i] = make([]uint8, dx)
        }
        return pic
    }
    ```    
## Map and Map literals
1. If the top-level type is just a type name, you can omit it from the elements of the literal. 
```go
	m = make(map[string]Vertex)
	m["Bell Labs"] = Vertex{
		40.68433, -74.39967,
	}
	fmt.Println(m["Bell Labs"])

    type Vertex struct {
        Lat, Long float64
    }

    var m = map[string]Vertex{
        "Bell Labs": {
            40.68433, -74.39967,
        },
        "Google": {
            37.42202, -122.08408,
        },
    }
	m := make(map[string]int)

	m["Answer"] = 42
	fmt.Println("The value:", m["Answer"])

	m["Answer"] = 48
	fmt.Println("The value:", m["Answer"])

	delete(m, "Answer")
	fmt.Println("The value:", m["Answer"])

	v, ok := m["Answer"]
	fmt.Println("The value:", v, "Present?", ok)    
```
1. 
    ```go
        package main

        import ("strings")

        func WordCount(s string) map[string]int {
            m := make(map[string]int)
            
            for _,word := range strings.Fields(s) {
                _, ok := m[word]
                if ok {
                    m[word]++;
                } else {
                    m[word] = 1
                }
            }
            return m
        }

        func main() {
            wc.Test(WordCount)
        }
    ```


## Zero value

1. nil -- zero value ```var z []int;```
1. The zero value of a map is nil. 
    1. A nil map has no keys, nor can keys be added. 


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

1. ```v := Vertex{1, 2};p := &v;p.X = 1e9```
    1. struct pointers can be accessed via (*p).X, 
        1. why does go-lang allows p.X, does it mean go also support syntactic sugar? 
        1. is this only one?
1. ```go install github.com/x-motemen/gore/cmd/gore@latest```        

## Functions are values, we can pass as argument, and return as result

```go

hypot := func(x, y float64) float64 {
    return math.Sqrt(x*x + y*y)
}

func compute(fn func(float64, float64) float64) float64 {
	return fn(3, 4)
}
```

## Functions are closures

```go
func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}


func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}

func main() {
	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(
			pos(i),
			neg(-2*i),
		)
	}
}
```

## Method (function with receiver argument)

1. Method is just a function with receiver arguments
```go
    func (Vertex v) Abs() float64 {
        return Math.Sqrt(v.Y*v.Y + v.Y *v.Y);
    }
```
1. type MyFloat float64
    1. Type alias
1. Methods can't be written for types that are defined in another package
1. Methods can work with values or pointers
    1. func (v *Vertex) Scale(f float64) //works on pointer
    1. func (v Vertex) Scale(f float64) //works on copy
1. Method argument, vs func argument - pointers
    1. Method argument and calle both can dynmically work for poiner type or value type.
        1. Caller and Callee both are adjusted for methods //not for function
    1. When method receives reciver as a pointer argument, caller can use with &argument or ```argument```
    1. Go interprets the statement v.Scale(5) as (&v).Scale(5) since the Scale method has a pointer receiver.
    1. Go interprets the statement v.Scale(5) as (&v).Scale(5) since the Scale method has a pointer receiver.   
    1. **All methods on a given type should have either value or pointer receivers, but not a ~~mixture of both~~.**
1. For function, if argument is pointer, caller has to use with &argument


## Interface

```go
type Abser interface {
    Abs() float64
}
type Vertex struct {
    X, Y float64
}
func (v *Vertex) Abs() float64 {
    return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

func main() {
    var a Abser
    v := Vertex {3, 5};
    a = &v; // a != v (v is of Vertex, but *v is Abser)
    fmt.Println(a.Abs())
}
```

## Simples interface

1. An interface value that holds a nil concrete value is itself non-nil.
1. in Go it is common to write methods that gracefully handle being called with a nil receiver
1. A nil interface value holds neither value nor concrete type.
1.  The interface type that specifies zero methods is known as the empty interface: - interface{}
    1. Every type implements at least zero methods.
    1. Empty interfaces are used by code that handles values of unknown type.

```go
import "fmt"

type I interface {
	M()
}

type T struct {
	S string
}

// This method means type T implements the interface I,
// but we don't need to explicitly declare that it does so.
func (t T) M() {
	fmt.Println(t.S)
}

func main() {
	var i I = T{"hello"}
	i.M()
}
```

## Type assertion

1. ```t := i.(T)``` -A type assertion provides access to an interface value's underlying concrete value.
1. ```t, ok := i.(T)``` -- If i holds a T, then t will be the underlying value and ok will be true.
1. ```f = i.(float64)``` -- panic  - panic: interface conversion: interface {} is string, not float64
1. Type switches
    ```go
        switch v := i.(type) {
        case int:
            fmt.Printf("Twice %v is %v\n", v, v*2)
        case string:
            fmt.Printf("%q is %v bytes long\n", v, len(v))
        default:
            fmt.Printf("I don't know about type %T!\n", v)
        }    
    ```
1. A Stringer is a type that can describe itself as a string. Similarly Error is generic type
1. ```go
        type Stringer interface {
            String() string
        }
        type error interface {
            Error() string
        }        
    ```


## Reader

```
	r := strings.NewReader("Hello, Reader!")

	b := make([]byte, 8)
	for {
		n, err := r.Read(b)
		fmt.Printf("n = %v err = %v b = %v\n", n, err, b)
		fmt.Printf("b[:n] = %q\n", b[:n])
		if err == io.EOF {
			break
		}
	}
```


## GO co-routine

1. A goroutine is a lightweight thread managed by the Go runtime. - ```go f(x, y, z)```
    1. The evaluation of f, x, y, and z happens in the invoking goroutine 
        1. The execution of f happens in the new goroutine.
    1. The sync package provides useful primitives for co-routine synchronization
1. Channels are pipes thorugh which we can send/receive values
    1. ch <- v (send v into Ch)
    1. v := <-ch (recive from ch... and store into v)
1. Channel supports buffer
    1. Sender can't send more than buffered count
    1. Receiver has to consume those buffered elements
1. Challen can be closed
    1. It is like file
    1. When receiver needs to be beformed EOF, we can close from sender
    1. Only sender should close the channel
1. Channels and for-loops
    1. The loop for i := range c receives values from the channel repeatedly until it is closed. 
    1. ```v, ok := <-ch```
        1. If channel is closed, ok would be false

## Select go routine example

1. The select statement lets a goroutine wait on multiple communication operations.
1. A select blocks until one of its cases can run, then it executes that case. It chooses one at random if multiple are ready. 
1.  The default case in a select is run if no other case is ready. Use a default case to try a send or receive without blocking:

```go
package main

import "fmt"

func fibonacci(c, quit chan int) {
	x, y := 0, 1
	for {
		select {
		case c <- x:
			x, y = y, x+y
		case <-quit:
			fmt.Println("quit")
			return
		}
	}
}

func main() {
	c := make(chan int)
	quit := make(chan int)
	go func() {
		for i := 0; i < 10; i++ {
			fmt.Println(<-c)
		}
		quit <- 0
	}()
	fibonacci(c, quit)
}
```

## GO Java's synchrnoized equivalent - sync.mutex

```go
// SafeCounter is safe to use concurrently.
type SafeCounter struct {
	mu sync.Mutex
	v  map[string]int
}

// Inc increments the counter for the given key.
func (c *SafeCounter) Inc(key string) {
	c.mu.Lock()
	// Lock so only one goroutine at a time can access the map c.v.
	c.v[key]++
	c.mu.Unlock()
}

// Value returns the current value of the counter for the given key.
func (c *SafeCounter) Value(key string) int {
	c.mu.Lock()
	// Lock so only one goroutine at a time can access the map c.v.
	defer c.mu.Unlock()
	return c.v[key]
}
```

## Golang errors

* 	c, python, java := true, false, "no!"; python := "false!"
    1. ./prog.go:9:9: no new variables on left side of :=
    1. ./prog.go:9:9: cannot use "false!" (type untyped string) as type bool in assignment