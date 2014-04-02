package org.gs.queue

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite


@RunWith(classOf[JUnitRunner])
class QueueSuite extends FunSuite  {
  test("remove") {
    val q = Queue('P', 'Q', 'E')
    val q1 = q enqueue 'Z'
    assert(q.head === 'P')
    assert(q.tail.head === 'Q')
    val qt = q.tail
    assert(qt.head === 'Q')
    val qtt = qt.tail
    assert(qtt.head === 'E')
    val qttf = qtt.tail
    intercept[NoSuchElementException](qttf.head)
    val qz = q1.tail.tail.tail.head
    assert(qz === 'Z')
  }

}