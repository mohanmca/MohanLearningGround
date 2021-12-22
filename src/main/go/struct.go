package main

import (
	"fmt"
)

func main() {
	s := []struct {
		i int
		b bool
	}{{1, true}, {2, false}, {3, true}}
	fmt.Println(s)
	fmt.Println(s[1:2])
}
