package org.gs.symboltable

import scala.annotation.tailrec

class LinearProbingHashST[T, U](initialSize: Int) {
  var m = initialSize
  var n = 0
  var st = new Array[(T, U)](m)

  private def hash(key: T) = (key.hashCode & 0x7fffffff) % m
  private def chainGet(x: (T, U), key: T) = (x._1 == key)

  def size() = n

  def isEmpty() = size == 0

  def get(key: T): Option[U] = {
    val i = hash(key)
    @tailrec
    def loop(j: Int): Option[(T, U)] = {
      if (st(j) == null) None else if (key.equals(st(j)._1)) Some(st(j)) else
        loop((j + 1) % m)
    }
    loop(i) match {
      case None => None
      case Some(x) => Some(x._2)
    }
  }

  def contains(key: T) = get(key) != None

  def delete(key: T) {
    @tailrec
    def find(j: Int): Int = {
      if (key.equals(st(j)._1)) j else
        find(j + 1 % m)
    }
    @tailrec
    def rehash(k: Int): Unit = {
      val kv = st(k)
      if (kv != null) {
        st(k) = null.asInstanceOf[(T, U)]
        n = n - 1
        put(kv._1, kv._2)
        rehash((k + 1) % m)
      }
    }
    if (contains(key)) {
      val i = hash(key)
      val j = find(i)
      st(j) = null.asInstanceOf[(T, U)]
      rehash(j)
      n = n - 1
      def halveSizeIfEigthFull() = if (n > 0 && n <= m / 8) resize(m / 2)
      halveSizeIfEigthFull
    }
  }

  def put(key: T, value: U) {
    if (value == null) delete(key) else {
      def doubleSizeIfHalfFull() = if (n >=m / 2) {
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
      n = n + 1
    }
  }

  def resize(capacity: Int) {
    var tmp = new LinearProbingHashST[T, U](capacity)
    for {
      kv <- st
      if (kv != null)
    } tmp.put(kv._1, kv._2)
    st = tmp.st
    m = capacity
  }

  import scala.collection.mutable.Queue
  def keys(): Seq[T] = {
    val q = Queue[T]()
    for {
      kv <- st
      if (kv != null)
    } q.enqueue(kv._1)
    q.toSeq
  }
  
  // debug methods
  def isLessThanHalfFull():Boolean = (m < 2 * n)
  
  def allKeysCanBeFound():Boolean = {
    val q = Queue[T]()
    for {
      kv <- st
      if(get(kv._1) == null)
    } q.enqueue(kv._1)
    if (q.length > 0) false else true
  }
}
object LinearProbingHashST {

}