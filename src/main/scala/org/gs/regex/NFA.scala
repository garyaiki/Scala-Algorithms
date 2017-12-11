package org.gs.regex

import org.gs.digraph.{Digraph, DirectedDFS}
import scala.annotation.tailrec
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/** Regex - Non Deterministic Finite Automata
  *
  * Incomplete api, doesn't have all regex operators
  *
  * @constructor creates a new NFA with a regex pattern
  * @param regexp
  * @see [[https://algs4.cs.princeton.edu/54regexp/NFA.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
class NFA(regexp: String) {
  private val M = regexp.length
  private val G = new Digraph(M + 1)

  private def loop(i: Int, ops: List[Int]): Unit = {
    if (i < M) {
      var lp = i
      val charAtI = regexp.charAt(i)
      val ops1 = charAtI match {
        case _ if (charAtI == '(' || charAtI == '|') => i :: ops
        case _ if (charAtI == ')') => {
          val or = ops.head
          var ops2 = ops.tail
          val headChar = regexp.charAt(or)
          headChar match {
            case _ if (headChar == '|') => {
              lp = ops2.head
              ops2 = ops2.tail
              G.addEdge(lp, or + 1)
              G.addEdge(or, i)
            }
            case _ if (headChar == '(') => lp = or
            case _ => assert(headChar == '|' || headChar == '(', s"expected '|' or '(' found:${headChar}")
          } 
          ops2
        }
        case _ => ops
      }

      if (i < M - 1 && regexp.charAt(i + 1) == '*') {
        G.addEdge(lp, i + 1)
        G.addEdge(i + 1, lp)
      }

      if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')') G.addEdge(i, i + 1)

      loop(i + 1, ops1)
    }
  }
  loop(0, List[Int]())

  /** returns if the text has a match for the pattern */
  def recognizes(txt: String): Boolean = {
    val dfs = new DirectedDFS(G, 0)
    var pc = Array.fill[List[Int]](G.numV)(List[Int]())
    for {
      v <- 0 until G.numV
      if (dfs.marked(v))
    } pc(v) = v :: pc(v)

    def add(a: ArrayBuffer[List[Int]])(v: Int): Unit =
      if (a.length <= v) a += List(v) else a(v) = v :: a(v)

    @tailrec
    def computePossibleStates(i: Int): Boolean = if (i < txt.length) {
      val matches = new ArrayBuffer[List[Int]]()
      pc.flatten foreach (v => if (v != M) {
        if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.') add(matches)(v + 1)
      })

      val dfs = new DirectedDFS(G, matches.flatten: _*)
      val pcB = new ArrayBuffer[List[Int]]()
      for {
        v <- 0 until G.numV
        if (dfs.marked(v))
      } add(pcB)(v)
      pc = pcB.toArray
      if (pc.size == 0) false else computePossibleStates(i + 1)
    } else true

    val possibleStates = computePossibleStates(0)
    if (!possibleStates) false else {
      val pcFlat = pc.flatten
      if (pcFlat.contains(M)) true else false
    }
  }
}
