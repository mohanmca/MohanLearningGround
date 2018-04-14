
* https://github.com/larsga/Duke/wiki/GettingStarted
* java no.priv.garshol.duke.Duke --verbose --progress --testdebug --showmatches  C:\Users\mohan\git\Duke\duke-core\src\main\resources\duplicate.conf.xml
* If there is no group in configuration, it would be considered as running for deduplication
*  

# References
* [Bayesian identity resolution](http://www.garshol.priv.no/blog/217.html)
* [Record_linkage#Identity_resolution](https://en.wikipedia.org/wiki/Record_linkage#Identity_resolution)
* 

# Configurations
  --progress            show progress report while running
  --showmatches         show matches while running
  --linkfile=<file>     output matches to link file
  --interactive         query user before outputting link file matches
  --testfile=<file>     test matches against known correct results in file
  --testdebug           display failures
  --verbose             display diagnostics
  --noreindex           reuse existing Lucene index
  --batchsize=n         set size of Lucene indexing batches
  --showdata            show all cleaned data (data debug mode)

  --profile             display performance statistics
  --threads=N           run processing in N parallell threads
  --pretty              pretty display when comparing records
  --singlematch         (in record linkage mode) only accept
                        the best match for each record
  --lookups             display lookup properties