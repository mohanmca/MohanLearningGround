## Electron (known as Atom shell)

1. nodejs + chromium
2. has access to local file-system and system api
3. we can use common-js without transpilation
4. Targeting only one browser


## Electron Basics

1. Every Electron app starts with a main script, very similar to how a Node.js application is started. 
2. The main script runs in the "main process". 
3. To display a user interface, the main process creates renderer processes – usually in the form of windows, which Electron calls  BrowserWindow. 
4. To get started, pretend that the main process is just like a Node.js process. All APIs and features found in Electron are accessible through the electron module, which can be required like any other Node.js module. 
5. The default fiddle creates a new BrowserWindow and loads an HTML file.

## BrowserWindow inside Electron

1. In the default fiddle, this HTML file is loaded in the  BrowserWindow. 
2. Any HTML, CSS, or JavaScript that works in a browser will work here, too. 
3. In addition, Electron allows you to execute Node.js code. 
4. Take a close look at the  <script /> tag and notice how we can call require() like we would in Node.js. 
5. This is the script we just required from the HTML file. In here, you can do anything that works in Node.js and anything that works in a browser. 
6. By the way: If you want to use an npm module here, just  require it.
   1. Electron Fiddle will automatically detect that you requested a module and install it as soon as you run your fiddle.


## Code base

1. package.json
   1. main: src/main.js
   2. scripts/start : 'electron .'
## Sample code
```java
const electron = require('electron')
const app = electron.app
const BrowserWindow = electron.BrowserWindow

app.on('ready', () => {
    new BrowserWindow({
            height: 400, width: 400
    })
})
```