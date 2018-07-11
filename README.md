# Wordify (number to text converter)

Converts number to British english text (words).

### Libraries used

```scala
val catsCoreVersion = "1.0.1"  // For Validation and triple equals
val shapelessVersion = "2.3.3" // For type tags
val scalaTestVersion = "3.0.5" // For tests

libraryDependencies ++= Seq(
  "org.typelevel"     %% "cats-core"       % catsCoreVersion,
  "com.chuusai"       %% "shapeless"       % shapelessVersion,
  "org.scalatest"     %% "scalatest"       % scalaTestVersion % "test"
)
```

### Instructions to compile, run source code and run tests

- Compile

```
sbt compile
```

- Run tests

```
sbt test
```

- Run project

```
sbt "run <number_to_be_converted_to_words>"
```

```
[wordify] sbt "run 34343"
[info] Loading settings from plugins.sbt ...
[info] Loading project definition from /Users/foobar/Documents/Projects/Scala/wordify/project
[info] Loading settings from build.sbt ...
[info] Set current project to wordify (in build file:/Users/foobar/Documents/Projects/Scala/wordify/)
[info] Packaging /Users/foobar/Documents/Projects/Scala/wordify/target/scala-2.12/wordify_2.12-0.1.jar ...
[info] Done packaging.
[info] Running com.wordify.main.Main 34343
result: thirty four thousand, three hundred and forty three
```

- Run jar

```
sbt assembly
```

then run jar

```
java -jar wordify.jar "<number_to_be_converted_to_words>"
```


```
[wordify] java -jar target/scala-2.12/wordify.jar 123
result: one hundred and twenty three
```

### Design

- Validate the input using Cats validation

- Parse the number and convert to ADT representation

- Interpret the ADT and convert the ADT to string


### Interesting code snippets

#### Interpreter which converts ADT to words

```scala

object Interpreter {

  def interpret(number: NumberTerms): String = translateTerms(number)

  def translateTerm(term: Term): String = (term match {
    case Hundreds(0, 0, 0) => ""
    case Hundreds(0, 0, c) => translateTerm(Units(c))
    case Hundreds(0, b, c) => translateTerm(Tens(b, c))
    case Hundreds(a, 0, 0) => s"${translateTerm(Units(a))} hundred"
    case Hundreds(a, b, c) =>
      s"${translateTerm(Units(a))} hundred and ${translateTerm(Tens(b, c))}"
    case Tens(0, 0) => ""
    case Tens(a, 0) => if (a === 1) tens(10) else tenMultiples(a * 10)
    case Tens(0, b) => translateTerm(Units(b))
    case Tens(a, b) =>
      if (a === 1) tens(a * 10 + b) else s"${tenMultiples(a * 10)} ${translateTerm(Units(b))}"
    case Units(0) => ""
    case Units(a) => units(a)
  }).trim

  def translateTerms(number: NumberTerms): String =
    number.terms.zipWithIndex.map {
      case (term, index) =>
        s"${translateTerm(term)} ${if (index === 0) "" else termMap(index)}".trim
    }.reverse.filterNot(_.trim.isEmpty).mkString(", ")
}

```
#### Converting number to ADT

```scala
object NumberParser {
  def parse(number: Int @@ WholeNumber): NumberTerms = {

    @scala.annotation.tailrec
    def loop(result: List[Int], left: Int): List[Int] =
      if (left === 0) result else loop(left % 10 :: result, left / 10)

    val terms = loop(Nil, number).reverse.grouped(3).map(_.reverse).map {
      case List(a, b, c) => Hundreds(a, b, c)
      case List(a, b) => Tens(a, b)
      case List(a) => Units(a)
    }.toList

    NumberTerms(terms)
  }
}

```


#### ADT for representing number to convert to words

```scala
/**
  * Every Number is composed of terms
  * For example: 123,006,089
  * The above number is composed of three terms. Each term is has max of 3 digits
  * First term 089, second term is 006 and third term is 123
  * 089 is represented as Tens(8, 9)
  * 006 is represented as Units(6)
  * 123 is represented as Hundreds(1, 2, 3)
  * So the number 123, 006, 089 gets converted to
  *  List(Tens(8, 9), Units(6), Hundreds(1, 2, 3)
  *  The above representation is then used for the converting the number to text
  */
sealed trait Term

case class Hundreds(a: Int, b: Int, c: Int) extends Term
case class Tens(a: Int, b: Int) extends Term
case class Units(a: Int) extends Term

```