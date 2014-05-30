/**
 * @see http://algs4.cs.princeton.edu/51radix/LSD.java.html
 */
package org.gs.sort

/**
 * @author Gary Struthers
 *
 */
object LSD {
  
  def sort(a: Array[String], w: Int): Unit = {
    val N = a.length
    val R = 256
    val aux = new Array[String](N)

    for(d <- (w - 1) to 0 by -1) {
      val count = new Array[Int](R + 1)

      for(i <- 0 until N) {
        count(a(i).charAt(d).toInt + 1) += 1
      }

      for(r <- 0 until R) count(r + 1) += count(r)

      for(i <- 0 until N) {
        val temp = count(a(i).charAt(d))
        aux(temp)  = a(i)
        count(a(i).charAt(d)) += 1
      }

      for(i <- 0 until N) a(i) = aux(i)
    }
  }
}
