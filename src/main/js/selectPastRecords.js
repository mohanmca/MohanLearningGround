 const selectPastRecords = () => {
    let table = document.getElementById("LibTblBulkDeals_BSE_Recent_Listing")
    let rows = Array.from(table.tBodies[0].rows)
    
    let [header, ...tail] =  Array.from(rows)
    let columnHeaders = Array.from(header.cells).map(cell => cell.innerText)
    let content = tail.map(row => Array.from(row.cells).map(cell => cell.innerText))    
    let result = content.map(record => {
        var r = {};
        record.map((v, i, a) => r[columnHeaders[i]] = v.replace(/^\s+|\s+$/g, ''));
        delete r[""];
        return r
    })

    /** end-of parsing the table into json */
    console.log("Size of the content ~~" + result.length)
     
    return {
        result
    };
};

module.exports = exports = selectPastRecords;