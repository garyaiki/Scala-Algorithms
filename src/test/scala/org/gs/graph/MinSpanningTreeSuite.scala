package org.gs.graph

import org.scalatest.FlatSpec

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue

@RunWith(classOf[JUnitRunner])
class MinSpanningTreeSuite extends FlatSpec {

  trait Builder {
    val tinyEWGData = Array((6, 0, 0.58000), (0, 2, 0.26000), (0, 4, 0.38000), (0, 7, 0.16000),
      (1, 3, 0.29000), (1, 2, 0.36000), (1, 7, 0.19000), (1, 5, 0.32000),
      (6, 2, 0.40000), (2, 7, 0.34000), (1, 2, 0.36000), (0, 2, 0.26000), (2, 3, 0.17000),
      (3, 6, 0.52000), (1, 3, 0.29000), (2, 3, 0.17000),
      (6, 4, 0.93000), (0, 4, 0.38000), (4, 7, 0.37000), (4, 5, 0.35000),
      (1, 5, 0.32000), (5, 7, 0.28000), (4, 5, 0.35000),
      (6, 4, 0.93000), (6, 0, 0.58000), (3, 6, 0.52000), (6, 2, 0.40000),
      (2, 7, 0.34000), (1, 7, 0.19000), (0, 7, 0.16000), (5, 7, 0.28000), (4, 7, 0.37000))
    val tinyEdgeArray = {
      for {
        e <- tinyEWGData
      } yield new Edge(e._1, e._2, e._3)
    }
  }

  trait GraphBuider extends Builder {
    val g = new EdgeWeightedGraph(8)
    for {
      ed <- tinyEdgeArray
    } {
      g.addEdge(ed)
    }
  }
  "edge toString" should "be v%d w%d weight weight%.5f" in {
    val piEdge = new Edge(12, 23, 3.14)
    assert(piEdge.toString === "12-23 3.14000")
  }
  "edge compareTo" should "be -1 for lt, 0 for ==, 1 for gt" in {
    val piEdge = new Edge(12, 23, 3.14)
    val ltEdge = new Edge(12, 23, 3.13)
    val gtEdge = new Edge(12, 23, 3.15)

    assert(piEdge.compareTo(gtEdge) === -1)
    assert(piEdge.compareTo(ltEdge) === 1)
    assert(piEdge.compareTo(piEdge) === 0)
  }

  "construct edge weighted graph" should " have 8 vertices, 32 edges" in new GraphBuider {
    assert(g.v === 8, s"${g.v} is the wrong number of vertices")
    assert(g.e === 32, s"${g.e} is the wrong number of edges")
  }

  "copy edge weighted graph" should " have 8 vertices, 32 edges" in new GraphBuider {
    val gc = new EdgeWeightedGraph(g)
    assert(gc.v === 8, s"${gc.v} is the wrong number of vertices")
    assert(gc.e === 32, s"${gc.e} is the wrong number of edges")
  }
}