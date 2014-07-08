package org.gs.digraph
/** @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.DigraphBuilder
/** @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class DepthFirstDirectedPathsSuite extends FlatSpec { 

  behavior of "a DepthFirstDirectedPaths"
  it should "find paths from source vertex to end vertex" in new DigraphBuilder {
    val from3 = new DepthFirstDirectedPaths(tinyDG, 3)
    assert(from3.pathTo(0).corresponds(List(3, 5, 4, 2, 0))(equals))
    assert(from3.pathTo(1).corresponds(List(3, 5, 4, 2, 0, 1))(equals))
    assert(from3.pathTo(2).corresponds(List(3, 5, 4, 2))(equals))
    assert(from3.pathTo(3).corresponds(List(3))(equals))
    assert(from3.pathTo(4).corresponds(List(3, 5, 4))(equals))
    assert(from3.pathTo(5).corresponds(List(3, 5))(equals))
  }

  it should "not find absent paths from source vertex to end vertex" in new DigraphBuilder {
    val from3 = new DepthFirstDirectedPaths(tinyDG, 3)
    assert(from3.hasPathTo(6) === false)
    assert(from3.hasPathTo(7) === false)
    assert(from3.hasPathTo(8) === false)
    assert(from3.hasPathTo(9) === false)
    assert(from3.hasPathTo(10) === false)
    assert(from3.hasPathTo(11) === false)
    assert(from3.hasPathTo(12) === false)
  }
} 