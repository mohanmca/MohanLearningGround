* https://github.com/larsga/Duke/wiki/GettingStarted
* java no.priv.garshol.duke.Duke --verbose --progress --testdebug --showmatches  C:\Users\mohan\git\Duke\duke-core\src\main\resources\duplicate.conf.xml
* If there is no group in configuration, it would be considered as running for deduplication
* Algorithm for comparing two records
  * Find list of properties needs to be compared
  * For each propery assign two probablity, if they don't match how much *low* probablity (0.2), if they match how much *high* probablity (0.8)
  * For each property in records should be canonicalized/lemmatize/stemmed
  * Compute similarity property using Levenshtein distance 
  * property_prob = (sim >= 0.5) ?  ((high - low) * (sim * sim)) + low : low;
  * Above would punish probablity if simularity is low using square technnique
  * record_prob = Utils.computeBayes(record_prob, property_prob); //Combine the record matching problity using Naive bayes
  * Refer duke-core\src\main\java\no\priv\garshol\duke\PropertyImpl.java and duke-core\src\main\java\no\priv\garshol\duke\Processor.java

# References
* [Bayesian identity resolution](http://www.garshol.priv.no/blog/217.html)
* [Record_linkage#Identity_resolution](https://en.wikipedia.org/wiki/Record_linkage#Identity_resolution)
* [Mike Mull: The Art and Science of Data Matching](https://www.youtube.com/watch?v=Y-nYEOgq3YE)
* [Peter Christen: Session 1 - Record Linkage Workshop at the ADRC-Scotland, 13 July 2015](https://www.youtube.com/watch?v=DyGonV7A_EY)
  * https://www.youtube.com/watch?v=DyGonV7A_EY&list=PL1gmpH4hgt0bSIOTWCOujVLRjhtwRIG7R 

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