* To print all global variable
  * str(as.list(.GlobalEnv)) 
* summary(variable)
* Combining Plots - R makes it easy to combine multiple plots into one overall graph, using either the par() or layout() function.

```R
str(as.list(.GlobalEnv))
tbl = table(data$Cheat)
100*tbl/sum(tbl)  # Percentage of table numbers
pie(tbl)
cor(a$longevity,a$gestation)
summary(dataframe)
```

```
a <- load("C:/Users/nikia/OneDrive/Math/Statistics/nightlight.RData")
head(nightlight)
tail(nightlight)
dnow <- data.frame(x=rnorm(100), y=runif(100))
dnow[1:4,]
dnow[dnow$x>0.0,]
typeof(dnow)
x <- c(0,1,2,3,4,5,6,7,8,9)
myGlobals <- objects()
typeof(get(myGlobals[2]))
```

```
a <- load("C:/Users/nikia/OneDrive/Math/Statistics/body_image.RData")
plot(body_image$HS_GPA, body_image$GPA, xlab="HS", ylab="Col")
cor(body_image$HS_GPA, body_image$GPA,use="complete.obs")
fem_summary <- summary(body_image[body_image$Gender=="Female",]$WtFeel)
m_summary <- summary(body_image[body_image$Gender=="Male",]$WtFeel)
obj <- 11:15
names(obj) <-  c("Num1", "Sum1", "Lum1", "Dum1", "Rum1")
values <- unname(obj)
f_pct <- round(fem_summary/sum(fem_summary)*100)
m_pct <- round(m_summary/sum(m_summary)*100)
lbls <- names(fem_summary)
lbls <- paste(lbls, f_pct)
lbls <- paste(lbls, "%",sep="")
pie(fem_summary,labels = lbls)
lbls <- names(m_summary)
lbls <- paste(lbls, m_pct)
lbls <- paste(lbls, "%",sep="")
pie(m_summary, labels=lbls)

Filter(function(x) x > 3.291, body_image[1:10,]$GPA)
```


```
L=lm(data$GPA~data$HS_GPA);
abline(L);
cf=coefficients(L);
lt=paste("GPA = ",round(cf[1],2),"+",round(cf[2],2),"HS_GPA")
legend(1.7,4.3,lt)
plot(data$HS_GPA,data$GPA)
abline(L);
```


```
tbl = table(data.frame(data$Gender,data$WtFeel)); tbl
100*tbl/rowSums(tbl) # to view them in percent
plot(factor(data$Seat), data$GPA)) # side by side comparision
tapply(data$Seat, factor(data$GPA), summary)
```

> tapply(data$GPA, data$Seat, summary)
$B
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA's 
  2.000   2.678   3.000   2.974   3.237   4.060       1 

$F
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA's 
  1.920   3.000   3.330   3.251   3.700   4.100       1 

$M
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max.    NA's 
  1.910   2.780   3.000   3.119   3.505   4.380       6 


## R-libraries for plotting
	1. ggplot2
	2. plotly
	3. Rmarkdown
	4. Flexdashboard
	5. Knitr
	6. Reshape2
	7. Elastic
	8. Httr
	9. Plyr
	10. Dplyr
	11. Scales
	12. Lubridate
  13. Plotly graphs: https://plot.ly

```R
pct_table = 100*prop.table(table(random_sample$Handed));
barplot(rbind(pop_percent,random_sample_percent), beside=T, col=c(0,1),legend.text=T,xlab="Handedness",ylab="Percent in Group",args.legend=list(x="topleft"))
random_sample_percent = 100*summary(random_sample$Handed)/length(random_sample$Handed);

par(mfrow=c(1,2));  #1 row, 2 columns
```	

```R
group = sample(1:3,450,replace=TRUE) # choose 1/2/3 450 samples
df = cbind(df, group)
```

```R
# Two way table for existing dataframe
tbl = table(data.frame(data$Treat,data$Outcome))
tbl/rowSums(tbl) * 100
```

data.Treat        0        1
         0 37.83784 62.16216
         1 71.05263 28.94737
         2 32.35294 67.64706
```
> tapply(data$Time, factor(data$Treat), summary)
$`0`
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  0.286   5.286  22.000  37.726  67.000 165.000 

$`1`
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  0.571  23.000  70.714  63.064 101.500 206.000 

$`2`
   Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
  0.000   5.393  17.786  37.584  61.714 131.000 
```