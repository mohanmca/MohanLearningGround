* Vital Statistics
  * Average
* Mathematics Statistics
  * Variance
  * Predictions
* Discrete - Countable - Color, Gender, Eye Color
* Continious - Measurable - Temperature, Height 
* Florance Nightingale
  * Nurse and Statistician
  * Reported about army mortality and death caused by diseases like typhoid
* Probablity
  * Probability approaches and distribution choices
  * 5 Approachaes
    * Subjective
    * Game of chance
    * Mathematical
    * Relative Frequency
    * Bayesian
  * 6 main distributions
    * Binomial
    * Poisson
    * Normal
    * Chi-Squared
    * t Distribution
    * F Distribution
      * Last 3 are used for statistical significance     
* De Moivre's Theorem
  * e^i@ (e power i theta)
* Binomial distribution
  * Binomial distribution for fair coin with 'n' flips
  * If a coin is flipped 10 times, chances of occurence of 10 heads
  * (1/2)^10 = 1/1024 => 1 in 1024 is the possibility  
  * (p + q)^n --> binomial distribution
  * When n approaches zero, Binomial becomes normal distribution
 * Posson distribution
   * The Poisson distribution can be used to calculate the probabilities of various numbers of "successes" based on the mean number of successes. ... The mean of the Poisson distribution is μ. The variance is also equal to μ.
   * The fundamental trait of the Poisson is its asymmetry
   * Extreme case of Binomial distribution
   * Simon Denis Poission - (French mathematician, poisson -> Statistics)  
* Normal distribution
  * Also known as Gaussian distribution, Bell curve
  * If a coin is flipped 10^10 times, chances of occurence of everything as heads, chances of occuring 1000 continious heads
  * (p + q)^n --> binomial distribution, when n approaches infinity, binomial becomes normal
  * Yard stick to compare other distributions
  * Three mathematical property
    * Mean (0), Standard-Deviation (1)    
    * Skewness (zero for normal), direction of tail indicates if it is +ve skewed or -ve skewed    
* Central Limit Theorem
  * Independent small errors, or law of error
  * Pierre-Simon Laplace (french mathematician)
  * Random number of very large number of many small and unrelated random effects will be approximately normally distributed
* Mean, Median, Mode
  * There are 3 averages for mathematician
    * Arithmatic mean, median and mode
  * Sometime easier to find median than mean (Ex: height of 100 men)
  * Median is middle (once sorted) - (n + 1)/2 spot in the ordered list
    * It is the number such that half of the observations fall above, and half fall below.
  * Mode - Modal family, most number of occrences. The value that occurs with the highest frequency is the mode.
  * It is possible to have more than one mode
  * Outliers (one of too high, too low) may skew Mean, but not mode. Mean is very sensitive to outliers.
  * Easy to pick one among, to mislead data
  * To know the truth, we should know variation around mean values
  * Example: Median mortality of eight months means, it is 50th percentail point in distribution, 50% of people live longer than eigth months
* Sampling techniques
  * Systematic - every n'th element. 
  * Incidental - use most accessible and available. (Unreliable)
  * Purposive - Find representative
  * Stratified - Select specific and find non-overlapping groups 
* Plotting
  * Histogram vs Bar Chart
  * Frequency polygon (simplest curve fitting)
* The method of moments
  * Average - first method of moments
  * The average squared deviation (Variance) - second method of moments. Measures spread
  * The average cubed deviation (Skewness) - third method of moments. Measures symmetry
  * The average deviation raised to the 4th power (Kurtosis) - fourth method of moments
  	* Kurtosis - negative - less peaked normal curve
  	* Kurtosis - positive - high peaked normal curve
  	* Kurtosis - zero - normal curve  	
  * Quartile and Range are other way to measure simple variances
  * Covariance
    * If two random variable moves in same direction - +ve variance   
    * If two random variable moves in opposite direction - -ve variance
    * If two random variable not moves to each other - zero variance
  * Standard deviation
    * Small - most of the data is near mean
    * Large - They are way away from mean    
  * Co-efficient of variation
    * Karl_Pearson - created domain of Mathematical statistics
    * Std.Deviation as % as of mean. [(Std.dev/Mean) * 100]
    * Used to compare "regardless of underlying unit"
    * Example: Compare celcius of london temp with "Farenheit of Newyork"
  * Nominal
    * Not continious, Example: Eye Color,
  * Orinal
    * if you sorted, what is the index number
    * Like enums
  * Interval and Ratio
  * zero temperature - doesn't show lack of temperature
  * 20 degress is not twice as 10 degree
  * Correlatoin - using one body part, deriving whole species
  * Illusory correlation vs spurious correlation
  * Correlation is not percentage
  * Correlation is curvilinear relationship (Exmple: Growth of human, small to high, high to small)
  * Regression towards the mean (requires two lines)[Exmple: Heights of father, and Heights of son)
  * Method of least square (reduces influence of errors)
  * Correlation coefficient (r = 08, 0, -0.9)
  * Product moment correlation coefficient 
  * Simple regression two variable (y =ax + b)
  * Multi-variate - matrix based, multiple independent variables are involved
  * QStatistic (short/tall, poor or rich) Derive binary from continious data
  * Point-biserial correlation
  * Person's triserial correlation
  * Rank order correlation
  * Tau coefficient
  * Statistical Hypothesis
  * Gosset guiness z-test
    * When sample size is smaller, normal distribution won't fit
    * First one used in quality control
    * t-test is replacement for z-test
  * Analysis of variance
* IQR - Another measure of spread is the inter-quartile range (IQR), which is the range covered by the middle 50% of the data.  
* An observation is considered a suspected outlier if it is:
  * Below Q1 - 1.5(IQR)
  * Above Q3 + 1.5(IQR)
* Inferential Statistics
  * Formal testing and estimation theory
  * Based on random variation

## Sampling  
* Sample results change from sample to sample, is called sampling variability.
* The sample mean and sample standard deviation are slightly different from the population mean and standard deviation.