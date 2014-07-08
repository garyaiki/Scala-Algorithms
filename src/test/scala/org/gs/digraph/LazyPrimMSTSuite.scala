/** @see http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 */
package org.gs.digraph
/** @author Gary Struthers
 *
 */
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph
import org.gs.digraph.fixtures.{ GraphBuilder, MSTBuilder, PrimMSTBuilder }
import org.scalatest.junit.JUnitRunner
import scala.annotation.tailrec

@RunWith(classOf[JUnitRunner])
class LazyPrimMSTSuite extends FlatSpec {

  trait LazyPrimMSTBuilder extends MSTBuilder {
    val primMST = new LazyPrimMST(g)
  }
  behavior of "a LazyPrimMST"

  it should "build" in new GraphBuilder {
    val primMST = new LazyPrimMST(g)
  }
  
  it should "match edges" in new LazyPrimMSTBuilder {
    val edges = primMST.edges
    val diff = edges.diff(tinyMSTArray)
    assert(edges.diff(tinyMSTArray).size === 0)
  }
  it should "match total weight of edges" in new LazyPrimMSTBuilder {
    val primWeight = primMST.weight
    assert(primWeight === 1.81)
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
      if(i < edges.length) {
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

  it should "be a minimal spanning forest" in new LazyPrimMSTBuilder {
    assert(primMST.checkIsMinSpanningForest === true)
  }
}
