/** @see https://algs4.cs.princeton.edu/43mst/PrimMST.java.html
  */
package org.gs.digraph
import org.gs.graph.{Edge, EdgeWeightedGraph}
import org.gs.queue.IndexMinPQ
import scala.annotation.tailrec
import scala.collection.mutable.Queue

/** Compute a minimal spanning tree in an edge weighted graph
  *
  * Only the shortest edge connecting a vertex to the tree remains on queue
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new PrimMST with an EdgeWeightedGraph
  * @param g [[org.gs.graph.EdgeWeightedGraph]]
  */
class PrimMST(g: EdgeWeightedGraph) {
  private val edgeTo = new Array[Edge](g.V)
  private val distTo = Array.fill[Double](g.V)(Double.MaxValue)
  private val marked = Array.fill[Boolean](g.V)(false)
  private val pq = new IndexMinPQ[Double](g.V)
  for (v <- 0 until g.V; if (!marked(v))) prim(v)

  private def scan(v: Int): Unit = {
    marked(v) = true

    def scanEdge(e: Edge): Unit = if (!marked(e.other(v))) {
      val w = e.other(v)
      if (e.weight < distTo(w)) {
        distTo(w) = e.weight
        edgeTo(w) = e
        if (pq.contains(w)) pq.decreaseKey(w, distTo(w)) else pq.insert(w, distTo(w))
      }
    }
    g.adj(v) foreach (scanEdge)
  }

  private def prim(s: Int): Unit = {
    distTo(s) = 0.0
    pq.insert(s, distTo(s))
    scan(s)

    @tailrec
    def loop(): Unit = if (!pq.isEmpty) {
      scan(pq.delMin)
      loop()
    }

    loop()
  }

  /** returns sum of edge weights in a MST */
  def weight(): Double = edges.foldLeft(0.0)(_ + _.weight)

  /** returns edges of a MST */
  def edges(): List[Edge] = {
    val mst = new Queue[Edge]()
    edgeTo foreach (e => if (e != null) mst.enqueue(e))
    mst.toList
  }
}
