package org.gs.queue

/** Immutable interface for hidden Queue implementation
  *
	* This copies a example from Programming in Scala instead of translating from Algorthms
  * I added an isEmpty method
  * @tparam A elements are generic
  * @tparam A
  * @see [[https://booksites.artima.com/programming_in_scala_2ed/examples/type-parameterization/Queues5.scala]]
  * @see [[https://algs4.cs.princeton.edu/13stacks/Queue.java.html]]
  * @author Scala translation by Gary Struthers from Java by Robert Sedgewick and Kevin Wayne.
  */
trait Queue[A] {
  def head: A
  def tail: Queue[A]
  def enqueue(x: A): Queue[A]
  def isEmpty(): Boolean
}

object Queue {

/** @param xs a variable number of objects of type A
  * @return an initialized Queue companion object
  * @see QueueSuite for usage
  */
def apply[A](xs: A*): Queue[A] = new QueueImpl[A](xs.toList, Nil)

  /** To Enqueue in constant time prepend to the trailing list
   *
   * To Dequeue in constant time take the head of the leading list. If leading is empty, reverse
   * trailing and assign it to head. The cost of reversing is amortized. After taking head, tail is
   * the remaining Queue
   *
   * @tparam A elements are generic
   */
  private class QueueImpl[A] (
    val leading: List[A],
    val trailing: List[A]) extends Queue[A] {

    private def mirror() = if (leading.isEmpty) new QueueImpl(trailing.reverse, Nil) else this

    def head(): A = mirror.leading.head

    def tail(): QueueImpl[A] = {
      val q = mirror
      new QueueImpl(q.leading.tail, q.trailing)
    }

    def enqueue(x: A): Queue[A] = new QueueImpl(leading, x :: trailing)

    def isEmpty(): Boolean = leading.isEmpty && trailing.isEmpty
  }
}
