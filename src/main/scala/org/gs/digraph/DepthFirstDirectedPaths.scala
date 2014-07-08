package org.gs.digraph

/** @see http://algs4.cs.princeton.edu/42directed/DepthFirstDirectedPaths.java.html
 */
import scala.annotation.tailrec

/** Find paths from single source vertex
 *
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param g [[org.gs.digraph.Digraph]]
 * @param s a single source vertex
 */
class DepthFirstDirectedPaths(g: Digraph, s: Int) {
  private val marked = new Array[Boolean](g.V)
  private val edgeTo = new Array[Int](g.V)

  private def dfs(v: Int) {
    marked(v) = true
    for {
      w <- g.adj(v)
      if (!marked(w))
    } {
      edgeTo(w) = v
      dfs(w)
    }
  }
  dfs(s)

  /** @return if there is a path from s to v */
  def hasPathTo(v: Int): Boolean = marked(v)

  /** @return the path from s to v */
  def pathTo(v: Int): List[Int] = {
    var pathStack = List[Int]()
    
    @tailrec
    def loop(x: Int): Int = {
      if (x == s) x else {
        pathStack = x :: pathStack
        loop(edgeTo(x))
      }
    }
    loop(v) :: pathStack
  }
}
