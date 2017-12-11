package org.gs.digraph

/** Find pre-order, post-order, & reverse post-order of digraph using depth first search
  *
  * @constructor creates a new DepthFirstOrder with a digraph and its number of vertices
  * @param g Digraph
  * @see [[https://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class DepthFirstOrder(g: Digraph ) extends BaseDepthFirstOrder(g.numV) {

  protected def dfs(v: Int): Unit = {
    marked(v) = true
    preCounter += 1
    _pre(v) = preCounter
    preOrder.enqueue(v)
    g.adj(v) foreach (x => if (!marked(x)) dfs(x))
    postOrder.enqueue(v)
    postCounter += 1
    _post(v) = postCounter
  }
}
