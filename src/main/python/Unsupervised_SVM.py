from sklearn.datasets import load_boston
from sklearn.model_selection import train_test_split

boston_data = load_boston()

X = boston_data.data
y = boston_data.target
X_train, X_test, y_train, Y_test = train_test_split(X,y,random_state=42)


from sklearn import svm, linear_model
from sklearn.metrics import mean_squared_error


model = svm.LinearSVR(random_state=42)
model.fit(X_train, y_train)
score = model.score(X_test, Y_test)
print(score)


model = svm.SVR()
model.fit(X_train, y_train)
score = model.score(X_test, Y_test)
print(score)


model = linear_model.LinearRegression()
model.fit(X_train, y_train)
y_pred = model.predict(X_test)
score = model.score(X_test, Y_test)
print(score)