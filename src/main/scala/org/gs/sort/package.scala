package org.gs

/** Algorithms for generic sorting and string sorting
  *
  * ==Generic Sorting==
  * {{{
  * val sorted = Merge.msort[String](strings)
  * }}}
  * {{{
  * val sorter = new QuickX[String]
  * val sorted = sorter.sort(strings)
  * }}}
  * ==String Sorting==
  * {{{
  * val strings = managedResource.loan(readFileToArray).toArray
  * val W = strings(0).length
  * LSD.sort(strings, W)
  * }}}
  * {{{
  * val strings = managedResource.loan(readFileToArray).toArray
  * Quick3String.sort(strings)
  * }}}
  * @see http://algs4.cs.princeton.edu/22mergesort/
  * @see http://algs4.cs.princeton.edu/23quicksort/
  * @see http://algs4.cs.princeton.edu/51radix/
  */

package object sort {

}