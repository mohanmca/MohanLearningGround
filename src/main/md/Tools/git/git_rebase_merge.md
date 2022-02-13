# Merge vs Rebase

## Git merge

*  Git does a simple three-way merge when merging feature branch and master (if they are diverged)
  * Using the two snapshots pointed to by the branch tips
  * The common ancestor of the two

* Merge commit
  * New snapshot that results from three way merge and automatically creates a new commit that points to it.
* In case of conflict
  * ============ -- Everything above equal sign is checkedout branch, and below is branch that i being merged
* 


## Git rebase
* Example: Check out the experiment feature branch, and then rebase it onto the master branch
* Rebase is recreating your work of one branch onto another.
* With the rebase, you can take all the changes that were committed on one branch and replay them on a different branch.
* Rebased replays by creating new commit for every old commit, with the same changes
* Rebase abandons existing commits and creating new ones that are similar but different.
* How rebase works?
  * ```bash 
      git checkout api-feature-experiment-branch
      git rebase master
    ```
  * Going to the common ancestor of the two branches (the one you’re on and the one you’re rebasing onto),
  * Getting the diff introduced by each commit of the branch you’re on
  * Saving those diffs to temporary files, resetting the current branch to the same commit as the branch you are rebasing onto
  * Finally applying each change in turn.

## Complex rebase

### Replay server branch commit to master branch

* git rebase master server

### Replay client branch commit to master branch, but ignore all the commit that are common between client and server (here client is branched from server)

* git rebase --onto master server client
  *  Take the client branch
  * Figure out the patches since it diverged from the server branch
  * Replay these patches in the client branch as if it was based directly off the master branch instead.”

## Downside of rebase

* When you rebase stuff, you’re abandoning existing commits and creating new ones that are similar but different. If you push commits somewhere and others pull them down and base work on them, and then you rewrite those commits with git rebase and push them up again, your collaborators will have to re-merge their work and things will get messy when you try to pull their work back into yours.