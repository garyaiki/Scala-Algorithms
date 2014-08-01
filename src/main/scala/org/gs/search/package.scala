package org.gs

/** Algorithms for Tries and Substring Search
  *
  * ==Tries== 
  * {{{
  * val tst = new TST[Int]()
  * strings.zipWithIndex.foreach(x => tst.put(x._1, x._2))
  * tst.get(key) match {
  *   case None =>
  *   case Some(x) => println("value" + x)
  * } 
  * }}}
  * ==Substring Search== 
  * {{{
  * val pattern = "abracadabra"
  * val bm = new RabinKarp(pattern)
  * val offset = bm.search(text) 
  * }}}
  * {{{
  * val pattern = "abracadabra".toCharArray
  * val kmp = new KMP(pattern)
  * val text = "abacadabrabracabracadabrabrabracad".toCharArray
  * val offset = kmp.search(text)
  * }}}
  * {{{
  * val pattern = "rabrabracad".toCharArray
  * val bm = new BoyerMoore(pattern)
  * val offset = bm.search(text)
  * }}}
  * @see http://algs4.cs.princeton.edu/52trie/
  * @see http://algs4.cs.princeton.edu/53substring/
  */

package object search {

}