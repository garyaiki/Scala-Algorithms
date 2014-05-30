/**
 * @see http://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
 */
package org.gs.digraph

import scala.annotation.tailrec
import org.gs.queue.IndexMinPQ

/**
 * @author Gary Struthers
 *
 */
class DijkstraSP(g: EdgeWeightedDigraph, s: Int) {
  require(g.edges.forall(_.weight >= 0))
  val distTo = Array.fill[Double](g.v)(Double.PositiveInfinity)
  distTo(s) = 0.0
  val edgeTo = new Array[DirectedEdge](g.v)
  val pq = new IndexMinPQ[Double](g.v)
  relaxVertices

  private def relaxVertices() {
    def relax(e: DirectedEdge) {
      val v = e.from
      val w = e.to
      if (distTo(w) > distTo(v) + e.weight) {
        distTo(w) = distTo(v) + e.weight
        edgeTo(w) = e
        if (pq.contains(w)) pq.decreaseKey(w, distTo(w))
        else pq.insert(w, distTo(w))
      }
    }
    @tailrec
    def loop() {
      if (!pq.isEmpty) {
        val v = pq.delMin
        for (e <- g.adj(v)) relax(e)
        loop
      }
    }
    pq.insert(s, distTo(s))
    loop
  }

  def getDistTo(v: Int): Double = distTo(v)

  def hasPathTo(v: Int): Boolean = distTo(v) < Double.PositiveInfinity

  def pathTo(v: Int): Option[List[DirectedEdge]] = {
    if (!hasPathTo(v)) None else {
      @tailrec
      def loop(e: DirectedEdge, path: List[DirectedEdge] ): List[DirectedEdge] = {
        if(e != null) {
          loop(edgeTo(e.from), e :: path)
        } else path
      }
      val path = loop(edgeTo(v), List[DirectedEdge]())
      Some(path)
    }
  }
}
