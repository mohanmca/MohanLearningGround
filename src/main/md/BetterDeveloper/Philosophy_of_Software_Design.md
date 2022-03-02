## Three major problems of software design
1. Software design is black art
1. No course is taught about Propblem of Decomposition
1. Some programmers are way way productive?
    1. How to ingrain this skill?
    1. Can we learn/teach this skill?

## Questions to audience - What is most important aspect of software design

1. Abstraction / (Layers of Abstraction)
1. Compositon / (Problem of Decomposition)
1. Testing
1. Complexity

## Talent is Overrated

1. How much they practice is only matters

## How to practice

1. Build large system possible by you
1. Review the code
1. Update code review feedback and rewrite

## What are the secrets for best software design?

1. Working code isn't sufficient, must minimize complexity
1. Complexity comes from dependencies and obscurity
1. Strategic vs Tactical Programming
1. Classes should be deep
1. General-purpose classes are deeper
1. New lyaer, new abstraction
1. Comments should describe things that are not obvious from the code
1. Define errors out of existence
1. Pull Complexity Downwards
1. Find and learn the red-flags
1. Philosophy is very constructive in the context of the code reviews


## Red Flags

1. If something is not right (not necesarilly wrong), and redesign/rework until red-flags go away
1. You can navigate to decent place by removing red-flags
1. Classes and Methods shuold be small (is a red-flag, it is not deep)
1. Common case matters a lot in design
    1. Counter example, Java fileinputstream complicated its design without buffer in place


## Error Design

1. unset (in TCL) throws the exception when program ties to delete variable it was not there
    1. Lesson learnt: unset could have created variable before deleting it, so it would work even for variable that doesn't exist
1. Windows file-system doesn't allow second process to delete when file is already opened by other process..
    1. Linux let process delete the file entry from catalog, but existing process still has got the content of the file (without entry)
1. "smallString".Substring(1,99)
    1. Why not function could automatically clip and return the entry, rather letting programmers to handle the clipping?
1. Design for the world where exceptions are dealt in very few world
    1. Let normal world deal without any exception (no exception handling makes code better).
1. Error code vs Exception
    1. If error code or exception not travelling long way is not adding lots of value
    1. If caller immediately check for return value (for error-code) is not much value.

## Additional philosophy

1. You are building software for future, hence think about evolution
1. Your interface is so special, it doesn't even act as interface (interface should be generic)
1. For most applcation, better to crash when we are out-of-memory (Crashing is okay)
1. Few kludges are OK?
    1. Complexity is incremental (exponential)
    1. Others would copy/paste the same, is that okay?
    1. When few of the same kludges are repeated in 100s of places, is that okay? kludges grows faster. It becomes spagatti.
    1. Don't let delivery preassure add those, they would tend to grow
1. It is 100s of tiny mistakes together makes it complex, hence try to reduce at the inception state.

## Tactical Tornaedo developer

1. Who writes code that works 80% quickly
1. Produces enormous amounts of pretty shoddy code and churn features
1. Let the entire system collapose
1. Organization treats them as heros
1. Moral (Working code is not the only goal)


## Tactical vs Strategic Programming

1. Strategic Programming
    1. Goal produce a great design
    1. Simplify future development
    1. Minimize complexity
    1. Must sweat the small stuff
2. Investment mindset
    1. Take extra time today
    2. Pays back in the long run
3. "Strategy without tactics is the slowest route to victory. Tactics without strategy is the noise before defeat." --Sun Tzu, "The Art of War"

## How much to invest?

1. Move quickly and break things (with strong infrastructure)
1. Strong Design (Google/VMWare)
1. Everything tactical (tiny startups)

## When changing existing code

1. Spend at-least 10% of the time to improve existing code
1. Always find something to improve
1. Don't settle for fewest modified lines of code
1. Goal: after change, system is the way it would have been if designed that way from the start

## Deep class vs Shall class

1. Class as rectangle area (large the area is better)
1. Coming up with a simple way of thinking about complex things
1. Making class as General makes it deeper
    1. Even if we use it one place, treat it like API (if possible, make it simpler and deeper)

## Layers of abstraction

1. Too many layers is very bad
2. Too few layer with high performance is possible
3. It is possible to have many layers with high performance


## Few more quotes from mailing list

1. If something is important, then the system must reflect that; trying to hide it will just cause problems. 
   1. However, if you can reduce the number of things that are important (e.g., by handling several things in the same way, so that the distinction between them is no longer important), you can end up with a simpler design.
2. 



## Philosophy of software design | John Ousterhout | Talks at Google

1. [Partha Ranganathan - technical infrastructure cloud (introduced) John Ousterhout](https://research.google/people/ParthasarathyRanganathan/)
1. [CS 190: Software Design Studio](http://web.stanford.edu/~ouster/cs190-winter22/)
1. [Software-design-book](https://groups.google.com/g/software-design-book)
1. [Information Hiding David Parnas](https://www.cs.cornell.edu/courses/JavaAndDS/files/infoHiding.pdf)
1. [Can Great Programmers Be Taught?](https://cs.stanford.edu/~hq6/files/Great%20Programmers%20Long%20(Aug).pdf)