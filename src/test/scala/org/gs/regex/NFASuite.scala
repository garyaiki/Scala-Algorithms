package org.gs.regex

import org.scalatest.FlatSpec

/** ScalaTest for Nondeterministic Finite Automata
  *
  * @see [[https://algs4.cs.princeton.edu/54regexp/NFA.java.html]]
  *
  * @author Gary Struthers
  */
class NFASuite extends FlatSpec {
  behavior of "a NFA"

  it should "find a pattern in the text" in {
    val nfa = new NFA("(A*B|AC)D")
    val matches = nfa.recognizes("AAAABD")
    assert(matches === true, "pattern not found")
  }

  it should "confirm a pattern in the text doesn't exist" in {
    val nfa = new NFA("(A*B|AC)D")
    val matches = nfa.recognizes("AAAAC")
    assert(matches === false, "pattern should not match")
  }

  it should "find another pattern in different text" in {
    val nfa = new NFA("(a|(bc)*d)*")
    val matches = nfa.recognizes("abcbcd")
    assert(matches === true, "pattern not found")
  }
  
  it should "find another pattern in longer text" in {
    val nfa = new NFA("(a|(bc)*d)*")
    val matches = nfa.recognizes("abcbcbcdaaaabcbcdaaaddd")
    assert(matches === true, "pattern not found")
  }
}
