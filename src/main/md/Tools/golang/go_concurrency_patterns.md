## GOlang channel concurrency patterns
```
Pattern	Purpose
Ping-Pong	Bi-directional communication
Fan-Out / Fan-In	Parallelism & aggregation
Done Channel	Cancellation
Timeout / Ticker	Time-based control
Semaphore	Concurrency limiting
Pipeline	Data processing stages
Or-Done	Unified cancellation
Tee	Duplication of stream
Bridge	Merging channels of channels
Closing Channel	Signaling producer termination
```

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
## Select statement = control statement or Control structure

1. Select statement helps to handle multiple channels
1. It is like switch but each case is communication
1. All channels are evaluated, if multiple can proceed, randomly one is proceeded
1. Default case, if no channel is ready
1. Generally select statement would be inside a infinite loop


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

## Timeout using select (1/2 patterns timeout - between messges)

```go
for {
    select {
        case s: <-c: fmt.Println(s)
        case <- time.After(1 * time.Second): 
                fmt.Println("Your are too slow"!)
                return

    }
}
```

## Timeout using select (2/2 Timeout overall) (overall timeout for all the channels)

```go
timeout := time.After(5 * time.Second)
for {
    select {
        case s: <-c: fmt.Println(s)
        case <-timeout: 
                fmt.Println("You talk too much"!)
                return

    }
}
```

## Simple Quit Channel

```go
    quit := make(chan bool)
    c := boring("somethig")
    for i := rand.Intn(10); i>=0; i-- {fmt.Pringln(<-c)}
    quit <- true

    select {
        case c <- fmt.Sprintf("%s, %d" message, i) 
        case <- quit: return
    }
```
* If main sends message to quit channel and immediately termintes, how to ensure quit completed cleanup tasks. Main has to wait from Quit, if it requires acknowledgement

## Daisy Chain Pattern

```go
func daisyChain(left, right chan int) {
    left <- 1 + <- right
}

func main() {
    const n = 10000
    leftmost := make(chan int)
    left := leftMost
    right := leftMost
    for i=0;i <n; i++ {        
        right = make(chan int);
        go daisyChain(left, right);
        left = right
    }
    go func(c chan int) { c <- 1}(right)
    fmt.Println("We got %d"., <- leftMost)
}
```


## Quit Channel with Acknowledgement (how to know if co-routing quit)

```go
quit := make(chan bool)
c := boring("somethig")
for i := rand.Intn(10); i>=0; i-- {fmt.Pringln(<-c)}
quit <- true
fmt.Printf("Joe says bye - %s", <-quit)

select {
    case c <- fmt.Sprintf("%s, %d" message, i) 
    case <- quit: 
        cleanup()
        quit <- "Cleanup done!, see you"
        return
}
```


## How google search works? (Google Search 1.0)

1. WebSearch + ImageSearch + YoutubeSearch + MapSearch
1.  ```go
    var (
        Web = fakeSearch("web")
        Image = fakeSearch("image")
        Video = fakeSearch("video")
    )
    type Search func(query string) Result
    func fakeSearch(kind string) Search {
        return func(query string) Result {
            time.Sleep(time.Duration(rand.intn(100))* time.Millisecond)
            return Result(fmt.Sprintf("%s result for query %q", kind, query))
        }
    }

    func main() {
        rand.Seed(time.Now().UnixNano())
        start := time.Now()
        results := Google("Search")
        elapsed := time.Since(start)
        fmt.Println(results)
        fmt.Println(elapsed)
    }

    func Google(query string) (results []Result) {
        results = append(results, Web(query))
        results = append(results, Image(query))
        results = append(results, Video(query))
        return
    }
    ```

## (Google Search 2.0) Using Fan-In pattern

```go
    func Google(query string) (results []Result) {
        c := make(chan Result);
        go func() { c <-  Web(query)}()
        go func() { c <-  Image(query)}()
        go func() { c <-  Video(query)}()

        for i := 0; i < 3; i++ {
            result := <- c
            results = append(results, result)
        }
        return
    }
```


## (Google Search 2.1) Timeout should be considered, use only result till timeout happens

```go
    func Google(query string) (results []Result) {
        c := make(chan Result);
        go func() { c <-  Web(query)}()
        go func() { c <-  Image(query)}()
        go func() { c <-  Video(query)}()
        timeout := time.After(80 * time.Millisecond)

        for i := 0; i < 3; i++ {
            select {
                case result := <- c
                    results = append(results, result)
                case <-timeout:
                    fmt.Println("timedout")
                    return
            }
        }
        return
    }
```

## How to avoid discarding results from slow servers (Google search 3.0)

1. Replicate the servers, and send the query to multiple replicas 
1. use result from fast server. Use the result from first response
```go
func First(query string, replicas ...Search) {
    c := make(chan Result)
    searchReplica := func(i int) {c <- replicas[i](query)}
    for i := range replicas {
        go searchReplica(i)
    }
    return <- c
}

func main() {
    rand.Seed(time.Now().UnixNano())
    start := time.Now()
    result := First("golang", fakeSearch("replica1"), fakeSearch("replica2"))
    elapsed := time.Since(start)
    fmt.Println(result)
    fmt.Println(elapsed)
}
```
## Google search using replicated servers

```go
    func Google(query string) (results []Result) {
        c := make(chan Result);
        go func() { c <-  First(query, web1, web2)}()
        go func() { c <-  First(query, Image1, Image2)}()
        go func() { c <-  First(query, Video1, Video2)}()
        timeout := time.After(80 * time.Millisecond)
        for i := 0; i < 3; i++ {
            select {
                case result := <- c: results = append(results, result)
                case <-timeout: fmt.Println("timeout ");return
            }
        }
        return
    }
```

## How to avoid over-doing go coroutines

1. Just use simple reference counter
1. sync and sync/atomic package has primitive tools
1. mutexes, condition-variables are quite important


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
