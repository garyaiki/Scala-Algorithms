package org.gs.sort
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
//import Quicksort._

object DebugQuicksort {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(201); 
  def main(args: Array[String]) { println("Quicksort worksheet") };System.out.println("""main: (args: Array[String])Unit""");$skip(113); 

  var input = ArrayBuffer[Char]('K', 'R', 'A', 'T', 'E', 'L', 'E', 'P', 'U', 'I', 'M', 'Q', 'C', 'X', 'O', 'S');System.out.println("""input  : scala.collection.mutable.ArrayBuffer[Char] = """ + $show(input ));$skip(12); 
  var i = 1;System.out.println("""i  : Int = """ + $show(i ));$skip(138); 
  def exchange(i: Int, j: Int) {
    val iVal = input(j)
    val jVal = input(i)
    input.update(i, iVal)
    input.update(j, jVal)

  };System.out.println("""exchange: (i: Int, j: Int)Unit""");$skip(346); 
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
  };System.out.println("""insertionSort: ()Unit""");$skip(18); 
  insertionSort();$skip(21); 
  println(s"$input")}
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
