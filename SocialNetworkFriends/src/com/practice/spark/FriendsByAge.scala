package com.practice.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

/** Compute the average number of friends a particular age group people has in a social network. */
object FriendsByAge {

  //Extracting only required data for our computation
  def parseLine(line: String) = {
      val fields = line.split(",")
      // Extract the age and numFriends fields, and convert to integers
      val age = fields(2).toInt
      val numFriends = fields(3).toInt

      (age, numFriends)
  }

  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "FriendsByAge")

    // Reading file
    val lines = sc.textFile("./resources/fakefriends.csv")

    // Use our parseLines function to convert to (age, numFriends) tuples
    val rdd = lines.map(parseLine)


    // Starting with an RDD of form (age, numFriends) as (key,value) pair
    // Using mapValues to convert each numFriends value to a tuple of (numFriends, 1)
    // Then we use reduceByKey to sum up the total numFriends and total instances for each age, by
    // adding together all the numFriends values and 1's respectively.
    val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey( (x,y) => (x._1 + y._1, x._2 + y._2))

    // So now totalsByAge is tuple of (age, (totalFriends, totalInstances))
    // To compute the average we divide totalFriends / totalInstances for each age.
    val averagesByAge = totalsByAge.mapValues(x => x._1 / x._2)

    // Collect the results from the RDD (This kicks off computing the DAG and actually executes the job)
    val results = averagesByAge.collect()

    // Printing result
    results.sorted.foreach(println)
  }

}
