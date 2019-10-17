# Developer

* Owns the implementation
* Specific to one system and one department
* When something goes wrong in the system, we know who built

# Architect

* Define strategic IT solution to meet the strategic business vision 
* Define and maintain IT system and its integration design
* Define and drive the technical vision for the integration and efficiency of IT assets
* Create and review documentation and process regarding recurring issues, new standard operating procedures 
* Enterprise-wide planning, communication and implementation
* Maintains strategic design and roadmap
* Develops technology standards and strategies
* Doesn't own any system/application specific failure
* Blueprints help architects ensure that what they are planning to build will work. Architects and their clients use blueprints to understand what they are going to build before they start building it.

# Why Architects are required (why not developer alone enough?)

* Thinking doesn't generate code, but it is important. Writing code without thinking is a recipe for bad code
* Writing (code/literature) is nature's way of letting you know how sloppy your thinking is.
* Blueprints help us think clearly about what we're building. Before writing a piece of code, we should write a blueprint. A blueprint for software is called a specification
* When there is disaster it is required to fly high to assess the damage, similarly planning also requires to fly high and requires to view out-of-the box.
* To design complex systems, the need for formal specs should be as obvious as the need for blueprints of a skyscraper. 
* Code is a bad medium for helping to understand code. Architects don't make their blueprints out of bricks.
* The key to understanding complexity is abstraction, which means rising above the code level.
* Thinking doesn't guarantee that we won't make mistakes. But not thinking guarantees that we will.

# Why Architects should code?

* Specs are useless because we can't generate code from them.
* Specs does not proove you are right. But running code can easily invalidate your specs.
* In this metaphor code is the equivalent of blueprints, the compiler is the builder, and the working software is the house. Building architects deal in blueprints, and software architects should deal in code.
* We're not designing and building things to last a hundred years. we're iterating and learning as we go based on user feedback
* Abstract it very high level, Architecture Astronauts who fly too high where there are no oxygen
* Try to sell without actual details peer-to-peer (than Napster), Soap (than XML)
  * They don't understand that new tool is just rehash of old solution
  
# Architect don't need to code

* Architect requires knowledge of data flows & elegant problem solving!
* Architect can program all day and the project will fail miserably.
  * Code reviews, customer/team communication and constant evolutionary reviews of the current state of the project are crucial. 
  * Coding is good, but it is important to spend time on other things.
* Architect could be absolutely terrible developer, but if architect really good at making different software components fit together to meet a business goal, that is enough. 
* Architect can relate well to business folks and developers. This is the essence of being an architect and frankly, most developers entirely lack this skill (they don't see business goal/problem).
* Should architect should start his work shoveling the floor and putting the first bricks, No! not required!
* Should infra/storage/network architect should code?
* If archiect code in spare time using assembly language, would that be sufficient? How about basic?

# How should Architects function (from developer)

* Modern Architects should write specification in a formal specification language like TLA+.
* Getting an algorithm right takes thought, which means writing a spec. (binary search was n't written within a day)
* The best language for being simple and precise is math. Writing spec on plain english may not be good enough for complex problem.
* Computer architect is somebody who could build the entire system by themselves given sufficient time.
* Architect is someone who is  capable of defining the Vision of the complete system delivering the functional and quality attributes required.
* Architects should have some skin in the game.
* Definitely need to know how the pieces fit together and their individual pros and cons.
* Architects should have a very good understanding of the consequences of their decisions. 
  * Especially wrt the non-functional attributes of the system such as flexibility, scalability, reliability, fault tolerance etc.

# How Architects should not function

* It's bad if architect keep telling other people how to code something specific after they stop coding themselves
* If the architecture is a mess and hard to develop for, architects should pay the price too, and not just developers.

# Building analgoy with architecture

* They think tearing down walls is hard but changing code is easy, so blueprints of programs aren't necessary.
* Changing working code is hard � especially if we don't want to introduce bugs.
* In this metaphor code is the equivalent of blueprints, the compiler is the builder, and the working software is the house. Building architects deal in blueprints, and software architects should deal in code.
* Architects could be compared  to football coaches. They aren't playing football anymore. Or at least not very often. But know how to score goals.

# My perception

* Architects not necessarily need to code, if they coded in the past, and know the business domain well, and take part actively in building system with *skin in the game*
* There are many architects (business architect, domain architect, security architect, solution architect, enterprise architect, application architect), Not everyone needs to code.
* Dedicated application architect without "skin in the game" is counter productive and useless
* Architect who solved similar problem for a given business domain is asset provided he starts with Spec even before others begin their work.
* If we could work with Architect would coded/code, and also acts as an Architect, probablity of delivering world-class system is high
  * Assume if "Doug Cutting" helps to build big-data archiecture for a bank.
 

# References

* https://www.joelonsoftware.com/2001/04/21/dont-let-architecture-astronauts-scare-you/
* [Leslie LamportLeslie Lamport - Why We Should Build Software Like We Build Houses] (https://www.wired.com/2013/01/code-bugs-programming-why-we-need-specs/) 
* [Should architect code?](https://twitter.com/dhh/status/1052958907063263232?s=20)