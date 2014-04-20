package org.gs.graph

class KosarajuSharirSCC(g: Digraph) {
  val depthFirstOrder = new DepthFirstOrder(g.reverse)
  val marked = Array.fill[Boolean](g.v)(false)
  val id = new Array[Int](g.v)
  var count = 0
  for {
    v <- depthFirstOrder.reversePost
    if(!marked(v))
  } {
    dfs(v)
    count = count + 1
  }
  
  def dfs(v: Int):Unit = {
    marked(v) = true
    id(v) = count
    for {
      w <- g.adj(v)
      if(!marked(w))
    } dfs(w)
  }

  def stronglyConnected(v:Int, w: Int) = {
    id(v) == id(w)
  }
  
  def idStrongComponent(v:Int) = id(v)
}

object KosarajuSharirSCC {

}