javascript:(
	function(){}
)();

javascript:alert("Hi Tancho");

View HTML document version
--------------------------

javascript:alert(document.documentMode);

document.write("JavaScript executed.");

Use when JQeury enabled in browser
----------------------------------

javascript:$("#web-nag").hide() 

IMGUR - upvote all the comments
-------------------------------
javascript:void($('.up').not('.pushed').click())


(function (name) {
  console.log(name + " functions can be auto-executed on definition")
})("Anonymous");

/*
Create a clone of a JavaScript object, retaining
all of the attributes of the original object, so 
that the clone can be independently manipulated.

Original and full explanation:
http://oranlooney.com/functional-javascript/

Usage

var jango_fett = new Object();
jango_fett.role = 'father';

var boba_fett = clone(jango_fett);
boba_fett.role = 'son';

console.log(jango_fett.role); // -> 'father'

*/
var clone = function(obj) {
	function Clone() { } 
	Clone.prototype = obj;
	var c = new Clone();
	c.constructor = Clone;
	return c;
};

HashTable
---------

var HashTable = function(name){
  this.name = name;
  this.table = {};
}
 
HashTable.prototype.get = function(key){
  return this.table[key];
}
 
HashTable.prototype.set = function(key, value){
  return this.table[key] = value;
}
 
var table = new HashTable("my table");
table.set("foo", "bar");
console.log(table.get("foo"));

// Javascript Crockford's Functional Inheritance
 
// spec receives instance properties
function animal (spec) {
  // interface object
  var that = {};
 
  // Private
  function breathe () {
    return "I'm breathing";
  }
 
  function say_name () {
    return spec.name || '';
  }
 
  // Public - goes into that
  that.breathe  = breathe;
  that.say_name = say_name;
 
  return that;
}
 
function dog (spec) {
  var that = animal(spec);
 
  // Private
  function bark () {
    return 'ow ow';
  }
 
  // Public
  that.bark = bark;
 
  return that;
}
 
// Main
var toto = dog({name: 'toto'});
console.log(toto.say_name());
console.log(toto.bark());

Classic Javascript
------------------


/*
Notes from Eric Elliott's talk at the fluent conference
JavaScript has FREEDOM.

In classical inheritance you have tight coupling brought on by the parent-child relationship. This is good in some areas but as the project grows, making changes to the parent that ripple through the descendants can be a big burden and error/bug prone. Thus, the need for duplication by necessity. This is the situation where you have a class that does almost everything you want but it needs to do something a little differently so you create a very identical class for your new need. This is caused by the gorilla banana problem. Where you want a banana but what you got was a gorilla holding the banana and the entire jungle. The problem with object-oriented languages is they've got all this implicit environment that they carry around with them.

JavaScript has inheritance that does not have these problems.

"Program to an interface, not an implementation."
"Favor object composition over class inheritance."
-Gang of Four

In JavaScript there is no class.

What is a prototype? It is a working sample.. that's it.

Three types of prototype:

*/

/*
1. Delegate prototype - when you can't find the mehtod you are looking for you look for it in the prototype; you are delegating this happen. This makes flyweights free.
*/

//old way
function Greeter(name){
    this.name = name || 'John Doe';
}
Greeter.prototype.hello = function hello(){
    return 'Hello, my nam is ' + this.name;
}
var george = new Greeter('George');
/*
we create a constructor, attach a method to its prototype, then instantiate an object with the new keyword. This is weird because you don't need the new keyword in javascript because you can just create an object literal and there you have your object. And the whole constructor business seems to be there to please the people who are used to the constructor style of doing things
*/

//new way
//proposed by Douglas Crockford
var proto = {
    hello : function hello(){
        return 'Hello, my nam is ' + this.name;
    }
};
var george = Object.create(proto);
george.name = 'George';
/*
create the prototype object, pass that prototype to Object.create() and there is your new object. You can now set any property you want on that object because JavaScript has dynamic object extending abilities.
*/



/* 
2. Cloning / Concatenation - creating a new object from all of the copied properties. Used a lot for default state or mixins
*/

//mixin style
var proto = {
    hello : function hello(){
        return 'Hello, my nam is ' + this.name;
    }
};
var george = _.extend({}, proto, { name : 'George'});
/*
Create your prototype (this is where you can set like your default state for things), and then use a utility function like $.extend(obj1, obj2, ...objN); - These usually copy all of the methods and properties of the object to the right of the first object into the first object. Going in left to right order, this provides sort of overriding.
*/

/* 

3. Functional Inheritance - this is not really functional or a prototype. But you can use it in conjunction with prototypal inheritance to create really rich functional objects.
These are good for data incapsulation and privacy
*/

var model = function(){
    var attrs = {};
    this.set = function(name, value){
        attrs[name] = value;
        //do somthing
        //we are in a closure scope so we have access to
    };
    
    //.. any mehtod you add in this lexical scope will have access to the
    //private variables because of closure
    
    _.extend(this, {foo: "bar"}, Backbone.Events /*, [andSoOn...] */);
};

var george = {};
model.call(george).set("name", "George");
/*
Create your model "Function" prototype. This function creates a closure and your var attrs will be private to the mehtods defined in this scope. The model.call(george) basically says, call the model function with the context of george - then you will have the ability to use the methods in this new object.
*/

/*
No one type of prototype will replace classes. But combined, they are very powerful! 

The problem is there is a lot of hoops to jump through to get there; this is cause for much of the demand for the class "sugar." This goes along with the idea tha prototypal inheritance is cool, fun, intuitive, but it could be easier.

One solution is to use factory functions.

Like constructors, when called they spit out a new object. But unlike them, you don't need new or this.
You can mix and match all three types of prototypes.
Use .call() and .apply() to swap out the source of prototypes at instantiation time.

Classes deal with the idea of an object.

-----

Prototypes deal with the object themselves


*/

-------------

// let & for or
let arr = [ 3, 5, 7 ];
 
for (let i in arr) {
    console.log(i); // logs "0", "1", "2"
}



--------------------------------
*** YCombinator ***

function Y(le) {
    return (function (f) {
        return f(f);
    }(function (f) {
        return le(function (x) {
            return f(f)(x);
        });
    }));
}


var factorial = Y(function (fac) {
    return function (n) {
        return n <= 2 ? n : n * fac(n - 1);
    };
});

var number120 = factorial(5);

-------------------------------------