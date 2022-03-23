//Sample for class
class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    toString() {
        return this.name + "-" + this.age;
    }
}

let p = new Person('Mohan', 14);
console.log( JSON.stringify(p) )

function Person2(name, age) {
    this.name = name;
    this.age = age;
}

Person2.prototype.toString = function() {
    return "Person2-" + this.name + '-' + this.age;
}

p = new Person2('Mohan', 15);
console.log(p.toString());

console.log( JSON.stringify(p) )