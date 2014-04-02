package org.gs.sort
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
//import Quicksort._

object DebugQuicksort {
  def main(args: Array[String]) { println("Quicksort worksheet") }
                                                  //> main: (args: Array[String])Unit

  var input = ArrayBuffer[Char]('K', 'R', 'A', 'T', 'E', 'L', 'E', 'P', 'U', 'I', 'M', 'Q', 'C', 'X', 'O', 'S')
                                                  //> input  : scala.collection.mutable.ArrayBuffer[Char] = ArrayBuffer(K, R, A, T
                                                  //| , E, L, E, P, U, I, M, Q, C, X, O, S)
  var i = 1                                       //> i  : Int = 1
  def exchange(i: Int, j: Int) {
    val iVal = input(j)
    val jVal = input(i)
    input.update(i, iVal)
    input.update(j, jVal)

  }                                               //> exchange: (i: Int, j: Int)Unit
  def insertionSort(): Unit = {
    var i = 1
    def loopI(): Unit = {
      var j = i
      def loopJ(): Unit = {
        if (input(j) >= input(j - 1)) j = 0 else {
          exchange(j, j - 1)
          j = j - 1
        }
        if (j > 0) loopJ
      }
      i = i + 1
      loopJ
      if (i < input.length) loopI
    }
    
    loopI
  }                                               //> insertionSort: ()Unit
  insertionSort()
  println(s"$input")                              //> ArrayBuffer(A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X)
}
/*
  while (i < input.length) {
    var j = i
    def loopJ(): Unit = {
      if (input(j) >= input(j - 1)) j = 0 else {
        exchange(j, j - 1)
        j = j - 1
      }
      if (j > 0) loopJ
    }
    i = i + 1
    loopJ
  }
  println(s"$input")
}

    while (j > 0) {
      if (input(j) < input(j - 1)) {
        exchange(j, j - 1)
        j = j - 1
      } else j = 0
    }
*/