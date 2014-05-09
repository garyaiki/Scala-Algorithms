/**
 * @see http://algs4.cs.princeton.edu/44sp/AcyclicSP.java.html
 */
package org.gs.digraph

import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

/**
 * @author Gary Struthers
 * @param g acyclic digraph
 * @param s source vertex
 */
class AcyclicSP(g: EdgeWeightedDigraph, s: Int) {
  val _distTo = Array.fill[Double](g.v)(Double.PositiveInfinity)
  _distTo(s) = 0.0
  val edgeTo = new Array[DirectedEdge](g.v)
  val topological = new Topological(g)
  topological.order match {
    case None => throw new IllegalArgumentException(s"EdgeWeightedDigraph:$g is not acyclic")
    case Some(x) => for{
      v <- x
      e <- g.adj(v)
    } relax(e)
  }

  private def relax(e: DirectedEdge): Unit = {
    val v = e.from
    val w = e.to
    if(_distTo(w) > _distTo(v) + e.weight) {
      _distTo(w) = _distTo(v) + e.weight
      edgeTo(w) = e
    }
  }
  
  def distTo(v: Int): Double = _distTo(v)
  
  def hasPathTo(v: Int): Boolean = _distTo(v) < Double.PositiveInfinity
  
  def pathTo(v: Int): Option[Seq[DirectedEdge]] = {
    if(!hasPathTo(v)) None else {
      val path = new ListBuffer[DirectedEdge]()
      @tailrec
      def loop(e: DirectedEdge) {
        if(e != null) {
          e +=: path
          loop(edgeTo(e.from))
        }
      }
      loop(edgeTo(v))
      Some(path.toSeq)
    }
  }
}