/** @see http://algs4.cs.princeton.edu/43mst/LazyPrimMST.java.html
  */
package org.gs.digraph

/** Compute a minimal spanning tree in an edge weighted graph
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * 
  * @constructor creates a new LazyPrimMST with an EdgeWeightedGraph
  * @param g [[org.gs.graph.EdgeWeightedGraph]]
  */
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
import scala.annotation.tailrec
import org.gs.queue.MinPQ
import org.gs.set.UF
import org.gs.graph.Edge
import org.gs.graph.EdgeWeightedGraph

class LazyPrimMST(g: EdgeWeightedGraph) {
  private var _weight: Double = 0.0
  private val mst = new Queue[Edge]()
  private val marked = Array.fill[Boolean](g.V)(false)
  private val pq = new MinPQ[Edge](new ArrayBuffer((g.e) * 2))
  for (v <- 0 until g.V; if (!marked(v))) prim(v)

  /** returns sum of edge weights in a MST */
  def weight(): Double = _weight

  /** returns edges of a MST */
  def edges(): List[Edge] = mst.toList

  private def scan(v: Int) {
    require(!marked(v), s"v:$v is already marked")
    marked(v) = true
    g.adj(v) foreach (ed => if (!marked(ed.other(v))) pq.insert(ed))
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

  /** Validate */
  def checkIsMinSpanningForest(): Boolean = {
    var cutOptimiality = true
    val uf = new UF(g.V)
    
    def mstEdges(e: Edge) {
      for (f <- mst) {
        val x = f.either
        val y = f.other(x)
        if (f != e) uf.union(x, y)
      }
    }

    def minWeightInCrossingCut(e: Edge): Boolean = {
      
      def cutCheck(f: Edge): Boolean = {
        val x = f.either
        val y = f.other(x)
        if (!uf.connected(x, y) && f.weight < e.weight) false else true
      }
      
      @tailrec
      def loopE(es: Seq[Edge]): Boolean = {
        es match {
          case last +: Seq() => cutCheck(last)
          case head +: tail => {
            if(cutCheck(head)) loopE(tail) else false
          }
        }
      }

      val es = g.edges
      loopE(es)
    }
    val es = edges
    
    @tailrec
    def loopMW(es: Seq[Edge]): Boolean = {
      
      def doEdge(e: Edge): Boolean = {
        mstEdges(e)
        if (!minWeightInCrossingCut(e)) false else true
      }
      
      es match {
          case last +: Seq() => doEdge(last)
          case head +: tail => {
            if(doEdge(head)) loopMW(tail) else false
          }
        }
    }
    loopMW(es)
  }
}
