package org.gs.digraph

/** @see https://algs4.cs.princeton.edu/44sp/tinyDG.txt
  * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
  */
import org.scalatest.FlatSpec
import org.gs.digraph.fixtures.DigraphBuilder
import org.gs.digraph.fixtures.UnweightedDigraphBuilder

/** @author Gary Struthers
  *
  */
class DigraphSuite extends FlatSpec {
  val builder = new UnweightedDigraphBuilder("https://algs4.cs.princeton.edu/42directed/tinyDG.txt")
  val tinyDG = builder.g
  val dAGbuilder = new UnweightedDigraphBuilder("https://algs4.cs.princeton.edu/44sp/tinyDAG.txt")
  val tinyDAG = dAGbuilder.g
  val equals = (_: Int) == (_: Int)
  
  behavior of "a Digraph"

  it should "find adjacent vertices" in {
    assert(tinyDAG.adj.flatten.corresponds(
        List(5, 1, 6, 0, 3, 5, 4, 9, 4, 6, 7, 11, 10, 12, 12))(equals))
    assert(tinyDG.adj.flatten.corresponds(
        List(5, 1, 0, 3, 5, 2, 3, 2, 4, 9, 4, 8, 0, 6, 9, 6, 11, 10, 12, 4, 12, 9))(equals))
  }

  it should "reverse and find vertcies that point to a vertex" in {
    val reversedDAG = tinyDAG.reverse
    assert(reversedDAG.adj.flatten.corresponds(
        List(2, 0, 2, 6, 5, 3, 0, 7, 0, 8, 6, 9, 9, 11, 9))(equals))

    val reversedDG = tinyDG.reverse
    assert(reversedDG.adj.flatten.corresponds(
        List(6, 2, 0, 4, 3, 4, 2, 11, 6, 5, 3, 0, 8, 7, 6, 12, 7, 6, 9, 9, 11, 10))(equals))
  }
}
