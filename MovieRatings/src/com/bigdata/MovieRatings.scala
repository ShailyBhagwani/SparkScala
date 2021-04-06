package com.bigdata

import org.apache.log4j._
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/* Find top movies which satisfy below 2 criteria:
 * 1. Atleast 1000 people should have rated that movie
 * 2. Average rating > 4.5  
 * using both RDD and DF/DS approach
 * */

object MovieRatings extends App{
  
  Logger.getLogger("org").setLevel(Level.ERROR)
  
  //This is solved using 2 approaches : 
  //1. RDD / Low Level Constructs approach
  val sc = new SparkContext("local[*]","Movie Ratings")
  
  //Read movies dataset which contains MovieId::Movie Name::Genre
  val movieInput = sc.textFile("./resources/movies.dat")
  
  //Read ratings dataset which contains userId::MovieId::Rating::Timestamp
  val ratingsInput = sc.textFile("./resources/ratings.dat")
  
  //(movieId,movieNm)
  val moviesNm = movieInput.map(x => {
    val fields = x.split("::")
    (fields(0),fields(1))
  })
  
  //(movieId,(rating,1))  : 1 for calculating average (total/no. of ratings)
  val userRatings = ratingsInput.map(x => {
    val fields = x.split("::")
    (fields(1),(fields(2).toFloat,1))
  })
  
  //for each movie, summing up ratings and no. of times it has been rated
  val ratingPerUser = userRatings.reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))

  //Filtering the no. of times it has been rated should be greater than 1000 since it is one of our criteria.
  val filter1000rated = ratingPerUser.filter(x => x._2._2 >= 1000)
 
  //Calculating average by dividing (total rating/no. of times)
  val averageRating = filter1000rated.mapValues(f => f._1/f._2)
  
  //Filtering movies having rating greater than 4.5 as our usecase
  val goodRatedMovies = averageRating.filter(f => f._2 > 4.5)
  
  //Joining the filtered movies with movies Name rdd since we are interested to know movies name rather than movies Id
  val joinedRDD = goodRatedMovies.join(moviesNm)
  
  //it is of the form (movieId,(averageRating,movieName))
  //mapping to get only movie Name
  val resultRDD = joinedRDD.map(f => (f._2._2))
  
  //Collecting resultant rdd and printing it
  resultRDD.collect().foreach(println)
  
  
  //####################################################################
  //2. DF / High Level Constructs Approach
  
  //creating spark session
  val spark = SparkSession.builder()
                          .appName("MovieRatings")
                          .master("local[*]")
                          .getOrCreate()
                          
  //Case classes for movies and ratings file                        
  case class MoviesSchema(movieID:Long,movieNm:String,genre:String)
  case class RatingsSchema(userID:Long,movieID:Long,rating:Int,timestamp:String)
  
  import spark.implicits._
  
  //Utilising rdds from previous step to convert to DF and applying schema from case class
  val moviesDF = movieInput.map(x => x.split("::")).map(x => MoviesSchema(x(0).toLong,x(1),x(2)) ).toDF()
  val ratingsDF = ratingsInput.map(x => x.split("::")).map(x => RatingsSchema(x(0).toLong,x(1).toLong,x(2).toInt,x(3))).toDF()
  
  //Calculating average rating and number of ratings for each movie
  val averageRatingsDF = ratingsDF.groupBy("movieID")
                                  .agg(count("rating").as("movieRatingsCount"),avg("rating").as("averageRating"))
 
  //applying ratings filter as per our use case
  val filterDF = averageRatingsDF.filter(expr("cast(movieRatingsCount as long) >= 1000 AND cast(averageRating as float) > 4.5"))
  
  //Joining with moviesDF to get the movie name
  val joinedDF = filterDF.join(moviesDF,Seq("movieID","movieID"),"INNER")
                         .orderBy(desc("averageRating"))
                         .select("movieNm")
             
  //Printing output                       
  joinedDF.show(false)
  
  //Results from both the approach are same as expected.
}