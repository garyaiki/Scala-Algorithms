/** @see http://algs4.cs.princeton.edu/42directed/DepthFirstOrder.java.html
  */
package org.gs.digraph

import scala.collection.mutable.Queue

/** Common code for finding pre-order, post-order, & reverse post-order in digraphs
  *
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @constructor called by subclass with number of vertices
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
  for (i <- 0 until v; if (!marked(i))) dfs(i)

  /** @param v vertex for depth first search to find pre-order & post-order */
  protected def dfs(v: Int): Unit

  /** returns pre-order number of vertex v */
  def pre(v: Int): Int = _pre(v)

  /** returns post-order number of vertex v */
  def post(v: Int): Int = _post(v)

  /** returns vertices in pre-order of vertex v */
  def pre(): List[Int] = preOrder.toList

  /** returns vertices in post-order of vertex v */
  def post(): List[Int] = postOrder.toList

  /** returns vertices in reverse post-order, which is a topological order, of vertex v */
  def reversePost(): List[Int] = {
    val reverse = postOrder.reverse
    reverse.toList
  }
}
