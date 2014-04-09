package org.gs.graph

class DirectedCycle(g: Digraph) {
  val marked = Array.fill[Boolean](g.v)(false)
  val onStack = Array.fill[Boolean](g.v)(false)
  val edgeTo = new Array[Int](g.v)
  var cycle = List[Int]()
  for {
    v <- 0 until g.v
    if(!marked(v))
  } dfs(v)

  private def dfs(v: Int) {
    onStack(v) = true
    marked(v) = true
    def recurOnNewVertex(w: Int):Boolean = {
      if (!marked(w)) {
        edgeTo(w) = v
        dfs(w)
        true
      } else false
    }
    def traceBack(w: Int):Boolean =  {
      if(onStack(w)) {
    	cycle = List[Int]()
        def loop(x: Int): Unit = {
          if (x != w) {
            cycle = x :: cycle
            loop(edgeTo(x))
          }
        }
        loop(v)
        cycle = w :: cycle
        cycle = v :: cycle
        true
      } else false
    }
    for {
      w <- g.adj(v)
    } {
      if (!hasCycle) {
        if(!recurOnNewVertex(w)) traceBack(w)
      }
    }
    onStack(v) = false
  }

  def hasCycle() = !cycle.isEmpty

}
object DirectedCycle {

}