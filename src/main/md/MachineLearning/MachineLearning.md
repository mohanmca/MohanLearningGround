## Getting Started with Machine Learning in Python - Rudy Lai

## Topics
* Environment to create ML models
* Prepare your datasets for ML with data cleaning
* Putting data into the right categories (classification - Not continious)
* Regression is opposite of classification (Predication - Continious)
* UnSupervised Learning: Segmenting Groups and Detecting Outliers
* Modelling complex relationships with Nonlinear models
  
## Rule based vs ML
* Rule based functions are table driven, developed by developer
* ML functions are complex, they are not defined by developer
* Machine derives function itself based on model and data
* How helicopter should fly can't be defined in single function
* ML = data + model + feedback_loop
* ML models are simplification are real-life
* feedback_loop is feeding the error back to system as a feedback
* Without data, there is no ML

## Tasks
* ML is all about predicting things
* Regression is the task of predicting numbers
* Classification is the task of predicting labels

## Why python
* Lots of modules related to data
* Interpretted - Feedback loop is faster in python due to REPL
* Large amount of ML modules are available
  * Pandas - Ability to manage tabular data
  * Scikit-Learn
  * Jupyter Notebook - Combine normal document with Python code
* 1000 data per class should be present

## Standardization and Normalization
* Machine Learning would work well for normally distributed data
* Mean of zero and standard deviation of 1 - should be there
* We can normalize data by converting into different units
* Kernel of SVM and L1 & L2 regularizer requires data to be normally distributed

## Scklearn Toy Datasets
* load_boston([return_X_y])	Load and return the boston house-prices dataset (regression).
* load_iris([return_X_y])	Load and return the iris dataset (classification).
* load_diabetes([return_X_y])	Load and return the diabetes dataset (regression).
* load_digits([n_class, return_X_y])	Load and return the digits dataset (classification).
* load_linnerud([return_X_y])	Load and return the linnerud dataset (multivariate regression).
* load_wine([return_X_y])	Load and return the wine dataset (classification).
* load_breast_cancer([return_X_y])	Load and return the breast cancer wisconsin dataset (classification).

## Supervised Learning
## UnSupervised Learning
## Reinforcement Learning


## Seaborn and matplotlib
* Seaborn is a library for making statistical graphics in Python. It is built on top of matplotlib and closely integrated with pandas data structures.
* Matplotlib is a Python 2D plotting library which produces publication quality figures in a variety of hardcopy formats and interactive environments across platforms. Matplotlib can be used in Python scripts, the Python and IPython shells, the Jupyter notebook, web application servers, and four graphical user interface toolkits.

## False Positive, False Negative
||*Male*|*Female*|
|-|-|-|
|**Male**|True Positive|False Positive|
|**Female**|False Negative|True Negative|
*X-axis - Gold Standard*/*Y-axis - Algorithm Classification*
* precision = tp / (tp + fp)
* recall  = tp / (tp + fn)
  * The precision is intuitively the ability of the classifier not to label as positive a sample that is negative.
  * The recall is intuitively the ability of the classifier to find all the positive samples.
  * F-beta score can be interpreted as a weighted harmonic mean of the precision and recall, where an F-beta score reaches its best value at 1 and worst score at 0.

* Logistic Function or Sigmoid Function
* SigmoidFunction: x -> [0.0-1.0] (Converts any x into 0.0 to 1.0)
* Why we need them? Linear Function should be converted into probability
* Logistic regression
  * Linear model + sigmoid function
* Linear regression  
  * Linear regression = simplest linear model (additive model for all features)
  * Effect of features are linear on target use linear regression
  * Linear regression = logistic regression - sigmoid function
* Least Square
  * Why we sqaure?
  * Smaller errors are amplified
  * Bigger errors are extremly amplified  
  * All errros become +ve

# UnSupervised learning
* Segmenting Groups and Detecting Outliers
* Finding groups automatically with k-means clustering
* Reducing the number of variables in our data with PCA
* Smooting out our histograms with kernel density estimation
* When there is no answer like Y/y, better to use UnSupervised learning.
* We can use UnSupervised learning as input into supervised learning
* Dimensionality reduction
  * Vector, Metrics (2 dimension), Tesnor (3 dimension, time could be one dimension)
  * We can compress more than one dimension into one dimension without loosing any details using dimension reduction

# What is PCA?
* Principal component analysis
* PCA does dimensionality reduction by isoloating components of data
* Especialy components are linear combinations of features
* Finds the principal component
* Removes noise or tail are keeps the core

# What is kernel density estimation
* It is density of data points within particular set of values
* In Kernal density estimation, we use kernel to do density estimation
* If there is no

## Non linear models
* Features and target is not linearly mapped
* Signals could be polynomial, cubic or quadradic relationship between features and target
* Explainable models - Decision Trees
* Automatic feature engieering - Support Vector Machine
* Dealing with non-linear relationship with polynomial regression
* Reducing the number of learned rules with regularization

## Decision Trees
* Allows to stack rules
  * Example rules for IRIS data
  * If sepal length is less than 3, ignore and use  cepal length alone
  * If sepal length is grether than 3, use and cepal length

## Support Vector Machine - SVM
* Vector is mathematical way of saying a line (could be curved)
* Find data-point that supports Vector
* Further to SVM is finding Kernel
  * Kernel would make SVM (curved line)
  * Kernel Breaks the linearity of classifier
* SVM score higher the better
* How good model explains the data is the score
* SVM can use the kernel trick to find the best transformation of data points of multiple non-linear features and find decision based on it


# Polynomial 
* Squared feature/Cubic Feature/Quardratic
* Order 5, 6 or higer


# Regularization
* When model doesn't require to learn too many co-efficient in one go
* When model should be avoided with complex co-efficient
* It is to avoid over-fit the data
* L1, L2 and alpha
* Avoid noise
* Lasso is L1 regularization, try to make features as zeros



## References
* [A visual introduction to machine learning](http://www.r2d3.us/visual-intro-to-machine-learning-part-1/)
* [What is standarscaler](http://benalexkeen.com/feature-scaling-with-scikit-learn/)
* [Euclidean norm or Square Norm](https://en.wikipedia.org/wiki/Norm_(mathematics)#Euclidean_norm)
