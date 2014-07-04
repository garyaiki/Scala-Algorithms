package org.gs.digraph
/**
 * @see http://algs4.cs.princeton.edu/44sp/tinyDG.txt
 * @see http://algs4.cs.princeton.edu/42directed/mediumDG.txt
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import scala.collection.mutable.Queue
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.SymbolDigraphBuilder
import org.gs.digraph.fixtures.DigraphBuilder
import org.gs.fixtures.IntArrayBuilder
import org.gs.digraph.fixtures.DirectedEdgeBuilder
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
      v <- 0 until tinyDG.V
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

  it should "find components of a mediumDG" in new DigraphBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/42directed/mediumDG.txt")
    val vEpairs = managedResource.loan(readFileToTuple)
    val savedLines = vEpairs._3
    val g = new Digraph(vEpairs._1)
    for {
      t <- savedLines
    } g.addEdge(t._1, t._2)
    val scc = new KosarajuSharirSCC(g)
    val m = scc.count
    assert(m === 10, s"ssc.count:${scc.count} not equal 10")
    val components = new Array[Queue[Int]](m)
    for {
      i <- 0 until m
    } {
      components(i) = new Queue[Int]()
    }
    for {
      v <- 0 until g.V
    } {
      components(scc.id(v)).enqueue(v)
    }
    assert(components(0).corresponds(List(21))(equals))
    assert(components(1).corresponds(List(2, 5, 6, 8, 9, 11, 12, 13, 15, 16, 18, 19, 22, 23, 25, 26,
        28, 29, 30, 31, 32, 33, 34, 35, 37, 38, 39, 40, 42, 43, 44, 46, 47, 48, 49))(equals))
    assert(components(2).corresponds(List(14))(equals))
    assert(components(3).corresponds(List(3, 4, 17, 20, 24, 27, 36))(equals))
    assert(components(4).corresponds(List(41))(equals))
    assert(components(5).corresponds(List(7))(equals))
    assert(components(6).corresponds(List(45))(equals))
    assert(components(7).corresponds(List(1))(equals))
    assert(components(8).corresponds(List(0))(equals))
    assert(components(9).corresponds(List(10))(equals))
  }
} 