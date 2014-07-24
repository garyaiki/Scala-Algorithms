/**
  * @see http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
  *
*/
package org.gs.queue

import scala.collection.mutable.ArrayBuffer
/** For min value on Q extends [[PriorityQueue]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @tparam A keys are generic and ordered using [[PriorityQueue.greater]]
  * @param pq priority queue array
  *
  */
class MinPQ[A](pq: ArrayBuffer[A])(implicit ord: Ordering[A]) extends PriorityQueue(pq) {

  /** insert key ordering with [[PriorityQueue.greater]] */
  def insert(key: A): Unit = insert(key, greater)

  /** remove min element */
  def pop(): Option[A] = pop(greater)

  /** check parent and children are in proper indexes */
  def isMinHeap(): Boolean = checkHeap(greater)

  /** return keys in ascending sorted order */
  def keys(): Seq[A] = pq.sorted[A]

  /** return keys as string */
  override def toString(): String = toString(keys)

}
