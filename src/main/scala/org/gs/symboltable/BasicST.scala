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

