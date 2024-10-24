## Find important authors who commit code

```
git  log -10000 --pretty=format:"%aN" | sort | uniq -c | sort -nr > authors.txt
```

## Find changes between two commit (only merged) for a particular directory
1. Filters the log to show only merge commits (i.e., commits created by merging branches).
2. --first-parent: Shows only the commits made on the "first parent" branch (mainline branch), ignoring commits from merged branches. This is useful when you want a simplified view of how the mainline progressed, focusing only on the top-level history.
```bash
git log --merges --first-parent --date=short --pretty=format:"| %h | %ad | %<(22,trunc)%an | %<(70,trunc)%b |" f0e25515431ec72de90e87995ca204cbf8baec17..95c73df718b46331e9512bd5005c45c8ca60dd8a service-gateway/
```

## Find list of files modified by authors

```
git  log --author=Mohan --name-only > filesModifiedByMohan.txt
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

## Find diff between local commit with origin

```bash
git diff origin/master --
git diff master remotes/origin/master..origin/master
```

## TO list the changes with remote branch before commiting

```bash
git diff origin/branch  --
```


# find untracked files

```bash
git ls-files --others --exclude-standard
```

# Create patch with origin
```bash
git format-patch origin/master
```

## Create patch for SHA
```bash
git format-patch -1 SHA1
git diff SHA1 SHA2 > diff_patch.patch
```

## Reset my local branch exactly like Origin

```bash
git fetch origin
git reset --hard origin/master
```
## Git apply patch locally

```bash
git apply --stat fix_empty_poster.patch (test)
git apply --check fix_empty_poster.patch (check)
git am --signoff < fix_empty_poster.patch
```

## List commit ids 

```bash
git log | grep commit | gawk '{print $2}'
```

## List commit done on weekdays alone

```bash
git log | grep Date | grep -v "Sun" | grep -v "Sat"
```

## Git overwrite one file

```bash
git fetch
git checkout origin/master <local_filepath>
```

## Git RCA commands

* To find how a commit ended up in a revision
```bash
git tag --contains shacommit

git log <olderTag>..<newerTag> --name-only --format="%aN <%aE>" --reverse

git log tag1..tag2
git diff tag1 tag2 --stat
git diff tag1 tag2 -- some/file/name
```

## Git Merging

```bash
git log -n 2 --author=Mohan --pretty=format:%H  | tac
git checkout branch (to merge content added in HEAD
git cherry-pick 6db4270dec5d0035430683c399c6c1340cc83c67
git cherry-pick 2bbfedb7998608298e669c0b4989f3225c4ab79e
```


## Handling git line feed

```
Eclispe > Window > Preferences > General > Workspace > New File line Delimiter > Unix
Eclipse Preferences / Team / Git / Configuration / User Settings @ Core Section - key: autocrlf value: false
git config --system core.whitespace cr-at-eol
git config --system core.autocrlf false
```

# Reference
* [Build your git within 20 minutes using shell - Git Roots and Branches - Scott Wiersdorf](https://www.youtube.com/watch?v=qq_s2Hh--aQ)
* [Tomy's reference](https://gist.github.com/mohanmca/d405dd27fdfa92b51975)
* [Ashish Rawat's reference](https://gist.github.com/eashish93/3eca6a90fef1ea6e586b7ec211ff72a5)


