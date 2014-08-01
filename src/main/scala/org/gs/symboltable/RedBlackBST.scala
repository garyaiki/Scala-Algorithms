/** @see http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
  */
package org.gs.symboltable

import math.Ordering
import scala.annotation.tailrec

/** Red Black Node
  *
  * @tparam A generic key
  * @tparam B generic value
  * @param count number of subtrees
  * @param red true if link to parent is red false if black
  */
sealed class Node[A, B](var key: A, var value: B, var count: Int = 1, var red: Boolean = true) {
  var left = null.asInstanceOf[Node[A, B]]
  var right = null.asInstanceOf[Node[A, B]]
}

/** Balanced search tree with Red/Black nodes
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @tparam A generic key type
  * @tparam B generic value type
  * @param ord implicit Ordering
  */
class RedBlackBST[A, B](implicit ord: Ordering[A]) {

  private var root = null.asInstanceOf[Node[A, B]]

  private def isRed(x: Node[A, B]): Boolean = if ((x == null) || (x.red == false)) false else true

  /** Make h.right the new root of subtree
    *
    * if h.right is red rotate left so h becomes the left child and h.right becomes the parent
    */
  private def rotateLeft(h: Node[A, B]): Node[A, B] = {
    assert(h != null && isRed(h.right), "error: black or null passed to rotateLeft")
    val x = h.right
    h.right = x.left
    x.left = h
    x.red = x.left.red
    x.left.red = true
    x.count = h.count
    h.count = 1 + size(h.left) + size(h.right)
    x
  }

  /** Make h.left the new root of subtree
    *
    * if h.left is red rotate right so h becomes the right child and h.left becomes the parent
    */
  private def rotateRight(h: Node[A, B]): Node[A, B] = {
    assert(h != null && isRed(h.left), "error: black or null passed to rotateRight")
    val x = h.left
    h.left = x.right
    x.right = h
    x.red = x.right.red
    x.right.red = true
    x.count = h.count
    h.count = 1 + size(h.left) + size(h.right)
    x
  }

  /** if both children are red make them black and h red */
  private def flipColors(h: Node[A, B]): Unit = {
    assert(h != null && h.left != null && h.right != null, "null node passed to flip colors")
    assert(!isRed(h) && isRed(h.left) && isRed(h.right) ||
      isRed(h) && !isRed(h.left) && !isRed(h.right),
      "error: flipColors root color must not equal both child colors")
    h.red = !h.red
    h.left.red = !h.left.red
    h.right.red = !h.right.red
  }

  /** insert key value into tree
    *
    * overwrite if key already there
    */
  def put(key: A, value: B): Unit = {

    def loop(x: Node[A, B]): Node[A, B] = {
      if (x == null) new Node(key, value) else {
        ord.compare(key, x.key) match {
          case 0 => x.value = value
          case n if (n < 0) => x.left = loop(x.left)
          case _ => x.right = loop(x.right)
        }
        x.count += 1
        if (isRed(x.right) && !isRed(x.left)) rotateLeft(x) else {
          val j = if (isRed(x.left) && isRed(x.left.left)) rotateRight(x) else x
          if (isRed(j.left) && isRed(j.right)) {
            flipColors(j)
          }
          j
        }
      }
    }

    root = loop(root)
    root.red = false
  }

  /** get value for key if present */
  def get(key: A): Option[B] = {

    @tailrec
    def loop(x: Node[A, B]): Option[B] = if (x == null) None else {
      ord.compare(key, x.key) match {
        case 0 => Some(x.value)
        case n if (n < 0) => loop(x.left)
        case _ => loop(x.right)
      }
    }
    loop(root)
  }

  /** delete the key */
  def delete(key: A): Unit = {

    if (!isRed(root.left) && !isRed(root.right)) {
      root.red = true
    }

    def loop(x: Node[A, B], key: A): Node[A, B] = {

      val h = ord.compare(key, x.key) match {
        case n if (n < 0) => {
          val j = if (!isRed(x.left) && !isRed(x.left.left)) moveRedLeft(x) else x

          j.left = loop(j.left, key)
          j
        }
        case _ => {
          val j = if (isRed(x.left)) rotateRight(x) else x

          ord.compare(key, j.key) match {
            case 0 if (j.right == null) => return null
            case _ => {
              val k = if (!isRed(j.right) && !isRed(j.right.left)) moveRedRight(j) else j

              if (ord.compare(key, k.key) == 0) {
                val y = min(k.right)
                k.key = y.key
                k.value = y.value
                k.right = deleteMin(k.right)
              } else {
                k.right = loop(k.right, key)
              }
              k
            }
          }

        }
      }
      balance(h)
    }

    root = loop(root, key)
    if (!isEmpty) {
      root.red = false
    }
  }

