package org.gs.queue
/** @author Gary Struthers
 *
 */
import scala.collection.mutable.ArrayBuffer
import scala.math.Ordering._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

trait QueueBuilder {
  val testChars = Array('P', 'H', 'I', 'O', 'E', 'G', 'A', 'R', 'T', 'N')
  val maxPQ = new MaxPQ(new ArrayBuffer[Char](20))
  for {
    c <- testChars
  } maxPQ.insert(c)
  
  val minPQ = new MinPQ(new ArrayBuffer[Char](20))
  for {
    c <- testChars
  } minPQ.insert(c)
}

@RunWith(classOf[JUnitRunner])
class MaxPriorityQueueSuite extends FlatSpec {

  behavior of "a MaxPQ"
  it should "return correct item and resize q" in {
    val pq = new MaxPQ(new ArrayBuffer[Char](0))
    pq.insert('P')
    assert(pq.pop() === Some('P'))
  }

  it should "return item with maximum value" in new QueueBuilder {
    assert(maxPQ.pop() === Some('T'))
  }

  it should "have a max heap" in new QueueBuilder {
    assert(maxPQ.isMaxHeap)
  }

  it should "return keys sorted in decending order" in new QueueBuilder {
    assert(maxPQ.toString === " T R P O N I H G E A")
  }

  it should "return None when empty" in new QueueBuilder {
    val pq = new MaxPQ(new ArrayBuffer[Char](10))
    val r = pq.pop()
    assert(r === None)
  }
 
  behavior of "a MinPQ"

  it should "return correct item and resize q" in {
    val pq = new MinPQ(new ArrayBuffer[Char](0))
    pq.insert('P')
    assert(pq.pop() === Some('P'))
  }

  it should "return the min value " in new QueueBuilder {
    assert(minPQ.pop() === Some('A'))
  }

  it should "have a min heap" in new QueueBuilder {
    assert(minPQ.isMinHeap)
  }

  it should "return keys sorted in ascending order" in new QueueBuilder {
    assert(minPQ.toString === " A E G H I N O P R T")
  }

  it should "return None when empty" in new QueueBuilder {
    val pq = new MinPQ(new ArrayBuffer[Char](10))
    val r = pq.pop()
    assert(r === None)
  }

}