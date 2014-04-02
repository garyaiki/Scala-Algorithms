package org.gs.queue

trait Queue[T] {
	def head: T
	def tail: Queue[T]
	def enqueue(x:T): Queue[T]
}

object Queue {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(184); 

	def apply[T](xs:T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)
		
	 class QueueImpl[T] (
		private var leading: List[T],
		private var trailing: List[T]
	) extends Queue[T] {
	
		def mirror =
			if (leading.isEmpty) new QueueImpl(trailing.reverse, Nil)
			else this
			
		def head: T = {
			val q = mirror
			q.leading.head
		}
		
		def tail: QueueImpl[T] = {
			val q = mirror
			new QueueImpl(q.leading.tail, q.trailing)
		}
		def enqueue(x:T) =
			new QueueImpl(leading, x :: trailing)
	};System.out.println("""apply: [T](xs: T*)org.gs.queue.Queue[T]""");$skip(509); 
  
 
  def main(args:Array[String]) = println("Welcome to the Scala worksheet");System.out.println("""main: (args: Array[String])Unit""");$skip(22); 
  
    val qu = Queue;System.out.println("""qu  : org.gs.queue.Queue.type = """ + $show(qu ))}
  
}
