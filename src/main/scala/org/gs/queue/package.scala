package org.gs

/** Algorithms for queues, priority queues, and index priority queues
  *
  * ==Queues== 
  * {{{
  * val q = Queue('P', 'Q', 'E')
  * val headOfQ = q.head
  * val q1 = q enqueue 'Z'
  * }}}
  * ==Priority Queues== 
  * {{{
  * val minPQ = new MinPQ(new ArrayBuffer[Char](20))
  * minPQ.insert(c))
  * val min = minPQ.pop()
  * }}}
  * {{{
  * val maxPQ = new MaxPQ(new ArrayBuffer[Char](20))
  * maxPQ.insert(c))
  * val max = maxPQ.pop()
  * }}}
  * ==Index Priority Queues== 
  * {{{
  * val testSize = testStrings.size
  * val minPQ = new IndexMinPQ[String](testSize)
  * for {
  *   i <- 1 until testSize
  * } minPQ.insert(i, testStrings(i))
  * val minKeyIndex = minPQ.minIndex()
  * val minKeyValue = minPQ.minKey()
  * val sortedKeys = minPQ.keys()
  * }}}
  * {{{
  * val testSize = testStrings.size
  * val maxPQ = new IndexMaxPQ[String](testSize)
  * for {
  *   i <- 1 until testSize
  * } maxPQ.insert(i, testStrings(i))
  * val maxKeyIndex = maxPQ.maxIndex()
  * val minKeyValue = maxPQ.maxKey()
  * val sortedKeys = maxPQ.keys()
  * }}}
  * @see http://algs4.cs.princeton.edu/13stacks/
  * @see http://algs4.cs.princeton.edu/24pq/
  */

package object queue {

}