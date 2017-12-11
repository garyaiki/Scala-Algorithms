package org.gs.digraph

import scala.annotation.tailrec

/** Find paths from single source vertex
  *
  * @constructor createa a new DepthFirstDirectedPaths with a digraph and a source vertex
  * @param g Digraph
  * @param s a single source vertex
  * @see [[https://algs4.cs.princeton.edu/42directed/DepthFirstDirectedPaths.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class DepthFirstDirectedPaths(g: Digraph, s: Int) {
  private val marked = new Array[Boolean](g.numV)
  private val edgeTo = new Array[Int](g.numV)

  private def dfs(v: Int) {
    marked(v) = true
    g.adj(v) foreach (w => if (!marked(w)) {
      edgeTo(w) = v
      dfs(w)
    })
  }
  dfs(s)

  /** returns if there is a path from s to v */
  def hasPathTo(v: Int): Boolean = marked(v)

  /** returns the path from s to v */
  def pathTo(v: Int): List[Int] = {

    @tailrec
    def loop(x: Int, xs: List[Int]): List[Int] = if (x == s) x :: xs else loop(edgeTo(x), x :: xs)

    loop(v, List[Int]())
  }
}
