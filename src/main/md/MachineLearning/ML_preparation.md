* Feature engineering is often the most impactful thing you can do to improve quality of models and a place where I often see beginners (and experts for that matter) get stuck.
* Feature engineering takes up about 75% of the time
* Good ML tools live or die with good feature engineering
* There are various feature engineering and feature extraction techniques. Filter methods, wrapper methods, and embedded methods. Principle component analysis, autoencoding, variance analysis, linear discriminant analysis, Gini index, genetic algorithms, etc -- the feature selection process will depend on the dataset, the problem domain, the analysis algorithm you ultimately use, etc.



* How did you figure out what algorithm(s) are appropriate?
  * It depends on the problem domain. 
  * Discrete or continuous data? 
  * Categorical features, numeric features, features as bitmasks. 
  * Do you need a probabilistic outcome? Etc.
  * Generally you start with the easiest algorithms in your toolbox to see how viable they are.
    * For a classification task I'll almost always start with a naive Bayes classifier (if the data allows) and/or a random forest and see how they perform. 
    * If the problem domain is highly non-linear you might start with a support vector or kernel method.
    * Neural network could be a last resort, asmost classification problems can be solved to a high accuracy much more simply.
    * Solution steps    
      * What's the distribution of each dimension?
      * Are the relationships linear, nonlinear, clustered, dispersed, logarithmic, etc.

## How to Practice?
* Find a dataset
* Choose a predictor
* filter, clean and massage your data till you get better metrics/understanding. 
* Rinse, repeat on many different datasets and problems, and you'll know how to do this.

## Reference
* [Machine Learning Crash Course](https://news.ycombinator.com/item?id=16493489)