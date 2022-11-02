 package main

import (
	"fmt"
	"io"
	"log"
	"net/http"
	"time"
)

var (
	username = "abc"
	password = "123"
)

func main() {
	call("http://localhost:8000/", "GET")
}

func call(url, method string) error {
	client := &http.Client{
		Timeout: time.Second * 10,
	}
	req, err := http.NewRequest(method, url, nil)
	if err != nil {
		return fmt.Errorf("Got error %s", err.Error())
	}
	log.Printf("Sending request")
	req.SetBasicAuth(username, password)
	response, err := client.Do(req)
	if err != nil {
		return fmt.Errorf("Got error %s", err.Error())
	}
	defer response.Body.Close()
	fmt.Print(response)
	if response.StatusCode == http.StatusOK {
		bodyBytes, err := io.ReadAll(response.Body)
		if err != nil {
			log.Fatal(err)
		}
		bodyString := string(bodyBytes)
		log.Print(bodyString)
	} else {
		log.Printf("Error receiving response ", response.StatusCode)
	}
	return nil
}
