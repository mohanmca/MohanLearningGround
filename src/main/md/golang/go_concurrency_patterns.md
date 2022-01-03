## Basic Go routine without Channel

```go
package main
import ("fmt";"time")

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Print(s)
	}
}

func main() {
	go say("world - ")
	say("hello - ")
}
```
* Output was ```world - hello - hello - world - world - hello - hello - world - world - hello - ```

## Usage of two channels to communicate exit of function

```go
package main

import "fmt"

func fibonacci(c, quit chan int) {
	x, y := 0, 1
	for {
		select {		case c <- x:			x, y = y, x+y
		                case <-quit:			fmt.Println("quit")
			            return
		}
	}
}

func main() {
	c := make(chan int, 2)
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

## Print ticket every 100 ms, .. in 50 s.. exit after 500

```go
package main
import ("fmt";"time")

func main() {
	tick := time.Tick(100 * time.Millisecond)
	boom := time.After(500 * time.Millisecond)
	for {
			select {
				case <-tick:			fmt.Println("tick.");
				case <-boom:			fmt.Println("BOOM!");			return
				default:				fmt.Println("    .");			time.Sleep(50 * time.Millisecond)
			}
	}
}
```



## How to create anki from this markdown file

* mdanki /Users/alpha/git/MohanLearningGround/src/main/md/golang/go_concurrency_patterns.md go_routine.apkg --deck "Everything::UnderSun::Work::GoCoRoutine"


## References

* [Primitives](https://go.dev/tour/concurrency/1)
* [CS-240 Concurrency In GO](https://sands.kaust.edu.sa/classes/CS240/F17/slides/02-concurrency-in-go.pptx)
* [Google I/O 2012 - Go Concurrency Patterns-**](https://www.youtube.com/watch?v=f6kdp27TYZs)
* [Visualizing Concurrency in Go-**](https://divan.dev/posts/go_concurrency_visualize/)
* [Elastic Beats Go Concurrency Patterns](https://www.elastic.co/blog/a-tour-of-go-concurrency-patterns-via-the-new-heartbeat-scheduler)
* [Go Concurrency Patterns: Pipelines and cancellation](https://go.dev/blog/pipelines)
* [Concurrency Patterns in Go ](https://dev.to/karankumarshreds/concurrency-patterns-in-go-3jfc)
* [Concurrency in Go](https://learning.oreilly.com/library/view/concurrency-in-go/9781491941294/ch04.html#bridgechannel)
* [Github Go patterns code](https://github.com/lotusirous/go-concurrency-patterns)
* [Applying Modern Go Concurrency Patterns to Data Pipelines](https://medium.com/amboss/applying-modern-go-concurrency-patterns-to-data-pipelines-b3b5327908d4)
* [Wait Groups](https://blog.logrocket.com/concurrency-patterns-golang-waitgroups-goroutines/)
* [An Introduction to Concurrency Patterns in Go](https://dzone.com/articles/an-intro-to-concurrency-patterns-in-go)
* [3 Concurrent Patterns Used in Golang](https://betterprogramming.pub/3-concurrent-patterns-used-in-golang-b107c52f1c06)
* [Go advanced concurrency patterns: part 3 (channels)](https://blogtitle.github.io/go-advanced-concurrency-patterns-part-3-channels/)
* [Concurrency Patterns in Go: sync.WaitGroup ](https://www.calhoun.io/concurrency-patterns-in-go-sync-waitgroup/)