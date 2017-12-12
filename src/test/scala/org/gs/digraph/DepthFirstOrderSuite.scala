package org.gs.digraph

/** @see [[http://algs4.cs.princeton.edu/42directed/tinyDAG.txt]]
  */
import org.gs.digraph.fixtures.DigraphBuilder
import org.scalatest.FlatSpec

/** @author Gary Struthers
  *
  */
class DepthFirstOrderSuite extends FlatSpec { 
  behavior of "a DepthFirstOrder"
  it should "find pre-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.pre.corresponds(List(0, 5, 4, 1, 6, 9, 11, 12, 10, 2, 3, 7, 8))(equals))
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.pre.corresponds(List(0, 5, 4, 3, 2, 1, 6, 9, 11, 12, 10, 8, 7))(equals))
  }

  it should "find post-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.post.corresponds(List(4, 5, 1, 12, 11, 10, 9, 6, 0, 3, 2, 7, 8))(equals))
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.post.corresponds(List(2, 3, 4, 5, 1, 0, 12, 11, 10, 9, 8, 6, 7))(equals))
  }

  it should "find reverse post-order of a Digraph" in new DigraphBuilder {
    val dfo = new DepthFirstOrder(tinyDAG)
    assert(dfo.reversePost.corresponds(List(8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 1, 5, 4))(equals))
    val dfoDG = new DepthFirstOrder(tinyDG)
    assert(dfoDG.reversePost.corresponds(List(7, 6, 8, 9, 10, 11, 12, 0, 1, 5, 4, 3, 2))(equals))
  }
}
