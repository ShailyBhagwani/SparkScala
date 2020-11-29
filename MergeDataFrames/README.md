Merge DataFrames with different schema
---------------------------------------------------------------------------------------------------------------------

This program takes input from 2 files having different schema and outputs a file with union of both in an automated and flexible manner. <br/>

Input
---------------------------------------------------------------------------------------------------------------------
file1.txt<br/>
EmployeeID,EmployeeName,Salary<br/>
101,Kriti Kapoor,10000<br/>
102,Shweta Dixit,50000<br/>
205,Rahul Yadav,25000<br/>
711,Aman Dubey,35000<br/>

file2.txt<br/>
EmployeeID,EmployeeName,Department<br/>
101,Kriti Kapoor,Sales<br/>
102,Shweta Dixit,Finance<br/>
205,Rahul Yadav,Marketing<br/>
814,Vishal Arora,Finance<br/>

Output
---------------------------------------------------------------------------------------------------------------------
+----------+------------+------+----------+
|EmployeeID|EmployeeName|Salary|Department|
+----------+------------+------+----------+
|       101|Kriti Kapoor| 10000|      null|
|       102|Shweta Dixit| 50000|      null|
|       205| Rahul Yadav| 25000|      null|
|       711|  Aman Dubey| 35000|      null|
|       101|Kriti Kapoor|  null|     Sales|
|       102|Shweta Dixit|  null|   Finance|
|       205| Rahul Yadav|  null| Marketing|
|       814|Vishal Arora|  null|   Finance|
+----------+------------+------+----------+


Key points:<br/>
-Program is reusable for any kind of schema difference between 2 files.<br/>
