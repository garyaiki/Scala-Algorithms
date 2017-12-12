package org.gs.digraph

import org.gs.digraph.fixtures.DigraphBuilder
import org.scalatest.FlatSpec

/** @see [[https://algs4.cs.princeton.edu/44sp/DirectedCycle.java.html]]
  * @see [[https://algs4.cs.princeton.edu/44sp/tinyDG.txt]]
  * @see [[https://algs4.cs.princeton.edu/42directed/tinyDAG.txt]]
  *
  * @author Gary Struthers
  */
class DirectedCycleSuite extends FlatSpec {

  behavior of "a DirectedCycle"
  it should "find a directed cycle when digraph has one" in new DigraphBuilder {
    val tdg = new DirectedCycle(tinyDG)
    assert(tdg.cycle.get.corresponds(List(3, 5, 4, 3))(equals), 
        s"directed cycle found ${tdg.cycle.get} expected ${List(3, 5, 4, 3)}")
    
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false) 
  }

  it should "confirm when a digraph has no directed cycles" in new DigraphBuilder {
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false)
  }
}
