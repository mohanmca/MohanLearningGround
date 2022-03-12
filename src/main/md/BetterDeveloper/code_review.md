## General philosophy

* Code review should assume that code works, but is it maintainable?
* Software should be maintainable, and readable, clear, conformant code is necessary for future maintinability.
* Explain why piece code is not good? Never share personal preference like "I don't like this pieice of code"
* Ask why did they code that way? Are there other/better ways to do?
* Rephrase your objection as a question 
  * How can we do XXX with your change? instead of This change will make XXX impossible.

## Quotes and Guidelines

1. “for each desired change, make the change easy (warning: this may be hard), then make the easy change” — Kent Beck


## Code principles

1. Software should thus be never thought of as “done.” As new capabilities are needed, the software changes to reflect that. Those changes can often be greater in the existing code than in the new code.

## Code reviews
1. CRs are very important in writing clear code.
1. CRs help more experienced developers pass knowledge to those less experienced.
1. Code might look clear to one dev, but not to entire team. CR standard (continuously updated) would do great help in that regard.
1. Code Reviews also give the opportunity for more people to suggest useful ideas. One developer can think of only few good ideas in a week. But entire team can have many
  1. Catloging them and asking team to read ahead would help to improve the code ahead of actual review and it also sets the standard
1. Refactoring code that needs to be reviewed would help us to come with **Second-level** of ideas  


## Refactoring dos and don'ts

1. The whole purpose of refactoring is to make us program faster, producing more value with less effort.
1. There is a strong synergy between the three practices of self-testing code, continuous integration, and refactoring.
  1. We shouldn't refactor the code that doesn't have test case, we can add test case if required before refactoring
1. Write tunable software first and then tune it for sufficient speed.
  1. If software is not tuneable, how to improve performance?
1. Performance Refactoring - Even if you know exactly what is going on in your system, measure performance, don’t speculate. You’ll learn something, and nine times out of ten, it won’t be that you were right!

## Automated Test and Unit Test

1. Adding test sounds simple, but laborious, procedure, it’s often much more tricky in practice. Unless it was designed with testing in mind

## Reactoring considerations

1. When original declaration of the structure is changed, existing data would still use the old structure, we have to change even that. (Prime the data, write tool to change old data into new, and new-data into old for fall-back)
  1. Expand and Contract - set up the updates so they update both old and new fields at once.
  1. Move the readers over to the new field. Once all data moved to the new field, and given a little time for any bugs to show themselves, we should remove the now-unused old field. 

## Some guidelines
* You should always write your code as if comments didn't exist.
* Never take mondaic type as input (integer, instead of optional<Integer>)
* Don't flatten the monad

# Reference
* [Better ways to convey code review comments](https://developers.redhat.com/blog/2019/07/08/10-tips-for-reviewing-code-you-dont-like/)