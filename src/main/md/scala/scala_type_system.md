# Scala type-system

* Scala compiler let us treat types themselves as values to certain extent
* Type alias are supported in Scala
  * ```type UserId = Int```
* We can tag known primitive type and enforce that API is used in right context
* Tagging is we are tagging existing value with additional context, so they can't be misused
  * def tag[U](i : Int) : Int @@ U = i.asInstanceOf[Int @@ U]
    * Usage: val userId = tag[User](10) ---now this id could be used as UserId    
  * def tag[U](i : String) : String @@ U = i.asInstanceOf[Int @@ U]
    * Usage: val userpassword = tag[User]("Gibrish@#$123") ---now this String could be used as password    
*   