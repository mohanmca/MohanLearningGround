```javascript
var hotels = JSON.parse(fs.readFileSync('./hotels.json').toString())
hotels.forEach(h => fs.writeFileSync("./data/" + h.ownerKey+".json", JSON.stringify(h,null,2), {} ))
```

```bash
curl --insecure -u admin:admin  -X DELETE https://localhost:9200/hotels/
curl --insecure -u admin:admin  -X PUT -H "Content-Type: application/json" https://localhost:9200/hotels/ -d @mappings_schema.json
curl --insecure -u admin:admin  -X PUT -H "Content-Type: application/json" https://localhost:9200/hotels/_doc/1 -d @ashdrxfwezp@abc.com.json 
```

## Errors
{"error":{"root_cause":[{"type":"parse_exception","reason":"unknown key [AC] for create index"}],"type":"parse_exception","reason":"unknown key [AC] for create index"},"status":400}
