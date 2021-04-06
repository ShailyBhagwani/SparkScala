#This program is solved using both RDD and DF approach
It solves below use case:
 Find top movies which satisfy below 2 criteria:
  1. Atleast 1000 people should have rated that movie
  2. Average rating > 4.5  


 Input is 2 files.
 One for movies containing MovieId::Movie Name::Genre
 Other for ratings containing userId::MovieId::Rating::Timestamp

 Output is list of movies names satisfying the criteria.
