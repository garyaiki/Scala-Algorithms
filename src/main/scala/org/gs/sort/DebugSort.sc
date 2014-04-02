package org.gs.sort
import MergeSortList._
import OrderedMergeSortList._
import scala.runtime.RichChar

object DebugSort {
  def main(args: Array[String]) {println("Welcome to the Scala worksheet")}
                                                  //> main: (args: Array[String])Unit
  msort(( x: Int, y: Int) => x < y)(List(5,7,1,3))//> res0: List[Int] = List(1, 3, 5, 7)
  val s = orderedMergeSort[Char](List('d','s','a','w','k'))
                                                  //> s  : List[Char] = List(a, d, k, s, w)
 s                                                //> res1: List[Char] = List(a, d, k, s, w)
 
}