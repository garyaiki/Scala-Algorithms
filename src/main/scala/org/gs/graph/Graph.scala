package org.gs.graph

class Graph(val v: Int) {
  var e = 0
  val adj = new Array[List[Int]](v)
  for {
    newV <- 0 until v
  } adj(newV) = List[Int]()

  def addEdge(aV: Int, otherV: Int) {
    def rangeGuard(x: Int) = {
      x match {
        case x if 0 until v contains x => true
        case _ => false
      }
    }
    require(rangeGuard(aV))
    require(rangeGuard(otherV))
    e = e + 1
    adj(aV) = otherV :: adj(aV)
    adj(otherV) = aV :: adj(otherV)
  }
}
object Graph {

}