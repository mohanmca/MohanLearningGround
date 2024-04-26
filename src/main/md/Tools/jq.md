## Frequently used jq

1. jq '.status.addresses[0].address'
2. jq -r '._embedded.guestbookMessages[] | {name: .name, message: .message}'
3. jq '.[] | select(.location=="Stockholm")' json
4. jq -c 'to_entries[] | select (.value.location == "Stockholm") | [.key, .value.name]' json
5. cat fb_questions.json  | jq '.[]'
6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.difficulty == "Hard")'

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