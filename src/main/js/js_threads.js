const fs = require('fs')

function reverse(content) {
  let data = content.split("")
  return data.map( (char,index) => data[data.length -index-1]).join('')
}

let content = fs.readFileSync("dummy.json").toString('utf-8');

function getJson() {
  return content;
}

function time(func) {
  var t1 = (new Date()).getTime();
  let output = func();
  var t2 = (new Date()).getTime()
  console.log("Time taken to complete " + func.name + ". " + (t2-t1));  
  return output;

}

function process() {
  console.log("Starting costly operation ");
  let json = getJson();
  let tenNumbers = [...Array(99999).keys()]
  let input = tenNumbers.map(number => getJson().replace("e","EE") )
  input = input.map(json => reverse(json))
  return input;
}
let boundFun = () => time(process)

setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);
setTimeout(boundFun, 0);


console.log("Completed!")