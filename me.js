//*[@id="rso"]/div/div
//*[@id="rso"]/div/div/div[1]/div/div/h3/a

//Google the results

var container = document.evaluate('//*[@id="rso"]/div/div', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null)
var results = Array.from(container.singleNodeValue.children)
var resultLinks = results.map(childNode =>  document.evaluate('div/div/h3/a', childNodes, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null))
var result = resultLinks.map( node  => 
	{
		var o = new Object()
		o['href']	 =  node.singleNodeValue.href
		o['text'] =   node.singleNodeValue.outerText
		return o;
	} 
)
console.table(result)