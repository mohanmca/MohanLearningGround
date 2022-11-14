#!/bin/bash
#  ./download_pdf.sh ./result/female/file
export i=0;
while IFS= read -r line; do
    i=$((i+1))
    url="https://chennaicorporation.gov.in/online-civic-services/birthCertificate.do?do=BirthCertificate&registrationNumber=$line"
    echo "curl -L -o $i.pdf" "\""$url"\""
done < $1