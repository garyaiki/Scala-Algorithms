package org.gs.digraph

import org.gs.digraph.fixtures.{EdgeWeightedGraphBuilder, GraphBuilder, PrimMSTBuilder}
import org.gs.graph.{Edge, EdgeWeightedGraph}
import org.gs.set.UF
import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import scala.annotation.tailrec

/** @see [[https://algs4.cs.princeton.edu/43mst/tinyEWG.txt]]
  *
  * @author Gary Struthers
  */
class PrimMSTSuite extends FlatSpec {

  behavior of "a PrimMST"

  it should "build from an EdgeWeightedGraph" in new GraphBuilder {
    val primMST = new PrimMST(g)
  }

  it should "calulate total weight of edges in tinyEWG MST" in new EdgeWeightedGraphBuilder {
    val g = buildGraph("https://algs4.cs.princeton.edu/43mst/tinyEWG.txt")
    val mst = new PrimMST(g)
    val weight = mst.weight
    assert(weight === 1.81 +- 0.000005)
  }

  it should "find expected edges in a MST" in new PrimMSTBuilder {
    val edges = primMST.edges.toArray
    val diff = edges.diff(tinyMSTArray)
    assert(edges.diff(tinyMSTArray).size === 0)
  }

  it should "be acyclic" in new PrimMSTBuilder {
    val hasCycle = buildUF(primMST.edges)
    assert(hasCycle === false)
  }

  it should "find when it has a spanning forest" in new PrimMSTBuilder {
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

  it should "validate a minimal spanning forest" in new PrimMSTBuilder {
    def checkIsMinSpanningForest(): Boolean = {
      var cutOptimiality = true
      val uf = new UF(g.numV)
      val mst = primMST.edges

      def mstEdges(e: Edge) {
        for {
          f <- mst
          x = f.either
          y = f.other(x)
        } if (f != e) uf.union(x, y)
      }

      def minWeightInCrossingCut(e: Edge): Boolean = {
        val edges = g.edges
        @tailrec
        def loop(i: Int): Boolean = {
          if (i < edges.length) {
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
        if (i < edges.length) {
          val e = edges(i)
          mstEdges(e)
          if (!minWeightInCrossingCut(e)) false else loop(i + 1)
        } else true
      }
      loop(0)
    }
    assert(checkIsMinSpanningForest === true)
  }
    
  it should "calulate total edge weight of mediumEWG MST" in new EdgeWeightedGraphBuilder {
    val g = buildGraph("https://algs4.cs.princeton.edu/43mst/mediumEWG.txt")
    val mst = new PrimMST(g)
    val weight = mst.weight
    assert(weight === 10.46351 +- 0.000005)
  }
  
  it should "calulate total edge weight of largeEWG MST" in new EdgeWeightedGraphBuilder {
    val g = buildGraph("https://algs4.cs.princeton.edu/43mst/largeEWG.txt")
    val mst = new PrimMST(g)
    val weight = mst.weight
    assert(weight === 647.66307 +- 0.000005)
  }
}
