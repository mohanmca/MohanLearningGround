package main

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"os"
	"strings"
	"time"
)

type configuration struct {
	Query                      string
	Server_fqdn                []string
	Request_Timeout_in_seconds int64
}

type esQuery struct {
	queryUrl         string
	responseFilename string
	requestTimeout   int64
}

func getEsQueryList(conf configuration) []esQuery {

	esRequests := make([]esQuery, len(conf.Server_fqdn))

	currentTime := time.Now()
	for i := 0; i < len(conf.Server_fqdn); i++ {

		filename := strings.Split(conf.Server_fqdn[i], ".")[0] + "_" + currentTime.Format("20060102150405") + ".json"
		esRequests[i] = esQuery{
			queryUrl:         "https://" + conf.Server_fqdn[i] + "/" + conf.Query,
			requestTimeout:   conf.Request_Timeout_in_seconds,
			responseFilename: filename,
		}
	}
	return esRequests
}

func getConfiguration() configuration {
	file, err_file_open := os.Open("es_query_conf.json")

	if err_file_open != nil {
		fmt.Println("error:", err_file_open)
	}
	defer file.Close()

	decoder := json.NewDecoder(file)
	conf := configuration{}

	err := decoder.Decode(&conf)
	if err != nil {
		fmt.Println("error:", err)
	}
	return conf
}

func getHttpResponse(esRequestInfo esQuery, respStatus chan<- string) {

	fmt.Println("HTTP GET:", esRequestInfo.queryUrl)
	resp, err := http.Get(esRequestInfo.queryUrl)
	if err != nil {
		//panic(err)
		respStatus <- "Error connecting to host"
		return
	}
	defer resp.Body.Close()

	// fmt.Println("Response status:", resp.Status)

	out, err := os.Create(esRequestInfo.responseFilename)
	if err != nil {
		// panic?
		fmt.Println("error while writing file: ", esRequestInfo.responseFilename)
	}
	defer out.Close()
	io.Copy(out, resp.Body)
	respStatus <- resp.Status
}

func getResponseData(esRequestInfo esQuery, respStatus <-chan string, completionStatus chan<- string) {
	select {
	case result := <-respStatus:
		fmt.Println(result)
		break
	case <-time.After(time.Duration(esRequestInfo.requestTimeout) * time.Second):
		fmt.Println("timedout: ", esRequestInfo)
	}
	completionStatus <- "completed"
}

func main() {
	conf := getConfiguration()
	esRequests := getEsQueryList(conf)

	responseStatusChannel := make(chan string, len(esRequests))
	waitForCompletionChannel := make(chan string, len(esRequests))

	for _, esRequest := range esRequests {
		go getHttpResponse(esRequest, responseStatusChannel)
		go getResponseData(esRequest, responseStatusChannel, waitForCompletionChannel)
	}

	for i := 0; i < len(esRequests); i++ {
		<-waitForCompletionChannel
	}
	fmt.Println("completed")
}
