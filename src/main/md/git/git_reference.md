# Find diff between local commit with origin
git diff origin/master --

# TO list the changes with remote branch before commiting
git diff origin/branch  --


# Create patch with origin
git format-patch origin/master

# Create patch for SHA
git format-patch -1 SHA1

git diff SHA1 SHA2 > diff_patch.patch

# Reset my local branch exactly like Origin
git fetch origin
git reset --hard origin/master

# Git apply patch locally
git apply --stat fix_empty_poster.patch (test)
git apply --check fix_empty_poster.patch (check)
git am --signoff < fix_empty_poster.patch

# List commit ids 
git log | grep commit | gawk '{print $2}'

# List commit done on weekdays alone
git log | grep Date | grep -v "Sun" | grep -v "Sat"

# Git overwrite one file
git fetch
git checkout origin/master <local_filepath>

Find lat n commit by author

# Git RCA commands
* To find how a commit ended up in a revision
```bash
git tag --contains shacommit

git log <olderTag>..<newerTag> --name-only --format="%aN <%aE>" --reverse

git log tag1..tag2
git diff tag1 tag2 --stat
git diff tag1 tag2 -- some/file/name
```

# Git Merging
```bash
git log -n 2 --author=Mohan --pretty=format:%H  | tac
git checkout branch (to merge content added in HEAD
git cherry-pick 6db4270dec5d0035430683c399c6c1340cc83c67
git cherry-pick 2bbfedb7998608298e669c0b4989f3225c4ab79e
```


# Handling git line feed
Eclispe > Window > Preferences > General > Workspace > New File line Delimiter > Unix
Eclipse Preferences / Team / Git / Configuration / User Settings @ Core Section - key: autocrlf value: false
git config --system core.whitespace cr-at-eol
git config --system core.autocrlf false

# Reference
* [Tomy's reference](https://gist.github.com/mohanmca/d405dd27fdfa92b51975)
* [Ashish Rawat's reference](https://gist.github.com/eashish93/3eca6a90fef1ea6e586b7ec211ff72a5)