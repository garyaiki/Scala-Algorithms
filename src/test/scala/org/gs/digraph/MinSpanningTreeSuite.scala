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
import scala.util.control.Breaks._
import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph
import scala.Array.canBuildFrom
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MinSpanningTreeSuite extends FlatSpec {

  trait Builder {
    val tinyEWGData = Array((6, 0, 0.58000), (0, 2, 0.26000), (0, 4, 0.38000), (0, 7, 0.16000),
      (1, 3, 0.29000), (1, 2, 0.36000), (1, 7, 0.19000), (1, 5, 0.32000), (6, 2, 0.40000), 
      (2, 7, 0.34000), (1, 2, 0.36000), (0, 2, 0.26000), (2, 3, 0.17000), (3, 6, 0.52000), 
      (1, 3, 0.29000), (2, 3, 0.17000), (6, 4, 0.93000), (0, 4, 0.38000), (4, 7, 0.37000), 
      (4, 5, 0.35000), (1, 5, 0.32000), (5, 7, 0.28000), (4, 5, 0.35000), (6, 4, 0.93000), 
      (6, 0, 0.58000), (3, 6, 0.52000), (6, 2, 0.40000), (2, 7, 0.34000), (1, 7, 0.19000), 
      (0, 7, 0.16000), (5, 7, 0.28000), (4, 7, 0.37000))
    val tinyEdgeArray = {
      for {
        e <- tinyEWGData
      } yield new Edge(e._1, e._2, e._3)
    }
  }

  trait GraphBuilder extends Builder {
    val g = new EdgeWeightedGraph(8)
    for {
      ed <- tinyEdgeArray
    } {
      g.addEdge(ed)
    }
  }

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
    assert(g.v === 8, s"${g.v} is the wrong number of vertices")
    assert(g.e === 32, s"${g.e} is the wrong number of edges")
  }

  it should "make a copy of itself" in new GraphBuilder {
    val gc = new EdgeWeightedGraph(g)
    assert(gc.v === 8, s"${gc.v} is the wrong number of vertices")
//    assert(gc.e === 32, s"${gc.e} is the wrong number of edges")
  }

  it should "have adjacencies for each vertex with all edges with that vertex" in new GraphBuilder {
    def curriedVertexCheck(adjIndex: Int)(e: Edge): Boolean = {
      val s = e.toString.take(3)
      s.contains(adjIndex.toString)
    }
    for {
      i <- 0 until g.v
    } assert(g.adj(i).forall(curriedVertexCheck(i)_), s" not all edges in adj $i contain $i")
  }

  it should "return all edges" in new GraphBuilder {
    assert(g.edges.length === g.e, "edges returned ${g.edges.length} should be ${g.e}")
  }

  trait LazyPrimMSTBuilder extends MSTBuilder {
    val primMST = new LazyPrimMST(g)
  }
  
  trait PrimMSTBuilder extends MSTBuilder {
    val primMST = new PrimMST(g)
  }
  
  trait MSTBuilder extends GraphBuilder {
    val tinyMST = Array((0, 2, 0.26000), (0, 7, 0.16000),
      (1, 7, 0.19000),
      (6, 2, 0.40000),
      (2, 3, 0.17000),
      (4, 5, 0.35000),
      (5, 7, 0.28000))
    val tinyMSTArray = {
      for {
        e <- tinyMST
      } yield new Edge(e._1, e._2, e._3)
    }
    val uf = new UF(g.v)

    def buildUF(edges: Seq[Edge]): Boolean = {
      var foundCycle = false
      breakable {
        for (e <- edges) {
          val v = e.either
          val w = e.other(v)
          if (uf.connected(v, w)) {
            foundCycle = true
            break
          } else {
            uf.union(v, w)
          }
        }
      }
      foundCycle
    }
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
    var spanningForest = true
    breakable {
      for (e <- edges) {
        val v = e.either
        val w = e.other(v)
        val foundV = uf.find(v)
        val foundW = uf.find(w)
        if (!uf.connected(v, w)) {
          spanningForest = false
          break
        }
      }
    }
    assert(spanningForest === true)
  }

  it should "be a minimal spanning forest" in new LazyPrimMSTBuilder {
    assert(primMST.checkIsMinSpanningForest === true)
  }

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
    var spanningForest = true
    breakable {
      for (e <- edges) {
        val v = e.either
        val w = e.other(v)
        val foundV = uf.find(v)
        val foundW = uf.find(w)
        if (!uf.connected(v, w)) {
          spanningForest = false
          break
        }
      }
    }
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
      breakable {
        for (f <- g.edges) {
          val x = f.either
          val y = f.other(x)
          if (!uf.connected(x, y)) {
            if (f.weight < e.weight) {
              cutOptimiality = false
              break
            }
          }
        }
      }
      cutOptimiality
    }
    breakable {
      val edges = primMST.edges
      for (e <- edges) {
        mstEdges(e)
        if (!minWeightInCrossingCut(e)) break
      }
    }
    cutOptimiality
  }
    assert(checkIsMinSpanningForest === true)
  }


}