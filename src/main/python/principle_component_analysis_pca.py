from mpl_toolkits.mplot3d import Axes3D
from sklearn.cluster import KMeans
from sklearn.datasets import load_boston
from sklearn.datasets import load_iris
from sklearn.decomposition import PCA

from sklearn import preprocessing  
import matplotlib.pyplot as pyplot
import pandas as pd
import seaborn as sns
import numpy as np

iris = load_iris()

X = iris.data

pca = PCA(n_components=3)
pca.fit(X)
X_pca = pca.transform(X)
X_pca_2 = pca.fit_transform(X)

print(X_pca)

print("\n"*5)
for pc in pca.components_:
  _p(pc)

print("\n"*5)
from pprint import pprint as _p
for pc in pca.components_:
  _p(list(zip(iris.feature_names, pc)))
  print('')

pca.explained_variance_ratio_  