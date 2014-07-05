/**
 * @see http://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
 */
package org.gs.queue
import scala.reflect.ClassTag

/**
 * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
 *
 * @param <A> keys are generic and ordered
 * @param nMax maximum number of elements
 */
class IndexMinPQ[A: ClassTag](nMax: Int) extends IndexPriorityQueue[A](nMax) {

  def insert(i: Int, key: A)(implicit ord: Ordering[A]): Unit = { insert(i, key, greater) }

  def minIndex(): Int = index()

  def minKey(): A = topKey

  def delMin()(implicit ord: Ordering[A]): Int = delTop(greater)

  def changeKey(i: Int, key: A)(implicit ord: Ordering[A]): Unit = { changeKey(i, key, greater) }

  def decreaseKey(i: Int, key: A)(implicit ord: Ordering[A]):Unit = { decreaseKey(i, key, greater) }

  def increaseKey(i: Int, key: A)(implicit ord: Ordering[A]):Unit = { increaseKey(i, key, greater) }

  def delete(i: Int)(implicit ord: Ordering[A]): Unit = { delete(i, greater) }

  def isMinHeap()(implicit ord: Ordering[A]): Boolean = checkHeap(greater)

  def keys()(implicit ord: Ordering[A]): Seq[A] = getKeys
}
