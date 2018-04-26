window.$x = xPath => document.evaluate(
    xPath,
    document,
    null,
    XPathResult.FIRST_ORDERED_NODE_TYPE,
    null
).singleNodeValue;

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