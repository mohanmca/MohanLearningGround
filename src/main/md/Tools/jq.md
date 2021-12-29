## Frequently used jq

1. jq '.status.addresses[0].address'
2. jq -r '._embedded.guestbookMessages[] | {name: .name, message: .message}'
3. jq '.[] | select(.location=="Stockholm")' json
4. jq -c 'to_entries[] | select (.value.location == "Stockholm") | [.key, .value.name]' json
5. cat fb_questions.json  | jq '.[]'
6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.difficulty == "Hard")'