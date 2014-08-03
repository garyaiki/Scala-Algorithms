# Scala-Algorithms

Java to Scala translations of around 50 algorithms from Robert Sedgewick and Kevin Wayne's website for their book _Algorithms_. I chose those Robert Sedgewick elaborated on in his video lectures in Coursera courses [Algorithms, Part I](https://www.coursera.org/course/algs4partI) and [Algorithms, Part II](https://www.coursera.org/course/algs4partII).

## Background

The courses started soon after Martin Ordersky's courses [Functional Programming Principles in Scala](https://class.coursera.org/progfun-003) and [Principles of Reactive Programming](https://class.coursera.org/reactive-001) ended. On the _Algorithms I_ forums, I asked if there were Scala versions but there was no reply. I thought writing them myself would build a repertoire in Scala and algorithms and be useful to other Scala programmers.

## Rule of least expressiveness

Scala combines Object Oriented and Functional Programming which gives you a bigger toolbox but makes you make choices other languages make for you. Peter Van Roy in [Concepts, Techniques, and Models](http://www.info.ucl.ac.be/~pvr/book.html) says: _each component should be programmed in its "natural model". Using a less expressive model would give a more complex program and using a more expressive model would not give a simpler program but would make reasoning about it harder._ So instead of trying to turn all tricky imperative code into pure functions, I left in vars, nulls, and side effects when getting rid of them would break or obscure equivalence to the originals.

Too many Scala examples are either deceptively Java-like or cryptically concise. I tried to write useful idiomatic Scala without vexing Java programmers.

## Algorithms in Scala and Java side by side

Java code written by Robert Sedgewick and Kevin Wayne is at [Java Algorithms and Clients](http://algs4.cs.princeton.edu/code/), Scala translations have the same class names but with `.scala` extensions. I followed their api so most methods have the same name but I made adjustments to follow Scala idioms.

Scala code is in [src/main/scala/org/gs/](https://github.com/garyaiki/Scala-Algorithms/tree/master/src/main/scala/org/gs) choose a package then open a source file, for example [AcyclicSP](https://github.com/garyaiki/Scala-Algorithms/blob/master/src/main/scala/org/gs/digraph/AcyclicSP.scala), on the first line is a link to Sedgewick and Wayne's code `/** @see` <http://algs4.cs.princeton.edu/44sp/AcyclicSP.java.html>

## Common Scala idioms

The class definition is also the primary constructor
```
class AcyclicSP(g: EdgeWeightedDigraph, s: Int) {...
```

Arrays and collections can be initalized when declared
```
private val _distTo = Array.fill[Double](g.V)(Double.PositiveInfinity)
```

Functions can be nested and can use variables defined in outer scopes. `Option` is preferred to `null`. Tail recursion is preferred to `while` loops and `for` loops that call `break` or `continue` or `return`. Pattern matching is preferred to complicated `if else` conditions and where `Option` values are extracted.
```
/** nested tail recursive function that pattern matches Optional edges */
      @tailrec
      def loop(e: DirectedEdge) {
        e +=: path
        edgeTo(e.from) match {
          case None =>
          case Some(x) => loop(x)
        }
      }
```

Arrays can be generic, `scala.reflect.ClassTag` recovers type information at runtime. Ordering and comparison can also be generic and be passed implicitly. 
 ```
 class IndexMinPQ[A: ClassTag](nMax: Int)(implicit ord: Ordering[A]) extends IndexPriorityQueue[A](nMax) {
  ```
Immutable variables are preferred to mutable. Mutable class variables, declared as `.var`, are usually turned into `.val` or made private or protected. Public methods that return a collection, usually, return `List`, which is recursive, rather than `Iterator`, which is imperative

## Scala and SBT setup

This uses Scala 2.10.3 [download](http://www.scala-lang.org/download/2.10.3.html) and [SBT](http://www.scala-sbt.org/0.13.1/docs/index.html) version 0.13.1 [install](http://www.scala-sbt.org/0.13.1/docs/Getting-Started/Setup.html#installing-sbt), then add paths
```
export SCALA_HOME="/Users/.../scala-2.10.3"
export SBT_HOME="/Users/.../sbt"
export PATH=...:$SCALA_HOME/bin::$SBT_HOME/bin
```

You don't need to launch Scala separately but to see if it is installed correctly you can launch it in a Terminal window in any directory.

```
Last login: Fri Jul 25 08:25:16 on console
...:~ ...$ scala
Welcome to Scala version 2.10.3 (Java HotSpot(TM) 64-Bit Server VM, Java 1.6.0_65).
Type in expressions to have them evaluated.
Type :help for more information.

scala> 
```

Quit Scala `scala> :q` Then navigate to the downloaded project and start SBT **giving it extra memory** so it can run tests on large datafiles.

```
...:Scala-Algorithms ...$ sbt -mem 2048
Loading .../sbt/bin/sbt-launch-lib.bash
[info] Loading project definition from .../git/scala-algorthms/Scala-Algorithms/project
[info] Set current project to Scala-Algorithms (in build file:.../git/scala-algorthms/Scala-Algorithms/)
> 
```

##ScalaTest

Usage examples are found in the ScalaTests.

There are _a lot_ of tests, this was a great help in keeping the code working as I refactored. [ScalaTest](http://www.scalatest.org) comes in several styles. I chose [FlatSpec](http://www.scalatest.org/user_guide/selecting_a_style), it creates [BDD](http://dannorth.net/introducing-bdd/) test reports that show non-coders what parts of a specification are working, unlike other BDD frameworks I've tried, these are almost as easy to write as JUnit tests. Finding descriptive test names is the only extra work.

To run tests, from the sbt prompt
```
> test
[info] Compiling 55 Scala sources to .../git/scala-algorthms/Scala-Algorithms/target/scala-2.10/classes...
[info] Compiling 56 Scala sources to .../git/scala-algorthms/Scala-Algorithms/target/scala-2.10/test-classes...
```
when tests complete there is a long BDD style report. Passes are green failures are red and ignored is orange (Github doesn't apply CSS in markdown, so no green in the Readme)
```
...
[info] PrimMSTSuite:
[info] a PrimMST
[info] - should build from an EdgeWeightedGraph
[info] - should calulate total weight of edges in tinyEWG MST
[info] - should find expected edges in a MST
[info] - should be acyclic
[info] - should find when it has a spanning forest
[info] - should validate a minimal spanning forest
[info] - should calulate total edge weight of mediumEWG MST
[info] - should calulate total edge weight of largeEWG MST
[info] SymbolGraphSuite:
[info] a SymbolGraph
[info] - should find vertices as keys and routes
[info] - should find movies and their actors as keys and adjacencies
[info] - should find actors and their movies as keys and adjacencies
[info] ScalaTest
[info] Run completed in 1 minute, 26 seconds.
[info] Total number of tests run: 164
[info] Suites: completed 42, aborted 0
[info] Tests: succeeded 164, failed 0, canceled 0, ignored 1, pending 0
[info] All tests passed.
[info] Passed: Total 164, Failed 0, Errors 0, Passed 164, Ignored 1
[success] Total time: 87 s, completed Jul 25, 2014 3:17:07 PM
> 
```
To validate against test data and expected results on the book's website, many tests read data files from it. Downloading them takes most of the test's time. A few tests are run on megabyte datafiles and these take most of the cpu time. The large datafile test for LazyPrimMST is ignored because it takes triple the time of all other tests combined. Change `ignore` to `it` 
in `.../git/scala-algorthms/Scala-Algorithms/src/test/scala/org/gs/digraph/LazyPrimMSTSuite.scala`
to run it. Its slowness should be taken as advice to use PrimMST instead.

Test files are in [src/test/scala/org/gs/](https://github.com/garyaiki/Scala-Algorithms/tree/master/src/test/scala/org/gs) and append "Suite" to the name of the class under test.

## Scaladoc


To generate scaladoc, from the sbt prompt:
```
> doc
[info] Main Scala API documentation to .../git/scala-algorthms/Scala-Algorithms/target/scala-2.10/api...
model contains 74 documentable templates
[info] Main Scala API documentation successful.
[success] Total time: 8 s, completed Jul 25, 2014 2:26:57 PM
> 
```

then open 
`.../git/scala-algorthms/Scala-Algorithms/target/scala-2.10/api/index.html`

Basic usage is shown on package pages.

Comments are terse partly because Scaladoc is by convention more compact than Javadoc and because this is a translation of well commented Java code. Scaladocs do show api differences to Java. [Algorithms, 4th Edition](http://algs4.cs.princeton.edu/home/) describes each algorithm.

##License and Copyright
Java code is Copyright © 2002–2014 Robert Sedgewick and Kevin Wayne. All rights reserved. It is has a GPLv3 license <http://algs4.cs.princeton.edu/faq/> Translation of software from one language to another falls under the copyright and license of the original authors. It adds a copyright for the translation which is subordinate to the original.


