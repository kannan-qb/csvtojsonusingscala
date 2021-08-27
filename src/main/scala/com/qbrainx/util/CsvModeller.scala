package com.qbrainx.util
import com.qbrainx.config.ApplicationConfig
import com.qbrainx.model.DataModel
import com.qbrainx.util.CsvReader.errorLogs
import scala.util.{Failure, Success, Try}

object CsvModeller {
  def dataExtraction(inputList:List[String]):
  List[(String, String, String, String, String, String)] = {
    inputList.iterator
      .map(line => line.split(","))
      .map(d => try {
      (d(0), d(1), d(2),d(3),d(4),d(5))
    } catch {
      case ex: Exception => ex.getMessage
        ("", "", "", "", "", "")
    }).toList
  }

  def mappingFn(input: List[(String, String, String, String, String, String)]):
   List[DataModel] = {
    input
      .iterator
      .map(f => {
        Try {
          Some(f._1.toInt, f._2, f._3, f._4.toInt,f._5,f._6)
        } match {
          case Success(value) => value
          case Failure(exception) =>
            errorLogs(ApplicationConfig.config.getString("errorpath"),exception.getMessage)
            None
        }
      })
      .map(f => {
        if (f.nonEmpty) {
          val tempvariable: (Int, String, String, Int, String, String) = f.get
          val mobileOption = if (tempvariable._6 != null && tempvariable._6.nonEmpty) Some(tempvariable._6) else None
          Some(tempvariable._1, tempvariable._2, tempvariable._3, tempvariable._4,tempvariable._5,mobileOption)
        } else {
          errorLogs(ApplicationConfig.config.getString("errorpath"),"None")
          None
        }
      })
      .map(f => {
        if (f.nonEmpty) {
          val tempvariable: (Int, String, String, Int, String, Option[String]) = f.get
          Some(DataModel(tempvariable._1, tempvariable._2, tempvariable._3, tempvariable._4,tempvariable._5,tempvariable._6))
        } else {
          None
        }
      })
      .filter(f=>f.nonEmpty)
      .map(f => f.get)
      .toList
  }

}


