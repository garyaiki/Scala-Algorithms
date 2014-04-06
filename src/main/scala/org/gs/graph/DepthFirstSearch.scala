package org.gs.graph

class DepthFirstSearch(g: Graph, s: Int) {
  val marked = new Array[Boolean](g.v)
  var count = 0
  
  private def dfs(v: Int) {
    count = count + 1
    marked(v) = true
    for {
      w <- g.adj(v)
      if (!marked(w))   
    } dfs(w) 
  }
  dfs(s)
}
object DepthFirstSearch {

}