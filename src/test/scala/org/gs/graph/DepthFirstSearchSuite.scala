package org.gs.graph
/** @see http://algs4.cs.princeton.edu/41undirected/tinyG.txt
  */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.graph.fixtures.GraphBuilder

@RunWith(classOf[JUnitRunner])
class DepthFirstSearchSuite extends FlatSpec {

  behavior of "a DepthFirstSearch"
  it should "count verticies adjacent to an edge" in new GraphBuilder {
    val from0 = new DepthFirstSearch(tinyG, 0)
    assert(from0.count === 7)
    val from1 = new DepthFirstSearch(tinyG, 1)
    assert(from1.count === 7)
    val from7 = new DepthFirstSearch(tinyG, 7)
    assert(from7.count === 2)
    val from9 = new DepthFirstSearch(tinyG, 9)
    assert(from9.count === 4)
  }

  it should "find when there's a path from source to target" in new GraphBuilder {
    val from0 = new DepthFirstSearch(tinyG, 0)
    for (j <- 0 to 6) {
      assert(from0.marked(j), s"0 - j:$j")
    }
    val from1 = new DepthFirstSearch(tinyG, 1)
    for (j <- 0 to 6) {
      assert(from1.marked(j), s"1 - j:$j")
    }
    val from7 = new DepthFirstSearch(tinyG, 7)
    for (j <- 0 to 6) {
      assert(from7.marked(j) === false, s"7 - j:$j")
    }
    val from9 = new DepthFirstSearch(tinyG, 9)
    for (j <- 0 to 6) {
      assert(from9.marked(j) === false, s"9 - j:$j")
    }
  }
}
