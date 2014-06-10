/**
 * ScalaTest, JUnit tests for FLowNetwork
 * @see http://algs4.cs.princeton.edu/64maxflow/tinyFN.txt
 */
package org.gs.digraph

import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.gs.digraph.fixtures.FlowEdgeBuilder

/**
 * @author Gary Struthers
 *
 */
@RunWith(classOf[JUnitRunner])
class FordFulkersonSuite extends FlatSpec {
  behavior of "a FordFulkerson"

  it should "have at least on of each edge in tinyFN" in new FlowEdgeBuilder {
    val managedResource = readURI("http://algs4.cs.princeton.edu/64maxflow/tinyFN.txt")
    val tuple = managedResource.loan(readFileToTuple)
    val g = new FlowNetwork(tuple._1)

    for (ed <- tuple._3) g.addEdge(ed)
    assert(g.edges.toSet.size === tuple._2)
    val maxflow = FordFulkerson(g, 0, tuple._1 - 1).get
    def testMinCut(ff: FordFulkerson): Unit = {//@FIXME
      val minCut = for {
        v <- 0 until g.v
        if (ff.inCut(v))
      } yield v
    }

    assert(maxflow.value === 4.0)

  }
}