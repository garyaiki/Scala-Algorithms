/** ScalaTest, JUnit tests for FLowNetwork
  * @see http://algs4.cs.princeton.edu/64maxflow/tinyFN.txt
  */
package org.gs.digraph

import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.FlowEdgeBuilder

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class FlowNetworkSuite extends FlatSpec {
  behavior of "a FlowNetwork"
  
  it should "have all vertices and edges in tinyFN" in new FlowEdgeBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/64maxflow/tinyFN.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val v = tuple._1
    val e = tuple._2
    val g = new FlowNetwork(v)
    
    for (ed <- tuple._3) g.addEdge(ed)
    assert(g.edges.toSet.size === e)
  }
}