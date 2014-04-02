package org.gs.queue

object DebugQ {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val q = Queue(1,2,3)                            //> q  : org.gs.queue.Queue[Int] = org.gs.queue.Queue$QueueImpl@502cb49d
  val q1 = q enqueue 4                            //> q1  : org.gs.queue.Queue[Int] = org.gs.queue.Queue$QueueImpl@2705d88a
  q.head                                          //> res0: Int = 1
}