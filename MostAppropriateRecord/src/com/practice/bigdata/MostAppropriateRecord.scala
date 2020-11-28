package com.practice.bigdata

import org.apache.spark.SparkContext._
import org.apache.spark._
import org.apache.log4j._

object MostAppropriateRecord {
  
  //case class for the input data types
  case class People(AppId:String,FirstName:String,LastName:String,Address:String,Gender:String,PhoneNum:Integer)
  
   def main(args: Array[String]) {
  
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    val sc = new SparkContext("local[*]","MostAppropriateRecord")
    
    //Reading all the input files and getting it into an rdd
     val fileRDD = sc.textFile("./resources/dataset/*.txt")  
     
     //Parsing the input lines with defined case class so as to map each column to a column name and corresponding data type
     val parsedRDD = fileRDD.map(x => x.split(' ')).map(x => People( x(0), x(1).toLowerCase(), x(2).toLowerCase(), x(3).toLowerCase(), x(4).toLowerCase(), x(5).toInt ))
     
     //Grouping by firsNm and LastNm 
     val nameRDD = parsedRDD.groupBy( f => (f.FirstName,f.LastName)).mapValues(x => (x.map( f => (f.AppId,f.Address,f.Gender,f.PhoneNum))))
     
  
    val resultRDD = nameRDD.map(p =>{
    
        //Again grouping by Address, Gender and PhoneNum and taking the count/size of valueSet occurrences
        val subGrpRDD = p._2.groupBy(f => (f._2,f._3,f._4)).mapValues(f => (f.size,f.map(x => x._1)))
        
        //Comparing with the count occurrence of valueSet (of Address, Gender and PhoneNum) and reducing to take only the highest occurence of row
        val subGrpRDD2 = subGrpRDD.map(x => (x._2._1,x._2._2,x._1)).reduce((x,y) => if(x._1 > y._1) x else y)
    
        //Taking min appId among the resultant rows and formating other fields to form rdd
        val grpRDD =   (subGrpRDD2._2.minBy{(r) => r.substring(1, r.length()).toInt},p._1._1,p._1._2,subGrpRDD2._3._1,subGrpRDD2._3._2,subGrpRDD2._3._3)
        
           (grpRDD._1,grpRDD._2,grpRDD._3,grpRDD._4,grpRDD._5,grpRDD._6)  
        
    } )

    resultRDD.foreach(f => println(f))
    //Writing the output to a file
    resultRDD.repartition(1).saveAsTextFile("./resources/result")
              
   }
}