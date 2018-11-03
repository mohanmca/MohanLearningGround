let data = [
    {
        "type": "employee",
        "name": "Mohan", "phone": "2932382", "bob": 24
    },
    {
        "type": "employee",
        "name": "Mohan", "phone": "2932382", "age": 24
    },
    {
        "type": "employee",
        "name": "Mohan", "title": "President", "age": 24
    },
    {
        "type": "customer",
        "name": "Mohan", "title": "President", "age": 24
    },
    {
        "type": "customer",
        "name": "Mohan", "age": 24
    }
]


function groupBy(list, keyGetter) {
    const map = new Map();
    list.forEach((item) => {
        const key = keyGetter(item);
        const collection = map.get(key);
        if (!collection) {
            map.set(key, [item]);
        } else {
            collection.push(item);
        }
    });
    return map;
}

let dataByIndex  = groupBy(data, data => data.type);
const onlyUnique = (value, index, self) =>   self.indexOf(value) === index
let reducer =  (accu, data) => {Array.prototype.push.apply(accu, Object.keys(data)); return accu }

let parseTypeVsColumns = function (data) {
	let dataByIndex = groupBy(data, data => data.type);
	let _t = {}
    let _data = [...dataByIndex.keys()]
    _data.forEach(type => _t[type] = dataByIndex.get(type).reduce(reducer,[]).filter(onlyUnique) )
	return _t;
}

console.log(parseTypeVsColumns(data))