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
  *   

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
