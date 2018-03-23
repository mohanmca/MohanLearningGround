# W3C Selectors API
* Basic API used accepted to iterate and navigate and read dom nodes, All browswers, JSoup and D3S
* Even if the method is invoked on a sepcific node element, selectors are still evaluated in the context of the entire document. 
| Selector | Usage | Example |
|:--------:|:--------:|:--------:|
| #id | "#content" | Select using id of the tag, Generally container tag  |
| p.description | className applied to a tag p | if we need to select all tag that has a class description |
| "selector1, selector2" | "p.warning, p.error" | select all p elements in the document that have a class of either "error" or "warning". |
| img:nth-of-type(n) | "#content img:nth-of-type(1)" | returns the 1st image within container "#content"  |
| form[action="feedback.php"] | "#content" | Select using id of the tag, Generally container tag }  |


* [Selector API](https://www.w3.org/TR/selectors-api/)