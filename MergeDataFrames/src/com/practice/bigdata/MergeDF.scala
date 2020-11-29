package com.practice.bigdata

import org.apache.spark._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.SaveMode

object MergeDF {
  
  def main(args: Array[String]) {
    
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    val spark = SparkSession.builder()
                            .appName("MergeDataFrames")
                            .master("local[*]")
                            .getOrCreate()
                            
     //read file1 which has schema of EmployeeID,EmployeeName,Salary                       
     var df1 = spark.read.option("header",true).csv("./resources/dataset/file1.txt")
     //read file2 which has schema of EmployeeID,EmployeeName,Department
     var df2 = spark.read.option("header",true).csv("./resources/dataset/file2.txt")
     
     //get the columns which are present in df1 but not in df2
     val missingColInDF2 = df1.columns.diff(df2.columns)
     //get the columns which are present in df2 but not in df1
     val missingColInDF1 = df2.columns.diff(df1.columns)
     
     //add all the missing columns in df2 as obtained in previous step
     for(col <- missingColInDF2)
     df2 = df2.withColumn(col.toString(), lit(null))
     
     //add all the missing columns in df2 as obtained in previous step
     for(col <- missingColInDF1)
     df1 = df1.withColumn(col.toString(), lit(null))
     
     //take union of both the dataframes as now both contains same columns
    val resultDF = df1.unionByName(df2)
    resultDF.show()
    
    //write the output to a csv file
    resultDF.coalesce(1).write.mode(SaveMode.Overwrite).csv("./resources/result")
    
    
  }
  
}