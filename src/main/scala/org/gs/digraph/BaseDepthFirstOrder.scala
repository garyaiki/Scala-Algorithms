/**
 * Common code for DepthFirstOrder, EdgeWeightedDepthFirstOrder
 */
package org.gs.digraph

import scala.collection.mutable.Queue

/**
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 * @param v number of vertices in digraph
 *
 */
abstract class BaseDepthFirstOrder(v: Int) {
  protected val _pre = new Array[Int](v)
  protected val _post = new Array[Int](v)
  protected val preOrder = new Queue[Int]()
  protected val postOrder = new Queue[Int]()
  protected var preCounter = 0
  protected var postCounter = 0
  protected val marked = Array.fill[Boolean](v)(false)
  for {
    i <- 0 until v
    if (!marked(i))
  } dfs(i)

  protected def dfs(v: Int): Unit

  def pre(v: Int): Int = _pre(v)

  def post(v: Int): Int = _post(v)

  def pre(): List[Int] = preOrder.toList

  def post(): List[Int] = postOrder.toList

  def reversePost(): List[Int] = {
    var reverse = List[Int]()
    for {
      v <- postOrder
    } reverse = v :: reverse
    reverse
  }
}
