/**
 * 
 * @see http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
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
class MinPQ[A](pq: ArrayBuffer[A]) extends PriorityQueue(pq) {
  
  def insert(key: A)(implicit ord: Ordering[A]): Unit = insert(key, greater)
  
  def pop()(implicit ord: Ordering[A]): Option[A] = pop(greater)

  def isMinHeap()(implicit ord: Ordering[A]): Boolean = checkHeap(greater)

  def keys()(implicit ord: Ordering[A]): Seq[A] = pq.sorted[A]

  def toString()(implicit ord: Ordering[A]): String = toString(keys)
}