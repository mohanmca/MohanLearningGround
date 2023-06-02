* If we need to make javascript as book-markelet, always use it always after encode them as URI

* Remove fixed element from browser
  * ```javascript:(function(){(funct`ion () {var i, elements = document.querySelectorAll('body *');for (i = 0; i < elements.length; i++) {if (getComputedStyle(elements[i]).position === 'fixed') {elements[i].parentNode.removeChild(elements[i]);}}})()})()```