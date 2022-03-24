## MessageType tag (35)
* 0 - Heart Beat
* 8 - Execution Report
* A - Logon
* R - QuoteRequest
* S - Quote
* U - Privately defined
* AI - Quote Status report
* AG - Quote Request Reject


## Fix grep two fields and its values - Using grep to extract specific FIX protocol tags values from a file

* ```awk -F '(55|6)=' -v OFS='\t' '/39=1/{sub(/\|$/, "", $2); print $2, $3}' file```
* ```grep -oP '\b(55|6)=\K[^|]+' file | paste  - -```
* ```grep -o -w '\(48\|55\)=[^[:cntrl:]]\+' FILE | perl -pe 's/(\d+=[^\n]+)\n/\1~/' | perl -pe 's/([^~]+~[^~]+)~/\1\n/g'```
* ```grep -o -w '\(48\|55\)=[^[:cntrl:]]\+' FILE | tr '\n' '~' | perl -pe 's/([^~]+~[^~]+)~/\1\n/g'```
* ```grep -o -w '\(48\|55\)=[^[:cntrl:]]\+' FILE | sed 'N;s/\n/ /'```
## Reference
