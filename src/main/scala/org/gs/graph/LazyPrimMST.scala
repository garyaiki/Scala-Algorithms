/**
 * @see http://algs4.cs.princeton.edu/43mst/LazyPrimMST.java.html
 */
package org.gs.graph
/**
 * @author Gary Struthers
 *
 */
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
import math.Ordering
import scala.annotation.tailrec

import org.gs.queue.MinPQ

class LazyPrimMST(g: EdgeWeightedGraph) {
  var sevens = 0
  private var weight: Double = 0.0
  private val mst = new Queue[Edge]()
  private val marked = Array.fill[Boolean](g.v)(false)
  private val pq = new MinPQ[Edge](new ArrayBuffer((g.e) * 2))
  for {
    v <- 0 until g.v
    if(!marked(v))
  } prim(v)

  def getWeight() = weight
  def edges() = mst.toSeq
  
  private def scan(v: Int) {
    require(!marked(v), s"v:$v is already marked")
    marked(v) = true
    for {
      ed <- g.adj(v)
      if(!marked(ed.other(v)))
    } pq.insert(ed)
  }
  
  private def prim(s: Int) {
    scan(s)
    @tailrec
    def loop(): Unit = {
      def doEdge(edg: Edge) {
        val v = edg.either
        val w = edg.other(v)
        val vM = marked(v)
        val wM = marked(w)
        require(vM || wM, "Edge:$edg has no marked endpoint" )
        if(!(vM && wM)) {
          mst.enqueue(edg)
          weight += edg.weight
          if(!vM) scan(v)
          if(!wM) scan(w)
        } 
      }
      if(!pq.isEmpty) {
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
}

object LazyPrimMST {

}