  private def moveRedRight(h: Node[A, B]): Node[A, B] = {
    assert(h != null, "null passed to moveRedRight")
    assert(isRed(h) && !isRed(h.right) && !isRed(h.right.left), "error: moveRedRight colors")
    flipColors(h)
    if (!isRed(h.left.left)) rotateRight(h) else h
  }

  private def moveRedLeft(h: Node[A, B]): Node[A, B] = {
    assert(h != null, "null passed to moveRedLeft")
    assert(isRed(h) && !isRed(h.left) && !isRed(h.left), "error: moveRedLeft colors")

    flipColors(h)
    if (isRed(h.right.left)) {
      h.right = rotateRight(h.right)
      rotateLeft(h)
    } else h
  }

  private def balance(h: Node[A, B]): Node[A, B] = {
    assert(h != null, "null passed to balance")

    val x = if (isRed(h.right)) rotateLeft(h) else h
    val y = if (isRed(x.left) && isRed(x.left.left)) rotateRight(x) else x
    if (isRed(y.left) && isRed(y.right)) {
      flipColors(y)
    }
    y.count = 1 + size(y.left) + size(y.right)
    y
  }

  private def deleteMin(h: Node[A, B]): Node[A, B] = {
    if (h.left == null) null else {
      val j = if (!isRed(h.left) && !isRed(h.left.left)) moveRedLeft(h) else h
      j.left = deleteMin(j.left)
      balance(j)
    }
  }

  /** delete minimum key */
  def deleteMin(): Unit = {
    if (!isRed(root.left) && !isRed(root.right)) {
      root.red = true
    }
    root = deleteMin(root)
    if (!isEmpty) {
      root.red = false
    }
  }

  /** delete maximum key */
  def deleteMax(): Unit = {

    def deleteMax(h: Node[A, B]): Node[A, B] = {
      val j = if (isRed(h.left)) rotateRight(h) else h
      if (j.right == null) null.asInstanceOf[Node[A, B]] else {
        val m = if (!isRed(j.right) && !isRed(j.right.left)) moveRedRight(j) else j
        m.right = deleteMax(m.right)
        balance(m)
      }
    }

    if (!isRed(root.left) && !isRed(root.right)) {
      root.red = true
    }
    root = deleteMax(root)
    if (!isEmpty) {
      root.red = false
    }
  }

  private def size(x: Node[A, B]): Int = if (x == null) 0 else x.count

  /** subtree count */
  def size(): Int = size(root)

  /** number of keys in lo..hi */
  def size(lo: A, hi: A): Int = {
    if (ord.compare(lo, hi) > 0) 0 else
      if (contains(hi)) rank(hi) - rank(lo) + 1 else {
      rank(hi) - rank(lo)
    }
  }

  /** is key present */
  def contains(key: A): Boolean = get(key) match {
    case None => false
    case Some(x) => if (x == null) false else true
  }

  /** are any keys in tree */
  def isEmpty(): Boolean = if (root == null) true else false

  @tailrec
  private def min(x: Node[A, B]): Node[A, B] = {
    assert(x != null, "null passed to min")
    if (x.left == null) x else min(x.left)
  }

  /** returns smallest key */
  def min(): A = min(root).key

  /** returns largest key */
  def max(): A = {

    @tailrec
    def max(x: Node[A, B]): Node[A, B] = {
      assert(x != null, "null passed to max")
      if (x.right == null) x else max(x.right)
    }
    max(root).key
  }

  /** returns largest key less than or equal to key */
  def floor(key: A): A = {

    def loop(x: Node[A, B]): Node[A, B] = {
      if (x == null) null else ord.compare(key, x.key) match {
          case 0 => x
          case n if (n < 0) => loop(x.left)
          case _ => {
            val t = loop(x.right)
            if (t != null) t else x // t in right tree, x is subroot
          }
      }
    }
    loop(root).key
  }

  /** returns smallest key greater than or equal to key */
  def ceiling(key: A): A = {

    def loop(x: Node[A, B]): Node[A, B] = if (x == null) null.asInstanceOf[Node[A, B]] else {
        val cmp = ord.compare(key, x.key)
        if (cmp == 0) x else {
          if (cmp < 0) {
            val t = loop(x.left)
            if (t != null) t else x
          } else loop(x.right)
        }
    }
    loop(root).key
  }

  /** returns number of keys less than key */
  def rank(key: A): Int = {

    def loop(x: Node[A, B]): Int = if (x == null) 0
    else {
      val cmp = ord.compare(key, x.key)
      if (cmp == 0) size(x.left)
      else {
        if (cmp < 0) loop(x.left)
        else 1 + size(x.left) + loop(x.right)
      }
    }
    loop(root)
  }

