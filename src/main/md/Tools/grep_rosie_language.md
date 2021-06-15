## Rosie Pattern Language replaces regex (RPL)

* What is problem with regex, how it fits with big-data?
* What is the role of rosie?
* Use cases of rosie
* Alternative to Grep

## ACK

* https://beyondgrep.com/


## Regex

* regular grammar and doesn't support all recursive grammar
* These expressions can be difficult to write, and are notoriously difficult to read and maintain.
* Not modular, Can't compose and Not debuggable
* Not suitable for bigdata
* Not possessive - would backtrack and consider different match (would cause performance delay)
* PCRE regtex functions can match much more than regular languages (Perl Compatible REgex)
  
## Grok

* Grok is apparently the plugin most depended upon for extracting information from log records,
* Grok has a library of named regex patterns
* Grok has proven to be flower than Rosie by a factor of around 4
* Grok patterns are not as expressive as RPL, but more powerful than regex

## RPL

* Based on PEG, could express recursive structures (like XML and JSON) that regular expressions cannot. 
* PEG can run in linear time in the size of the input data, making them a good choice for processing big data.
* Supports contex-free grammar
* Like programming language
* RPL := Comments + Modules + Identifiers + Whitespace + Quoted Literals + Unit Tests + Macros
* Standard Patterns in the standard library

## RPL

* is greedy - * would try to match as long as match possible (opposite is lazy as few as possible)
* i possesive - possessive if the matching engine will not backtrack to consider a different match
  
  
## RPL VS REGEX

* In regex or match would use | (pipe)
* In RPL or match would use / (slash)

## Reference

* [Rosie Language](http://rosie-lang.org/ex/)
* [Rosie Pattern Language for faster data mining](https://www.youtube.com/watch?v=P5v2ZtcY2-k)
* [Rosie Pattern Language](https://developer.ibm.com/code/open/projects/rosie-pattern-language/)
* [Rosie presentation](https://developer.ibm.com/code/wp-content/uploads/sites/118/2017/11/Rosie-Update-Charts.pdf)
* [Rosie Replaces Regex for Data Mining](https://developer.ibm.com/code/wp-content/uploads/sites/118/2017/11/Rosie-Replaces-Regex.pdf)
* [Rossie QA](https://developer.ibm.com/code/2016/12/08/rosie-pattern-language-qa/)
* [Grok patterns](https://github.com/elastic/logstash/blob/v1.4.2/patterns/grok-patterns)