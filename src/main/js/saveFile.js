const fs = require('fs');

 function writeFile(path, data, opts = 'utf8') {
    new Promise((res, rej) => {
        fs.writeFile(path, data, opts, (err) => {
            if (err) rej(err)
            else res()
        })
    })
}

writeFile("/mohan/content.json", "test")