/**
 * @see http://algs4.cs.princeton.edu/51radix/Quick3string.java.html
 */
package org.gs.sort

import scala.annotation.tailrec
import scala.math.min
import scala.util.Random
/**
 * @author Gary Struthers
 *
 */
object Quick3String {
  private val Cutoff = 15

  def sort(a: Array[String]) {
    val r = Random.shuffle(a.toSeq).toArray
    sort(a, 0, a.length -1, 0)
  }

  private def sort(s: Array[String], lo: Int, hi: Int, d: Int): Unit = {
    def insertion() {
      
      @tailrec
      def loopI(i: Int): Unit = {
        
        @tailrec
        def loopJ(j: Int): Unit = {
          if (j > lo && less(s(j), s(j - 1))) {
            exch(j, j - 1)
            loopJ(j - 1)
          }
        }
        
        if (i <= hi) {
          loopJ(i)
          loopI(i + 1)
        }
      }
      
      loopI(lo)
    }

    def exch(i: Int, j: Int) {
      val temp = s(i)
      s(i) = s(j)
      s(j) = temp
    }

    def less(v: String, w: String): Boolean = {
      assert(v.substring(0, d).equals(w.substring(0, d)))
      val x = min(v.length, w.length)
      
      @tailrec
      def loop(i: Int): Boolean = {
        if(i < x) {
          if (v.charAt(i) < w.charAt(i)) true
          else if (v.charAt(i) > w.charAt(i)) false
          else loop(i + 1) 
        } else v.length < w.length
      }
      loop(d)
    }

    def charAt(s: String): Int = {
      assert(d >= 0 && d <= s.length)
      if (d == s.length) -1 else s.charAt(d)
    }

    if (hi <= lo + Cutoff) insertion() else {
      val v = charAt(s(lo))
      
      @tailrec
      def loop(lt: Int, gt: Int, i: Int): (Int, Int, Int) = {
        if (i <= gt) {
          val t = charAt(s(i))
          if(t < v) {
            exch(lt, i)
            loop(lt + 1, gt, i + 1)
          } else if(t > v) {
            exch(i, gt)
            loop(lt, gt - 1, i)
          } else loop(lt, gt, i + 1)
        } else (lt, gt, i)
      }
      
      val tuple = loop(lo, hi, lo + 1)
      sort(s, lo, tuple._1, d)
      if(v >= 0) sort(s, tuple._1, tuple._2, d + 1)
      sort(s, tuple._2 + 1, hi, d)
    }
  }
}
