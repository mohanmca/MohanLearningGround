* Lucene - inverted index
  * Index - Page to words (Table of content)
  * Inverted Index - Words to page (Back of the book index)
  * Indexing involves adding Documents to an IndexWriter
  * Searching involves retrieving Documents from an index via an IndexSearcher
* Lucene has Document, IndexWriter, IndexSearcher
  * Document has Fields
  * IndexWriter has IndexWriterConfig and Directory (index)
  * IndexWriterConfig has Analyzer 
  * IndexSearcher has IndexReader

* Analyzers 
  * used during ingestion, when a document is indexed, and at query time. 
  * An analyzer examines the text of fields and generates a token stream. 
  * Analyzers may be a single class or they may be composed of a series of tokenizer and filter classes.
* Tokenizers break field data into lexical units, or tokens.
* Filters : Filters examine a stream of tokens and keep them, transform or discard them, or create new ones.
  * Tokenizers and filters may be combined to form pipelines, or chains, where the output of one is input to the next. 
  * Sequence of tokenizers and filters is called an analyzer and the resulting output of an analyzer is used to match query results or build indices.
  * org.apache.lucene.analysis.core.StopFilter

Filters examine a stream of tokens and keep them, transform or discard them, or create new ones. Tokenizers and filters may be combined to form pipelines, or chains, where the output of one is input to the next. Such a sequence of tokenizers and filters is called an analyzer and the resulting output of an analyzer is used to match query results or build indices.  

* Every record in Lucene is considered as document, and document will have one or more fields
  ```Java 
   Document doc = new Document(); 
   doc.add(new TextField("title", "Lucene in Action", Field.Store.YES));
   doc.add(new StringField("isbn", "193398817", Field.Store.YES));
  ```
* Documents are stored in index
   ```Java
  Directory index = new RAMDirectory()
  IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig(new StandardAnalyzer()));
  indexWriter.addDocument(doc);
   ```
* ```Java
	Query q = new QueryParser("title", analyzer).parse(querystr);
	IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index));
	TopDocs docs = searcher.search(q, hitsPerPage);
	ScoreDoc[] hits = docs.scoreDocs;
   ```

* Lucene Query Syntx
```yaml
title:foo  #Search for word "foo" in the title field.
title:"foo bar"
title:"foo bar" AND body:"quick fox"
(title:"foo bar" AND body:"quick fox") OR title:fox
title:foo -title:bar
title:foo* #Search for any word that starts with "foo" in the title field.
title:foo*bar #Search for any word that starts with "foo" and ends with bar in the title field.
"foo bar"~4 #Search for "foo bar" within 4 words from each other.
mod_date:[20020101 TO 20030101]  #Range Queries allow one to match documents whose field(s) values are between the lower and upper bound specified by the Range Query.
(title:foo OR title:bar)^1.5 (body:foo OR body:bar) #Assigning higher boosts to title matches than to body content matches - Query time boost
```
 
# References
* [Lucene in 5 minutes](http://www.lucenetutorial.com/lucene-in-5-minutes.html)
* [Basic Concepts](http://www.lucenetutorial.com/basic-concepts.html)
* [Query syntax](http://www.lucenetutorial.com/lucene-query-syntax.html)
* [Analyzers, Tokenizers, and Filters](https://lucene.apache.org/solr/guide/6_6/understanding-analyzers-tokenizers-and-filters.html)