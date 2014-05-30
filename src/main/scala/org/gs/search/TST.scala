/**
 * @see http://algs4.cs.princeton.edu/52trie/TST.java.html
 */
package org.gs.search

import scala.annotation.tailrec
import scala.collection.mutable.Queue

/**
 * @author Gary Struthers
 *
 */
class TST[A] {
  private var N = 0
  private var root: Option[Node] = None

  private class Node(val c: Char) {
    var left: Option[Node] = None
    var mid: Option[Node] = None
    var right: Option[Node] = None
    var value: Option[A] = None
  }

  def size(): Int = N

  @tailrec
  private def get(x: Option[Node], key: String, d: Int): Option[Node] = {
    require(key != null && !key.isEmpty, s"key:$key is null or empty")
    x match {
      case None => None
      case Some(x) => {
        val c = key.charAt(d)
        if (c < x.c) get(x.left, key, d)
        else if (c > x.c) get(x.right, key, d)
        else if (d < key.length -1) get(x.mid, key, d + 1)
        else Some(x)
      }
    }
  }

  def get(key: String): Option[A] = {
    require(key != null && !key.isEmpty, s"key:$key is null or empty")

    get(root, key, 0) match {
      case None => None
      case Some(x) => x.value
    }
  }

  def contains(key: String): Boolean = {
    get(key) match {
      case None => false
      case Some(x) => true
    }
  }

  def put(s: String, value: A): Unit = {
    if (!contains(s)) N = N + 1

    def put(x: Option[Node], d: Int): Option[Node] = {
      val c = s.charAt(d)
      val y = x match {
        case None => new Node(c)
        case Some(y) => y
      }
      if (c < y.c) y.left = put(y.left, d)
      else if (c > y.c) y.right = put(y.right, d)
      else if (d < s.length - 1) y.mid = put(y.mid, d + 1)
      else y.value = Some(value)
      Some(y)
    }
    root = put(root, 0)
  }

  def longestPrefixOf(s: String): Option[String] = {
    def loop(length: Int, x: Option[Node], i: Int): Int = {
      x match {
        case None => length
        case Some(y) => if (s == null || s.isEmpty) length else {
          val c = s.charAt(i)
          if (c < y.c) loop(length, y.left, i)
          else if (c > y.c) loop(length, y.right, i)
          else {
            y.value match {
              case None => loop(i + 1, y.mid, i + 1)
              case Some(v) => loop(length, y.mid, i + 1)
            }
          }
        }
      }
    }
    if (s == null || s.isEmpty) None else {
      val len = loop(0, root, 0)
      if (len == 0) None else
        Some(s.substring(0, len))
    }
  }

  def keys(): Seq[String] = {
    val q = new Queue[String]
    collect(root, "", q)
    q.toSeq
  }

  private def collect(x: Option[Node], prefix: String, q: Queue[String]): Unit = {
    def loop(x: Option[Node], prefix: String): Unit = {
      x match {
        case None =>
        case Some(y) => {
          loop(y.left, prefix)
          y.value match {
            case Some(v) => q.enqueue(prefix + y.c)
            case None =>
          }
          loop(y.mid, prefix + y.c)
          loop(y.right, prefix)
        }
      }
    }
    loop(x, prefix)
  }

  def prefixMatch(prefix: String): Seq[String] = {
    val q = new Queue[String]
    val x = get(root, prefix, 0)
    x match {
      case None => q.toSeq
      case Some(y) => {
        y.value match {
          case None =>
          case Some(v) => q.enqueue(prefix)
        }
        collect(y.mid, prefix + y.c, q)
        collect(y.right, prefix, q)
        q.toSeq
      }
    }
  }

  def wildcardMatch(pat: String): Seq[String] = {
    val q = new Queue[String]
    def collect(x: Option[Node], prefix: String, i: Int): Unit = {
      x match {
        case None =>
        case Some(y) => {
          val c = pat.charAt(i)
          if(c == '.' || c < y.c) collect(y.left, prefix, i)
          if(c == '.' || c == y.c) {
            if(i == pat.length - 1 && y.value == None) q.enqueue(prefix + y.c)
            if(i < pat.length - 1) collect(y.mid, prefix + y.c, i + 1)
          }
          if(c == '.' || c > y.c) collect(y.right, prefix, i)
        }
      }
    }
    collect(root, "", 0)
    q.toSeq
  }
}
