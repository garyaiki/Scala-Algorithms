/** @see https://github.com/jsuereth/scala-in-depth-source/blob/master/chapter11/src/main/scala/scalax/resource/resource.scala
  */
package org.gs.fixtures

import scala.io.Source
import scala.collection.immutable.TreeMap
import org.gs.digraph.Digraph
import scala.io.BufferedSource
import scala.collection.mutable.ArrayBuffer

/** @author Gary Struthers
  *
  */
trait ManagedResource[T] {
  def loan[U](f: T => U): U
}

trait BufferedSourceBuilder {
  def readURI(uri: String) = new ManagedResource[BufferedSource] {
    /**
     * BufferedSource can only be iterated once
     * @param f
     * @return
     */
    def loan[U](f: BufferedSource => U): U = {
      val bufferdSource = Source.fromURL(uri)
      try {
        f(bufferdSource)
      } finally {
        bufferdSource.close
      }
    }
  }
}

trait StringArrayBuilder extends BufferedSourceBuilder {

  def readFileToArray(buffSource: BufferedSource): Array[String] = {
    val savedLines = new ArrayBuffer[String]()
    val it = buffSource.getLines
    for (a <- it) savedLines.append(a)
    savedLines.toArray
  }

  def buildFromManagedResource(uri: String): Array[String] = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToArray)
  }
}

trait SymbolTableBuilder {

  def buildStringIndex(delimiter: String, savedLines: Array[String]): TreeMap[String, Int] = {
    val buf = new ArrayBuffer[String]()
    for {
      a <- savedLines
      s <- a.split(delimiter)
      if (!buf.contains(s))
    } buf += s

    val kvs = buf.zipWithIndex
    TreeMap[String, Int](kvs: _*)
  }

  def invertIndexKeys(st: TreeMap[String, Int]) = {
    val keys = new Array[String](st.size)
    for (name <- st.keys) {
      val keyOpt = st.get(name)
      keyOpt match {
        case Some(x) => keys(x) = name
        case None =>
      }
    }
    keys
  }
}

trait WordArrayBuilder extends BufferedSourceBuilder {

  def readFileToArray(buffSource: BufferedSource): Array[String] = {
    val savedLines = new ArrayBuffer[String]()
    val it = buffSource.getLines
    val words = for {
      a <- it
      i <- a.split("\\s+")
    } yield i
    words.toArray
  }

  def buildFromManagedResource(uri: String): Array[String] = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToArray)
  }
}

trait IntArrayBuilder extends BufferedSourceBuilder {
  val equals = (_: Int) == (_: Int)
  
  def readFileToArray(buffSource: BufferedSource): ArrayBuffer[Int] = {
    val savedLines = new ArrayBuffer[Int]()
    val it = buffSource.getLines
    for {
      a <- it
      i <- a.split("\\s+")
    } savedLines.append(i.toInt)
    savedLines
  }

  def buildFromManagedResource(uri: String): ArrayBuffer[Int] = {
    val managedResource = readURI(uri)
    managedResource.loan(readFileToArray)
  }
}