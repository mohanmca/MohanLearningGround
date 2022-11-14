## To extract BirthCertificate
1. cat 1977_11_10.txt | grep onclick | awk '{print $8}' | grep -o "'C.*[^']*'" | awk -F, '{print $1}' > ../../certificates.txt

```python
f = open('../../certificates.txt','r')
content = f.read()
lines = content.split('\n')
for idx, line in enumerate(lines):                                                                                                                                           
    print(f"curl -L -o {idx}.pdf  https://chennaicorporation.gov.in/online-civic-services/birthCertificate.do?do=BirthCertificate&registrationNumber="+line.replace("'",""))
```