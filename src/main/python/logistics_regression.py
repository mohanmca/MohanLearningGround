
# coding: utf-8

# In[5]:


from sklearn.datasets import load_iris
import pandas as pd

iris_data = load_iris()
print(iris_data.DESCR)


# In[7]:


X = iris_data.data
X_df = pd.DataFrame(X, columns=iris_data.feature_names)
X_df


# In[10]:


X_df['flower_type']=iris_data.target #append the target column, or Y
X_df.groupby('flower_type').mean()


# In[12]:


X_df.groupby('flower_type').max()


# In[13]:


X_df.groupby('flower_type').min()


# In[47]:


from sklearn.linear_model import LogisticRegression
model = LogisticRegression(multi_class='ovr', solver='newton-cg')


# In[58]:


from sklearn.model_selection import train_test_split
y = iris_data.target
X_train, X_test, y_train, y_test = train_test_split(X,y, test_size=0.33,random_state=65)


# In[59]:


y_test.shape


# In[60]:


model.fit(X_train,y_train)


# In[61]:


model.predict(X_train[0].reshape(1,-1))


# In[62]:


y_train[0]


# In[63]:


model.predict(X_test)


# In[64]:


y_test


# In[65]:


model.predict(X_test) == y_test


# In[66]:


y_pred = model.predict(X_test)
model.score(X_test,y_test)


# In[67]:


from sklearn.metrics import classification_report
print(classification_report(y_pred=y_pred, y_true=y_test))

