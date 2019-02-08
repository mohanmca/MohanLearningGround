## Jupyter docker images
* https://jupyter-docker-stacks.readthedocs.io/en/latest/using/selecting.html
  * jupyter/scipy-notebook
  * jupyter/tensorflow-notebook
  * jupyter/datascience-notebook
  * jupyter/pyspark-notebook
  * jupyter/all-spark-notebook
  * docker run -p 8888:8888 jupyter/scipy-notebook:17aba6048f44


```bash
sudo sh
sudo apt-get install python3
pip install scikit-learn
apt install python-pip
apt install python3-pip python3-dev
sudo -H pip3 install --upgrade pip
sudo -H pip3 install virtualenv
mkdir ~/ml
cd ~/ml/
virtualenv ml
source ml/bin/activate
pip install jupyter
jupyter notebook --allow-root --ip=0.0.0.0 --port=80
```
