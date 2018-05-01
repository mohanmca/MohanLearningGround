* git clone https://github.com/camunda/camunda-bpm-webapp.git
* git grep -2 spring /* details of the search result, with prior 2 line and following 2 lines as context */
* git grep -c spring /* summary of search with counts */ 
* git grep  -e ProcessDefinitionDto  --and -e result /* search for the word ProcessDefinitionDto followed by result in same line*/
* If we need to find *When* a term started come to existence, use git log search
  * git log -S queryParameter --oneline
  * git log -p -S queryParameter /* search the word in the commit diff */
  * git log --all --grep='queryParameter' /* search the word across all branch in commit*/
  * git grep queryParameter $(git rev-list --all) /* Might throw Argument list too long */
  * git rev-list --all | xargs git grep queryParameter