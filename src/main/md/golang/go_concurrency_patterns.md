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

## Google I/O 2012 - Go Concurrency Patterns 

1. CSP paper by Tony Hoare is base for concurrency, It is the core for Erlang and Go
1. Channel is introduced in it, Channels are like file-descriptor
1. GO is all about Channels
1. Channels provide infra for routines to communicate between them
1. ```go
    func boring(say string) {
        for i:=0;i<5;i++ {
            fmt.Println(say, i)
            time.Sleep(2 * time.Second)
            //time.Sleep(time.Duration(rand.Intn(1e3)) * time.MilliSecond)
        }    
    }
    ```

1. Go program ends when main ends
1. Go program is composition of Go routines
1. Go routing has independent stack, can grow and shrink (unlike Java)
1. Go routines are multiplexed dynamically onto threads
1. Arrow indicates the direction of the data flow on a channel
1. ```go
    c := make(chan int) 
    c <- 4
    v = <- c
    ```
1. ```go
    func boring(say string, c chan string) {
        for i:=0;i<5;i++ {
            c <- fmt.Sprintf("%s %d", say, i)
            time.Sleep(time.Duration(rand.Intn(1e3)) * time.MilliSecond)
        }    
    }
    func main() {
        c := make(ch string)
        go boring("say something", c)
        for i:=0; i<10; i++ {
            fmt.Printf("You say %q\n", <-c)
        }
        fmt.Println("you are boring!, Leaving")
    }        
    ```
1. Go buffers are like mailboxes in Er-lang, and avoids synchronisation between receiver and sender
1. Go concurrent pattern basic principle -  "Don't communicate by sharing memory, share memory by communicating."
1. Pattern - Generator function that returns the channel
1. ```go
    func main() {
        c := boring("something");
        for i:=0;i<5; i++ {
            fmt.Printf("You say: %q\n",<-c)
        }
        fmt.Println("boring leaving!")
    }

    func boring(msg string) <-chan string {
        c := make(chan string)
        go func() {
            for i:=0; ; i++ {
                c <- fmt.Sprintf("%s %d", say, i)
                time.Sleep(time.Duration(rand.Intn(1e3)) * time.MilliSecond)
            }
        }()
        return c;
    }
    ```
1. FanIn - Multiplexing - choose whoever is ready
1. ```go
    func fanIn(input1, input2, <- chan string) {
        c := make(chan string)
        go func() { for {c <- <-input1 }} ()
        go func() { for {c <- <-input2 }} ()
        return c
    }
    func main() {
        c := fanIn(boring('joe'), boring('ann'))
        for i:=0;i<10; i++ {
            fmt.Println(<-c)
        }
        fmt.Println("You are boring! leaving.")
    }
    ```   
1. Pattern - Sequencing between the channel
1. ```go
    type Message struct {
        text string
        wait chan bool
    }
    func main() {
        c := boring("something");
        for i:=0;i<5; i++ {
            msg1 := <-c
            msg2 := <-c
            fmt.Printf("You say: %q\n", msg1.text)
            fmt.Printf("You say: %q\n", msg2.text)
            msg1.wait <- true
            msg2.wait <- true
        }
        fmt.Println("boring leaving!")
    }

    func boring(msg string) <-chan string {
        waitForIt := make(chan string)
        go func() {
            for i:=0; ; i++ {
                c <- Message{ fmt.Sprintf("%s %d", say, i), waitForIt }
                time.Sleep(time.Duration(rand.Intn(1e3)) * time.MilliSecond)
                <- waitForIt
            }
        }()
        return c;
    }
    ```

## FanIn (Multiplexing) pattern using Select

```go
    func fanIn(input1, input2, <- chan string) {
        c := make(chan string)
        go func() { 
            for {
                select {
                    case s := <- input1: c <- s
                    case s := <- input2: c <- s
                }
            }
        }()        
        return c
    }
```

## Select statement = control statement or Control structure

1. Select statement helps to handle multiple channels
1. It is like switch but each case is communication
1. All channels are evaluated, if multiple can proceed, randomly one is proceeded
1. Default case, if no channel is ready


## How to create anki from this markdown file

* mdanki /Users/alpha/git/MohanLearningGround/src/main/md/golang/go_concurrency_patterns.md go_routine.apkg --deck "Everything::UnderSun::Work::GoCoRoutine"


## References

* [Primitives](https://go.dev/tour/concurrency/1)
* [CS-240 Concurrency In GO](https://sands.kaust.edu.sa/classes/CS240/F17/slides/02-concurrency-in-go.pptx)
* [Google I/O 2012 - Go Concurrency Patterns-**](https://www.youtube.com/watch?v=f6kdp27TYZs)
* [ACM-Program your next server in Go](https://talks.golang.org/2016/applicative.slide#47)
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