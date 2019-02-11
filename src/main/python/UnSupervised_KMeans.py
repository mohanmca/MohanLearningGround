import seaborn as sns
from sklearn.datasets import load_iris
from sklearn.cluster import KMeans

import matplotlib.pyplot as pyplot
from mpl_toolkits.mplot3d import Axes3D

iris = load_iris()
X = iris.data
y = iris.target

model = KMeans(n_clusters=3)
model.fit(X)
labels = model.labels_
labels

# In[10]:
fig = pyplot.figure(1, figsize=(10,10))
ax = Axes3D(fig, rect=[0,0,.95,1], elev=48, azim=134)

ax.scatter(X[:, 3], X[:, 0], X[:, 2], c=labels.astype(np.float), edgecolor='k')

ax.set_xlabel('Petal width')
ax.set_ylabel('Sepal length')
ax.set_zlabel('Petal length')