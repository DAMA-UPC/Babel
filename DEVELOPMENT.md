### Development

#### Run project tests

On the project root:

```
sbt test
```

### Publishing

#### STEP 1: Log into the Bintray account

In the project root, using SBT set the Bintray account login credentials:

```
sbt bintrayChangeCredentials
```

#### STEP 2: Publish the project to Bintray

On the project root:

```
sbt publish
```
