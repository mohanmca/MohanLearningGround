## Local Setup for pyspak
1. /Users/alpha/git/KavinPython/venv/bin/pip
2. pip install jupyterlab
3. jupyter-lab
4. jupyter notebook
5. jupyter notebook


## How to create Anki card - from https://spark.apache.org/docs/latest/api/java/

```javascrtpt
let elements = Array.from(document.getElementsByClassName("colLast"))
elements = elements.slice(1)
let count = 1;
for (var e in elements) {	  
	let items = Array.from(elements[e].childNodes);
	if(items.length > 2) {
		console.log('## ' + count +". " + Array.from(elements[e].childNodes)[2].textContent ) ;
		console.log(" ")
		console.log("* " + Array.from(elements[e].childNodes)[0].textContent ) ;
	}
	count++;
}


let header = c[2].textContent
let detail = c[0].textContent 
```
