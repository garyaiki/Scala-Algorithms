/**
 * @see http://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
 */
package org.gs.digraph

import scala.collection.mutable.Queue

/**
 * @author Gary Struthers
 *
 */
class DepthFirstOrder(g: Digraph) {
  private val pre = new Array[Int](g.v)
  private val post = new Array[Int](g.v)
  val preOrder = new Queue[Int]()
  val postOrder = new Queue[Int]()
  private var preCounter = 0
  private var postCounter = 0
  private val marked = Array.fill[Boolean](g.v)(false)
  for {
    i <- 0 until g.v
    if (!marked(i))
  } dfs(i)

  def dfs(v: Int): Unit = {
    marked(v) = true
    preCounter += 1
    pre(v) = preCounter
    preOrder.enqueue(v)
    for {
      w <- g.adj(v)
      if (!marked(w))
    } dfs(w)
    postOrder.enqueue(v)
    postCounter += 1
    post(v) = postCounter
  }

  def preOrderVertex(v: Int) = pre(v)
  def postOrderVertex(v: Int) = post(v)

  def preOrderSeq(v: Int) = preOrder.toSeq
  def postOrderSeq(v: Int) = postOrder.toSeq

  def reversePost(): Seq[Int] = {
    var reverse = List[Int]()
    for {
      v <- postOrder
    } reverse = v :: reverse
    reverse.toSeq
  }

}
