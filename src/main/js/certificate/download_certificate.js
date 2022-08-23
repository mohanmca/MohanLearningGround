import { launch } from "puppeteer";
import { writeFile, existsSync, openSync } from 'fs';

/**
* 1. if file already exists skip the request
* 2. create folder using year > month > date.txt
* 3. If there are file with error, download and remove the error file upon successufl download
* 4. if given word exists... mark the file with __xxx__
*/

const download = async function (year, month, date) {
  const browser = await launch({headless: true,  slowMo: 100});
  // const browser = await launch({headless: true});
  const page = await browser.newPage();
  const navigationPromise = page.waitForNavigation();

  await page.goto(
    "https://chennaicorporation.gov.in/gcc/online-services/birth-certificate/"
  );

  await page.setViewport({ width: 1920, height: 1020 });

  await page.waitForSelector("#gender-2");
  await page.click("#gender-2");

  await page.waitForSelector("#sel_year");
  await page.click("#sel_year");

  await page.select("#sel_year", year);

  await page.waitForSelector("#sel_year");
  await page.click("#sel_year");

  await page.waitForSelector("#sel_month");
  await page.click("#sel_month");

  await page.select("#sel_month", month);

  await page.waitForSelector("#sel_month");
  await page.click("#sel_month");

  await page.waitForSelector("#txtCaptcha_span");
  await page.click("#txtCaptcha_span");

  await page.waitForSelector("#txtCaptcha_span");
  await page.click("#txtCaptcha_span");


  const regcaptchNo = await page.evaluate( () => document.getElementById("txtCaptcha").value, element => element.textContent );
  page.on('console', msg => { console.dir(regcaptchNo);  });

  await page.$eval('input[name=regcaptchNo]', (el, value) => { el.value = value }, regcaptchNo);

  await page.waitForSelector("#sel_day");
  await page.click("#sel_day");

  await page.select("#sel_day", date);

  await page.waitForSelector("#sel_day");
  await page.click("#sel_day");

  await page.waitForSelector("#form-btn1");
  await page.click("#form-btn1");

  await navigationPromise;
  let result = year+"_"+month+"_"+date+".txt";

 try {
    await page.waitForSelector('.card-body > .tableBorder > tbody')

    const file = "result/" + result;
    writeFile(file, (await page.content()), function (err) {
      if (err) return console.log(err);
    });

 } catch(error) {
    const file = "result/" + result+".error";
    writeFile(file, "error", function (err) {
      if (err) return console.log(err);
    });
    console.log(result + "....." + error);
 }

  await browser.close()
};

Date.prototype.addDays = function(days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date;
}

function getDates(startDate, stopDate) {
    var dateArray = new Array();
    var currentDate = startDate;
    while (currentDate <= stopDate) {
        dateArray.push(new Date (currentDate));
        currentDate = currentDate.addDays(1);
    }
    return dateArray;
}


let fd = new Date("1981-01-01");
let td = new Date("1987-01-01");

let dates = getDates(fd, td);
let i =0;
for(let d of dates) {
    i++;
    let date = ("0" + d.getDate()).slice(-2) + "";
    let m = d.getMonth()+1;
    let month = ("0" + m).slice(-2)+"";
    let year = d.getFullYear()+"";

    let result = year+"_"+month+"_"+date+".txt";
    const error_file = "result/" + result+".error";
    const file = "result/" + result;
    if(!existsSync(file) || existsSync(error_file)) {
        openSync(file, 'w');
        await download(year, month, date);
    } else {
        console.log("File already exists for " + file);
    }
}

