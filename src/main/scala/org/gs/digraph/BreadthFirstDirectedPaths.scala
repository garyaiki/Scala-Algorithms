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
  val marked = Array.fill[Boolean](g.v)(false)
  val edgeTo = new Array[Int](g.v)
  val distTo = Array.fill[Int](g.v)(Int.MaxValue)

  private def bfs(s: Int) {
    val q = new Queue[Int]()
    marked(s) = true
    distTo(s) = 0
    q.enqueue(s)
    for {
      dq <- q
      w <- g.adj(dq)
      if !(marked(w))
    } {
      edgeTo(w) = dq
      distTo(w) = distTo(dq) + 1
      marked(w) = true
      q.enqueue(w)
    }
  }
  bfs(s)

  def hasPathTo(v: Int): Boolean = marked(v)

  def distance(v: Int): Int = distTo(v)

  def pathTo(v: Int): Seq[Int] = {
    var pathStack = List[Int]()
    @tailrec
    def loop(x: Int): Int = {
      if(distTo(x) == 0) x else {
        pathStack = x :: pathStack
        loop(edgeTo(x))
      }
    }
    (loop(v) :: pathStack).toSeq
  }
}
