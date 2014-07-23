/** @see http://algs4.cs.princeton.edu/51radix/LSD.java.html
  */
package org.gs.sort

/** Sort function sorts an array of strings with the same width
  *
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  */
object LSD {

  /** sort strings with the same width
    *
    * @param a string array
    * @param w string width
    */
  def sort(a: Array[String], w: Int): Unit = {
    val N = a.length
    val R = 256
    val aux = new Array[String](N)

    for (d <- (w - 1) to 0 by -1) {
      val count = new Array[Int](R + 1)

      for (i <- 0 until N) count(a(i).charAt(d).toInt + 1) += 1

      for (r <- 0 until R) count(r + 1) += count(r)

      for (i <- 0 until N) {
        val temp = count(a(i).charAt(d))
        aux(temp) = a(i)
        count(a(i).charAt(d)) += 1
      }

      for (i <- 0 until N) a(i) = aux(i)
    }
  }
}
