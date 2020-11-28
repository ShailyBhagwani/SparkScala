This program finds out the most appropriate record for each firstName & LastName pair, taking into account the number of occurrence other fields(Address, Gender & PhoneNum) and minimum value of appID amongst them.

Input (n number of txt files with below format)
file.txt
appID,firstName,LastName,Address,Gender,PhoneNum
t1 suraj kh 10oak m 1111
t2 kushal Kumar 5wren m 2222
t3 sara chauhan 15nvi f 6666
t4 suraj kh 10oak f 1111
t5 Suraj KH 10oak m 1111
t6 kushal Kumar 5wren f 2222
t7 kushal Kumar 5wren f 2222


Output
(t1,suraj,kh,10oak,m,1111)
(t6,kushal,Kumar,5wren,f,2222)
(t3,sara,chauhan,15nvi,f,6666)

Key points:
-Case insensitive (Uppercase names should be considered as same as lowercase)
-appId must be the min among all relevant appropriate records of each row

Problem statement source: https://www.youtube.com/watch?v=xNByKhPtnL8 
