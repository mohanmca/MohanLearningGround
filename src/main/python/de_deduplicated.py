
# coding: utf-8

# In[2]:


import pandas as pd
from sklearn.datasets import load_boston
boston_house_prices_data = load_boston()
X = pd.DataFrame(boston_house_prices_data.data)


# In[3]:


X[X.duplicated()]


# In[6]:


X = X.append(X.iloc[0,:], ignore_index=True)


# In[7]:


X[X.duplicated()]


# In[15]:


X.iloc[[2,3,4],:]
X.iloc[[2,3,4],3]


# In[16]:


X=X.drop_duplicates()
X[X.duplicated()]

