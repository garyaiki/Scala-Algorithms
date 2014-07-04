package org.gs.graph
/**
 * @see http://algs4.cs.princeton.edu/41undirected/BreadthFirstPaths.java.html
 */
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

/**
 * Find shortest path from source vertex to v
 * 
 * Enqueue a vertex that has been marked but its adjacencies haven't been. Then dequeue a vertex and
 * enqueue all its unmarked adjacencies and mark them
 * @author Gary Struthers
 * @param g [[org.gs.graph.Graph]]
 * @param s source vertex
 */
class BreadthFirstPaths(g: Graph, s: Int) {
  private[graph] val marked = Array.fill[Boolean](g.V)(false)
  private[graph] val edgeTo = new Array[Int](g.V)
  private[graph] val _distTo = Array.fill[Int](g.V)(Int.MaxValue)

  private def bfs(s: Int): Unit = {
    val q = new Queue[Int]()
    _distTo(s) = 0
    marked(s) = true
    q.enqueue(s)
    for {
      v <- q
      w <- g.adj(v)
      if (!marked(w))
    } {
      edgeTo(w) = v
      _distTo(w) = _distTo(v) + 1
      marked(w) = true
      q.enqueue(w)
    }
  }
  bfs(s)

  /** is there a path from source vertex s to v */
  def hasPathTo(v: Int): Boolean = marked(v)

  /** @return number of edges in shortest path from source vertex to v */
  def distTo(v: Int): Int = _distTo(v)

  /** @return a list of vertices in the shortest path from the source vertex to v */
  def pathTo(v: Int): Option[List[Int]] = {
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
      Some(path.toList)
    }
  }
}
