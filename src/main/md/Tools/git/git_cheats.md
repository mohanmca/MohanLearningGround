## Find important authors who commit code

```
git  log -10000 --pretty=format:"%aN" | sort | uniq -c | sort -nr > authors.txt
```

## Find list of files modified by authors under specific directory

```
git  log --author=Mohan --name-only -- serice_directory > filesModifiedByMohan.txt
sort filesModifiedByMohan.txt | gawk -F/ '{print $NF}' | sort |  uniq -c| sort -nr > authorModifiedFiles.txt
```


## Find multiple repositories under one org

```javascript
navigate to: http://bitbucket/org/

let links= Array.from(document.getElementByTagName("a")).filter(e => e.hasAttribute("data-repository-id")).map(link => link.href)
let ssh_links = links.map(link => link.toLowerCase().replace('https','ssh').replace('bitbucket)
```


## Clone multiple repositories

```bash
while read name;
do 
  echo $name;
  git clone $name;
done < git_links.txt
```

## Update multiple repos

```bash
base=`pwd`
for folder in `ls -d *`;
do 
  cd $folder;
  echo "Git pull for ".$folder;
  git pull;
  cd $base
#  cd ..;
done
```


## Find branch operations

```
git checkout -b new_branch_from_checkedout_branch
```
