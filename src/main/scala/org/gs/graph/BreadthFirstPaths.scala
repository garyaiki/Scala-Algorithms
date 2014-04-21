package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/BreadthFirstPaths.java.html
 */
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
class BreadthFirstPaths(g: Graph, s: Int) {
  private[graph] val marked = Array.fill[Boolean](g.v)(false)
  private[graph] val edgeTo = new Array[Int](g.v)
  private[graph] val distTo = Array.fill[Int](g.v)(Int.MaxValue)

  private def bfs(s: Int) {
    val q = new Queue[Int]()
    distTo(s) = 0
    marked(s) = true
    q.enqueue(s)
    for {
      v <- q
      w <- g.adj(v)
      if (!marked(w))
    } {
      edgeTo(w) = v
      distTo(w) = distTo(v) + 1
      marked(w) = true
      q.enqueue(w)
    }
  }
  bfs(s)

  def hasPathTo(v: Int) = marked(v)
  def distance(v: Int) = distTo(v)
  
  def pathTo(v: Int): Option[Seq[Int]] = {
    val path = ListBuffer[Int]()
    if (!hasPathTo(v)) None else {
      def loop(x: Int): Unit = {
        if (distTo(x) == 0) {
          path.prepend(x)
          val y = edgeTo(x)
          loop(y)
        }  
        path.prepend(v)
      }
      loop(v)
      Some(path)
    }

  }
}
object BreadthFirstPaths {

}