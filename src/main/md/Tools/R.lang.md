* To print all global variable
  * str(as.list(.GlobalEnv)) 
* summary(variable)
* 

```R
str(as.list(.GlobalEnv))
tbl = table(data$Cheat)
100*tbl/sum(tbl)  # Percentage of table numbers
pie(tbl)
```