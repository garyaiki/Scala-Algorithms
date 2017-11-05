/** @see https://algs4.cs.princeton.edu/44sp/AcyclicSP.java.html
  */
package org.gs.digraph

import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

/** Solves for shortest path from a source where edge weights can be positive, negative, or zero
  *
  * Uses [[org.gs.digraph.Topological]] order
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new AcyclicSP with a digraph and source vertex
  * @param g acyclic digraph
  * @param s source vertex
  */
class AcyclicSP(g: EdgeWeightedDigraph, s: Int) {
  private val _distTo = Array.fill[Double](g.V)(Double.PositiveInfinity)
  _distTo(s) = 0.0
  private val edgeTo = Array.fill[Option[DirectedEdge]](g.V)(None)
  private val topological = new Topological(g)

  topological.order match {
    case None => throw new IllegalArgumentException(s"EdgeWeightedDigraph:$g is not acyclic")
    case Some(x) => for {
      v <- x
      e <- g.adj(v)
    } relax(e)
  }

  private def relax(e: DirectedEdge): Unit = {
    val v = e.from
    val w = e.to
    if (_distTo(w) > _distTo(v) + e.weight) {
      _distTo(w) = _distTo(v) + e.weight
      edgeTo(w) = Some(e)
    }
  }

  /** returns length of shortest path from source to v */
  def distTo(v: Int): Double = _distTo(v)

  /** returns if there is a path from source to v */
  def hasPathTo(v: Int): Boolean = _distTo(v) < Double.PositiveInfinity

  /** returns path from source to v if it exists */
  def pathTo(v: Int): Option[Seq[DirectedEdge]] = {
    if (!hasPathTo(v)) None else {
      val path = new ListBuffer[DirectedEdge]()

      @tailrec
      def loop(e: DirectedEdge) {
        e +=: path
        edgeTo(e.from) match {
          case None =>
          case Some(x) => loop(x)
        }
      }

      edgeTo(v) match {
        case None =>
        case Some(x) => loop(x)
      }
      Some(path.toSeq)
    }
  }
}
