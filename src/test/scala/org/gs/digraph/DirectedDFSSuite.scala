package org.gs.digraph
/** @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import scala.collection.mutable.Queue
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.SymbolDigraphBuilder
import org.gs.digraph.fixtures.DigraphBuilder
/** @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class DirectedDFSSuite extends FlatSpec {

  
  behavior of "a DirectedDFS"
  it should "mark reachable verticies" in new DigraphBuilder {
    val from1 = new DirectedDFS(tinyDG, 1)
    assert(from1.marked === Array[Boolean](false, true, false, false, false, false, false, false,
      false, false, false, false, false))
    val from2 = new DirectedDFS(tinyDG, 2)
    assert(from2.marked === Array[Boolean](true, true, true, true, true, true, false, false, false,
      false, false, false, false))
  }

  it should "count reachable verticies" in new DigraphBuilder {
    val from1 = new DirectedDFS(tinyDG, 1)
    assert(from1.count === 1)
    val from2 = new DirectedDFS(tinyDG, 2)
    assert(from2.count === 6)
  }

  it should "count reachable verticies from 2 sources" in new DigraphBuilder {
    val from1And2 = new DirectedDFS(tinyDG, 1, 2)
    assert(from1And2.count === 6)
  }
  
  
  it should "count reachable verticies from 2 sources in an array" in new DigraphBuilder {
    val from1And2 = new DirectedDFS(tinyDG, Array[Int](1, 2): _*)
    assert(from1And2.count === 6)
  }
} 