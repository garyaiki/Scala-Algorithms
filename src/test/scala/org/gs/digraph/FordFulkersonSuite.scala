/** ScalaTestfor FLowNetwork
  * @see https://algs4.cs.princeton.edu/64maxflow/tinyFN.txt
  */
package org.gs.digraph

import org.gs.digraph.fixtures.{DirectedEdgeBuilder, FlowEdgeBuilder}
import org.scalatest.FlatSpec

/** @author Gary Struthers
  *
  */
class FordFulkersonSuite extends FlatSpec {
  behavior of "a FordFulkerson"

  it should "find the max flow and st cut" in new FlowEdgeBuilder {
    val managedResource = readURI("https://algs4.cs.princeton.edu/64maxflow/tinyFN.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new FlowNetwork(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    assert(g.edges.toSet.size === tuple._2)
    
    val ff = FordFulkerson(g, 0, tuple._1 - 1).get

    assert(ff.value === 4.0, s"flow value:${ff.value}")
    assert(ff.inCut(2) && !ff.inCut(1) && !ff.inCut(3) && !ff.inCut(4), "failed st cut")
  }
}
