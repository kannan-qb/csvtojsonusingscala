package com.qbrainx.util
import com.qbrainx.config.ApplicationConfig
import java.io.PrintWriter
import java.util
import scala.io.Source
import scala.util.{Failure, Success, Try}

object CsvReader {
  def readFromCsv(filePath: String):Option[util.List[String]] = {
    val source = Source.fromFile(filePath)
    val linesList: Iterator[String] = source.getLines()
    val list: util.List[String] = new util.LinkedList[String]()
      while (linesList.hasNext) {
        list.add(linesList.next())
      }
      source.close()
      Try(Some(list))
      match {
      case Success(value) => value
      case Failure(exception) => errorLogs(ApplicationConfig.config
                                 .getString("errorpath"),exception.getMessage)
                                  None
    }
    }
  def errorLogs(path: String, errorMessage: String): Unit = {
    Try(new PrintWriter(path)) match {
      case Success(value) => value.println(errorMessage)
                             value.close()
      case Failure(exception) => exception.getMessage
    }
  }
  def writeTojson(filePath: String,input: String): Unit = {
    Try(new PrintWriter(filePath)) match {
      case Success(value) => value.println(input)
                              value.close()
      case Failure(exception) => exception.getMessage
    }

  }
}
