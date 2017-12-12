package org.gs.digraph

import org.gs.digraph.fixtures.GraphBuilder
import org.gs.graph.{Edge, EdgeWeightedGraph}
import org.scalatest.FlatSpec

/** @see [[https://algs4.cs.princeton.edu/43mst/tinyEWG.txt]]
  *
  * @author Gary Struthers
  */
class MinSpanningTreeSuite extends FlatSpec {

  behavior of "an Edge"
  it should "format toString v%d w%d weight weight%.5f" in {
    val piEdge = new Edge(12, 23, 3.14)
    assert(piEdge.toString === "12-23 3.14000 ")
  }

  it should "compareTo -1 for lt, 0 for ==, 1 for gt" in {
    val piEdge = new Edge(12, 23, 3.14)
    val ltEdge = new Edge(12, 23, 3.13)
    val gtEdge = new Edge(12, 23, 3.15)

    assert(piEdge.compare(gtEdge) === -1)
    assert(piEdge.compare(ltEdge) === 1)
    assert(piEdge.compare(piEdge) === 0)
  }

  behavior of "an EdgeWeightedGraph"

  it should "construct edge weighted graph with given vertices and edges" in new GraphBuilder {
    assert(g.numV === 8, s"${g.numV} is the wrong number of vertices")
    assert(g.e === 32, s"${g.e} is the wrong number of edges")
  }

  it should "make a copy of itself" in new GraphBuilder {
    val gc = new EdgeWeightedGraph(g)
    assert(gc.numV === 8, s"${gc.numV} is the wrong number of vertices")
    assert(gc.e === 32, s"${gc.e} is the wrong number of edges")
  }

  it should "have adjacencies for each vertex with all edges with that vertex" in new GraphBuilder {
    def curriedVertexCheck(adjIndex: Int)(e: Edge): Boolean = {
      val s = e.toString.take(3)
      s.contains(adjIndex.toString)
    }
    for (i <- 0 until g.numV)
      assert(g.adj(i).forall(curriedVertexCheck(i)_), s" not all edges in adj $i contain $i")
  }

  it should "return all edges" in new GraphBuilder {
    assert(g.edges.length === g.e, "edges returned ${g.edges.length} should be ${g.e}")
  }
}
