from sklearn.datasets import load_iris
from sklearn.neighbors import KernelDensity
import matplotlib.pyplot as pyplot
import numpy as np

iris = load_iris()

X = iris.data



#sepal with (cm)
feature = X[:, 1]
pyplot.hist(feature, bins=30, normed=True)
pyplot.show()

plot_space = np.linspace(min(feature), max(feature), 1000)

kde = KernelDensity(bandwidth=0.05, kernel='gaussian')
kde.fit(feature.reshape(-1,1))
densities = np.exp(kde.score_samples(plot_space.reshape(-1,1)))

pyplot.bar(plot_space, densities)
pyplot.ylim(min(densities)-1, max(densities) +1)
pyplot.show()