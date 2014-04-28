/**
 * @see http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
 */
package org.gs.symboltable

import math.Ordering
import scala.annotation.tailrec

/**
 * @author Gary Struthers
 *
 * @param <T>
 * @param <U>
 */
class RedBlackSymbolTable[T, U](implicit ord: Ordering[T]) {
  class Node[T, U](var key: T, var value: U, var count: Int = 1, var red: Boolean = true) {
    var left = null.asInstanceOf[Node[T, U]]
    var right = null.asInstanceOf[Node[T, U]]
    //count is number of nodes in subtree
    // red is color of parent link
  }

  var root = null.asInstanceOf[Node[T, U]]
  def less(a: T, b: T)(implicit ord: Ordering[T]) = ord.lt(a, b)
  def greater(a: T, b: T)(implicit ord: Ordering[T]) = ord.gt(a, b)
  def isRed(x: Node[T, U]) = if ((x == null) || (x.red == false)) false else true

  def rotateLeft(h: Node[T, U]) = {
    assert(h != null && isRed(h.right), "error: black or null passed to rotateLeft")
    val x = h.right // x is new root of subtree
    h.right = x.left
    x.left = h
    x.red = x.left.red
    x.left.red = true
    x.count = h.count
    h.count = 1 + size(h.left) + size(h.right)
    x
  }
  def rotateRight(h: Node[T, U]) = {
    assert(h != null && isRed(h.left), "error: black or null passed to rotateRight")
    val x = h.left // x is new root of subtree
    h.left = x.right
    x.right = h
    x.red = x.right.red
    x.right.red = true
    x.count = h.count
    h.count = 1 + size(h.left) + size(h.right)
    x
  }

