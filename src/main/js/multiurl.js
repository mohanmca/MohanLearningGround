let http = require('http')
let fs = require('fs')

let fileName = process.argv[2]
let urls = fs.readFileSync(fileName).toString().split("\n").map(line => line.trim()).filter(line => line.length > 10)

console.log(urls)

urls
    .map(url => {
        let components = url.split("/")
        let fileName = components[components.length - 1]
        http.get(url, (response) => {
            if (response.statusCode == 200) {
                let imagedata = [];
                response.on('data', (chunk) => {
                    imagedata.push(chunk);
                }).on('end', function () {
                    writeToBinaryFile(fileName, Buffer.concat(imagedata))
                })
            } else {
                console.log(`${response.statusCode} - for ${url}`);
            }
        })
    })

function writeToBinaryFile(fileName, buffer) {
    fs.writeFile(fileName, buffer, 'binary', function (err) {
        if (err) throw err
        console.log('File saved.')
    });
}