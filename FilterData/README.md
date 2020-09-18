This program is basically for filtering data from n number of txt files and output the city and type wise opened area with total capacity based on filter criteria.

Input (n number of txt files with below format)
-------------------------------------------------------------------------------------------------

data1.txt
-------------------------------------------------------------------------------------------------
#,CTY,Types,isOpen,seats <br /> 
1,BNG,school,true,100 <br />
2,HYD,school,true,150 <br />
3,MUM,school,true,200 <br />
4,BNG,restaurant,false,1000 <br />
5,HYD,restaurant,true,1500 <br />
6,MUM,restaurant,false,500 <br />

data2.txt
-------------------------------------------------------------------------------------------------
#,CTY,Types,isOpen,seats <br />
7,BNG,hospital,true,700 <br />
8,HYD,hospital,true,300 <br />
9,MUM,hospital,true,1000 <br />
10,BNG,school,false,200 <br />
11,HYD,restaurant,true,50 <br />
12,MUM,hospital,true,900 <br />

Filter Condition (User input, can be any one of the following types)
-------------------------------------------------------------------------------------------------
BNG:school <br />
HYD:school,restaurant <br />
BNG:school | HYD:school,restaurant <br />

Output
-------------------------------------------------------------------------------------------------
(MUM,hospital,1900,2) <br />
(MUM,school,200,1) <br />
(BNG,hospital,700,1) <br />
(HYD,hospital,300,1) <br />

Key points:
-------------------------------------------------------------------------------------------------
-Header is removed in Output <br />
-First it is filtered with areas that are open (isOpen=true) <br />
-Then based on filter condition, gives output city & type wise total seats. <br />

Problem Statement Credits:
-------------------------------------------------------------------------------------------------
OnlineLearningCenter : https://www.youtube.com/watch?v=QawWKtjtWqg
