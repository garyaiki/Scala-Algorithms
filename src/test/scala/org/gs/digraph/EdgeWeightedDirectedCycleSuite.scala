/** ScalaTest, JUnit tests for EdgeWeightedDirectedCycle
  */
package org.gs.digraph

import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class EdgeWeightedDirectedCycleSuite extends FlatSpec {

  behavior of "a EdgeWeightedDirectedCycle"
  it should "confirm an edge weighted digraph does not have a cycle" in new DirectedEdgeBuilder {

    val managedResource = readURI("http://algs4.cs.princeton.edu/44sp/tinyEWDAG.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new EdgeWeightedDigraph(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    val a = new EdgeWeightedDirectedCycle(g)

    assert(a.hasCycle === false)
  }
  
  it should "confirm an edge weighted digraph has a cycle" in new DirectedEdgeBuilder {

    val managedResource = readURI("http://algs4.cs.princeton.edu/44sp/tinyEWDn.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new EdgeWeightedDigraph(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    val a = new EdgeWeightedDirectedCycle(g)
    if (a.hasCycle) {
      var first = null.asInstanceOf[DirectedEdge]
      var last = null.asInstanceOf[DirectedEdge]
      for (e <- a.cycle.get) {
        if (first == null) first = e
        if (last != null) {
          if (last.to != e.from) fail(s"cycle edges last:$last and e:$e not incident")
        }
        last = e
      }
      if (last.to != first.from) fail(s"cycle edges last:$last and first:$first not incident")
    }
    assert(a.hasCycle === true)
  }

  it should "confirm an edge weighted digraph has a negative cycle" in new DirectedEdgeBuilder {

    val managedResource = readURI("http://algs4.cs.princeton.edu/44sp/tinyEWDnc.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new EdgeWeightedDigraph(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    val a = new EdgeWeightedDirectedCycle(g)

    assert(a.hasCycle === true)
  }
}