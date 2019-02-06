/**
 * var table = document.getElementById("ranking")
 * console.log(hTableToJson(table))
 */

function hTableToJson(table) {
	var rows = [...table.rows]
	var content = rows.map(row => Array.from(row.cells).map(cell => cell.innerText))
	var headers = content[0]
	var result = content.map(record => { var r = {}; record.map( (v,i,a) => r[headers[i]] = v.replace(/^\s+|\s+$/g,'') ); delete r[""]; return r } )
	return JSON.stringify(result, null, 2);
}

function downloadURI(uri, name) {
  var link = document.createElement("a");
  link.download = name;
  link.href = uri;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  delete link;
}

function download(url) {
	xmlhttp=new XMLHttpRequest()
	xmlhttp.open("GET", url, false)
	xmlhttp.send()
	return xmlhttp.responseText
}

function parseHtml(string) {
  let parser = new DOMParser()
  let contentDom = parser.parseFromString(content, "text/xml")
  return contentDom;
}

/**
 * @param {*} scriptUrl 
 * addScript('https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.5.1/lodash.js')
 * _.filter([1,2,3], x => x%2 == 0)
 * addScript('https://cdnjs.cloudflare.com/ajax/libs/rxjs/5.4.2/Rx.js')
 * Rx.Observable.range(1,3)
 * 
 */
function addScript(scriptUrl) {
 var script = document.createElement('script');
 script.type = 'text/javascript';
 script.src = scriptUrl;
 document.head.appendChild(script);
}

/**

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

 */

let dailyJs = {
  hTableToJson,
  downloadURI,
  download,
  addScript,
  parseHtml
}

module.exports = dailyJs