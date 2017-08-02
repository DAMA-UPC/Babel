# Babel  [![Build Status](https://travis-ci.org/DAMA-UPC/Babel.svg?branch=master)](https://travis-ci.org/DAMA-UPC/Babel) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/438d92393c7e4b0e897fc37dcf788a75)](https://www.codacy.com/app/DAMA-UPC/Babel?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DAMA-UPC/Babel&amp;utm_campaign=Badge_Grade) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/438d92393c7e4b0e897fc37dcf788a75)](https://www.codacy.com/app/DAMA-UPC/Babel?utm_source=github.com&utm_medium=referral&utm_content=DAMA-UPC/Babel&utm_campaign=Badge_Coverage)

WIP: Synthetic data generator framework

### Development

#### Run project tests

On the project root:

```
sbt test
```

### Publishing

#### STEP 1: Log into the bintray account

In the project root, using SBT set the Bintray account login credentials:

```
sbt bintrayChangeCredentials
```

#### STEP 2: Publish the project to Bintray

On the project root:

```
sbt publish
```
