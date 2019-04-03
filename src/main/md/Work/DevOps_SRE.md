# Which is better DevOps or SRE?
## DevOps 
* Back in the day operators and developers had a lot of contention developers used to throw their code over the metaphorical wall and operators were responsible for keeping that code running in production operators had little understanding of the code bases and developers had little understanding of operational practices
* Developers were concerned with shipping code and operators were concerned with reliability this misalignment often caused tension within the organization
* Developers were responsible for features and the operators were responsible for stability 
* Being the developers wanted to move faster to get their features out faster a
* Operators wanted to move slower to keep things stable DevOps is a set of practices and a culture designed to break down those barriers between developers operators and other parts of the organization
----
## DevOps five key areas
* first reduce organizational silos
  * by breaking down barriers across teams
  * we can increase collaboration and thoroughput 
* second accept failure is normal computers are inherently unreliable 
  * so we can't expect perfection 
  * when we introduce humans into the system we get even more imperfection 
* third  implement gradual change 
  * changes are not small, incremental changes easier to review 
  * but in the event that a gradual change does make a bug in production it allows us to reduce our mean time to recover making it simple to rollback 
* fourth - leverage tools
  * we need to leverage tooling and automation 
* fifth 
  * we need to measure everything
  * measurement is a critical gauge for success 
  * Without a way a measure of our first four pillars were successful we would have no way of knowing if they were so
```java
  class SRE implements DevOps
```

## Role of SRE
* SRE share ownership of production with our developers 
* SRE  use the same tooling in order to make sure everyone has the same view and same approach to working with production
* SRE should have blameless post-mortems where SRE should make sure that the failures that happen in our production systems don't happen the exact same way  more than once 
* SRE accept the failures as normal by encoding a concept of an error
* SRE can try canary things that roll things out to a small percentage of the fleet before move them out for all
* Auotmate as much as possible.
* Fifth when you talked about measuring everything 
  * Measuring the reliability and health of our systems 

## References
* [DevOps vs SRE](https://www.youtube.com/watch?time_continue=297&v=uTEL8Ff1Zvk)_  