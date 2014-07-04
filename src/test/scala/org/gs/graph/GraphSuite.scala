package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/mediumG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.fixtures.IntArrayBuilder

@RunWith(classOf[JUnitRunner])
class GraphSuite extends FlatSpec {



  behavior of "a Graph"
  it should "build mediumG.txt" in new IntArrayBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/41undirected/mediumG.txt")
    val savedLines = managedResource.loan(readFileToArray)
    val g = new Graph(savedLines(0))
    val twoInts = savedLines.drop(2).grouped(2)
    for {
      t <- twoInts
    } g.addEdge(t(0), t(1))
    assert(g.adj(2).diff(Array[Int](141, 110, 108, 86, 79, 51, 42, 18, 14)) === Array())
  }
}