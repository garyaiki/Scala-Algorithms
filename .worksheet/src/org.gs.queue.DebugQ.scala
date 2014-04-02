package org.gs.queue

object DebugQ {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(81); 
  println("Welcome to the Scala worksheet");$skip(23); 
  val q = Queue(1,2,3);System.out.println("""q  : org.gs.queue.Queue[Int] = """ + $show(q ));$skip(23); 
  val q1 = q enqueue 4;System.out.println("""q1  : org.gs.queue.Queue[Int] = """ + $show(q1 ));$skip(9); val res$0 = 
  q.head;System.out.println("""res0: Int = """ + $show(res$0))}
}
