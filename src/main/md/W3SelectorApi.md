# W3C Selectors API
* Basic API used accepted to iterate and navigate and read dom nodes, All browswers, JSoup and D3S
* Even if the method is invoked on a sepcific node element, selectors are still evaluated in the context of the entire document.
* The querySelector() methods on the Document, DocumentFragment, and Element interfaces must return the first matching Element node within the subtrees of the context node. If there is no matching Element, the method must return null.
* The querySelectorAll() methods on the Document, DocumentFragment, and Element interfaces must return a NodeList containing all of the matching Element nodes within the subtrees of the context node, in document order. If there are no matching nodes, the method must return an empty NodeList.


| Selector | Usage | Example |
|:--------:|:--------:|:--------:|
| #id | "#content" | Select using id of the tag, Generally container tag  |
| p.description | className applied to a tag p | if we need to select all tag that has a class description |
| "selector1, selector2" | "p.warning, p.error" | select all p elements in the document that have a class of either "error" or "warning".  |
| img:nth-of-type(n) | "#content img:nth-of-type(1)" | returns the 1st image within container "#content"  |
| form[action="feedback.php"] | "#content" | Select using id of the tag, Generally container tag }  |
| #body img[src^="http" | within body |  selects 1st image with src beginning with "http" |
| #body img[src^="http" | within body |  selects 1st image with src ending with "http" |


## DOM API
* Node and Element, NamedNodeMap, NodeList
* Document, DocumentFragment
* Document interface gives the factory methods needed to create elements, nodes
```IDL
# - 0 index
interface NodeList {
  Node               item(in unsigned long index);
  readonly attribute unsigned long   length;
};
```

* [DOM API](https://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407/core.html#ID-536297177)
* [Selector API](https://www.w3.org/TR/selectors-api/)
* [Examples](http://www.javascriptkit.com/dhtmltutors/css_selectors_api.shtml)