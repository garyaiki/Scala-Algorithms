/**
 * 
 * @see http://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
 * 
*/
package org.gs.queue

import scala.collection.mutable.ArrayBuffer

/**
 *
 * @author Gary Struthers
 *
 * @param <A> keys are generic and ordered
 * @param pq priority queue array
 * 
 */
class MaxPQ[A](pq: ArrayBuffer[A]) extends PriorityQueue(pq) {
  
  def insert(key: A)(implicit ord: Ordering[A]): Unit = insert(key, less)

  def pop()(implicit ord: Ordering[A]): Option[A] = pop(less)

  def isMaxHeap()(implicit ord: Ordering[A]): Boolean = checkHeap(less)

  def keys()(implicit ord: Ordering[A]): Seq[A] = pq.sorted(Ordering[A].reverse)

  def toString()(implicit ord: Ordering[A]): String = toString(keys)
}