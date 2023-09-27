## MessageType tag (35)
* 0 - Heart Beat
* D - Order Single
* 8 - Execution Report
* A - Logon
* R - QuoteRequest
* S - Quote
* U - Privately defined
* AI - Quote Status report
* AG - Quote Request Reject

## Swap RFQ Message Types - 35

1. R - QuoteRequest
2. S - Quote
3. I - MassQuote (Contribution)
4. AH - Rfq Request
5. AJ - Quote Response
6. CW - Quote Ack
7. AG - Quote Request Sent
8. AI - Quote Status Report

## Often used tags
1. 55 - Symbol
2. 58 - SecurityId
3. 132 - Bid Price
4. 133 - Offer Price
5. 423 - Price-Type
   1. 9 - yield (if value == 9)
6. 131 - QuoteRequestId
7. 693 - QuoteRespID
8. 11 - ClOrdId
9. 49 - Sender CompId
10. 34 = MsgSeqNumber
11. 44 - Price
12. 54 - Side
    1. 1 Buy, 2 Sell, 3 Buy Minus, 4 Sell plus, 5 Sell short, 6 Sell short exempt, 7 Undisclosed
13. 523- PartySubId

## AJ Message Type
1. The execution venue sends QuoteResposnse(AJ) with QuoteRespType (694) = 110 (Request to Consent)
2. The execution venue sends QuoteResposnse(AJ) with QuoteRespType (694) = 7 (End Trade)
3. The execution venue sends QuoteResposnse(AJ) with QuoteRespType (694) = 8 (Timeout)

## Contribution
1. 


## Fix grep two fields and its values - Using grep to extract specific FIX protocol tags values from a file

* ```awk -F '(55|6)=' -v OFS='\t' '/39=1/{sub(/\|$/, "", $2); print $2, $3}' file```
* ```grep -oP '\b(55|6)=\K[^|]+' file | paste  - -```
* ```grep -o -w '\(48\|55\)=[^[:cntrl:]]\+' FILE | perl -pe 's/(\d+=[^\n]+)\n/\1~/' | perl -pe 's/([^~]+~[^~]+)~/\1\n/g'```
* ```grep -o -w '\(48\|55\)=[^[:cntrl:]]\+' FILE | tr '\n' '~' | perl -pe 's/([^~]+~[^~]+)~/\1\n/g'```
* ```grep -o -w '\(48\|55\)=[^[:cntrl:]]\+' FILE | sed 'N;s/\n/ /'```

## Reference
