package org.gs.graph
import scala.collection.mutable.Queue
class BreadthFirstDirectedPaths(g: Digraph, s: Int) {
  val marked = Array.fill[Boolean](g.v)(false)
  val edgeTo = new Array[Int](g.v)
  val distTo = Array.fill[Int](g.v)(Int.MaxValue)
  
  private def bfs(s: Int) {
    val q = new Queue[Int]()
    marked(s) = true
    distTo(s) = 0
    q.enqueue(s)
    for {
      dq <- q
      w <- g.adj(dq)
      if !(marked(w))   
    } {
      edgeTo(w) = dq
      distTo(w) = distTo(dq) + 1
      marked(w) = true
      q.enqueue(w)
    } 
  }
  bfs(s)
  
  def hasPathTo(v: Int) = marked(v)
  def distance(v: Int) = distTo(v)
  def pathTo(v: Int) = {
    var pathStack = List[Int]()
    def loop(x: Int): Int = {
      if(distTo(x) == 0) x else {
        pathStack = x :: pathStack
        loop(edgeTo(x))
      }
    }
    (loop(v) :: pathStack).toSeq
  }
}
object BreadthFirstDirectedPaths {

}