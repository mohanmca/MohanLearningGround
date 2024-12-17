## Nested array questions, flatten array and filter arrays (inside parrent array or nested array), where inner array object has one of the attribute 'correlationId'

```bash
jq 'flatten' working_nested_array.json
jq '.[] | select(any(.[]; has("correlationId")))' working_json.json
```


## How to combine multi-line independent json inside a file
```bash
jq . -s input_file.json
```

## How to list unique keys of arry of objects
```
jq 'map(keys) | add | unique' 29517092.json
```

## In array object select all object that doesn't have attribute position

```bash
jq '.' q 29517092.json | jq 'map(select(has("position") | not))'
```

## how to filter all the objects inside an array where objects attribute match some label

```
jq '.components | .[] | select(.status!="Up")'  healthcheck.json
```

## How to generate json using null-input
1. JQ can be used as calculator
1. This is useful when you want to generate JSON data from scratch or work with predefined JSON structures directly
```json
jq -n '.name = "mohan"'

{
  "name": "mohan"
}
jq  -n '1+2'       
3
```

## What is the use of -r or --raw-input

```
echo "apple\nbanana\norange" | jq --raw-input .
echo "Hello, World!" | jq --raw-input 'ascii_downcase'
```

## What is the use of -s or --slurp
1. Read the entire input stream into a single JSON array 
2. It assumes [ and ] (before line and after EOF) and , in-between the new-line
3. echo "{"name": "Alice", "age": 30}\n{"name": "Bob", "age": 25}" -s jq
4. 
```bash
echo '{"name": "Alice", "age": 30}\n{"name": "Bob", "age": 25}' |  jq -s -c
```
```json
[{"name":"Alice","age":30},{"name":"Bob","age":25}]
```

## How to filter the array, where one of the objects attribute matches the value

```bash
export val='[[{"name": "Alice", "age": 30},{"name": "Bob", "age": 25},{"name": "Charlie", "gauge": 20}],[{"name": "David", "gauge": 25},{"name": "Eve", "age": 35}]]'
echo $val | jq '.[] | select(any(.[]; .gauge == 25))'
```

## How to compact json file using jq

```
jq -c '.' data.json > temp.json && mv temp.json data.json
jq -c '.' -i data.json
```

## How to merge two two-dimensional array into one two dimensional array

```
[[[1, 2], [3, 4]], [[5, 6], [7, 8]]]
jq '.[][]' data.json
```
1. [][]: This jq filter accesses each element of the top-level array ([]) and then each element of the nested arrays ([] again), effectively flattening and merging the inner arrays into a single two-dimensional array.

## Frequently used jq

1. jq '.status.addresses[0].address'
2. jq -r '._embedded.guestbookMessages[] | {name: .name, message: .message}'
3. jq '.[] | select(.location=="Stockholm")' json
4. jq -c 'to_entries[] | select (.value.location == "Stockholm") | [.key, .value.name]' json
5. cat fb_questions.json  | jq '.[]'
6. cat fb_questions.json | jq '.data.companyTag.questions[] | select (.difficulty == "Hard")'

## How to feed data into multiple filters

1. Use COMMA - , to feed to multiple inputs
2. It is similar to tee command in linux
```
cat stackexchange_sites | jq '.items[1:5] | .[].name,.[].site_url'
```

## "Expected another key-value pair at " (For trailing comma)
1. sed -i 's/,[[:space:]]*}/}/g' ff.json

## Handle Array or Array Or Objects
1. jq '.[]| .[1] ' output.json ---> find first object in nested array ```[[{1},{2}],[{1},{2}]]``` -- list only 1s
2.  jq '.[] | select(.[1].partitionId == 2)  ' output.json  --> in array of array, select the arrays whose first objects attribute partitionId==2

## Reference
1. [JQ Cheatsheet](https://lzone.de/cheat-sheet/jq)
2. [Zendesk Cheatsheet](https://developer.zendesk.com/documentation/integration-services/developer-guide/jq-cheat-sheet/)


## Find all the question that has array tag, but find relevant tag most used along with Array 

```
cat fb_questions.json | jq '.data.companyTag.questions[] | select (.topicTags[].name == "Array")'
11       "slug": "union-find",
➜  patterns git:(master) cat fb_questions.json | jq '.data.companyTag.questions[] | select (.topicTags[].name == "Array")' | grep slug | grep -v "array"  | sort | uniq -c | sort -nr
67       "slug": "matrix",
67       "slug": "hash-table",
```

## Generate mdanki
mdanki jq.md jq.apkg --deck "Mohan::DeepWork::jq"
