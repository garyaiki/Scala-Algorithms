package org.gs.symboltable

import math.Ordering
import scala.annotation.tailrec

class OrderedSymbolTable[T, U](implicit ord: Ordering[T]) {
  class Node[T, U](val key: T, var value: U, var count: Int) {
    var left = null.asInstanceOf[Node[T, U]]
    var right = null.asInstanceOf[Node[T, U]]
    //count is number of nodes in subtree
  }

  var root = null.asInstanceOf[Node[T, U]]
  def less(a: T, b: T)(implicit ord: Ordering[T]): Boolean = ord.lt(a, b)

  def greater(a: T, b: T)(implicit ord: Ordering[T]): Boolean = ord.gt(a, b)

  def put(key: T, value: U): Unit = {
    def loop(x: Node[T, U])(implicit ord: Ordering[T]): Node[T, U] = {
      if (x == null) new Node(key, value, 1) else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) x.value = value
        else {
          if (cmp < 0) x.left = loop(x.left)
          else x.right = loop(x.right)
        }
        x.count = 1 + size(x.left) + size(x.right)
        x
      }
    }
    root = loop(root)
  }
  def get(key: T)(implicit ord: Ordering[T]): Option[U] = {
    @tailrec
    def loop(x: Node[T, U])(implicit ord: Ordering[T]): Option[U] = {
      if (x == null) None else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) Some(x.value)
        else {
          var childNode = null.asInstanceOf[Node[T, U]]
          if (cmp < 0) childNode = x.left
          else childNode = x.right
          loop(childNode)
        }
      }
    }
    loop(root)
  }


  def contains(key: T): Boolean = {
    val option = get(key)
    option match {
      case None => false
      case Some(x) => if (x == null) false else true
    }
  }
  def isEmpty(): Boolean = if (root == null) true else false
  def min(): T = ???
  def max(): T = ???
  def floor(key: T): T = { // largest key less than or equal to key
    def loop(x: Node[T, U])(implicit ord: Ordering[T]): Node[T, U] = {
      if (x == null) null.asInstanceOf[Node[T, U]] else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) x else {
          if (cmp < 0) loop(x.left)
          else {
            val t = loop(x.right)
            if (t != null) t else x // t in right tree, x is subroot
          }
        }
      }
    }
    loop(root).key
  }
  def ceiling(key: T): T = { // smallest key greater than or equal to key
    def loop(x: Node[T, U])(implicit ord: Ordering[T]): Node[T, U] = {
      if (x == null) null.asInstanceOf[Node[T, U]] else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) x else {
          if (cmp < 0) {
            val t = loop(x.left)
            if (t != null) t else x
          }
          else {
            val t = loop(x.right)
            if (t != null) t else x // t in right tree, x is subroot
          }
        }
      }
    }
    loop(root).key
  }

  def rank(key: T): Int = { // number of keys less than key
    def loop(x: Node[T, U])(implicit ord: Ordering[T]): Int = {
      if (x == null) 0 else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) size(x.left) else {
          if (cmp < 0) loop(x.left) else {
            1 + size(x.left) + loop(x.right)
          }
        }
      }
    }
    loop(root)
  }
  def select(rank: Int): T = ??? // key of rank k
  def deleteMin(): Unit = {
    put(min(), null.asInstanceOf[U])
  }
  def deleteMax(): Unit = {
    put(max(), null.asInstanceOf[U])
  }
  private def size(x: Node[T, U]) = if (x == null) 0 else x.count
  def size(): Int = {
    size(root)
  }
  def size(lo: T, hi: T): Int = ??? // number of keys in lo..hi
  import scala.collection.immutable.Queue
  def keys(): Seq[T] = {
    val q = Queue[T]()
    def inorder(x: Node[T, U], q: Queue[T]): Queue[T] = {
      if (x != null) {
        val qL = inorder(x.left, q)
        val qL2 = qL.enqueue(x.key)
        inorder(x.right, qL2)
      } else q
    }
    inorder(root, Queue[T]()).toSeq
  }
  def keys(lo: T, hi: T): Seq[T] = ???

}
/*
class FrequencyCounter[T, Int](keys: List[T]) {
  val st = new BasicSymbolTable[T, Int]

  private def countKeys(k: T): Unit = {
    val count: Int = st.get(k).getOrElse(0)
    st.put(k, (count + 1))
  }
  keys.foreach(countKeys(_))
  def getMax(): Int = ???
}
*
*/
