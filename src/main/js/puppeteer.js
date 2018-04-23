/**
 * @name screenshots
 *
 * @desc Snaps a basic screenshot of the full New York Time homepage and saves it a .png file.
 *
 * @see {@link https://github.com/GoogleChrome/puppeteer/blob/master/docs/api.md#screenshot}
 */

const fs = require('fs');
const puppeteer = require('puppeteer');
const jsonStream  = fs.createWriteStream('myOutput.json');


let result = (async () => {      
    const browser = await puppeteer.launch({
        headless: true,
        devtools: false
    })
    const page = await browser.newPage()
    const resolutions = [{
        width: 1280,
        height: 800
    }, {
        width: 1920,
        height: 1080
    }]
    await page.setViewport(resolutions[1])
    await page.on('console', msg => console.log('PAGE LOG:', msg.text()));
    await page.goto('http://www.religareonline.com/market/stock/nse-bse-bulk-deals')
    await page.screenshot({
        path: 'bulk-deals_1.png',
        fullPage: true
    })

    await page.evaluate(() => {
        window.hTableToJson = function (table) {
            var rows = Array.from(table.rows)
            var content = rows.map(row => Array.from(row.cells).map(cell => cell.innerText))
            var headers = content[0]
            var result = content.map(record => {
                var r = {};
                record.map((v, i, a) => r[headers[i]] = v.replace(/^\s+|\s+$/g, ''));
                delete r[""];
                return r
            })
            return result;
        }

        window.writeFile = function(path, data, opts='utf8') {
            new Promise((res, rej) => {
                fs.writeFile(path, data, opts, (err) => {
                    if (err) rej(err)
                    else res()
                })
            })
        };
    })

    await page.on('console', msg => console.log('PAGE LOG:', msg.text()));
    const bulkDeals1 = await page.evaluate(() => {
        var table = document.getElementById("fnCtrBulkDealsListingLibTblBulkDeals_BSE_Recent_Listing")
        return hTableToJson(table)
    })

    await page.type("#txtPageNumber", "2")
    await page.click("a.pgbtn-next")
    await page.waitForSelector('#fnCtrBulkDealsListingLibTblBulkDeals_BSE_Recent_Listing')
    const bulkDeals2 = await page.evaluate(() => {
        var table = document.getElementById("fnCtrBulkDealsListingLibTblBulkDeals_BSE_Recent_Listing")
        return hTableToJson(table)
    })

    let bulkDeals = await [].concat(bulkDeals2).concat(bulkDeals1);
    let content = await JSON.stringify(bulkDeals, '', 2)

    await jsonStream.write(JSON.stringify(content,'',2))

    return {
        content:  content,
        result: "test"
      }
})()


result.then((content) => console.log(content))