package org.gs.graph

import org.gs.digraph.Digraph
import org.gs.digraph.DirectedCycle
import org.gs.digraph.DepthFirstOrder

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