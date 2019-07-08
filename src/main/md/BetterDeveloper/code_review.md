# General philosophy
* Code review should assume that code works, but is it maintainable?
* Software should be maintainable, and readable, clear, conformant code is necessary for future maintinability.
* Explain why piece code is not good? Never share personal preference like "I don't like this pieice of code"
* Ask why did they code that way? Are there other/better ways to do?
* Rephrase your objection as a question 
  * How can we do XXX with your change? instead of This change will make XXX impossible.
*   


## Some guidelines
* You should always write your code as if comments didn't exist.
* Never take mondaic type as input (integer, instead of optional<Integer>)
* Don't flatten the monad



# Reference
* [Better ways to convey code review comments](https://developers.redhat.com/blog/2019/07/08/10-tips-for-reviewing-code-you-dont-like/)