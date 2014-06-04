/**
 * @see http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 */
package org.gs.digraph
/**
 * @author Gary Struthers
 *
 */
import org.scalatest.FlatSpec
import org.scalautils._
import org.scalautils.Tolerance._
import org.junit.runner.RunWith
import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph
import org.gs.digraph.fixtures.{ GraphBuilder, PrimMSTBuilder }
import org.scalatest.junit.JUnitRunner
import scala.annotation.tailrec

@RunWith(classOf[JUnitRunner])
class PrimMSTSuite extends FlatSpec {

  behavior of "a PrimMST"

  it should "build" in new GraphBuilder {
    val primMST = new PrimMST(g)
  }

  it should "match edges" in new PrimMSTBuilder {
    val edges = primMST.edges.toArray
    println(edges.mkString(" "))
    val diff = edges.diff(tinyMSTArray)
    assert(edges.diff(tinyMSTArray).size === 0)
  }

  it should "match total weight of edges" in new PrimMSTBuilder {
    val primWeight = primMST.weight
    assert(primWeight === 1.81 +- 0.0100)
  }

  it should "be acyclic" in new PrimMSTBuilder {
    val hasCycle = buildUF(primMST.edges)
    assert(hasCycle === false)
  }

  it should "be a spanning forest" in new PrimMSTBuilder {
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

  it should "be a minimal spanning forest" in new PrimMSTBuilder {
    def checkIsMinSpanningForest(): Boolean = {
      var cutOptimiality = true
      val uf = new UF(g.v)
      val mst = primMST.edges
      
      def mstEdges(e: Edge) {
        for (f <- mst) {
          val x = f.either
          val y = f.other(x)
          if (f != e) uf.union(x, y)
        }
      }
      
      def minWeightInCrossingCut(e: Edge): Boolean = {
        val edges = g.edges
        @tailrec
        def loop(i: Int): Boolean = {
          if(i < edges.length) {
            val x = edges(i).either
            val y = edges(i).other(x)
            if (!uf.connected(x, y)) {
              if (edges(i).weight < e.weight) false 
            }
            loop(i + 1)
          } else true
        }
        loop(0)
      }

      val edges = primMST.edges
      @tailrec
      def loop(i: Int): Boolean = {
        if(i < edges.length) {
          val e = edges(i)
          mstEdges(e)
          if (!minWeightInCrossingCut(e)) false else loop(i + 1)
        } else true
      }
      loop(0)
    }
    assert(checkIsMinSpanningForest === true)
  }
}
