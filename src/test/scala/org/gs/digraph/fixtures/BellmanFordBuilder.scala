/**
 *
 */
package org.gs.digraph.fixtures

import org.gs.digraph.BellmanFordSP
import org.scalatest.PrivateMethodTester.PrivateMethod
import org.gs.digraph.EdgeWeightedDigraph
import org.gs.digraph.DirectedEdge
import scala.collection.mutable.ArrayBuffer
import scalaz.std.tuple

/**
 * @author Gary Struthers
 *
 */
trait BellmanFordBuilder extends DirectedEdgeBuilder {
  def buildVEDirectedEdges(url: String, s: Int): (Int, Int, ArrayBuffer[DirectedEdge]) = {
    val managedResource = readURI(url)
    managedResource.loan(readFileToTuple)
  }

  def buildEdgeWeightedDigraph(v: Int): EdgeWeightedDigraph = new EdgeWeightedDigraph(v)

  def buildBellmanFordSP(g: EdgeWeightedDigraph, edges: ArrayBuffer[DirectedEdge]): BellmanFordSP = {
    for (ed <- edges) g.addEdge(ed)
    val s = 0
    new BellmanFordSP(g, s)
  }

  def expectedTinyEWDnPaths(v: Int, edges: ArrayBuffer[DirectedEdge]) = {
    val paths = new Array[List[DirectedEdge]](v)
    paths(0) = List()
    paths(1) = List(edges(7), edges(10), edges(8), edges(12), edges(14), edges(0), edges(5))
    paths(2) = List(edges(7))
    paths(3) = List(edges(7), edges(10), edges(8))
    paths(4) = List(edges(7), edges(10), edges(8), edges(12), edges(14))
    paths(5) = List(edges(7), edges(10), edges(8), edges(12), edges(14), edges(0))
    paths(6) = List(edges(7), edges(10), edges(8), edges(12))
    paths(7) = List(edges(7), edges(10))

    paths
  }
  
  def expectedTinyEWDncPaths(edges: ArrayBuffer[DirectedEdge]) = List(edges(0), edges(1))
 
}