package com.qbrainx.app

import com.qbrainx.config.ApplicationConfig
import com.qbrainx.model.DataModel
import com.qbrainx.util.{CsvModeller, CsvReader}



object  Application extends App{
  val input: Option[List[String]] = CsvReader.readFromCsv(ApplicationConfig
                                         .config.getString("inputpath"))
  if (input.nonEmpty){
    val inputdata: List[(String, String, String, String, String, String)] = {
      CsvModeller.dataExtraction(input.get)
    }
    println(inputdata)

    val mappedData: List[DataModel] = CsvModeller.mappingFn(inputdata)
    val jsonString: String = DataModel.jsonFormat(mappedData)
    CsvReader.writeTojson(ApplicationConfig.config.getString("outputpath"),jsonString)

  }
}
