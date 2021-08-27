package com.qbrainx.model

import java.util.stream.Collectors
import scala.jdk.CollectionConverters._


case class DataModel (id:Int,name:String,department:String,age:Int,
                      bloodgroup:String,mobile:Option[String])

object DataModel{
  def datatoJson(datamodel:DataModel):String = {
    val stringrep1 =
      s"""{
         |    "id": ${datamodel.id},
         |    "name": "${datamodel.name}",
         |    "department": "${datamodel.department}",
         |    "age: ${datamodel.age},
         |    "bloodgroup": "${datamodel.bloodgroup}",""".stripMargin
    val stringrep2 =
      s"""\n    "mobile": ${datamodel.mobile.get}
           |}""".stripMargin


    val stringrep3 = s"""{
                        |    "id": ${datamodel.id},
                        |    "name": "${datamodel.name}",
                        |    "department": "${datamodel.department}",
                        |    "age: ${datamodel.age},
                        |    "bloodgroup": "${datamodel.bloodgroup}"
                        |}""".stripMargin
   if(datamodel.mobile.nonEmpty){
     stringrep1+stringrep2
   } else{
     stringrep3
   }

  }
  def jsonFormat(dataModels:List[DataModel]):String  = {
    val jsonform: String = dataModels.asJava.stream()
                         .map(data => datatoJson(data))
                          .collect(Collectors.joining(",", "[", "]"))
    jsonform



  }
}
