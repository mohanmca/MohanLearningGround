const fs = require('fs');

function writeFile(path, data, opts = 'utf8') {
    new Promise((res, rej) => {
        fs.writeFile(path, data, opts, (err) => {
            if (err) rej(err)
            else res()
        })
    })
}
let fileName = "1.txt";

fs.openSync(fileName, 'w');

fs.writeFile(fileName, "error", function (err) {
  if (err) return console.log(err);
});


//writeFile("/mohan/content.json", "test")