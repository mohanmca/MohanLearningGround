package main

import ("fmt")

func main() {
	defer fmt.Println("Hello1")
	fmt.Println("Hello")
	defer fmt.Println("Hello2")
	defer fmt.Println("Hello3")
}