  def flipColors(h: Node[T, U]) {
    assert(h != null && h.left != null && h.right != null, "null node passed tp flip colors")
    val blackH = !isRed(h) && isRed(h.left) && isRed(h.right)
    val redH = isRed(h) && !isRed(h.left) && !isRed(h.right)
    assert(blackH || redH, "error: flipColors root color must !- both child colors")
    h.red = !h.red
    h.left.red = !h.left.red
    h.right.red = !h.right.red
  }
  def put(key: T, value: U): Unit = {

    def loop(x: Node[T, U])(implicit ord: Ordering[T]): Node[T, U] = {
      if (x == null) new Node(key, value) else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) x.value = value
        else {
          if (cmp < 0) x.left = loop(x.left)
          else x.right = loop(x.right)
        }
        x.count = 1 + size(x.left) + size(x.right)
        var h = x
        if (isRed(x.right) && !isRed(x.left)) h = rotateLeft(x)
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h)
        if (isRed(h.left) && isRed(h.right)) flipColors(h)
        h
      }
    }
    root = loop(root)
    root.red = false
  }
  //@TODO refactor
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

  def delete(key: T): Unit = {
    
      if (!isRed(root.left) && !isRed(root.right)) root.red = true
      def loop(x: Node[T, U], key: T): Node[T, U] = {
        var h = x
        if (ord.compare(key, x.key) < 0) {
          if (!isRed(x.left) && !isRed(x.left.left)) h = moveRedLeft(x)
          h.left = loop(h.left, key)
        } else {
          if (isRed(h.left)) h = rotateRight(x)
          if ((ord.compare(key, h.key) == 0) && (h.right == null)) return null else {
            if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h)
            if (ord.compare(key, h.key) == 0) {
              val y = min(h.right)
              h.key = y.key
              h.value = y.value
              h.right = deleteMin(h.right)
            } else h.right = loop(h.right, key)
          }
        }
        balance(h)
      }
      root = loop(root, key)
      if (!isEmpty) root.red = false
  }

  def moveRedRight(hm: Node[T, U]): Node[T, U] = {
    assert(hm != null, "null passed to moveRedRight")
    assert(isRed(hm) && !isRed(hm.right) && !isRed(hm.right.left), "error: moveRedRight colors")
    flipColors(hm)
    if (!isRed(hm.left.left)) rotateRight(hm) else hm
  }
  def moveRedLeft(hm: Node[T, U]): Node[T, U] = {
    assert(hm != null, "null passed to moveRedLeft")
    assert(isRed(hm) && !isRed(hm.left) && !isRed(hm.left), "error: moveRedLeft colors")

    flipColors(hm)
    if (isRed(hm.right.left)) {
      hm.right = rotateRight(hm.right)
      rotateLeft(hm)
    } else hm
  }
  def balance(h: Node[T, U]): Node[T, U] = {
    assert(h != null, "null passed to balance")

    var x = h
    if (isRed(x.right)) x = rotateLeft(h)

    if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x)
    if (isRed(x.left) && isRed(x.right)) flipColors(x)
    x.count = 1 + size(x.left) + size(x.right)
    x
  }

  def deleteMin(h: Node[T, U]): Node[T, U] = {
    var hm = h

    if (hm.left == null) null.asInstanceOf[Node[T, U]] else {
      if (!isRed(hm.left) && !isRed(hm.left.left))
        hm = moveRedLeft(hm)
      hm.left = deleteMin(hm.left)
      balance(hm)
    }
  }

  def deleteMin() {
    if (!isRed(root.left) && !isRed(root.right)) root.red = true
    root = deleteMin(root)
    if (!isEmpty) root.red = false
  }

  def deleteMax(): Unit = {
    def deleteMax(h: Node[T, U]): Node[T, U] = {
      var hm = h

      if (isRed(hm.left)) hm = rotateRight(h)
      if (hm.right == null) {
        hm = null.asInstanceOf[Node[T, U]]
      } else {
        if (!isRed(hm.right) && !isRed(hm.right.left)) hm = moveRedRight(hm)

        hm.right = deleteMax(hm.right)
        hm = balance(hm)
      }
      hm
    }

    if (!isRed(root.left) && !isRed(root.right)) root.red = true
    root = deleteMax(root)
    if (!isEmpty) root.red = false
  }

  private def size(x: Node[T, U]) = if (x == null) 0 else x.count
  def size(): Int = {
    size(root)
  }
  def size(lo: T, hi: T): Int = { // number of keys in lo..hi
    if (ord.compare(lo, hi) > 0) 0 else if (contains(hi)) rank(hi) - rank(lo) + 1 else
      rank(hi) - rank(lo)
  }

  def contains(key: T): Boolean = {
    val option = get(key)
    option match {
      case None => false
      case Some(x) => if (x == null) false else true
    }
  }
  def isEmpty(): Boolean = if (root == null) true else false
  def min(x: Node[T, U]): Node[T, U] = {
    assert(x != null, "null passed to min")
    if (x.left == null) x else min(x.left)
  }
  def min(): T = {
    min(root).key
  }
  def max(): T = {
    def max(x: Node[T, U]): Node[T, U] = {
      assert(x != null, "null passed to max")
      if (x.right == null) x else max(x.right)
    }
    max(root).key
  }
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
          } else {
            loop(x.right)
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
  def select(rank: Int): Option[T] = { // key of rank k
    def select(x: Node[T, U], k: Int): Node[T, U] = {
      val t = size(x.left)
      if (t > k) select(x.left, k) else {
        if (t < k) select(x.right, k - t - 1) else x
      }
    }
    val node = select(root, rank)
    if (node == null) None else Some(node.key)
  }
  import scala.collection.mutable.Queue
  def keys(): Seq[T] = {
    val q = Queue[T]()
    val lo = min
    val hi = max
    def loop(x: Node[T, U]) {
      if (x != null) {
        val cmpLo = ord.compare(lo, x.key)
        if (cmpLo < 0) loop(x.left)
        val cmpHi = ord.compare(hi, x.key)
        if (cmpLo <= 0 && cmpHi >= 0) q.enqueue(x.key)
        if (cmpHi > 0) loop(x.right)
      }
    }
    loop(root)
    q.toSeq
  }
  /*
 * debugging code
 */
  def inorderTreeWalk(): String = {
    val sb = new StringBuilder
    def loop(x: Node[T, U]) {
      if (x != null) {
        loop(x.left)
        sb.append(s" ${x.key}")
        loop(x.right)
      }
    }
    sb.append(s" root ${root.key} ")
    loop(root)
    sb.toString
  }

  def levelOrderTreeWalk(): String = {
    val sb = new StringBuilder()
    val q = new Queue[Node[T, U]]()
    q.enqueue(root)
    var level = 0
    while (q.size > 0) {
      val n = q.dequeue
      sb.append(f" ${n.key} ${n.red}, ${n.count} \n")
      if (n.left != null) {
        q.enqueue(n.left)
      }
      if (n.right != null) {
        q.enqueue(n.right)
      }
    }
    sb.toString
  }
  override def toString(): String = {//TODO cleanup
    def buildString(): StringBuilder = {
      val sb = new StringBuilder
      val node = root
      val l = node.left
      val r = node.right
      if (root != null) sb.append(s"root ${root.key} red ${root.red} ") else sb.append("root is null ")
      if (l != null) {
        sb.append(s"l ${l.key} red ${l.red} ")
        val ll = l.left
        if (ll != null) {
          sb.append(s"ll ${ll.key} red ${ll.red} ")
          val lll = ll.left
          if (lll != null) {
            sb.append(s"lll ${lll.key} red ${lll.red} ")
            val llll = lll.left
            if (llll != null) {
              sb.append(s"llll ${llll.key} red ${llll.red} ")
            } else sb.append("llll is null ")
            val lllr = lll.right
            if (lllr != null) {
              sb.append(s"lllr ${lllr.key} red ${lllr.red} ")
            } else sb.append("lllr is null ")
          } else sb.append("lll is null ")
          val llr = ll.right
          if (llr != null) {
            sb.append(s"llr ${llr.key} red ${llr.red} ")
          } else sb.append("llr is null ")
        } else sb.append("ll is null ")
        val lr = l.right
        if (lr != null) {
          sb.append(s"lr ${lr.key} red ${lr.red} ")
          val lrl = lr.left
          if (lrl != null) {
            sb.append(s"lrl ${lrl.key} red ${lrl.red} ")
            val lrll = lrl.left
            if (lrll != null) {
              sb.append(s"lrll ${lrll.key} red ${lrll.red} ")
            } else sb.append("lrl is null ")
            val lrlr = lrl.right
            if (lrlr != null) {
              sb.append(s"lrlr ${lrlr.key} red ${lrlr.red} ")
            } else sb.append("lrr is null ")
          } else sb.append("lrl is null ")
          val lrr = lr.right
          if (lrr != null) {
            sb.append(s"lrr ${lrr.key} red ${lrr.red} ")
          } else sb.append("lrr is null ")
        } else sb.append("lr is null ")
      } else sb.append("l is null ")
      if (r != null) {
        sb.append(s"r ${r.key} red ${r.red} ")
        val rl = r.left
        if (rl != null) {
          sb.append(s"rl ${rl.key} red ${rl.red} ")
          val rll = rl.left
          if (rll != null) {
            sb.append(s"rll ${rll.key} red ${rll.red} ")
          } else sb.append("rll is null ")
          val rlr = rl.right
          if (rlr != null) {
            sb.append(s"rlr ${rlr.key} red ${rlr.red} ")
          } else sb.append("rlr is null ")
        } else sb.append("rl is null ")
        val rr = r.right
        if (rr != null) {
          sb.append(s"rr ${rr.key} red ${rr.red} ")
          val rrl = rr.left
          if (rrl != null) {
            sb.append(s"rrl ${rrl.key} red ${rrl.red} ")
          } else sb.append("rrl is null ")
          val rrr = rr.right
          if (rrr != null) {
            sb.append(s"rrr ${rrr.key} red ${rrr.red} ")
          } else sb.append("rrr is null ")
        } else sb.append("rr is null ")
      } else sb.append("r is null ")
      sb
    }
    buildString().toString
  }

  def isBST(): Boolean = {   
    def loop(x: Node[T, U], min: T, max: T): Boolean = {
      if (x == null) true else {
        if (min != null && ord.compare(x.key, min) <= 0) false else if (max != null && ord.compare(x.key, max) >= 0) false else
          loop(x.left, min, x.key) && loop(x.right, x.key, max)
      }
    }
    loop(root, null.asInstanceOf[T], null.asInstanceOf[T])
  }

  def isSizeConsistent(): Boolean = {    
    def loop(x: Node[T, U]): Boolean = {
      if (x == null) true else if (x.count != size(x.left) + size(x.right) + 1) false else
        loop(x.left) && loop(x.right)
    }
    loop(root)
  }

  def isRankConsistent(): Boolean = {
    def checkRank: Boolean = {
      for (i <- 0 until size) {
        select(i) match {
          case None =>
          case Some(x) => if (rank(x) != i) return false
        }
      }
      true
    }
    def checkKeys: Boolean = {
      for (i <- keys) {
        select(rank(i)) match {
          case None =>
          case Some(x) => if (ord.compare(i, x) != 0) return false
        }
      }
      true
    }
    checkRank && checkKeys
  }

  def is23(): Boolean = {
    def loop(x: Node[T, U]): Boolean = {
      if (x == null) true else {
        if (isRed(x.right)) false else if (x != root && isRed(x) && isRed(x.left)) false else
          loop(x.left) && loop(x.right)
      }
    }
    loop(root)
  }

  def isBalanced(): Boolean = {
    var black = 0
    def loop(x: Node[T, U], black: Int): Boolean = {
      var blackVar = black
      if (x == null) black == 0 else {
        if (!isRed(x)) blackVar = black - 1
        loop(x.left, blackVar) && loop(x.right, blackVar)
      }
    }

    var n = root
    while (n != null) {
      if (!isRed(n)) black += 1
      n = n.left
    }
    loop(root, black)
  }
}
