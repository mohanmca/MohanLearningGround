# Advantages static type or optional static type
* Beyond certain size (say 2000 lines of code), program becomes difficult to refactor
* How about even bigger project wide refactoring
* How to refactor all the property called "Name" particular for "That" kind of a class
  * There could be many properties like "Name","FullName", how do we know?
  * Can we find and replace? Catastrophe?
  * Manually revisit all the code and usage?
* When we annotate one function with type
  * You could infer many other type where it is being used/invoked, and so on
* Navigation of method using tools.
  * When someObject.foo() is invoked, with type, we know whose foo() is invoked
  * Without type, navigation by the tools are also impossible in certain cases
* Types are also act as additional document what you mean here
* If dev tools needs to be smarter, types would help
* If you have transpiler in your toolchain, why not use type itself (for Javascript)

# Subjective benefits of type systems
* Type systems really shine when it comes to making assumptions, conventions, patterns, and invariants explicit.
* Type systems is freeing up brain capacity because you can offload so much information to the toolchain.

# TDD
* TDD is about designing your code to be testable.
* TDD as a method of getting quick feedback on whether what code written works well


# Type sysems
Static <-> dynamic tells you when the types are determined.
Static = without running the program, which means you can check their correctness with a compiler or an editor.
Dynamic/static typing is about whether the type checks are done at compile or run-time. Strong/weak typing is about whether you can break out of the type system.

# Strong type
Strong typing means that the type of a value doesn't suddenly change. A string containing only digits doesn't magically become a number, as may happen in Perl. Every change of type requires an explicit conversion.
Strong <-> weak is more of a spectrum that goes from more strict (stronger) to less strict (weaker).


# Dynamic Type
Dynamic typing means that runtime objects (values) have a type, as opposed to static typing where variables have a type.
Dynamic = at run-time, which means you have to run the specific branch of the program to reliably know what type something is going to have - it can also change during program execution.

# Weak Type
In a language with strong typing, a type mismatch will usually result in an error.
With weak typing, you will often get an implicit conversion. For example, accessing a non-existing property in JavaScript will simply return the value undefined, instead of causing an error.

# Duck Type
* "Duck typing is the system wherein you wish to pass something that requires a Duck object, and you only have a Dog, you have the decency to wrap it in a Duck suit."

# TDD reinvents functional programming
(http://erlang.org/pipermail/erlang-questions/2009-February/041969.html)
While it is nice that you like Erlang, I don't think your arguments
will work very well to convince somebody to try Erlang. You sound more
like http://www.urbandictionary.com/define.php?term=drink+the+kool-aid.

Believe me, there can be doubt about lambda calculus being "ultimate".
This is the internet, and your argument is WEAK.

Phrases like "elegant", "what is right", "creative soul" do simply not
work. Try a Java guy sometimes, they will dislike that Erlang

* is not Java
* is not running on the JVM
* is not equipped with classes nor the object-dot-metod-arglist syntax
* is just plain different and academic for no practical advantage
* is not doing anything you couldn't do with Java using NIO, blocking
queues, and something called osgi they read about in a trade mag but
haven't used personally yet
* is unimportant now that Scala exists
* is unimportant because Kilim is adding cheap actors to Java

... and then continue with weak reasons. The human being is not
rational. The human being rationalizes its irrational choices though.
Argumenting with emotions wont work since they have strong emotions in
favour of OOP already.


I used to think that I liked erlang programming just because it was
quite academic, and that this was just a personal preference. But I
have come to realize that side-effect free programming is easier to
reason about. Not always easier to write though. Being easier to
reason about means that I can imagine more execution in my head than I
can in languages that rebind variables and mutate values. Because
those destructive changes defines a before-change, and after-change,
sequence, order, and there are only so many steps one can keep the
mind without the brain starting to lie to you, filling out the blanks
without telling you.

I recently read an article published in idg.se where Erik Stenman of
kreditor was quoted saying that (something like) java systems grow
complex over time in a way that Erlang does not as much. This is
something I experience with Java systems. In Java there is no cost in
adding more things that keep state. It is said that you just
encapsulate state it in your object, but that state is not as
encapsulated as you think. Problems are fixed by adding more things
with state, and more. and more. Pretty soon you have behavior that
depends on several hundred mutable variables, and the code-base feels
hard to grasp, difficult to reason back into previous code execution
to see the "big picture". It makes architectural fixes more difficult.
And the problems accelerates, since more quick fixes are added. (I
really need to get better at describing this.)

The funny thing is that the OOP world have found one way to manage the
complexity and the code-bases that grow ugly: They are using unit
tests, and practice the art of writing testable code. ("Testable code"
is something that is simple to write unit tests for. )

* You make it very easy to supply the dependencies to code under test
need. Avoid things that go out and grab values from global state.
* You avoid side-effects, and the side-effects you need (database
updates, writing files etc) you make sure that you perform with a
layer of indirection so that during tests you can replace the real
object with (for example) a do-nothing mock.

What they are doing is that they are making as much of their code as
possible to be side-effect free and placing all that code in one
method so it can be called from a unit test. They are concentrating
side-effects to well-defined places, carefully avoiding mixing
side-effects and testable/test-worthy logic.

What they are doing is that they're reinventing functional programming.

PS
Anyone care to make my argument better or shoot it down?