* Every time once node is installed or upgraded
  * npm install -g yarn babel-cli webpack webpack-cli nodemon
  * npm install --save-dev babel-preset-env
  * yarn add -D webpack-dev-server 
  
* Generate project with webpack and babel
  * ```bash
       npm install -g yo
       npm install -g generator-simple-webpack
       yo simple-webpack
    ```    
* NPX is not available on windows when Node is installed using NVM
  * npm i -g npx

* window RefError when using webpack for node project
  * target: "node" and remove other loaders related to html
  
* Raw babel compiler
  * npx babel src --out-dir dist

* (node:5904) UnhandledPromiseRejectionWarning: Error: Evaluation failed: ReferenceError: _toConsumableArray is not defined
  *   var rows = [...table.rows] was changed to var rows = Array.from(table.rows)

* TypeError: (intermediate value)(intermediate value)(...) is not a function

* Never mix the logic of extracting data from dom-node and extracting dom-node
  * Extract dom node
  * Finally extract data from dom node

* babel-node is buggy, always try to use node    

## webpack
  * [Compiler plugin](https://github.com/webpack/docs/wiki/plugins)
  * webpack --watch src\shorten.js dist\app.js

## VSCODE
* how-to-i-hide-node_modules-files-from-the-sidebar-in-visual-studio-code
  * File > Preferences > Settings > "Workspace Settings"
```json
 "settings": {
	"files.exclude": {
		"node_modules/": true
	}	
 }
```

## PUPPETEER
* await page.addScriptTag({ path: './node_modules/varname/build/varname.js' });
* Try to avoid raising events, but manipulate dom if possible instead of events 