* [TextQL: Execute SQL Against CSV or TSV](https://news.ycombinator.com/item?id=16781294)
* SQLite can do
  * ```bash
  		sqlite> create table test (id integer, datatype_id integer, level integer, meaning text);
  		sqlite> .separator ","
  		sqlite> .import no_yes.csv test
    ```
  * sqlite import will not accept stdin, breaking unix pipes. textql will happily do so.
  * sqllite - Need to create table upfront
  * https://github.com/mingodad/sqlite3-hashcode-2018
  * textql supports quote escaped delimiters, sqlite does not.
  * extql leverages the sqlite in memory database feature as much as possible and only touches disk if asked.
  * https://github.com/dinedal/textql#key-differences-between-textql-and-sqlite-importing
* https://jsvine.github.io/intro-to-visidata/
  * Quickly open, explore, summarize, and analyze datasets
  * Wonderul documentation
* http://harelba.github.io/q/
  * q - the name can make it a littler harder to find if you don't remember the repo.
  * q can read stdin and write CSV to stdout 
  * q can chain several queries on the command line, or use it in series with other with other commands such as cat, grep, sed, etc. 
  * Highly recommended if you like SQL and deal with delimited files.
  * q "SELECT COUNT(*) FROM ./clicks_file.csv WHERE c3 > 32.3"
  * ps -ef | q -H "SELECT UID,COUNT(*) cnt FROM - GROUP BY UID ORDER BY cnt DESC LIMIT 3"
  * Supports multiple encodin
* https://csvkit.readthedocs.io/en/1.0.3/
  * csvsql --query "select name from data where age > 30" data.csv > new.csv
  * Csvsql uses sqlite under neath and you can do some nice things with this like joins, using sql functions, etc
  * amazon athena that allows to do similar things in s3 at scale.
  * Csvkit is great with pipes and you can also easily convert between csv tsv and even stuff like json  
* Python's pandas library can also do sql-like queries against csv data.
* Charlatan - https://github.com/BatchLabs/charlatan#charlatan
  * much lower memory footprint and faster execution
  * works on stream
  * subset of SQL that’s implemented
* [Windows you may consider using Log Parser Studio](https://gallery.technet.microsoft.com/office/Log-Parser-Studio-cd458765)
  * https://en.m.wikipedia.org/wiki/Logparser
* [Apache Drill](https://drill.apache.org/)
  * It works perfectly fine on local CSV and JSON files
* [BigBash](http://bigbash.it)
  * Converts an Sql statement to a bash one-liner (using sed, grep, awk,...)
  * Can execute the query using linux commands
  * on very large file(s) because of the streaming nature
* https://github.com/kamac/AskXML
  * Works on XML and JSON
* ClickHouse
  * ```bash
  		ps aux | tail -n +2 | awk '{ printf("%s\t%s\n", $1, $4) }' | \
        clickhouse-local -S "user String, mem Float64" \
            -q "SELECT user, round(sum(mem), 2) as memTotal FROM table GROUP BY user ORDER BY memTotal DESC FORMAT Pretty"
  ```
* Sqawk
  * ```bash
  $ ps aux | sqawk -output table \
                   'select user, round(sum("%mem"), 2) as memtotal
                    from a
                    group by user
                    order by memtotal desc' \
                   header=1  
  ```
* http://quisp.sourceforge.net/shsqlhome.html
  * Similat to textql
* Facebook's osquery. 
  * Turns even complex structures into sql tables
* https://www.lnav.org/
  * An advanced log file viewer
  * File formats are automatically detected and compressed files are unpacked on the fly.
* fsql - https://metacpan.org/pod/distribution/App-fsql/bin/fsql
  * lets you perform SQL queries against one or several "flat" files of various formats.
  * can modify data (currently CSV only) via SQL INSERT or DELETE commands.