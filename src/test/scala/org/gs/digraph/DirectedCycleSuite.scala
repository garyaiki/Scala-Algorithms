package org.gs.digraph
/**
 * @see http://algs4.cs.princeton.edu/44sp/DirectedCycle.java.html
 * @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.DigraphBuilder

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class DirectedCycleSuite extends FlatSpec {

  behavior of "a DirectedCycle"
  it should "find digraph's directed cycles" in new DigraphBuilder {
    val tdg = new DirectedCycle(tinyDG)
    assert(tdg.cycle.get.corresponds(List(3, 5, 4, 3))(equals), 
        s"directed cycle found ${tdg.cycle.get} expected ${List(3, 5, 4, 3)}")
    
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false) 
  }

  it should "show when digraph has no directed cycles" in new DigraphBuilder {
    val tdag = new DirectedCycle(tinyDAG)
    assert((tdag.hasCycle) === false)
  }
}
