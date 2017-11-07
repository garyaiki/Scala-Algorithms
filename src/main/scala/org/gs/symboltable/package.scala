package org.gs

/** Algorithms for symbol tables
  *
  * ==Symbol Tables==
  * {{{
  * val ost = new RedBlackBST[Char, Int]()(Ordering.Char)
  * val item = testInput(0)
  * ost.put(item._1, item._2)
  * val value = ost.get('K')
  * }}}
  * {{{
  * val ost = new SeparateChainingHashST[Char, Int](50)
  * val item = testInput(0)
  * ost.put(item._1, item._2)
  * val value = ost.get('K')
  * }}}
  * {{{
  * val ost = new LinearProbingHashST[Char, Int](50)
  * val item = testInput(0)
  * ost.put(item._1, item._2)
  * val value = ost.get('K')
  * }}}
  * @see https://algs4.cs.princeton.edu/33balanced/
  * @see https://algs4.cs.princeton.edu/34hash/
  */

package object symboltable {}
