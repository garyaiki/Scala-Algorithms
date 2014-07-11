/**
  * @see http://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
  *
*/
package org.gs.queue

import scala.collection.mutable.ArrayBuffer

/** For max value on Q extends [[PriorityQueue]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @tparam A keys are generic and ordered using [[PriorityQueue.less]]
  * @param pq priority queue array
  *
  */
class MaxPQ[A](pq: ArrayBuffer[A]) extends PriorityQueue(pq) {

  /** insert key ordering with [[PriorityQueue.less]] */
  def insert(key: A)(implicit ord: Ordering[A]): Unit = insert(key, less)

  /** remove max element */
  def pop()(implicit ord: Ordering[A]): Option[A] = pop(less)

  /** check parent and children are in proper indexes */
  def isMaxHeap()(implicit ord: Ordering[A]): Boolean = checkHeap(less)

  /** return keys in decending sorted order */
  def keys()(implicit ord: Ordering[A]): Seq[A] = pq.sorted(Ordering[A].reverse)

  /** return keys as string */
  def toString()(implicit ord: Ordering[A]): String = toString(keys)
}
