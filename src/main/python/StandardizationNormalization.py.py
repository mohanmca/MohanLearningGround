
# coding: utf-8

# In[6]:


data = [
    [0,0],[0,0],
    [1,1],[1,1]
]
data


# In[4]:


from sklearn.preprocessing import StandardScaler
StandardScaler().fit_transform(data)


# In[7]:


from sklearn import preprocessing
preprocessing.normalize(data, norm='l2')

