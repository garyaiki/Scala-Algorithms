/** @see http://algs4.cs.princeton.edu/44sp/BellmanFordSP.java.html
 */
package org.gs.digraph

import scala.collection.mutable.Queue
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/** @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param g acyclic digraph
 * @param s source vertex
 */
class BellmanFordSP(g: EdgeWeightedDigraph, s: Int) {
  private val _distTo = Array.fill[Double](g.v)(Double.PositiveInfinity)
  private val edgeTo = new Array[DirectedEdge](g.v)
  private val onQueue = Array.fill[Boolean](g.v)(false)
  private val queue = new Queue[Int]()
  private var cost = 0
  private var cycle = null.asInstanceOf[List[DirectedEdge]]
  private def getEdgeTo(v: Int): DirectedEdge = edgeTo(v)
  _distTo(s) = 0.0
  queue.enqueue(s)
  onQueue(s) = true
  @tailrec
  private def loop(): Unit = {
    if (!queue.isEmpty && !hasNegativeCycle) {
      val v = queue.dequeue
      onQueue(v) = false
      relax(v)
      loop
    }
  }
  loop

  def hasNegativeCycle(): Boolean = cycle != null

  def negativeCycle(): List[DirectedEdge] = cycle

  private def findNegativeCycle(): Unit = {
    val spt = new EdgeWeightedDigraph(edgeTo.length)
    for {
      v <- 0 until edgeTo.length
      if (edgeTo(v) != null)
    } spt.addEdge(edgeTo(v))

    val finder = new EdgeWeightedDirectedCycle(spt)
    finder.cycle match {
      case Some(x) => cycle = x
      case _ =>
    }
  }

  private def relax(v: Int): Unit = {
    for {
      e <- g.adj(v)
    } {
      val w = e.to
      if (_distTo(w) > _distTo(v) + e.weight) {
        _distTo(w) = _distTo(v) + e.weight
        edgeTo(w) = e
        if (!onQueue(w)) {
          queue.enqueue(w)
          onQueue(w) = true
        }
      }
      if (cost % g.v == 0) findNegativeCycle
      cost += 1
    }
  }

  def distTo(v: Int): Double = {
    require(!hasNegativeCycle, s"negative cycle from $s to $v")
    _distTo(v)
  }

  def hasPathTo(v: Int): Boolean = _distTo(v) < Double.PositiveInfinity

  def pathTo(v: Int): Option[List[DirectedEdge]] = {
    require(!hasNegativeCycle, s"negative cycle from $s to $v")
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
      Some(path.toList)
    }
  }
}
