## Quick search operators
  * filename:pom.xml
  * extension:conf or extension:yaml
  * language:scala
  * stars:>10

## How to find Dijkstra algorithm coded in javascript  
* function language:javascript

## Github CodeSearch

1. [Javascript interview](https://cs.github.com/?scopeName=All+repos&scope=&q=javascript+%22%3Cdetails%3E%3Csummary%3E%22+++language%3Amarkdown)

## How to exclude test code from result

1. ```NOT language:html```
1. ```foo NOT path:/_test\.go$/```
1. ```bellman ford language:go NOT path:/_test\.go$/```

## Other possible commands
* Search repository containing a word "aws.Polly" but written using Javascript
  * aws.Polly language:javascript stars:>10
* (search "AWS SWF" in file name or in path name)
  * AWS SWF in:file,path 
* Find project using below libraries on Maven
  * spring hibernate spock filename:pom.xml
* Search Akka Stream usage http://doc.akka.io/docs/akka/2.4/scala/stream/stream-io.html#Streaming_File_IO usage
  * https://github.com/search?q=FileIO.fromPath+language:Scala&type=Code
* Search code for integration layer
  * api.github.com language:scala
* Search the configuration, Extension here is file extension name 
  * ClusterActorRefProvider extension:conf
* Search the respositories, where repo name contains a word course 
  * course in:name 
* Search documentation on github
  * https://github.com/search?q=hot+module+reload+express+js&type=Wikis
* Search document without a word
  * hot module reload express js NOT react
* Search a sample configuration for babel-preset-env within any of package.json
  * babel-preset-env filename:package.json  created:>2017-12-31  
* Search the repo that uses puppeteer and fs module
  * fs puppeteer extension:js language:js  
  * use sort by using "recenly indexed"

## How github codesearch works?

1. [How github code search works](https://news.ycombinator.com/item?id=29489675)
1. (https://github.com/RoaringBitmap/roaring-rs)
1. (https://github.com/google/zoekt)
1. (https://srcgr.ph/zoekt-memory-optimizations)
1. [github codesearch developer](https://news.ycombinator.com/submitted?id=colin353)

## Famous code searchs
* [Javascript courses = course in:name language:javascript forks:>1000](https://github.com/search?q=course+in%3Aname+language%3Ajavascript+forks%3A%3E1000)
* [java courses = course in:name language:java forks:>1000](https://github.com/search?q=course+in%3Aname+language%3Ajava+forks%3A%3E1000)

## References
* [Github advanced search](https://github.com/search/advanced)
* [Searching code](https://help.github.com/articles/searching-code/)
* [Gist search](https://gist.github.com/search)
* [Bitbucket search](https://support.atlassian.com/bitbucket-cloud/docs/search-in-bitbucket-cloud/)
  
