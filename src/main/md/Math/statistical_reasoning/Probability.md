
## General Remarks about probability
* Probability is a discipline by itself. In the context of the big picture of this course, probability is used to quantify the imperfection associated with drawing conclusions about the entire population based only on a random sample drawn from it.

* We talk about the probability of an event, which is a statement about the outcome of a random experiment. In practice, each event corresponds to a subset of outcomes from the sample space.

* Probability of an event can be as low as 0 (when the event is impossible) and as high as 1 (when the event is certain).

* In some cases the only way to find the probability of an event of interest is by repeating the random experiment many times and using the relative frequency approach.

* When all the possible outcomes of an random experiment are equally likely, the probability of an event is the fraction of outcomes which satisfy it.

## Random Variables

* A random variable is a variable whose values are numerical results of a random experiment.

* A discrete random variable is summarized by its probability distribution -- a list of its possible values and their corresponding probabilities.

    * The sum of the probabilities of all possible values must sum to 1.

    * The probability distribution can be represented by a table, histogram, or a formula.

* The probability distribution of a random variable can be supplemented by numerical measures of center and spread of the random variable.

    * Center: The center of a random variable is measured by its mean.

    * -- The mean of a random variable can be interpreted as its long run average.

    * -- The mean is a weighted average of the possible values of the random variable weighted by their corresponding probabilities.

  * Spread: The spread of a random variable is measured by its variance, or more typically by its standard deviation (the square root of the variance).

  * -- The standard deviation of a random variable can be interpreted as the typical (or long run average) distance between the value that the random variable assumes and the mean of X.

## Continuous Random Variables
The probability distribution of a continuous random variable is represented by a probability density curve. The probability that the random variable takes a value in any interval of interest is the area above this interval and below the density curve.

An important example of a continuous random variable is the normal random variable, whose probability density curve is symmetric (bell-shaped), bulging in the middle and tapering at the ends.

* There are "many" normal random variables, each determined by its mean μ (which determines where the density curve is centered) and standard deviation σ, (which determines how spread out (wide) the the normal density curve is).

*  Any normal random variable follows the Standard Deviation Rule which can help us find probabilities associated with the normal random variable.

*  Another way to find probabilities associated with the normal random variable is using the standard normal table. This process involves finding the z-score of values, which tells us how many standard deviations below or above the mean the value is.

* * An important application of the normal random variable is that it can be used as an approximation to the binomial random variable (under certain conditions). A continuity correction can improve this approximation.

## Sampling Distributions
A *parameter* is a number that describes the population, and a statistic is a number that describes the sample.

  * Parameters are fixed, and in practice, usually unknown.

  * Statistics change from sample to sample due to sampling variability.

  * The behavior of the possible values the statistic can take in repeated samples is called the sampling distribution of that statistic.

## The sampling distribution of the sample proportion, p, (under certain conditions):

  * is centered around p, the proportion in the entire population from which the sample is drawn.
  * has standard deviation of √p(1−p)/n
  * is approximately normal (under certain condtions).

According to the Central Limit Theorem, the sampling distribution of the sample mean, ¯¯¯X (X-bar):
* is centered around μ the mean in the entire population from which the sample is drawn.
* has a standard deviation of σ√n
* .for large enough sample size n, is approximately normal (regardless of the shape of the population distribution).

## Reference
* [CMU statistical reasoning summary] (https://oli.cmu.edu/jcourse/workbook/activity/page?context=8ff078e10a0001dc511d84c945d35bdd)