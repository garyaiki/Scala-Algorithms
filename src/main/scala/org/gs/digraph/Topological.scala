package org.gs.digraph


class Topological(g: Digraph) {
  val finder = new DirectedCycle(g)
  var order:Option[Seq[Int]] = None 
  def findOrder():Seq[Int] = {
    (new DepthFirstOrder(g: Digraph)).reversePost
  }
  if(finder.hasCycle) order = Some(findOrder)
  
  def hasOrder() = order != None
}
object Topological {

}