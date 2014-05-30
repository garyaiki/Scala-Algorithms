package org.gs.symboltable

class BasicSymbolTable[T, U]() {
  def put(key: T, value: U): Unit = ???
  def get(key: T): Option[U] = ???
  def delete(key: T): Unit = {
    put(key, null.asInstanceOf[U])
  }
  def contains(key: T): Boolean = {
    val option = get(key)
    option match {
      case None => false
      case Some(_) => true
    }
  }
  def isEmpty(): Boolean = ???
  def size(): Int = ???
  def keys(): Seq[T] = ???
}
/*
class FrequencyCounter[T, Int](keys: List[T]) {
  val st = new BasicSymbolTable[T, Int]

  private def countKeys(k: T): Unit = {
    val count: Int = st.get(k).getOrElse(0)
    st.put(k, (count + 1))
  }
  keys.foreach(countKeys(_))
  def getMax(): Int = ???
}
*
*/
