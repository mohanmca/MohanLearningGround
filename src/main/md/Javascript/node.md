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
* NPM version
  * ^ - As long as major version is same, it would update latest version - Example: ^4.minor.patch (don't care minor and patch version)
  * ~ - More restrictive - As long as major version is same, it would update latest version  - Example: ~4.14.y (don't care patch version, but retains minor and major)

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

## NVM
```bash
nvm ls-remote
nvm list available
nvm install 10.15.0
nvm use 10.15.0
```

## node.js other tools
```
npm instal -g chalk
npm instal -g morgan
npm instal -g nodemon
npm instal -g eslint && eslint --init
node_modules\.bin\eslint app.js --fix
```

## node debug module
```javascript
npm install debug
set DEBUG=* & node app.js
set DEBUG=app & node app.js
app.use(morgan('combined'));
app.use(morgan('tiny'));
```


## express.js notes
* without path module, how to serve html file. File path is problematic
* Ensure following toolings are proper in express.js project
  * nodemon, environment variable for port, npm start, ES6, ESLINT
  * morgan is sample middleware
  * Simplest middleware
    ```js
      app.use((req, res, next)={
        console.log("Middleware!");
        next();
      })
    ```

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

## Reference
* [Node Green](http://node.green)