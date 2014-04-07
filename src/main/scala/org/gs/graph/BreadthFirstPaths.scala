package org.gs.graph
import scala.collection.mutable.Queue
class BreadthFirstPaths(g: Graph, s: Int) {
  val paths = new Array[(Boolean, Int)](g.v) // marked _1, edgeTo _2
  val distTo = Array.fill[Int](g.v)(Int.MaxValue)
  
  private def bfs(s: Int) {
    val q = new Queue[Int]()
    distTo(s) = 0
    paths(s) = (true, if(paths(s) != null) paths(s)._2 else null.asInstanceOf[Int])
    q.enqueue(s)
    for {
      dq <- q
      w <- g.adj(dq)
      if (paths(w) == null || !paths(w)._1)   
    } {
      paths(w) = (true, dq)
      distTo(w) = distTo(dq) + 1
      q.enqueue(w)
    } 
  }
  bfs(s)
  
  def hasPathTo(v: Int) = paths(v) != null && paths(v)._1
  def distance(v: Int) = distTo(v)
  def pathTo(v: Int) = {
    var pathStack = List[Int]()
    def loop(x: Int): Int = {
      if(distTo(x) == 0) x else {
        pathStack = x :: pathStack
        loop(paths(x)._2)
      }
    }
    pathStack(loop(v))
  }
}
object BreadthFirstPaths {

}