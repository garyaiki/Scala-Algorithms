package org.gs.digraph
/**
 * @see http://algs4.cs.princeton.edu/42directed/BreadthFirstDirectedPaths.java.html
 */
import scala.collection.mutable.Queue
import scala.annotation.tailrec

/**
 * @author Gary Struthers
 *
 */
class BreadthFirstDirectedPaths(g: Digraph, s: Int) {
  private val marked = Array.fill[Boolean](g.V)(false)
  private val edgeTo = new Array[Int](g.V)
  private val _distTo = Array.fill[Int](g.V)(Int.MaxValue)

  private def bfs(s: Int) {
    val q = new Queue[Int]()
    marked(s) = true
    _distTo(s) = 0
    q.enqueue(s)
    for {
      dq <- q
      w <- g.adj(dq)
      if !(marked(w))
    } {
      edgeTo(w) = dq
      _distTo(w) = _distTo(dq) + 1
      marked(w) = true
      q.enqueue(w)
    }
  }
  bfs(s)

  def hasPathTo(v: Int): Boolean = marked(v)

  def distTo(v: Int): Int = _distTo(v)

  def pathTo(v: Int): List[Int] = {
    @tailrec
    def loop(x: Int, xs: List[Int]): List[Int] = {
      if (_distTo(x) == 0) x :: xs else {
        loop(edgeTo(x), x :: xs)
      }
    }
    loop(v, List[Int]())
  }
}
