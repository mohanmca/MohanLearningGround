import { launch } from "puppeteer";
import { writeFile, existsSync, openSync, rmSync, statSync } from "fs";

/**
 * 1. if file already exists skip the request
 * 2. create folder using year > month > date.txt
 * 3. If there are file with error, download and remove the error file upon successful download
 * 4. if given word exists... mark the file with __xxx__
 * 5. gender-false-female
 */

let gender = false;
let isShuffle = true;
const download = async function (year, month, date) {
  const browser = await launch({ headless: true, slowMo: 100 });
  // const browser = await launch({headless: true});
  const page = await browser.newPage();
  const navigationPromise = page.waitForNavigation();

  await page.goto(
    "https://chennaicorporation.gov.in/gcc/online-services/birth-certificate/"
  );

  await page.setViewport({ width: 1920, height: 1020 });

  if (gender) {
    await page.waitForSelector("#gender-1");
    await page.click("#gender-1");
  } else {
    await page.waitForSelector("#gender-2");
    await page.click("#gender-2");
  }

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

  const regcaptchNo = await page.evaluate(
    () => document.getElementById("txtCaptcha").value,
    (element) => element.textContent
  );
  page.on("console", (msg) => {
    console.dir(regcaptchNo);
  });

  await page.$eval(
    "input[name=regcaptchNo]",
    (el, value) => {
      el.value = value;
    },
    regcaptchNo
  );

  await page.waitForSelector("#sel_day");
  await page.click("#sel_day");

  await page.select("#sel_day", date);

  await page.waitForSelector("#sel_day");
  await page.click("#sel_day");

  await page.waitForSelector("#form-btn1");
  await page.click("#form-btn1");

  await navigationPromise;

  let [file, error_file] = getFileName(year, month, date);
  try {
    await page.waitForSelector(".card-body > .tableBorder > tbody");

    writeFile(file, await page.content(), function (err) {
      if (err) return console.log(err);
    });
  } catch (error) {
    writeFile(error_file, "error", function (err) {
      if (err) return console.log(err);
    });
    console.log(file + "....." + error);
  }

  await browser.close();
};

Date.prototype.addDays = function (days) {
  var date = new Date(this.valueOf());
  date.setDate(date.getDate() + days);
  return date;
};

function getDates(startDate, stopDate) {
  var dateArray = new Array();
  var currentDate = startDate;
  while (currentDate <= stopDate) {
    dateArray.push(new Date(currentDate));
    currentDate = currentDate.addDays(1);
  }
  return dateArray;
}

let fd = new Date("1967-01-01");
let td = new Date("1977-12-31");

let dates = getDates(fd, td).reverse();
if (isShuffle) {
  dates = shuffle(dates);
}

let i = 0;
while (dates.length > 0) {
  i++;
  let d = dates.pop();
  let m = d.getMonth() + 1;
  let year = d.getFullYear() + "";
  let month = ("0" + m).slice(-2) + "";
  let date = ("0" + d.getDate()).slice(-2) + "";

  let [file, error_file] = getFileName(year, month, date);
  try {
      if (existsSync(error_file)) {
          console.log("File removed .. " + error_file + " .." + file);
          rmSync(file, { force: true });
          rmSync(error_file, { force: true });
       }
  } catch(error) {
        console.log("Race condition for " + error_file + " Or Empty : " + file);
  }

  try {
       if(statSync(file).size == 0) {
            rmSync(file, { force: true });
            console.log("Empty file was removed .. " + file);
       }
  } catch(error) {
        console.log("Race condition for Empty : " + file);
  }

  if (!existsSync(file)) {
     try {
            openSync(file, "w");
            console.log("Initiating download form " + file);
            if(i % 5 == 0) {
                await download(year, month, date);
            }  else {
                download(year, month, date);
            }
    } catch(error) {
        console.log("Race condition for " + file);
    }
  } else {
    console.log("File already exists for " + file);
  }
}

function getFileName(year, month, date, isError) {
  let result = year + "_" + month + "_" + date + ".txt";
  const xes = gender ? "male" : "female";
  const file = `result/${xes}/` + result;
  return [file, file + ".error"];
}

function shuffle(original) {
  let copy = [].concat(original);
  copy.sort(() => 0.5 - Math.random());
  return copy;
}

console.log("File download completed!");