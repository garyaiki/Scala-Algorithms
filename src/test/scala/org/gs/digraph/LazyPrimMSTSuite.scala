/**
 * @see http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 */
package org.gs.digraph
/**
 * @author Gary Struthers
 *
 */
import org.scalautils._
import org.scalautils.Tolerance._
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph
import org.gs.digraph.fixtures.{ GraphBuilder, MSTBuilder, PrimMSTBuilder }
import org.scalatest.junit.JUnitRunner
import scala.annotation.tailrec
import org.gs.digraph.fixtures.DirectedEdgeBuilder
import org.gs.graph.fixtures.EdgeBuilder
import org.gs.digraph.fixtures.EdgeWeightedGraphBuilder

@RunWith(classOf[JUnitRunner])
class LazyPrimMSTSuite extends FlatSpec {

  trait LazyPrimMSTBuilder extends MSTBuilder {
    val primMST = new LazyPrimMST(g)
  }
  behavior of "a LazyPrimMST"

  it should "create a minimal spanning tree from an EdgeWeightedGraph" in new GraphBuilder {
    val primMST = new LazyPrimMST(g)
  }

  it should "calulate total edge weight of tinyEWG MST" in new EdgeWeightedGraphBuilder {
    val g = buildGraph("http://algs4.cs.princeton.edu/43mst/tinyEWG.txt")
    val mst = new LazyPrimMST(g)
    val weight = mst.weight
    assert(weight === 1.81)
  }
  
  it should "match expected edges in a MST" in new LazyPrimMSTBuilder {
    val edges = primMST.edges
    val diff = edges.diff(tinyMSTArray)
    assert(edges.diff(tinyMSTArray).size === 0)
  }

  it should "be acyclic" in new LazyPrimMSTBuilder {
    val hasCycle = buildUF(primMST.edges)
    assert(hasCycle === false)
  }

  it should "be a spanning forest" in new LazyPrimMSTBuilder {
    val edges = primMST.edges
    val hasCycle = buildUF(primMST.edges)

    @tailrec
    def loop(i: Int): Boolean = {
      if (i < edges.length) {
        val v = edges(i).either
        val w = edges(i).other(v)
        val foundV = uf.find(v)
        val foundW = uf.find(w)
        if (!uf.connected(v, w)) false else loop(i + 1)
      } else true
    }
    var spanningForest = loop(0)
    assert(spanningForest === true)
  }

  it should "validate a minimal spanning forest" in new LazyPrimMSTBuilder {
    assert(primMST.checkIsMinSpanningForest === true)
  }
  
  it should "calulate total edge weight of mediumEWG MST" in new EdgeWeightedGraphBuilder {
    val g = buildGraph("http://algs4.cs.princeton.edu/43mst/mediumEWG.txt")
    val mst = new LazyPrimMST(g)
    val weight = mst.weight
    assert(weight === 10.46351 +- 0.000005)
  }
  
  // -Xmx1g test passes but it takes 5 minutes to complete
  ignore should "calulate total edge weight of largeEWG MST" in new EdgeWeightedGraphBuilder {
    val g = buildGraph("http://algs4.cs.princeton.edu/43mst/largeEWG.txt")
    val mst = new LazyPrimMST(g)
    val weight = mst.weight
    assert(weight === 647.66307 +- 0.000005)
  }
}