  /** returns key of rank k */
  def select(rank: Int): Option[A] = {

    def select(x: Node[A, B], k: Int): Node[A, B] = {
      val t = size(x.left)
      if (t > k) select(x.left, k)
      else if (t < k) select(x.right, k - t - 1) else x
    }
    val node = select(root, rank)
    if (node == null) None else Some(node.key)
  }

  import scala.collection.mutable.Queue
  /** returns all keys */
  def keys(): List[A] = {
    val q = Queue[A]()
    val lo = min()
    val hi = max()

    def loop(x: Node[A, B]) {
      if (x != null) {
        val cmpLo = ord.compare(lo, x.key)
        if (cmpLo < 0) {
          loop(x.left)
        }
        val cmpHi = ord.compare(hi, x.key)
        if (cmpLo <= 0 && cmpHi >= 0) {
          q.enqueue(x.key)
        }
        if (cmpHi > 0) {
          loop(x.right)
        }
      }
    }
    loop(root)
    q.toList
  }

  /** debugging code  */

  /** print nodes left to right
    * @param full all node arguments if true just key if false
    * @return tree nodes as string
    */
  def inorderTreeWalk(full: Boolean = false): String = {
    val sb = new StringBuilder

    def loop(x: Node[A, B]) {
      if (x != null) {
        loop(x.left)
        if (full) sb append (s" key:${x.key} value:${x.value} count:${x.count} red:${x.red}")
        else sb append (s" ${x.key}")
        loop(x.right)
      }
    }
    sb append (s" root ${root.key} ")
    loop(root)
    sb.toString()
  }

  /** returns string of nodes from top down by level */
  def levelOrderTreeWalk(): String = {
    val sb = new StringBuilder()
    val q = new Queue[Node[A, B]]()
    q.enqueue(root)

    @tailrec
    def loop(): Unit = {
      if (!q.isEmpty) {
        val n = q.dequeue
        sb append (f" ${n.key} ${n.red}, ${n.count} \n")
        if (n.left != null) {
          q.enqueue(n.left)
        }
        if (n.right != null) {
          q.enqueue(n.right)
        }
        loop()
      }
    }

    loop()
    sb.toString()
  }

  /** returns all node args left to right as string */
  override def toString(): String = inorderTreeWalk(true)

  /** does this satisfy requirements for balanced search tree */
  def isBST(): Boolean = {

    def loop(x: Node[A, B], min: A, max: A): Boolean = {
      if (x == null) true else {
        if (min != null && ord.compare(x.key, min) <= 0) false
        else if (max != null && ord.compare(x.key, max) >= 0) false else {
          loop(x.left, min, x.key) && loop(x.right, x.key, max)
        }
      }
    }
    loop(root, null.asInstanceOf[A], null.asInstanceOf[A])
  }

  /** are field sizes correct */
  def isSizeConsistent(): Boolean = {

    def loop(x: Node[A, B]): Boolean = if (x == null) true
      else if (x.count != size(x.left) + size(x.right) + 1) false
      else {
        loop(x.left) && loop(x.right)
    }

    loop(root)
  }

  def isRankConsistent(): Boolean = {

    def checkRank: Boolean = {

      @tailrec
      def loop(i: Int): Boolean = {
        if (i == size) true else {
          val goodRank = select(i) match {
            case None => true
            case Some(x) if (rank(x) != i) => false
            case Some(x) => true
          }
          if (goodRank) loop(i + 1) else false
        }
      }

      loop(0)
    }

    def checkKeys: Boolean = {

      @tailrec
      def loop(keys: List[A]): Boolean = keys match {
        case Nil => true
        case x :: xs => {
          val goodKey = select(rank(x)) match {
            case None => true
            case Some(k) if (ord.compare(x, k) != 0) => false
            case Some(k) => true
          }
          if (goodKey) loop(xs) else false
        }
      }

      loop(keys)
    }
    checkRank && checkKeys
  }

  /** are red and black links correct */
  def is23(): Boolean = {

    def loop(x: Node[A, B]): Boolean = if (x == null) true
    else {
      if (isRed(x.right)) false
      else if (x != root && isRed(x) && isRed(x.left)) false
      else loop(x.left) && loop(x.right)
    }

    loop(root)
  }

  /** do all paths from root have same number of black links */
  def isBalanced(): Boolean = {

    @tailrec
    def loopRB(n: Node[A, B], black: Int): Int = {
      if (n == null) black
      else {
        if (!isRed(n)) loopRB(n.left, black + 1)
        else loopRB(n.left, black)
      }
    }

    val black = loopRB(root, 0)

    def loop(x: Node[A, B], black: Int): Boolean = {
      if (x == null) black == 0 else {
        if (!isRed(x)) loop(x.left, black - 1) && loop(x.right, black - 1)
        else loop(x.left, black) && loop(x.right, black)
      }
    }

    loop(root, black)
  }
}
