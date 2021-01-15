https://docs.itrsgroup.com/docs/geneos/4.9.0/Integrations/Cassandra/cassandra_monitoring_tr.html#Metrics_and_dataviews_..230

## What is Reliabilitiy?

* The probability that [a system] will perform a required function without failure under stated conditions for a stated period

## Why SRE?

* If software engineering delivers baby (software) after 10 months of labour, labour after birth is where actually most of our efforts are spent.
* SRE deals with lifecycle after first version.
  * Continuous delivery
  * Operation
  * Refinement
  * Eventful peaceful decommissioning.

## Who is an SRE?

* An engineer
  * Invent, Design, Build and Test machines of complex systems, structures and materials to fullfill functional objectives
* Building cross cutting concern solution for existing applications like load-balance, backup
* Figure out - How to apply existing solutions to new problems?
* Focused on operating services on distributed platforms
* Focus on web-services
* Margaret Hamilton
  * First SRE kind of role, worked in Apollo-8
  * Famous DSKY - P01 - moon mission error code

## Who is the duty of SRE?

* Thoroghness and dedication, belief in the value of preparation and documentation
* Awareness of what could go wrong?
* Strong desire to fix it
* 50% of time in development of platform

## Tenets of SRE - (SRE team is responsible)

* Availability
* latency
* performance
* efficiency
* change management
* monitoring
* emergency response
* capacity planning of their service(s).
* Proivisioning

## What is reliability function?

* [Availability plane](https://www.desmos.com/calculator/asc0p9d6me)
* MTTR and MTBF are the independent variables in the availability function
  * A (MTTR, MTBF) = MTBF / (MTBF+MTTR)
* [The Factors That Impact Availability, Visualized](https://orangematter.solarwinds.com/2015/12/21/the-factors-that-impact-availability-visualized/)

## Error budget

* Error budget = 100 -  Expected Availability
  * 0.01% for 99.99% available systems
  * 0.001% for 99.999% available systems

## What is the tug-of-war between operate and development team?

* The development teams want to launch new features and see them adopted by users.
* The ops teams want to make sure the service doesn’t break
  * Most outages are caused by some kind of change a new configuration, a new feature launch, or a new type of user traffic

## What alternative skill is essential to Google-SRE

* SRE's are smart engineers just like othe development team engineers
* UNIX system internals and networking (Layer 1 to Layer 3) expertise are the two most common types of alternate technical.
* SREs are engineers who can't tolerate doing repeated manual tasks that can't scale for million processor cloud.

## Provisioning vs load shifting

* Adding new capacity often involves spinning up a new instance or location, making significant modification to existing systems 
* Provisioning involves - (configuration files, load balancers, networking), and validating that the new capacity performs and delivers correct results. 
* it is a riskier operation than load shifting, 
  * Load shifting is often done multiple times per hour, and must be treated with a corresponding degree of extra caution.


## Quotes

* "A thorough understanding of how to operate system is enough to prevent human errors." - "Margaret Hamilton"


## Reference

* [Google SRE Book](https://sre.google/sre-book/preface/)
* [Awarded Books](https://icdt.osu.edu/cybercanon)
* [Systems Engineering](http://qpr.buaa.edu.cn/__local/2/AA/B8/BB116BBD20312235B2E7F93FAD2_483F18EF_5132FE.pdf)
