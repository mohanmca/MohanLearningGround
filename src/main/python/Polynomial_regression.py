from sklearn.preprocessing import PolynomialFeatures
import numpy as np

X = np.arange(4).reshape(2,2)
X

poly = PolynomialFeatures(degree=2)
poly.fit_transform(X)

from sklearn.linear_model import LinearRegression
from sklearn.pipeline import Pipeline
model = Pipeline([('poly', PolynomialFeatures(degree=3)), ('linear', LinearRegression(fit_intercept=False))])

x = np.arange(5)
y = 3 - 2 * x + x ** 2 - x ** 3
np.stack([x,y])

model.fit(x[:,None],y)

model.named_steps['linear'].coef_