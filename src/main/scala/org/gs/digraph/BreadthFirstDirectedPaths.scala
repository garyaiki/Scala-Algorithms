package org.gs.digraph
/** @see http://algs4.cs.princeton.edu/42directed/BreadthFirstDirectedPaths.java.html
  */
import scala.collection.mutable.Queue
import scala.annotation.tailrec

/** Find shortest path from source vertex
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * 
  * @constructor creates a new BreadthFirstDirectedPaths with a digraph and source vertex
  * @param g [[org.gs.digraph.Digraph]]
  * @param s a single source vertex
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

  /** returns if there is a path from s to v */
  def hasPathTo(v: Int): Boolean = marked(v)

  /** returns number of edges in shortest path from s to v */
  def distTo(v: Int): Int = _distTo(v)

  /** returns the path from s to v */
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
