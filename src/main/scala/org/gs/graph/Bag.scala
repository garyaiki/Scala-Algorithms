package org.gs.graph

class Bag[T] {
  val nodes = List[T]()
  var n = 0
  
  def isEmpty() = nodes.isEmpty
  
  def size = n
  
  def add(item: T) {
    item :: nodes
    n = n + 1
  }
  
  def iterator():Iterator[T] = {
    nodes.iterator
  }
}

object Bag {

}