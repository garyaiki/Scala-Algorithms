/**
 * @see http://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
 */
package org.gs.digraph

/** Find pre-order, post-order, & reverse post-order of digraph using depth first search
 *  
 * Extends [[org.gs.digraph.BaseDepthFirstOrder]]
 *  
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param g [[org.gs.digraph.Digraph]]
 * @param g.V number of vertices in g
 */
class DepthFirstOrder(g: Digraph) extends BaseDepthFirstOrder(g.V){

  protected def dfs(v: Int): Unit = {
    marked(v) = true
    preCounter += 1
    _pre(v) = preCounter
    preOrder.enqueue(v)
    for {
      w <- g.adj(v)
      if (!marked(w))
    } dfs(w)
    postOrder.enqueue(v)
    postCounter += 1
    _post(v) = postCounter
  }
}
