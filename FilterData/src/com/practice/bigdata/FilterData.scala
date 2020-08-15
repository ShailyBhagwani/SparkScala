package com.practice.bigdata

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object FilterData {

  def parseLine(line: String) = {
    val fields = line.split(",")
    val id = fields(0).toInt
    val city = fields(1).toString()
    val types = fields(2).toString()
    val isOpen = fields(3).toBoolean
    val seats = fields(4).toLong
    (id, city, types, isOpen, seats)
  }

  def main(args: Array[String]) {

    println("Enter filter condition: ")
    val inputFilter = scala.io.StdIn.readLine()
    println(inputFilter)
    val eachFilter = inputFilter.split('|')
    //Filter is of the form:
    //BNG:school
    //HYD:school,restaurant
    //BNG:school | HYD:school,restaurant
    
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine, named FilterData
    val sc = new SparkContext("local[*]", "FilterData")

    val lines = sc.textFile("./resources/dataset/*.txt")

    //val reqFields = lines.mapPartitionsWithIndex((i, it) => if (i == 0) it.drop(1) else it)
    val reqFields = lines.filter(x => !x.contains("#")).map(parseLine)
    val isOpenRec = reqFields.filter(x => x._4 == true).map(x => (x._1, x._2, x._3, x._5))
    // isOpenRec.foreach(println)
    var resultRows = isOpenRec

    for (fil <- eachFilter) {
      var filterCity = fil.trim().split(":")(0).trim()
      var filterTypes = fil.trim().split(":")(1).split(",")
      for (typ <- filterTypes) {
        val subRows = isOpenRec.filter(x => (x._2 == filterCity && x._3 == typ.trim()))
        resultRows = resultRows.subtract(subRows)
      }
    }
    //   resultRows.foreach(println)
    // s.groupBy(_._1).mapValues(_.map(_._2).sum)
    // .mapValues(_.map(_ => 1).reduce(_ + _))
    
    //.mapValues(_.map(_._4).sum)
    // val countRes= sortedResult.countByValue()
    //.mapValues(_.size)
    
    val sortedResult = resultRows.groupBy(x => (x._2, x._3)).mapValues(x => (x.map(_._4).sum, x.size))
    val result = sortedResult.map(x => (x._1._1, x._1._2, x._2._1, x._2._2))
    println("Result")
    result.foreach(println)
    result.repartition(1).saveAsTextFile("./resources/result")

  }

}