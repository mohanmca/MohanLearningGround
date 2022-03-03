## Rules

1. We are likely unaware of many complexities, dependencies, and edge cases that the code must account for. I suggest working with the code a bit longer before you make such bold proclamations as to its quality.
2. You have a low chance of success (first, convincing your manager, and second, successfully refactoring the code), but a high chance of loss (alienating your teammates).
3. How do you think colleagues felt when you told them it needs a whole refactoring that was built by them?
4. [Chesterton's fence - is the principle that reforms should not be made until the reasoning behind the existing state of affairs is understood.](https://wiki.lesswrong.com/wiki/Chesterton%27s_Fence)

## How to start?

1. for every bugfix add a regression test, so it never happens again. Find a list of the most recent bug fixes, find if there is a common pattern.
2. Its 100% not worth it's getting depressed feelings for a codebase.

##  Refactoring parties and ideas
1. Have refactoring parties once a month with pizza and chipper.
2. On the last Friday afternoon of the month review 10 random code files changed in the last month and tidy messy code, add comments, docs, unit test etc.
3. Adopt coding standards, code reviews and unit testing.

## How to sell refactoring?
1. Talk to your boss. If you feel strongly it needs to be done, explain why, and what the benefits are. You need to be able to quantify and qualify this so do your homework and sell it.
2. If you can demonstrate the bad code is costing the company more in man hours (and therefore money), then maybe propose a a series of refactoring projects in order to help improve the code and increase efficiency.
3. How would you convince him that it would break less after refactoring code


## What needs to be agreed?

1. Adopt Coding Standards
2. Code reviews
3. Unit testing
4. Software design principles


## Reference

1. [Ask HN: How to be productive with big existing code base](https://news.ycombinator.com/item?id=19254008)
2. [Ask HN: Started a new job and their existing code sucks. What to do?](https://news.ycombinator.com/item?id=16233961)
3. [Normalization of deviance](https://danluu.com/wat/)
4. [The Codeless Code](http://thecodelesscode.com/contents)
5. [Working Effectively with Legacy Code](https://www.amazon.co.uk/Working-Effectively-Legacy-Michael-Feathers/dp/0131177052)
6. [Peter Naur – Programming as Theory Building (1985) [pdf]](https://pages.cs.wisc.edu/~remzi/Naur.pdf)
   1. [Peter Naur – Programming as Theory Building (1985)](https://news.ycombinator.com/item?id=10833278)
7. [LEGACY CODE ROCKS](https://www.legacycode.rocks/)
8. [Refactoring JavaScript: Turning Bad Code Into Good Code](https://www.amazon.com/Refactoring-JavaScript-Turning-Code-Into/dp/1491964928#customerReviews)
9. [Refactoring: Improving the Design of Existing Code (Addison-Wesley Signature Series (Fowler))](https://www.amazon.com/gp/product/B07LCM8RG2/ref=dbs_a_def_rwt_bibl_vppi_i0)
10. [Flamegraph](https://github.com/davidmarkclements/0x)
    1. [Flamegraph Introduction](https://www.youtube.com/watch?v=D53T1Ejig1Q)
11. [Complexity of Value](https://www.lesswrong.com/tag/complexity-of-value)