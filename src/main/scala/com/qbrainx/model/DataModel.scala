package com.qbrainx.model
import java.util
import java.util.stream.Collectors


case class DataModel (id:Int,name:String,department:String,age:Int,
                      bloodgroup:String,mobile:Option[Long])

object DataModel{
  def datatoJson(datamodel:DataModel):String = {
    val stringrep1 =
      s"""{
         |    "id": ${datamodel.id},
         |    "name": "${datamodel.name}",
         |    "department": "${datamodel.department}",
         |    "age: ${datamodel.age},
         |    "bloodgroup": "${datamodel.bloodgroup}",""".stripMargin
    val stringrep2 = if(datamodel.mobile.nonEmpty) {
      s"""\n    "mobile": ${datamodel.mobile.get}
           |}""".stripMargin
      }else {
         ""
    }
    stringrep1+stringrep2
  }
  def jsonFormat(dataModels:util.List[DataModel]):String  = {
    dataModels.stream()
      .map(student => datatoJson(student))
      .collect(Collectors.joining(",",  "[", "]"))
  }


}
