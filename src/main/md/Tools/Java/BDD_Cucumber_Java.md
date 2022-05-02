## What is BDD?

1. Collaboration and coversation
2. Concrete examples and business rules
3. A common language and a shared understanding
4. Faster feedback through early and effective automation
5. BDD = Specification + Automated Acceptance Tests + Functional Documentation
6. BDD is agile (it gives concrete method for agile)

## BDD Process implemented?
1. BDD starts with conversation (Illustrate / Discovery phase)
2. Discovery phase - Talks through concrete example and counter example
3. Formulate Phase - Given/When/Then
4. Automate Phase - Implement Gherkin Language
5. Validate Phase - User validates the solution

## Gherkin
1. Gherkin = A business readable notation for executable specifications
2. Cucumber = Runtime VM for Gherkin - is a software that executes executable specifications written in Gherkins
3. Scenario - Given/When/Then
   1. Given - Precondition
   2. When - Heart of Scenario, Action happens, Events
   3. Then - Post condition, Outcome we expect. 
   4. But - Post negative condition
4. Feature - is illustrated by number of scenarios
5. Language atoms
   1. Scenarios
   2. Features
      1. Has title (filename aligned with title of the feature)
      2. Has description
   3. Data Tables
   4. Backgrounds
      1. If Given section is repeats in multiple Feature file, it is better to refactor and bring it under background section 
      2. It is executed at the start of each scenario
   5. Scenario Outlines

## Gherkin - Embedded Tables (or Data table)

1. When we repeat certain element multiple times, we can refactor them as a table
   ```pre
      When Michale orders Smoothie
         And he orders capuccino
         And he orders 2 Crossiants
   ```
    ```pre
      When Michale places the following order:
         | Product        | Quantity |
         | Apple Smooting | 1        |
         | Crossiants     | 2        |
   ```
2. Data tables are more flexible
3. Data table rows may or may not have column headers
4. Scenario Outline
   1. Scenario data is injected from the table into scenario
   2. It helps to avoid writing similar scenario
       
## User story and scenario

[UserStoryTitle] (One line describing the story) 
As a [Role]
I want a [Feature]
So that [Benefit]

Scenario 1: [Scenario Title]
Given [context]
And [Some more contexts]
When [Event occurs]
Then [Outcome]
And/But [Some more outcomes]

## Cucumber Test runner

```java
@Runwith(Cucumber.class)
@CucumberOptions(plugin={"pretty", "html:target/cucumber"}, features={"classpath:features"}, glue="com.server.package" )
class AcceptanceTestRunner {
    
}
```

## BDD Business requirement

1. Three Amigos (different perception)
   1. Business
   2. Development
   3. QA
2. BDD Requirements Discovery technique/practices
   1. Feature Mapping
      1. Break each example into steps and outcomes
      2. For each step, look for variations that might lead to different outcomes
      3. Find business rules that we hadn't know about it
   2. Exampling Mapping
      1. Color post-its
         1. List down all the Business Rule/Examples/Questions
      2. Tables and whiteboards
3. Teams  that practices BDD uses examples and tables that come out of conversation to form the acceptance criteria
   1. BDD Example: ![alt text][BDD_Examples]

## Test Harness
* A test harness or automated test framework is a collection of software 
  * And test data configured to test a program unit 
  * It does by running software under varying conditions and monitoring its behavior and outputs.
* Supporting tools required to automate test execution also considered as test harness

## Java

```java
mvn archetype:generate                      \
   "-DarchetypeGroupId=io.cucumber"           \
   "-DarchetypeArtifactId=cucumber-archetype" \
   "-DarchetypeVersion=7.0.0"               \
   "-DgroupId=hellocucumber"                  \
   "-DartifactId=hellocucumber"               \
   "-Dpackage=hellocucumber"                  \
   "-Dversion=1.0.0-SNAPSHOT"                 \
   "-DinteractiveMode=false"
```

## Test automation layer

* Well written layer has certain constrains, it has 3 layers
1. Executable Specification - Gherkin
2. Glue Code - (Cucumber step definitions)
   1. This layer shouldn't manipulate application itself
   2. Don't make rest call
   3. Don't make db query
3. Test domain
   1. This layer can manipulate application itself
   2. This layer - driver make rest call
   3. This layer - driver make db query

## Glue Code - Cucumber Expression

1. "I have order 9 smoothies"
   1. "I have order {int} smoothies"
2. I have order "Green goodness smoothies"
   1. "I have ordered {string}"
3. I am a Gold card member
   1. "I am a {word} card member"
4. 3.141 - {float}
5. When he orders a Green Goodness Smoothie
   1. he orders a {} Smoothie
   2. Green Goodness - Matches {} - Anonymous  matcher

## Glue Code - Regular Expression
1. More powerful than cucumber expressions, but becomes less readable
2. Examples
   1. @Given("^I have ordered (.*) smoothies$")
3. Expression syntax
   1. ^ - Start of the string
   2. $ - End of the string
   3. (.*) - Matches any string
   4. (.+) - Matches at least one of anything
   5. (\\d+) - Sequence of digits
   6. (\\W+) - Sequence of letters or digits
   7. ? - Optional character (s?he) - Matches she or he
   8. an? - Matches a or an
   9. (?:orders | have ordered) - Matches but not captures as parameter
4. Tables are passed as paramter
   1. List<String>
   2. List<Map<String, String>> table
   3. List<Map<String, Integer>> table
5. Glue code will not change for scenario and scenario_outline

## Reference
1. [BDD Process](https://johnfergusonsmart.com/behaviour-driven-development-3-minute-rundown/)
2. [John Ferguson Smart: BDD in Action: Testing Modern Web Applications at Scale - SCL Conf 2019](https://www.youtube.com/watch?v=hdBxLZ8f82Y)
3. [Java: BDD with Cucumber and Gherkin Getting Started](https://app.pluralsight.com/course-player?clipId=53c142f4-9ec9-4eb8-a65c-52b58ad7aff1)
4. [BDD Engineering](https://thesai.org/Downloads/Volume12No2/Paper_74-Using_Behaviour_driven_Requirements_Engineering.pdf)
5. [Cucumber](https://cucumber.io/docs/guides/10-minute-tutorial/)

[BDD_Examples]: img/Examples.png "BDD_Examples"  
