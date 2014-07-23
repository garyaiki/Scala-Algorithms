package org.gs.queue
/** This copies a example from Programming in Scala instead of translating from Algorthms
  * I added an isEmpty method
  * @see http://booksites.artima.com/programming_in_scala_2ed/examples/type-parameterization/Queues5.scala
  * @see http://algs4.cs.princeton.edu/13stacks/Queue.java.html
  */

/** Immutable interface for hidden Queue implementation
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  *
  * @param <T>
  */
trait Queue[T] {
  def head: T
  def tail: Queue[T]
  def enqueue(x: T): Queue[T]
  def isEmpty(): Boolean
}

object Queue {

/** @param xs a variable number of objects of type T
  * @return an initialized Queue companion object
  * @see QueueSuite for usage
  */
def apply[T](xs: T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)
  /*
   * To Enqueue in constant time prepend to the trailing list
   * To Dequeue in constant time take the head of the leading list. If leading is empty, reverse
   * trailing and assign it to head. The cost of reversing is amortized. After taking head, tail is
   * the remaining Queue
   */
  private class QueueImpl[T] (
    val leading: List[T],
    val trailing: List[T]) extends Queue[T] {

    private def mirror = if (leading.isEmpty) new QueueImpl(trailing.reverse, Nil) else this

    def head: T = mirror.leading.head

    def tail: QueueImpl[T] = {
      val q = mirror
      new QueueImpl(q.leading.tail, q.trailing)
    }

    def enqueue(x: T): Queue[T] = new QueueImpl(leading, x :: trailing)
    
    def isEmpty(): Boolean = leading.isEmpty && trailing.isEmpty
  }
}
