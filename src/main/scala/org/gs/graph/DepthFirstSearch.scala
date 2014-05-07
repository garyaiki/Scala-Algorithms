/**
 * @see http://algs4.cs.princeton.edu/41undirected/DepthFirstSearch.java.html
 */
package org.gs.graph

/**
 * @author Gary Struthers
 * @param g
 * @param s
 */
class DepthFirstSearch(g: Graph, s: Int) {
  val marked = new Array[Boolean](g.v)
  var count = 0
  
  private def dfs(v: Int) {
    count += 1
    marked(v) = true
    for {
      w <- g.adj(v)
      if (!marked(w))   
    } dfs(w) 
  }
  dfs(s)
}
