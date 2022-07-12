## Cross-site-scripting: Reflected
1. [Cross_Site_Scripting_Prevention_Cheat_Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)
   1. [](https://owasp.org/www-community/Types_of_Cross-Site_Scripting)
2. Ensure that all variables go through validation and are then escaped or sanitized is known as perfect injection resistance.
3. Output Encoding is recommended when you need to safely display data exactly as a user typed it in.

## Logback debug
1. Inside the xml enable debug=true attribute
   1. <configuration debug="true />
2. -Dlogback.debug=true
3. -Dlogback.configurationFile=file:///c:\apps\logback_test.xml