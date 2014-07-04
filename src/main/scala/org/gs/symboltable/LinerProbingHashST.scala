/**
 * @see http://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html
 */
package org.gs.symboltable

import scala.annotation.tailrec

/**
 * Hash key to index value, when there is a collision try succeeding indexes insert in empty one
 * @author Gary Struthers
 *
 * @param <A> generic key type
 * @param <B> generic value type
 * @param initialSize of array
 */
class LinearProbingHashST[A, B](initialSize: Int) {
  private var m = initialSize
  private var n = 0
  private var st = new Array[(A, B)](m)

  /** turn hash into array index */
  private def hash(key: A) = (key.hashCode & 0x7fffffff) % m

  private def chainGet(x: (A, B), key: A) = (x._1 == key)

  /** number of key value pairs */
  def size(): Int = n

  /** @return true if zero pairs */
  def isEmpty(): Boolean = size == 0

  /** get value for key if it is present */
  def get(key: A): Option[B] = {
    val i = hash(key)
    
    @tailrec
    def loop(j: Int): Option[(A, B)] = {
      if (st(j) == null) None else if (key.equals(st(j)._1)) Some(st(j)) else
        loop((j + 1) % m)
    }
    loop(i) match {
      case None => None
      case Some(x) => Some(x._2)
    }
  }

  /** @return true if key present */
  def contains(key: A): Boolean = get(key) != None

  /** delete pair for key if it is present */
  def delete(key: A) {
    @tailrec
    def find(j: Int): Int = {
      if (key.equals(st(j)._1)) j else
        find(j + 1 % m)
    }

    @tailrec
    def rehash(k: Int): Unit = {
      val kv = st(k)
      if (kv != null) {
        st(k) = null.asInstanceOf[(A, B)]
        n -= 1
        put(kv._1, kv._2)
        rehash((k + 1) % m)
      }
    }

    if (contains(key)) {
      val i = hash(key)
      val j = find(i)
      st(j) = null.asInstanceOf[(A, B)]
      rehash(j)
      n -= 1
      def halveSizeIfEigthFull(): Unit = if (n > 0 && n <= m / 8) resize(m / 2)
      halveSizeIfEigthFull
    }
  }

  /** insert key value pair, double size if half full */
  def put(key: A, value: B) {
    if (value == null) delete(key) else {
      def doubleSizeIfHalfFull(): Unit = if (n >= m / 2) {
        resize(m * 2)
      }
      doubleSizeIfHalfFull

      def loop(j: Int): Unit = {
        if (st(j) != null) {
          if (key.equals(st(j)._1)) st(j) = (key, value) else
            loop((j + 1) % m)
        } else st(j) = (key, value)
      }
      loop(hash(key))
      n += 1
    }
  }

  private def resize(capacity: Int) {
    val tmp = new LinearProbingHashST[A, B](capacity)
    for {
      kv <- st
      if (kv != null)
    } tmp.put(kv._1, kv._2)
    st = tmp.st
    m = capacity
  }

  import scala.collection.mutable.Queue
  /**
   * @return keys in a list
   */
  def keys(): List[A] = {
    val q = Queue[A]()
    for {
      kv <- st
      if (kv != null)
    } q.enqueue(kv._1)
    q.toList
  }

  // debug methods
  def isLessThanHalfFull(): Boolean = (m < 2 * n)

  def allKeysCanBeFound(): Boolean = {
    val q = Queue[A]()
    for {
      kv <- st
      if (get(kv._1) == null)
    } q.enqueue(kv._1)
    if (q.length > 0) false else true
  }
}
