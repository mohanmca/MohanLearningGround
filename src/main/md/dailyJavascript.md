* Array with 10 numbers - [...Array(10).keys()]
* Goto https://www.omgeo.com/documentation/?p=alert And run below script to download documents..
$('a[target="_blank"]').filter((index,tag) => tag.href.contains("documentation/D")).map((index, tag) => console.log(tag.href))

* [Download details from google result](https://github.com/mohanmca/MohanLearningGround/blob/master/src/main/js/google/tools/rip_google_results.js)

* https://gist.github.com/TomyJaya/6468ce42e43b8e0dad20


* Convert HTML Table to JSON
```
http://singapores100.com/Ranking.aspx
var table = document.getElementById("ranking")
console.log(hTableToJson(table))

function hTableToJson(table) {
	var rows = [...table.rows]
	var content = rows.map(row => Array.from(row.cells).map(cell => cell.innerText))
	var headers = content[0]
	var result = content.map(record => { var r = {}; record.map( (v,i,a) => r[headers[i]] = v.replace(/^\s+|\s+$/g,'') ); return r } )
	return JSON.stringify(result, null, 2);
}

```

* Download an URL to a file using Javascript code
```
function downloadURI(uri, name) {
  var link = document.createElement("a");
  link.download = name;
  link.href = uri;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  delete link;
}
```
* Download an URL to a variable
```Javascript
/* Download to a variable */
function download(url) {
	xmlhttp=new XMLHttpRequest()
	xmlhttp.open("GET", url, false)
	xmlhttp.send()
	return xmlhttp.responseText
}
let content = download("https://gist.github.com/mohanmca/5816e30d911620c983302b13d7a187b4")
```

* Parse html text content into dom
```Javascript
parser = new DOMParser()
let contentDom = parser.parseFromString(content, "text/xml")
```
* Add script to download data
```Javascript
var result = $.get("http://localhost:9000/json/search/searchText", {}).done(
		function(response) {
			return response.data
		}).done(function(result) {
	console.log(result.results)
	return result.results;
});

```Javascript

* Add script to chrome console
```Javascript
function addScript(scriptUrl) {
 var script = document.createElement('script');
 script.type = 'text/javascript';
 script.src = scriptUrl;
 document.head.appendChild(script);
}
```

```Javascript
addScript('https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.5.1/lodash.js')
_.filter([1,2,3], x => x%2 == 0)
addScript('https://cdnjs.cloudflare.com/ajax/libs/rxjs/5.4.2/Rx.js')
Rx.Observable.range(1,3)
```

```Javascript
var jsdom = require('jsdom')
jsdom.env({
 url: "http://news.ycombinator.com/",
 scripts: ["http://code.jquery.com/jquery.js"],
 done: function (err, window) {
   var $ = window.$;
   console.log("HN Links");
   $("td.title:not(:last) a").each(function() {
     console.log(" -", $(this).text());
   });
 }
});
```

Steps to create ES2016 project on node

* Ensure following two files are there in root
* webpack.config.js
* .bablerc

//Update node
npm update -g
npm cache clean
npm install --save-dev babel-core babel-loader babel-preset-react
npm install -g webpack
webpack -w //watch file and transform
webpack -p //ship to production

//Simple bable rc
{
  "presets": [
    "react"
  ]
}

```Javascript
//webpack.config.js
// In webpack.config.js
var HtmlWebpackPlugin = require('html-webpack-plugin')
var HTMLWebpackPluginConfig = new HtmlWebpackPlugin({
  template: __dirname + '/app/index.html',
  filename: 'index.html',
  inject: 'body'
});
module.exports = {
  entry: [
    './app/index.js'
  ],
  module: {
    loaders: [{
      test: /\.coffee$/,
      exclude: /node_modules/,
      loader: "coffee-loader"
    }, {
      test: /\.js$/,
      exclude: /node_modules/,
      loader: "babel-loader"
    }]
  },
  output: {
    filename: "index_bundle.js",
    path: __dirname + '/dist'
  },
}
```
 
```Javascript
var packages = []
var nodes = document.getElementsByClassName("css-truncate-target")
for(var i=0; i< nodes.length;i++){ if(nodes[i].children[0]) packages.push(nodes[i].children[0].text) }
packages.map( _ => "apm install " + _).join("\n")
"apm install " +  packages.join(", ")
```


* Copy list of links from google chrome

```Javascript
const onlyUnique = (value, index, self) =>   self.indexOf(value) === index
var container = document.evaluate('//*[@id="rso"]/div/div', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null)
var results = Array.from(container.singleNodeValue.children)
var resultLinks = results.map(childNode =>  document.evaluate('div/div/h3/a', childNode, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null))
resultLinks.map( link => link.singleNodeValue.href)

var result = resultLinks.map(x => x.singleNodeValue.href).filter(onlyUnique).join("\r\n")
copy(result)
```


* Copy list of links from ycombinator articles - Hacker news
```Javascript
// Navigate to - https://news.ycombinator.com/item?id=15154903 or https://news.ycombinator.com/item?id=16745042
const container = document.evaluate('//a', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE , null)
const items = Array.from(Array(container.snapshotLength).keys())
const _links = items.map(i => container.snapshotItem(i).innerHTML).filter(text => text.indexOf("http")!=-1)
const links = _links.sort().filter((e,i,a) => a.indexOf(e)==i)
console.log("Unique links \n" + links.join("\n"))
let map = {}
_links.sort().map(link => link.substring(0,100)).forEach(link => map[link] = (map[link] || 0) + 1)
Object.entries(map).sort(kv => kv[1]).map(kv =>  kv[1].toString().padEnd(4) + kv[0]).join("\n")


console.log(links.join("\n"))
```

* https://atom.io/packages/list?direction=desc&sort=stars
* https://atom.io/packages/list?direction=desc&sort=downloads

```Javascript
let links = [...document.getElementsByTagName("a")].filter(link => link.href.endsWith("pdf")).map(link => link.href)

function downloadURI(uri) {
  var link = document.createElement("a");
  link.href = uri;
  document.body.appendChild(link);
  link.click();
  delete link;
}
```

### How to extract youtube motivational speech text

* https://www.youtube.com/watch?v=I22Lf0xF0UE and skip advertisement
*  find "...More" button, and click that button and select "Transcript"
*  Select english as language
*  press f12 > select console
*  paste below lines of code, and get transcript.


```Javascript
{
    let transcriptLines = [...document.getElementById("transcript-scrollbox").childNodes]
    let text = transcriptLines.map(text => text.childNodes[1].innerHTML) 
    let result = text.filter(text => text.indexOf("MUSIC") == -1) 
    console.log(result)
}
```


```Javascript
{
    let transcript = [...document.getElementsByClassName("ytd-transcript-renderer")].filter(element => element.id == "body")[0].innerText
 let result = transcript.split("\n").filter(text => text.indexOf(":") == -1).filter(text => text.toLocaleLowerCase().indexOf("music") == -1)
 console.log(result.join("\n"))
}
```