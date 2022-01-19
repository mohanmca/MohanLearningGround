## Installation of CockroachDB on MAC
```shell
softwareupdate --all --install --force
sudo rm -rf /Library/Developer/CommandLineTools
sudo xcode-select --install
brew install cockroachdb/tap/cockroach
export LANG=en_US.UTF-8
export LC_ALL=$LANG

curl https://binaries.cockroachdb.com/cockroach-v21.2.4.src.tgz | tar -xz
cd cockroach-v21.2.4
make build
make install


curl https://binaries.cockroachdb.com/cockroach-v21.2.4.darwin-10.9-amd64.tgz | tar -xJ && cp -i cockroach-v21.2.4.darwin-10.9-amd64/cockroach /usr/local/bin/
tar -xJ && cp -i cockroach-v21.2.4.darwin-10.9-amd64/cockroach /usr/local/bin/
cockroach demo  
```

## Cockroach DB Certificates
1. [Introduction to Distributed SQL and CockroachDB](https://university.cockroachlabs.com/certificates/b2838d8b65404afbac4f83a44289f710)