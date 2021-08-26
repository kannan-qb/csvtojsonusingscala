package com.qbrainx.app

import com.qbrainx.config.ApplicationConfig
import com.qbrainx.model.DataModel
import com.qbrainx.util.{CsvModeller, CsvReader}

import java.util

object  Application extends App{


  val input: Option[util.List[String]] = CsvReader.readFromCsv(ApplicationConfig
                                         .config.getString("inputpath"))
  if (input.nonEmpty){
    val inputdata: util.List[(String, String, String, String, String, String)] =
      CsvModeller.dataExtraction(input.get)
    val mappedData = CsvModeller.mappingFn(inputdata)
    val jsonString: String = DataModel.jsonFormat(mappedData)
    CsvReader.writeTojson(ApplicationConfig.config.getString("outputpath"),jsonString)

  }
}
