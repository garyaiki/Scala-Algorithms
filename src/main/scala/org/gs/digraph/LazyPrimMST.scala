package org.gs.digraph

import org.gs.graph.{Edge, EdgeWeightedGraph}
import org.gs.queue.MinPQ
import org.gs.set.UF
import scala.annotation.tailrec
import scala.collection.mutable.{ArrayBuffer, Queue}

/** Compute a minimal spanning tree in an edge weighted graph
  *
  * @constructor creates a new LazyPrimMST with an EdgeWeightedGraph
  * @param g EdgeWeightedGraph
  * @see [[https://algs4.cs.princeton.edu/43mst/LazyPrimMST.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class LazyPrimMST(g: EdgeWeightedGraph) {
  private var _weight: Double = 0.0
  private val mst = new Queue[Edge]()
  private val marked = Array.fill[Boolean](g.numV)(false)
  private val pq = new MinPQ[Edge](new ArrayBuffer((g.e) * 2))
  for {
    v <- 0 until g.numV
    if (!marked(v))
  } prim(v)

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
        pq.pop match {
          case Some(e) => doEdge(e)
          case None => throw new NoSuchElementException("Priority queue underflow")
        }
        loop()
      }
    }
    loop()
  }

  /** Validate */
  def checkIsMinSpanningForest(): Boolean = {
    val uf = new UF(g.numV)

    def mstEdges(e: Edge) {
      mst foreach (f => if (f != e) uf.union(f.either, f.other(f.either)))
    }

    def minWeightInCrossingCut(e: Edge): Boolean = {

      def cutCheck(f: Edge): Boolean = {
        if (!uf.connected(f.either, f.other(f.either)) && f.weight < e.weight) false else true
      }

      @tailrec
      def loopE(es: Seq[Edge]): Boolean = es match {
          case last +: Seq() => cutCheck(last)
          case head +: tail => if(cutCheck(head)) loopE(tail) else false
      }

      loopE(g.edges)
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
        case head +: tail => if(doEdge(head)) loopMW(tail) else false
      }
    }
    loopMW(es)
  }
}
