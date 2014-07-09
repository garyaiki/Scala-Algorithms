/**
 */
package org.gs.digraph.fixtures

import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph
import org.gs.digraph.PrimMST
import scala.annotation.tailrec
import org.gs.graph.fixtures.EdgeBuilder


/** @author Gary Struthers
 *
 */
  trait Builder {
    val tinyEWGData = Array((6, 0, 0.58000), (0, 2, 0.26000), (0, 4, 0.38000), (0, 7, 0.16000),
      (1, 3, 0.29000), (1, 2, 0.36000), (1, 7, 0.19000), (1, 5, 0.32000), (6, 2, 0.40000), 
      (2, 7, 0.34000), (1, 2, 0.36000), (0, 2, 0.26000), (2, 3, 0.17000), (3, 6, 0.52000), 
      (1, 3, 0.29000), (2, 3, 0.17000), (6, 4, 0.93000), (0, 4, 0.38000), (4, 7, 0.37000), 
      (4, 5, 0.35000), (1, 5, 0.32000), (5, 7, 0.28000), (4, 5, 0.35000), (6, 4, 0.93000), 
      (6, 0, 0.58000), (3, 6, 0.52000), (6, 2, 0.40000), (2, 7, 0.34000), (1, 7, 0.19000), 
      (0, 7, 0.16000), (5, 7, 0.28000), (4, 7, 0.37000))
    val tinyEdgeArray = {
      for (e <- tinyEWGData) yield new Edge(e._1, e._2, e._3)
    }
  }

  trait GraphBuilder extends Builder {
    val g = new EdgeWeightedGraph(8)
    for (ed <- tinyEdgeArray) g.addEdge(ed)
  }

  trait PrimMSTBuilder extends MSTBuilder {
    val primMST = new PrimMST(g)
  }
  
  trait MSTBuilder extends GraphBuilder {
    val tinyMST = Array((0, 2, 0.26000), (0, 7, 0.16000), (1, 7, 0.19000), (6, 2, 0.40000),
      (2, 3, 0.17000), (4, 5, 0.35000), (5, 7, 0.28000))
    val tinyMSTArray = for(e <- tinyMST) yield new Edge(e._1, e._2, e._3)
    val uf = new UF(g.V)

    def buildUF(edges: Seq[Edge]): Boolean = {
      @tailrec
      def loop(i: Int): Boolean = {
        if(i < edges.length) {
          val v = edges(i).either
          val w = edges(i).other(v)
          if (uf.connected(v, w)) true else {
            uf.union(v, w)
            loop(i + 1)
          }
        } else false
      }
      loop(0)
    }
  }
  
  trait EdgeWeightedGraphBuilder extends EdgeBuilder {
    def buildGraph(uri: String): EdgeWeightedGraph = {
      val managedResource = readURI(uri)
      val tuple = managedResource.loan(readFileToTuple)
      val g = new EdgeWeightedGraph(tuple._1)
      val edges = tuple._3
      for (ed <- edges) g.addEdge(ed)
      g
    }
  }
