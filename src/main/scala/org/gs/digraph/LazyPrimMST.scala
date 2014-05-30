/**
 * @see http://algs4.cs.princeton.edu/43mst/LazyPrimMST.java.html
 */
package org.gs.digraph
/**
 * @author Gary Struthers
 *
 */
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
import scala.annotation.tailrec
import org.gs.queue.MinPQ
import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph
import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable

class LazyPrimMST(g: EdgeWeightedGraph) {
  private var _weight: Double = 0.0
  private val mst = new Queue[Edge]()
  private val marked = Array.fill[Boolean](g.v)(false)
  private val pq = new MinPQ[Edge](new ArrayBuffer((g.e) * 2))
  for {
    v <- 0 until g.v
    if (!marked(v))
  } prim(v)

  def weight(): Double = _weight
  
  def edges(): Seq[Edge] = mst.toSeq

  private def scan(v: Int) {
    require(!marked(v), s"v:$v is already marked")
    marked(v) = true
    for {
      ed <- g.adj(v)
      if (!marked(ed.other(v)))
    } pq.insert(ed)
  }

  private def prim(s: Int): Unit = {
    scan(s)
    @tailrec
    def loop(): Unit = {
      def doEdge(edg: Edge) {
        val v = edg.either
        val w = edg.other(v)
        val vM = marked(v)
        val wM = marked(w)
        require(vM || wM, "Edge:$edg has no marked endpoint")
        if (!(vM && wM)) {
          mst.enqueue(edg)
          _weight += edg.weight
          if (!vM) scan(v)
          if (!wM) scan(w)
        }
      }
      if (!pq.isEmpty) {
        val edge = pq.pop
        edge match {
          case Some(e) => doEdge(e)
          case None => throw new NoSuchElementException("Priority queue underflow")
        }
        loop
      }
    }
    loop
  }

  import scala.util.control.Breaks._
  /**
   * This function only used for test. It's in the class to keep "mst" private and
   * "g" hidden from companion object
   */
  def checkIsMinSpanningForest(): Boolean = {
    var cutOptimiality = true
    val uf = new UF(g.v)
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
      for (e <- edges) {
        mstEdges(e)
        if (!minWeightInCrossingCut(e)) break
      }
    }
    cutOptimiality
  }
}
