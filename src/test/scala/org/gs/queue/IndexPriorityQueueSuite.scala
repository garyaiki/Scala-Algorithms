package org.gs.queue
/**
 * @author Gary Struthers
 *
 */
import scala.collection.mutable.ArrayBuffer
import scala.math.Ordering._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import scala.reflect.ClassTag

trait IndexQueueBuilder {

  val testStrings =
    Array[String](null, "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst")
  val testSize = testStrings.size
  val minPQ = new IndexMinPQ[String](testSize)
  for {
    i <- 1 until testSize
  } minPQ.insert(i, testStrings(i))
  
  val minSorted = Vector("best", "it", "it", "the", "of", "times", "the", "was", "was", "worst")
  val maxPQ = new IndexMaxPQ[String](testSize)
  for {
    i <- 1 until testSize
  } maxPQ.insert(i, testStrings(i))
  
  val maxSorted = Vector("worst", "was", "times", "the", "was", "the", "it", "best", "of", "it")
}
@RunWith(classOf[JUnitRunner])
class IndexMaxPriorityQueueSuite extends FlatSpec {
  behavior of "a IndexMinPQ"

  it should "push 1 item and pop it" in {
    val testChar = Array[Char]('P')
    val pq = new IndexMinPQ[Char](testChar.size + 1)
    pq.insert(1, 'P')
    assert(pq.minKey === 'P')
  }

  it should "return the index of the min key " in new IndexQueueBuilder {
    assert(minPQ.minIndex === 4)
  }
  
  it should "return the min value " in new IndexQueueBuilder {
    assert(minPQ.minKey === testStrings.drop(1).min)
  }
    
  it should "delete the minium key and return the new min key" in new IndexQueueBuilder {
    minPQ.delMin
    assert(minPQ.minKey === "it")
  }

  
  it should "return keys sorted in ascending order" in new IndexQueueBuilder {
    assert(minPQ.keys === minSorted)
  }

  it should "return a string of keys sorted in ascending order" in new IndexQueueBuilder {
    assert(minPQ.toString === minSorted.mkString(" "))
  }

  it should "change an indexed key to a new one" in new IndexQueueBuilder {
    minPQ.changeKey(2, "Hodor")
    assert("Hodor" === minPQ.keyOf(2))
  }

  it should "decrease indexed key and replace the index with a new key" in new IndexQueueBuilder {
    minPQ.decreaseKey(4, "Hodor")
    assert("Hodor" === minPQ.keyOf(4))
  }
  
  it should "increase an indexed key and replace the index with a new key" in new IndexQueueBuilder {
    minPQ.increaseKey(4, "of")
    assert("of" === minPQ.keyOf(5))
  }
  
    
  it should "delete an indexed key" in new IndexQueueBuilder {
    val before = minPQ.keyOf(6)
    minPQ.delete(6)
    intercept[IllegalArgumentException](minPQ.keyOf(6))
  }
  
  behavior of "a IndexMaxPQ"
  it should "push 1 item and pop it" in {
    val testChar = Array[Char]('P')
    val pq = new IndexMaxPQ[Char](testChar.size + 1)
    pq.insert(1, 'P')
    assert(pq.maxKey === 'P')
  }

  it should "return the index of the max key " in new IndexQueueBuilder {
    assert(maxPQ.maxIndex === 10)
  }
  
  it should "return the max value " in new IndexQueueBuilder {
    assert(maxPQ.maxKey === testStrings.drop(1).max)
  }
    
  it should "delete the maxium key and return the new max key" in new IndexQueueBuilder {
    maxPQ.delMax
    assert(maxPQ.maxKey === "was")
  }

  
  it should "return keys sorted in decending order" in new IndexQueueBuilder {
    assert(maxPQ.keys === maxSorted)
  }

  it should "return a string of keys sorted in ascending order" in new IndexQueueBuilder {
    assert(maxPQ.toString === maxSorted.mkString(" "))
  }

  it should "change an indexed key to a new one" in new IndexQueueBuilder {
    maxPQ.changeKey(2, "Hodor")
    assert("Hodor" === maxPQ.keyOf(2))
  }

  it should "decrease an indexed key and replace the index with a new key" in new IndexQueueBuilder {
    maxPQ.decreaseKey(4, "Hodor")
    assert("Hodor" === maxPQ.keyOf(4))
  }
  
  it should "increase an indexed key and replace the index with a new key" in new IndexQueueBuilder {
    maxPQ.increaseKey(4, "of")
    assert("of" === maxPQ.keyOf(5))
  }
  
    
  it should "delete an indexed key" in new IndexQueueBuilder {
    val before = maxPQ.keyOf(6)
    maxPQ.delete(6)
    intercept[IllegalArgumentException](maxPQ.keyOf(6))
  }

}