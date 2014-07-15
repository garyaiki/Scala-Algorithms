/** @see http://algs4.cs.princeton.edu/54regexp/NFA.java.html
  */
package org.gs.regex

import scala.collection.mutable.ListBuffer
import org.gs.digraph.Digraph
import scala.collection.mutable.ArrayBuffer
import org.gs.digraph.DirectedDFS
import scala.annotation.tailrec

/** Regex - Non Deterministic Finite Automata
  *
  * Doesn't several regex operators
  * 
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  * @constructor creates a new NFA with a regex pattern
  */
class NFA(regexp: String) {
  private val M = regexp.length
  private var ops = List[Int]()
  private val G = new Digraph(M + 1)

  for (i <- 0 until M) {
    var lp = i
    if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|') ops = i :: ops
    else if (regexp.charAt(i) == ')') {
      val or = ops.head
      ops = ops.tail
      
      if (regexp.charAt(or) == '|') {
        lp = ops.head
        ops = ops.tail
        G.addEdge(lp, or + 1)
        G.addEdge(or, i)
      } else if (regexp.charAt(or) == '(') lp = or else
            assert(regexp.charAt(or) == '|' || regexp.charAt(or) == '(',
                s"expected '|' or '(' found:${regexp.charAt(or)}")
    }

    if (i < M - 1 && regexp.charAt(i + 1) == '*') {
      G.addEdge(lp, i + 1)
      G.addEdge(i + 1, lp)
    }

    if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
      G.addEdge(i, i + 1)
  }

  /** returns if the text has a match for the pattern */
  def recognizes(txt: String): Boolean = {
    val dfs = new DirectedDFS(G, 0)
    var pc = Array.fill[List[Int]](G.V)(List[Int]())
    for {
      v <- 0 until G.V
      if (dfs.marked(v))
    } pc(v)  = v :: pc(v)

    def add(a: ArrayBuffer[List[Int]])(v: Int): Unit = {
      if (a.length <= v) a += List(v) else a(v) = v :: a(v)
    }

    @tailrec
    def computePossibleStates(i: Int): Boolean = {
      if (i < txt.length) {
        val matches = new ArrayBuffer[List[Int]]()
        for {
          v <- pc.flatten
          if (v != M)
        } if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.') add(matches)(v + 1)

        val dfs = new DirectedDFS(G, matches.flatten: _*)
        val pcB = new ArrayBuffer[List[Int]]()
        for {
          v <- 0 until G.V
          if (dfs.marked(v))
        } add(pcB)(v)
        pc = pcB.toArray
        if (pc.size == 0) false else computePossibleStates(i + 1)
      } else true
    }

    val possibleStates = computePossibleStates(0)
    if (!possibleStates) false else {
      val pcFlat = pc.flatten
      if (pcFlat.contains(M)) true else false
    }
  }
}
