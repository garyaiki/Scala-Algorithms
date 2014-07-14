/** @see http://algs4.cs.princeton.edu/44sp/BellmanFordSP.java.html
  */
package org.gs.digraph

import scala.collection.mutable.Queue
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/** Solves for shortest path from a source where there are no negative cycles
  *
  * Edge weights can be positive, negative, or zero. It finds either a shortest path to every other
  * vertex or a negative cycle reachable from source
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor creates a new BellmanFordSP with an edge weighted digraph and source vertex
  * @param g acyclic digraph
  * @param s source vertex
  */
class BellmanFordSP(g: EdgeWeightedDigraph, s: Int) {
  private val _distTo = Array.fill[Double](g.V)(Double.PositiveInfinity)
  private val edgeTo = new Array[DirectedEdge](g.V)
  private val onQueue = Array.fill[Boolean](g.V)(false)
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

  /** returns if it has a negative cycle */
  def hasNegativeCycle(): Boolean = cycle != null

  /** returns a negative cycle if it exists */
  def negativeCycle(): Option[List[DirectedEdge]] = if (cycle != null) Some(cycle) else None

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
      if (cost % g.V == 0) findNegativeCycle
      cost += 1
    }
  }

  /** returns length of shortest path from source to v */
  def distTo(v: Int): Double = {
    require(!hasNegativeCycle, s"negative cycle from $s to $v")
    _distTo(v)
  }

  /** returns if there is a path from source to v */
  def hasPathTo(v: Int): Boolean = _distTo(v) < Double.PositiveInfinity

  /** returns path from source to v if it exists */
  def pathTo(v: Int): Option[List[DirectedEdge]] = {
    require(!hasNegativeCycle, s"negative cycle from $s to $v")
    if (!hasPathTo(v)) None else {
      val path = new ListBuffer[DirectedEdge]()

      @tailrec
      def loop(e: DirectedEdge) {
        if (e != null) {
          e +=: path
          loop(edgeTo(e.from))
        }
      }
      loop(edgeTo(v))
      Some(path.toList)
    }
  }
}
