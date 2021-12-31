## Frequently used jq

1. jq '.status.addresses[0].address'
2. jq -r '._embedded.guestbookMessages[] | {name: .name, message: .message}'
3. jq '.[] | select(.location=="Stockholm")' json
4. jq -c 'to_entries[] | select (.value.location == "Stockholm") | [.key, .value.name]' json
5. cat fb_questions.json  | jq '.[]'
6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.difficulty == "Hard")'

## Find all the question that has array tag, but find relevant tag most used along with Array 

6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.topicTags[].name == "Array")'
   11       "slug": "union-find",
   ➜  patterns git:(master) cat fb_questions.json | jq '.data.companyTag.questions[] | select (.topicTags[].name == "Array")' | grep slug | grep -v "array"  | sort | uniq -c | sort -nr
   67       "slug": "matrix",
   67       "slug": "hash-table",
   64       "slug": "sorting",
   56       "slug": "dynamic-programming",
   51       "slug": "binary-search",
   37       "slug": "two-pointers",
   32       "slug": "string",
   29       "slug": "greedy",
   29       "slug": "breadth-first-search",
   28       "slug": "math",
   25       "slug": "heap-priority-queue",
   19       "slug": "design",
   19       "slug": "depth-first-search",
   19       "slug": "backtracking",
   18       "slug": "stack",
   17       "slug": "prefix-sum",
   15       "slug": "divide-and-conquer",
   14       "slug": "simulation",
   14       "slug": "bit-manipulation",
   13       "slug": "monotonic-stack",
   11       "slug": "union-find",
   11       "slug": "sliding-window",
   9       "slug": "counting",
   8       "slug": "queue",
   7       "slug": "trie",

