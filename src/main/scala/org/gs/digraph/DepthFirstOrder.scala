/** @see http://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
  */
package org.gs.digraph

/** Find pre-order, post-order, & reverse post-order of digraph using depth first search
  *   
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * 
  * @constructor creates a new DepthFirstOrder with a digraph and its number of vertices
  * @param g [[org.gs.digraph.Digraph]]
  * @param g.V number of vertices in g
  */
class DepthFirstOrder(g: Digraph ) extends BaseDepthFirstOrder(g.V) {

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
