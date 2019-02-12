from sklearn.linear_model import LinearRegression
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import PolynomialFeatures
from sklearn.model_selection import train_test_split
from sklearn import linear_model
import matplotlib.pyplot as pyplot
from pprint import pprint as _p

X = boston_data.data
y = boston_data.target
X_train, X_test, y_train, Y_test = train_test_split(X,y,random_state=42)

model = linear_model.LinearRegression()
model.fit(X_train, y_train)

for coef, label in zip(model.coef_, boston_data.feature_names):
  print("{:10.4f}".format(coef), label)
print("\n"*2)

model = linear_model.Ridge(alpha=1)
model.fit(X_train, y_train)
for coef, label in zip(model.coef_, boston_data.feature_names):
  print("{:10.4f}".format(coef), label)
print("\n"*2)

model = linear_model.Lasso()
model.fit(X_train, y_train)
for coef, label in zip(model.coef_, boston_data.feature_names):
  print("{:10.4f}".format(coef), label)
print("\n"*2)

model = linear_model.Lasso(alpha=0.1)
model.fit(X_train, y_train)
for coef, label in zip(model.coef_, boston_data.feature_names):
  print("{:10.4f}".format(coef), label)
