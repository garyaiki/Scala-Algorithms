/**
 * @see http://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
 */
package org.gs.symboltable

/**
 * @author Gary Struthers
 *
 * @param <T>
 * @param <U>
 */
class SeparateChainingHashST[T, U](initialSize: Int) {
  private var m = initialSize
  private var n = 0
  private var st = new Array[List[(T, U)]](m)

  private def hash(key: T): Int = (key.hashCode & 0x7fffffff) % m
  
  private def chainGet(x: (T, U), key: T): Boolean = (x._1 == key)

  def size(): Int = n

  def isEmpty(): Boolean = size == 0

  def get(key: T): Option[U] = {
    val i = hash(key)
    if (st(i) == null) None else {
      val r = st(i).find(chainGet(_, key))
      r match {
        case None => None
        case Some(x) => Some(x._2)
      }
    }
  }

  def contains(key: T): Boolean = get(key) != None

  def delete(key: T): Unit = {
    val i = hash(key)
    val chainList = st(i)
    val j = chainList.indexWhere(chainGet(_, key))
    if (j != -1) st(i) = chainList.take(j) ++ chainList.takeRight(chainList.length - j - 1)
  }

  def put(key: T, value: U): Unit = {
    if (value == null) delete(key) else {
      if (n >= 10 * m) resize(2 * m)
      val i = hash(key)
      if (st(i) == null) {
        st(i) = List((key, value))
        n += 1
      } else {
        st(i) = (key, value) :: st(i)
        if (!st(i).contains(key)) n += 1
      }
    }
  }

  private def resize(chains: Int): Unit = {
    val tmp = new SeparateChainingHashST[T, U](chains)
    for {
      chain <- st
      kv <- chain
    } tmp.put(kv._1, kv._2)
    m = tmp.m
    st = tmp.st
  }

  def keys(): Seq[T] = {
    val q = scala.collection.mutable.Queue[T]()
    for {
      chain <- st
      if(chain != null)
      kv <- chain
    } q.enqueue(kv._1)
    q.toSeq
  }
}
