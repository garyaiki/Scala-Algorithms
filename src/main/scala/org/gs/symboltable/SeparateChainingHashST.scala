package org.gs.symboltable

class SeparateChainingHashST[T, U](initialSize: Int) {
  var m = initialSize
  var n = 0
  var st = new Array[List[(T, U)]](m)

  private def hash(key: T) = (key.hashCode & 0x7fffffff) % m
  private def chainGet(x: (T, U), key: T) = (x._1 == key)
  
  def size() = n
  
  def isEmpty() = size == 0

  def get(key: T) = {
    val i = hash(key)
    val r = st(i).find(chainGet(_, key))
    r match {
      case None => None
      case Some(x) => Some(x._2)
    }
  }

  def contains(key: T) = get(key) != None

  def delete(key: T) {
    val i = hash(key)
    val chainList = st(i)
    val j = chainList.indexWhere(chainGet(_, key))
    if (j != -1) st(i) = chainList.take(j) ++ chainList.takeRight(chainList.length - j - 1)
  }

  def put(key: T, value: U) {
    if (value == null) delete(key) else {
      if (n >= 10 * m) resize(2 * m)
      val i = hash(key)
      val r = st(i).find(chainGet(_, key))
      if ( r == None) n = n + 1
      st(i) = (key, value) :: st(i)
    }
  }
  
  def resize(chains: Int) {
    var tmp = new SeparateChainingHashST[T, U](chains)
    for {
      chain <- st
      kv <- chain
    } tmp.put(kv._1, kv._2)
    m = tmp.m
    n = tmp.n
    st = tmp.st
  }
  
  import scala.collection.mutable.Queue
  def keys(): Seq[T] = {
    val q = Queue[T]()
    for {
      chain <- st
      kv <- chain
    } q.enqueue(kv._1)
    q.toSeq
  }
}
object SeparateChainingHashST {

}