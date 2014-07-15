/** ScalaTest, JUnit tests for Nondeterministic Finite Automata
  * @see http://algs4.cs.princeton.edu/54regexp/NFA.java.html
  */
package org.gs.regex

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/** @author Gary Struthers
  *
  */
@RunWith(classOf[JUnitRunner])
class NFASuite extends FlatSpec {
  behavior of "a NFA"

  it should "find first pattern in the text" in {
    val nfa = new NFA("(A*B|AC)D")
    val matches = nfa.recognizes("AAAABD")
    assert(matches === true, "pattern not found")
  }

  it should "find not find first pattern in the text" in {
    val nfa = new NFA("(A*B|AC)D")
    val matches = nfa.recognizes("AAAAC")
    assert(matches === false, "pattern false match")
  }

  it should "find second pattern in the text" in {
    val nfa = new NFA("(a|(bc)*d)*")
    val matches = nfa.recognizes("abcbcd")
    assert(matches === true, "pattern not found")
  }
  
  it should "find second pattern in longer text" in {
    val nfa = new NFA("(a|(bc)*d)*")
    val matches = nfa.recognizes("abcbcbcdaaaabcbcdaaaddd")
    assert(matches === true, "pattern not found")
  }
}