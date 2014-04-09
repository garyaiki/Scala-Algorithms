package org.gs.graph

class DirectedDFS(g: Digraph, s: Int) {
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
  
  def isMarked(v: Int) = marked(v)
}
object DirectedDFS {

}