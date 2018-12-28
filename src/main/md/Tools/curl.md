* curl is library libcurl and command line tool
  *  can be used to test communication for many protocols
* **curl can't recursively download files, so it can't mirror a site to remote location. If we need it, we have to make use of wget**
* curl supports FTP, FTPS, Gopher, HTTP, HTTPS, SCP, SFTP, TFTP, Telnet, DICT, LDAP, LDAPS, IMAP, POP3, SMTP, RTSP and URI
* curl command line options
  * -o output to a file
  * -L follow the redirect
* curl usage
  * curl -X<VERB> '<PROTOCOL>://<HOST>/<PATH>?<QUERY_STRING>' -d '<BODY>'
  * curl --cookie "name=xyz" --referer -H "Accept: application/json" -H "Content-Type: application/json"  http://hostname/resource
  * curl --cookie "name=xyz" --referer --header "Accept: application/json"  http://hostname/resource
  * curl --data "param1=value1&param2=value2" http://hostname/resource
  * curl --form "fileupload=@filename.txt" http://hostname/resource
  * curl -X POST -d @jsonFile http://hostname:9200/_search
  * wget --no-parent -r http://WEBSITE.com/DIRECTORY (only stuff curl can't do, downloadin recusive)

# Reference
* [Curl SO](https://stackoverflow.com/questions/356705/how-to-send-a-header-using-a-http-request-through-a-curl-call)