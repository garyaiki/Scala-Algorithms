/**
 * Common code for DepthFirstOrder, EdgeWeightedDepthFirstOrder
 */
package org.gs.digraph

import scala.collection.mutable.Queue

/**
 * @author Gary Struthers
 * @param v number of vertices in digraph
 *
 */
abstract class BaseDepthFirstOrder(v: Int) {
  protected val _pre = new Array[Int](v)
  protected val _post = new Array[Int](v)
  val preOrder = new Queue[Int]()
  val postOrder = new Queue[Int]()
  protected var preCounter = 0
  protected var postCounter = 0
  protected val marked = Array.fill[Boolean](v)(false)
  for {
    i <- 0 until v
    if (!marked(i))
  } dfs(i)

  def dfs(v: Int): Unit

  def pre(v: Int): Int = _pre(v)

  def post(v: Int): Int = _post(v)

  def pre(): Seq[Int] = preOrder.toSeq

  def post(): Seq[Int] = postOrder.toSeq

  def reversePost(): Seq[Int] = {
    var reverse = List[Int]()
    for {
      v <- postOrder
    } reverse = v :: reverse
    reverse.toSeq
  }
}
