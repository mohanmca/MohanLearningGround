* If a and b are the probabilities associated with two independent pieces of evidence, then combined they indicate a probability of:

```
       ab
-------------------
  ab + (1 - a)(1 - b)
  
            abc           
---------------------------
abc + (1 - a)(1 - b)(1 - c) 
```
* 7 feet tall indicates with 60% probability that someone is a basketball player, and carrying a basketball indicates this with 72% probability. If you see someone who is over 7 feet tall and carrying a basketball, what is the probability that they're a basketball player?

  * 
```  
           (.60)(.72)
    ------------------------------- = 0.794 = 79.5%
    (.60)(.72) + (1 - .60)(1 - .72)
```

* Reference (http://mathforum.org/library/drmath/view/62710.html)
```pre
In a box there are nine fair coins and one two-headed coin. One coin 
is chosen at random and tossed twice. Given that heads show both 
times, what is the probability that the coin is the two-headed one? 
What if it comes up heads for three tosses in a row?

I understand that there are 10 coins in total. My teammates tried it 
out also and they got 4/9 + 4 for the first part and 8/9 + 8 for the 
second part. I don't understand how they got this.

Here's a way to think about it. Make a tree:

                                  flip two heads (1/4) 
                                 /
          choose fair coin (9/10)
         /                       \flip anything else (3/4)
10 coins                            
         \                            
         choose two-headed coin (1/10) -> flip 2 heads (1/1)


Study this tree, and it becomes clear that there are 3 possibilities: 

1 - the top one has probability (9/10)*(1/4)  =  9/40
2 - the next one has probability (9/10)*(3/4) = 27/40
3 - the last one has probability (1/10)*1     =  1/10

Before you did the experiment, these were all the possibilities there
were. Then you did the experiment. What did it tell you? It told you 
that the middle option is out. The coin did NOT show a tail, so we 
know it wasn't the second outcome.

This narrows our universe to the 9/40 and the 1/10. The trick now is
to re-normalize these probabilities so that they show a total
probability of 1, but stay in the same ratio. Within that universe
(all the possibilities that are left) lines (1) and (3) remain in the
ratio 9:4. So the probability of the top one is 9/13 and the bottom 
is 4/13, where 13 is just the sum of 9 and 4.
        
Can you extend this reasoning to come up with the corresponding result
for three flips?

(This kind of reasoning is called Bayesian probability, and it is one
of the most confusing topics in probability at any level of study.)
```

# Gregory's Theorem
* https://divisbyzero.com/2018/09/28/proof-without-word-gregorys-theorem/

# References
* http://www.paulgraham.com/naivebayes.html
* https://en.wikipedia.org/wiki/Naive_Bayes_spam_filtering

# Youtube math tricks
* https://www.youtube.com/watch?v=Rgw9Ik5ZGaY
* Channel name - tecmath


# [Logistic Regression from Bayes' Theorem and Linear regression](https://www.countbayesie.com/blog/2019/6/12/logistic-regression-from-bayes-theorem)