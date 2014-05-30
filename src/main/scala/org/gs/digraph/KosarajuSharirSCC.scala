/**
 * @see http://algs4.cs.princeton.edu/42directed/KosarajuSharirSCC.java.html
 */
package org.gs.digraph


/**
 * @author Gary Struthers
 * @param g digraph
 */
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
    count += 1
  }

  def dfs(v: Int):Unit = {
    marked(v) = true
    id(v) = count
    for {
      w <- g.adj(v)
      if(!marked(w))
    } dfs(w)
  }

  def stronglyConnected(v:Int, w: Int): Boolean = id(v) == id(w)

  def idStrongComponent(v:Int): Int = id(v)
}

