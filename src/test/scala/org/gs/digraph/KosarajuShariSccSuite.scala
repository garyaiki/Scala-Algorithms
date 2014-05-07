package org.gs.digraph
/**
 * @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/tinyDAG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import scala.collection.mutable.Queue
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.SymbolDigraphBuilder
import org.gs.digraph.fixtures.DigraphBuilder
/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class KosarajuSharirSCCSuite extends FlatSpec {

  behavior of "a KosarajuSharirSCC"

  it should "find number of components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    val m = scc.count
    assert(m === 5)
  }

  it should "find components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    val m = scc.count
    val components = new Array[Queue[Int]](m)
    for {
      i <- 0 until m
    } {
      components(i) = new Queue[Int]()
    }
    for {
      v <- 0 until tinyDG.v
    } {
      components(scc.id(v)).enqueue(v)
    }
    assert(components(0).corresponds(List(1))(equals))
    assert(components(1).corresponds(List(0, 2, 3, 4, 5))(equals))
    assert(components(2).corresponds(List(9, 10, 11, 12))(equals))
    assert(components(3).corresponds(List(6, 8))(equals))
    assert(components(4).corresponds(List(7))(equals))
  }
  
  it should "find strongly connected components of a Digraph" in new DigraphBuilder {
    val scc = new KosarajuSharirSCC(tinyDG)
    assert(scc.stronglyConnected(0,2))
    assert(scc.stronglyConnected(0,3))
    assert(scc.stronglyConnected(0,4))
    assert(scc.stronglyConnected(0,5))
    assert(scc.stronglyConnected(6,8))
    assert(scc.stronglyConnected(9,10))
    assert(scc.stronglyConnected(10,11))
    assert(scc.stronglyConnected(11,12))
  } 

} 