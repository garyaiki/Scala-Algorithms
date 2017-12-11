package org.gs.queue

import scala.reflect.ClassTag

/** Max priority queue with index
  *
  * @constructor creates a new IndexMaxPQ with maximum number of elements
  * @tparam A keys are generic and ordered
  * @param nMax maximum number of elements
  * @param ord implicit ordering
  * @see [[https://algs4.cs.princeton.edu/24pq/IndexMaxPQ.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class IndexMaxPQ[A: ClassTag](nMax: Int)(implicit ord: Ordering[A]) extends IndexPriorityQueue[A](nMax) {

  /** Add key to end of array then swim up to ordered position
    *
    * @param i index where key will be inserted if not already there
    * @param key generic element
    */
  def insert(i: Int, key: A): Unit = insert(i, key, less)

  /** returns index associated with max key */
  def maxIndex(): Int = index

  /** returns max key */
  def maxKey(): A = topKey

  /** returns max key and removes it from queue */
  def delMax(): Int = delTop(less)

  /** Change key at index to new value, because it can be > or < current, it both swims and sinks
    *
    * @param i index
    * @param key value
    */
  def changeKey(i: Int, key: A): Unit = changeKey(i, key, less)

  /** Decrease key at index to new value, because it is < current, it swims
    *
    * @param i index
    * @param key value
    */
  def decreaseKey(i: Int, key: A): Unit = decreaseKey(i, key, less)

  /** Increase key at index to new value, because it is > current, it sinks
    *
    * @param i index
    * @param key value
    */
  def increaseKey(i: Int, key: A): Unit = increaseKey(i, key, less)

  /** Remove key at index
    *
    * @param i index
    */
  def delete(i: Int): Unit = delete(i, less)

  /** check parent in position has left child at k * 2, right child at k * 2 + 1 */
  def isMinHeap(): Boolean = checkHeap(less)

  /** returns keys in order */
  def keys(): Seq[A] = getKeys
}
