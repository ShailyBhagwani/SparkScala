This program is basically for filtering data from n number of txt files and output the city and type wise opened area with total capacity based on filter criteria.

Input (n number of txt files with below format)
-------------------------------------------------------------------------------------------------

data1.txt
-------------------------------------------------------------------------------------------------
#,CTY,Types,isOpen,seats 
1,BNG,school,true,100
2,HYD,school,true,150
3,MUM,school,true,200
4,BNG,restaurant,false,1000
5,HYD,restaurant,true,1500
6,MUM,restaurant,false,500

data2.txt
-------------------------------------------------------------------------------------------------
#,CTY,Types,isOpen,seats
7,BNG,hospital,true,700
8,HYD,hospital,true,300
9,MUM,hospital,true,1000
10,BNG,school,false,200
11,HYD,restaurant,true,50
12,MUM,hospital,true,900

Filter Condition (User input, can be any one of the following types)
-------------------------------------------------------------------------------------------------
BNG:school
HYD:school,restaurant
BNG:school | HYD:school,restaurant

Output
-------------------------------------------------------------------------------------------------
(MUM,hospital,1900,2)
(MUM,school,200,1)
(BNG,hospital,700,1)
(HYD,hospital,300,1)

Key points:
-------------------------------------------------------------------------------------------------
-Header is removed in Output
-First it is filtered with areas that are open (isOpen=true)
-Then based on filter condition, gives output city & type wise total seats.
