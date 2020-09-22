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