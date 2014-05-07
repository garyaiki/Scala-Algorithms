package org.gs.queue
/***
 * @see http://algs4.cs.princeton.edu/24pq/IndexMaxPQ.java.html
 */
import scala.reflect.ClassTag



/**
 * @author Gary Struthers
 *
 * @param <A> keys are generic and ordered
 * @param nMax maximum number of elements
 */
class IndexMaxPQ[A: ClassTag](nMax: Int) extends IndexPriorityQueue[A](nMax) {
  
  def insert(i: Int, key: A)(implicit ord: Ordering[A]) { insert(i, key, less) }

  def maxIndex() = index()

  def maxKey() = topKey

  def delMax()(implicit ord: Ordering[A]): Int = delTop(less)

  def changeKey(i: Int, key: A)(implicit ord: Ordering[A]): Unit = { changeKey(i, key, less) }

  def decreaseKey(i: Int, key: A)(implicit ord: Ordering[A]): Unit = { decreaseKey(i, key, less) }

  def increaseKey(i: Int, key: A)(implicit ord: Ordering[A]): Unit = { increaseKey(i, key, less) }

  def delete(i: Int)(implicit ord: Ordering[A]): Unit = { delete(i, less) }

  def isMinHeap()(implicit ord: Ordering[A]): Boolean = checkHeap(less)

  def keys()(implicit ord: Ordering[A]): Seq[A] = getKeys

}