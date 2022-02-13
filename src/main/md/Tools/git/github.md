## Github Actions - Features

* Test against multiple target environment
* Dedicated test jobs
* Access to build artifacts
* Branch protections
* Required reviews
* Obvious approvals

## Github Actions - Run on multipl os'es

```yaml

    strategy:
      matrix:
        node-version: [10.x, 12.x, 14.x]
        os: [ubuntu-latest, windows-2016]
```


# To preview github html source as normal html
  * Append the url after http://htmlpreview.github.io/? 
  * http://htmlpreview.github.io/?https://github.com/thomaspark/bootswatch/blob/v3.3.5/cosmo/index.html
  * http://htmlpreview.github.io/?https://github.com/mohanmca/MohanLearningGround/blob/master/src/main/webapp/random_numbers.html

# Sample message for every repository
…or create a new repository on the command line
echo "# DroolsPOC" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/mohanmca/DroolsPOC.git
git push -u origin master

## Github Actions

* [Interactive Lesson](https://github-actions-hero.now.sh/)
* [Github learning lab](https://lab.github.com/)
* [git-hub actions](https://stackoverflow.com/questions/tagged/github-actions)
* [Github actions - repo](https://github.com/cschleiden/github-actions-hero)
* [Github Event Types](https://docs.github.com/en/developers/webhooks-and-events/github-event-types#issuesevent)
* [Pluralsight course](https://github.com/a-a-ron)
  * [Github actions continious delivery](https://github.com/a-a-ron/github-actions-continuous-delivery-azure)
  * [NodeJs sample workflow](https://github.com/a-a-ron/github-actions-for-ci/blob/a-a-ron-patch-1/.github/workflows/node.js.yml)
  * [Sample template](https://github.com/a-a-ron/github-actions-course-template)