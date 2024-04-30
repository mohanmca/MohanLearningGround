## Frequently used jq

1. jq '.status.addresses[0].address'
2. jq -r '._embedded.guestbookMessages[] | {name: .name, message: .message}'
3. jq '.[] | select(.location=="Stockholm")' json
4. jq -c 'to_entries[] | select (.value.location == "Stockholm") | [.key, .value.name]' json
5. cat fb_questions.json  | jq '.[]'
6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.difficulty == "Hard")'


## "Expected another key-value pair at " (For trailing comma)
1. sed -i 's/,[[:space:]]*}/}/g' ff.json

## Handle Array or Array Or Objects
1. jq '.[]| .[1] ' output.json ---> find first object in nested array ```[[{1},{2}],[{1},{2}]]``` -- list only 1s
2.  jq '.[] | select(.[1].partitionId == 2)  ' output.json  --> in array of array, select the arrays whose first objects attribute partitionId==2

## Reference
1. [JQ Cheatsheet](https://lzone.de/cheat-sheet/jq)
2. [Zendesk Cheatsheet](https://developer.zendesk.com/documentation/integration-services/developer-guide/jq-cheat-sheet/)


## Find all the question that has array tag, but find relevant tag most used along with Array 

6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.topicTags[].name == "Array")'
   11       "slug": "union-find",
   ➜  patterns git:(master) cat fb_questions.json | jq '.data.companyTag.questions[] | select (.topicTags[].name == "Array")' | grep slug | grep -v "array"  | sort | uniq -c | sort -nr
   67       "slug": "matrix",
   67       "slug": "hash-table",

## Generate mdanki
mdanki jq.md jq.apkg --deck "Mohan::DeepWork::jq"
