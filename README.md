# Babel: A domain specific language for graph generation
[![Build Status](https://travis-ci.org/DAMA-UPC/Babel.svg?branch=master)](https://travis-ci.org/DAMA-UPC/Babel) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/438d92393c7e4b0e897fc37dcf788a75)](https://www.codacy.com/app/DAMA-UPC/Babel?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DAMA-UPC/Babel&amp;utm_campaign=Badge_Grade) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/438d92393c7e4b0e897fc37dcf788a75)](https://www.codacy.com/app/DAMA-UPC/Babel?utm_source=github.com&utm_medium=referral&utm_content=DAMA-UPC/Babel&utm_campaign=Badge_Coverage)

WIP: Synthetic data generator framework


![running-example](docs/running-example.png)

### Step 1: Define the node models

```scala
import babel._
import java.time.LocalDate

@node class Actor(name: String,
                  gender: Option[String],
                  country: String,
                  birthDate: LocalDate)
```


```scala
import babel._

@node class Movie(director: String,
                  title: String,
                  releaseDate: LocalDate,
                  country: String,
                  budgetInUSDollars: Option[Double])
```

### Step 2: Define the edge models

```scala
import babel._
import java.time.LocalDate

@edge(source = Actor, target = Movie, cardinality = ManyToMany)
class Portrayed(characterName: String)
```

WIP