## Basic commands
1. How to run other notebook
   1. %run ../Includes/Classroom-Setup-01.2
2. How to retrieve additional variable from the running context
   1. %sql
      ```
      SELECT '${da.username}' AS current_username,
      '${da.paths.working_dir}' AS working_directory,
      '${da.schema_name}' as schema_name
      ```
3. Databricks Utilities
   1. dbutils.fs.help()
   2. dbutils.fs.ls(path)
   3. files = dbutils.fs.ls(path);display(files)


## Reference
1. [Databricks](https://github.com/databricks-academy/data-engineering-with-databricks-english)