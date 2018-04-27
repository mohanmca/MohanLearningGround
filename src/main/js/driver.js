/**
 * @name screenshots
 *
 * @desc Snaps a basic screenshot of the full New York Time homepage and saves it a .png file.
 *
 * @see {@link https://github.com/GoogleChrome/puppeteer/blob/master/docs/api.md#screenshot}
 */

const fs = require('fs');
const puppeteer = require('puppeteer');
const selectRecords = require('./selectRecords');
const selectPastRecords = require('./selectPastRecords');

const timeString = (new Date).toISOString().slice(0,16).replace(/-|:/g,'_')
const outFileName = `rippedRecords_${timeString}.json`
const jsonStream = fs.createWriteStream(outFileName);
let content = []

async function run() {
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
    await page.addScriptTag({
        path: './helperFunctions.js'
    });
    await page.goto('http://www.religareonline.com/market/stock/nse-bse-bulk-deals')
    await page.on('console', msg => console.log('PAGE LOG:', msg.text()));
    let records = await page.evaluate(selectRecords)
    records.result.forEach((record) => content.push(record))
    let numberOfTables = (records.lastPage)
    //let numberOfTables = 10    
    for (let j = 2; j <= numberOfTables; j++) {
        await page.evaluate((txtPageNumber) => {
            document.querySelector('#txtPageNumber').value = txtPageNumber;
            document.querySelector('a.pgbtn-next').click();
        }, j);

        await page.waitForSelector('#LibTblBulkDeals_BSE_Recent_Listing')
        let values = await page.evaluate(selectPastRecords)
        values.result.forEach((record) => content.push(record))
        console.log("Size of the content " + content.length)
    }

    return content;

}

function fixColumn(o) {
    Object.keys(o).map(key => {
        let temp = o[key];
        delete o[key];
        key = key.replace(/,/g, '_')
            .replace(/\n/g, '_').replace(/\//g, 'O').replace(/\s+/g, '_')
            .replace(/\(/g, '')
            .replace(/\)/g, '')
            .replace(/_+/g, '_')
        o[key] = temp
    })
    return o;
}

run().then((content) => {
    let output = content.map(fixColumn)
    let result = {
        output
    }
    jsonStream.write(JSON.stringify(result, '', 2));
    jsonStream.close();
    process.exit()
})