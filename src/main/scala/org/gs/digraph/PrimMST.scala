/**
 * @see http://algs4.cs.princeton.edu/43mst/PrimMST.java.html
 */
package org.gs.digraph
import scala.collection.mutable.Queue
import scala.annotation.tailrec
import org.gs.queue.IndexMinPQ
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph

/**
 * @author Gary Struthers
 * @param g EdgeWeightedGraph
 */
class PrimMST(g: EdgeWeightedGraph) {
  private val edgeTo = new Array[Edge](g.v)
  private val distTo = Array.fill[Double](g.v)(Double.MaxValue)
  private val marked = Array.fill[Boolean](g.v)(false)
  private val pq = new IndexMinPQ[Double](g.v)
  for {
    v <- 0 until g.v
    if (!marked(v))
  } prim(v)

  private def scan(v: Int): Unit = {
    marked(v) = true
    for {
      e <- g.adj(v)
      if (!marked(e.other(v)))
    } {
      val w = e.other(v)
      if (e.weight < distTo(w)) {
        distTo(w) = e.weight
        edgeTo(w) = e
        if (pq.contains(w)) pq.decreaseKey(w, distTo(w))
        else pq.insert(w, distTo(w))
      }
    }
  }

  private def prim(s: Int): Unit = {
    distTo(s) = 0.0
    pq.insert(s, distTo(s))
    scan(s)
    @tailrec
    def loop(): Unit = {
      if (!pq.isEmpty) {
        scan(pq.delMin)
        loop
      }
    }
    loop
  }

  def edges(): List[Edge] = {
    val mst = new Queue[Edge]()
    val length = edgeTo.length
    for (v <- 0 until length) {
      val e = edgeTo(v)
      if (e != null) mst.enqueue(e)
    }
    mst.toList
  }

  def weight(): Double = edges.foldLeft(0.0)(_ + _.weight)
}
