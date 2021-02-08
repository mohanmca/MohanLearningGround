let fs = require('fs')
var args = process.argv.slice(2);
let file = args[0];
let content = fs.readFileSync(file)
let tweets = JSON.parse(content)
let result = Object.values(tweets['globalObjects']['tweets']).map(t => t.full_text).map( v => v.split("\n")).map(vs => toText(vs)).join("\n");
console.log(result);

function toText(values) {
     let header = values[0];
     let body = values.slice(1).join("\n");
     let value = `### ${header} \n\n* ${body}\n\n\n----`
     return value;
}
