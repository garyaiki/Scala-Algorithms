/** @see http://algs4.cs.princeton.edu/55compression/Huffman.java.html
  */
package org.gs.compression

import org.gs.queue.MinPQ
import scala.collection.mutable.ArrayBuffer
import scala.annotation.tailrec

/** Huffman compression and expansion of binary data
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  */
class Huffman {
  private val R = 256

  def compress(input: Array[Char]): Unit = {

    def buildTrie(input: Array[Char]): Node = {
      val freq = new Array[Int](R)
      for (i <- 0 until input.length) freq(input(i)) += 1

      val pq = new MinPQ[Node](new ArrayBuffer[Node])
      for {
        i <- 0 until R
        if (freq(i) > 0)
      } pq insert (Node(i.toChar, freq(i), null, null))

      if (pq.size == 1) {
        if (freq('\0') == 0) pq insert (Node('\0', 0, null, null))
        else pq insert (Node('\1', 0, null, null))
      }

      @tailrec
      def mergeSmallestTrees(): Unit = {
        if (pq.size > 1) {
          val left = pq.pop.get
          val right = pq.pop.get
          pq insert (Node('\0', left.freq + right.freq, left, right))
          mergeSmallestTrees()
        }
      }
      mergeSmallestTrees()
      pq.pop.get
    }

    val root = buildTrie(input)

    val st = new Array[String](R)

    def buildCode(x: Node, s: String): Unit = {
      if (!x.isLeaf) {
        buildCode(x.left, s + '0')
        buildCode(x.right, s + '1')
      } else st(x.ch) = s
    }
    buildCode(root, "")

    def writeTrie(x: Node): Unit = {
      if (x.isLeaf) println(s" true ${x.ch} ${x.freq} 8") else {
        println(false)
        writeTrie(x.left)
        writeTrie(x.right)
      }
    }
    writeTrie(root)
    println("encode")
    for {
      i <- 0 until input.length
    } {
      val code = st(input(i))
      for(j <- 0 until code.length) print (code.charAt(j))
    }
  }

}

object Huffman {

  def comp(input: Array[Char]): Unit = {
    val h = new Huffman
    h.compress(input)
  }
  //@TODO
  private def readTrie(): Node = {
    Node('\0', 1, null, null)
  }
  //@TODO
  def expand(): Unit = {
    val root = readTrie
  }
}

/** Huffman trie node
  *
  */
class Node private (val ch: Char, val freq: Int, val left: Node, val right: Node)
  extends Ordered[Node] {

  def isLeaf(): Boolean = {
    require((left == null && right == null) || (left != null && right != null))
    (left == null && right == null)
  }

  def compare(that: Node): Int = freq compareTo (that.freq)

  def canEqual(other: Any): Boolean = other.isInstanceOf[Node]

  override def hashCode(): Int = 41 * (41 + ch) + freq.hashCode

  override def equals(that: Any): Boolean = that match {
    case that: Node => (that canEqual this) && this.hashCode == that.hashCode
  }

}
object Node {
  def apply(ch: Char, freq: Int, left: Node, right: Node): Node = {
    new Node(ch, freq, left, right)
  }
}
