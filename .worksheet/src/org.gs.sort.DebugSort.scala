package org.gs.sort
import MergeSortList._
import OrderedMergeSortList._
import scala.runtime.RichChar

object DebugSort {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(198); 
  def main(args: Array[String]) {println("Welcome to the Scala worksheet")};System.out.println("""main: (args: Array[String])Unit""");$skip(51); val res$0 = 
  msort(( x: Int, y: Int) => x < y)(List(5,7,1,3));System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(60); 
  val s = orderedMergeSort[Char](List('d','s','a','w','k'));System.out.println("""s  : List[Char] = """ + $show(s ));$skip(3); val res$1 = 
 s;System.out.println("""res1: List[Char] = """ + $show(res$1))}
 
}
