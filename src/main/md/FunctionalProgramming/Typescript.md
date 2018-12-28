```Typescript
=> is called fat arrow or lambda operator

var add: (x: number, y: number) => number = function(x: number, y: number) : number {
	return x+y
}

var $ = (id) => document.getElementBy(id);

above is equivalent to

var $ = function(id) { return document.getElementBy(id); };

//id is called inline parameter (since it doesn't have type parameter)
// => fat arrow is separator between parameter and function body


class Calc {
	add(x: number, y: number): number {
		return x+y;
	}
}

---

function add(msg: string, x: number, y: number) {
	console.log(msg + (x+y));
}

add('Total =',3,4);

function buildName(firstName: string, lastName = "Smith") {
    return firstName + " " + lastName;
}

var result1 = buildName("Bob");  //works correctly now, also
var result2 = buildName("Bob", "Adams", "Sr.");  //error, too many parameters
var result3 = buildName("Bob", "Adams");  //ah, just right

---
Optional parameters and default parameters also share what the type looks like. Both:

function buildName(firstName: string, lastName?: string) {
and
function buildName(firstName: string, lastName = "Smith") {

share the same type "(firstName: string, lastName?: string)=>string". 
The default value of the default parameter disappears, leaving only the knowledge that the parameter is optional.

---

function buildName(firstName: string, ...restOfName: string[]) {
	return firstName + " " + restOfName.join(" ");
}

var employeeName = buildName("Joseph", "Samuel", "Lucas", "MacKinzie");

```
