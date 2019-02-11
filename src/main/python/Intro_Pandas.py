
# coding: utf-8

# In[3]:


from sklearn.datasets import load_boston
boston_house_prices_data = load_boston()


# In[5]:


print(boston_house_prices_data.DESCR)


# In[6]:


import pandas as pd
import numpy as np

pd.Series([1,3,4,2,3])
pd.Series([1,np.nan,4,2,None])


# In[7]:


num_rooms = pd.Series([1,np.nan,4,2,None])
num_rooms.isnull()


# In[10]:


num_rooms[num_rooms.notnull()]


# In[16]:


df = pd.DataFrame(
    [[1,np.nan, 2],
     [2,300, 5],
     [1,np.nan, np.nan],
    ]
)
df


# In[17]:


df.isnull()


# In[18]:


df.dropna()


# In[20]:


means = df.mean(axis=0)


# In[21]:


means


# In[22]:


df.fillna(means)

