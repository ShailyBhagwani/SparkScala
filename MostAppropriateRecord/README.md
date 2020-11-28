Finding Most Appropriate Record 
--------------------------------------------------------------------------------------------------------
This program finds out the most appropriate record for each firstName & LastName pair, taking into account the number of occurrence other fields(Address, Gender & PhoneNum) and minimum value of appID amongst them. <br/>
<br/>
Input (n number of txt files with below format)
--------------------------------------------------------------------------------------------------------
file.txt<br/>
appID,firstName,LastName,Address,Gender,PhoneNum<br/>
t1 suraj kh 10oak m 1111<br/>
t2 kushal Kumar 5wren m 2222<br/>
t3 sara chauhan 15nvi f 6666<br/>
t4 suraj kh 10oak f 1111<br/>
t5 Suraj KH 10oak m 1111<br/>
t6 kushal Kumar 5wren f 2222<br/>
t7 kushal Kumar 5wren f 2222<br/>
<br/>
<br/>
Output
--------------------------------------------------------------------------------------------------------
(t1,suraj,kh,10oak,m,1111)<br/>
(t6,kushal,Kumar,5wren,f,2222)<br/>
(t3,sara,chauhan,15nvi,f,6666)<br/>
<br/>
Key points:<br/>
--------------------------------------------------------------------------------------------------------
-Case insensitive (Uppercase names should be considered as same as lowercase)<br/>
-appId must be the min among all relevant appropriate records of each row<br/>
<br/>
Problem statement source: https://www.youtube.com/watch?v=xNByKhPtnL8 <br/>
