 const selectRecords = () => {
    let table = document.getElementById("fnCtrBulkDealsListingLibTblBulkDeals_BSE_Recent_Listing")
    let lastControl = document.querySelectorAll('#tnt_pagination li.Control')[1]
    let lastPage = parseInt(lastControl.previousSibling.textContent.match(/[0-9]+/g)[0])
    debugger;
    
    /** start parsing the table into json */
    let rows = Array.from(table.rows)
    let [header, ...tail] =  Array.from(table.rows)
    let columnHeaders = Array.from(header.cells).map(cell => cell.innerText)
    let content = tail.map(row => Array.from(row.cells).map(cell => cell.innerText))    
    let result = content.map(record => {
        var r = {};
        record.map((v, i, a) => r[columnHeaders[i]] = v.replace(/^\s+|\s+$/g, ''));
        delete r[""];
        return r
    })
    /** end-of parsing the table into json */
     
    return {
        result,
        lastPage
    };
};

module.exports = exports = selectRecords;