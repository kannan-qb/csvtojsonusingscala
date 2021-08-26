package com.qbrainx.util
import com.qbrainx.config.ApplicationConfig
import com.qbrainx.model.DataModel
import com.qbrainx.util.CsvReader.errorLogs

import java.util
import java.util.stream.Collectors
import scala.util.{Failure, Success, Try}

object CsvModeller {
  def dataExtraction(inputList: util.List[String]):
  util.List[(String, String, String, String, String, String)] = {
    inputList
      .stream()
      .map(line => line.split(","))
      .map(d => Try {
          (d(0), d(1), d(2), d(3), d(4), d(5))
        } match {
          case Success(value) => value
          case Failure(exception) =>
            errorLogs(ApplicationConfig.config.getString("errorpath"),exception.getMessage)
            ("", "", "", "", "", "")
        })
      .collect(Collectors.toList[(String, String, String, String, String, String)])
  }

  def mappingFn(input: util.List[(String, String, String, String, String, String)]):
  util.List[DataModel] = {
    input
      .stream()
      .map(f => {
        Try {
          (f._1.toInt, f._2, f._3, f._4.toInt,f._5,f._6)
        } match {
          case Success(value) => Some(value)
          case Failure(exception) =>
            errorLogs(ApplicationConfig.config.getString("errorpath"),exception.getMessage)
            None
        }
      })
      .map(f => {
        if (f.nonEmpty) {
          val tempvariable: (Int, String, String, Int, String, String) = f.get
          val mobileOption = if (tempvariable._6 != null && tempvariable._6.nonEmpty) Some(tempvariable._6.toLong) else None
          Some(tempvariable._1, tempvariable._2, tempvariable._3, tempvariable._4,tempvariable._5,mobileOption)
        } else {
          errorLogs(ApplicationConfig.config.getString("errorpath"),"None")
          None
        }
      })
      .map(f => {
        if (f.nonEmpty) {
          val tempvariable: (Int, String, String, Int, String, Option[Long]) = f.get
          Some(DataModel(tempvariable._1, tempvariable._2, tempvariable._3, tempvariable._4,tempvariable._5,tempvariable._6))
        } else {
          None
        }
      })
      .filter(f => f.nonEmpty)
      .map(f => f.get)
      .collect(Collectors.toList[DataModel])
  }

}


