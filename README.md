# Ahjzli-Application
---------------------
user account ->
email: s@gmail.com
pass: 123456
-----------------------
owner account ->
email: su@gmail.com
pass: 123456
------------------------------
------------------------------
## :scroll:Introduction
The idea of the application is to reserved in the store, such as (restaurant, cafes .. etc) and solve the problem of wasting time in waiting in the queue.
There is two application one for the users and the second one for the store owners.

## :bulb: Motivation and Context
1-Data storage
-Use the firebase to store the data.
1-	Firestore to store all the data except the password
2-	Authorization to store the email and the password.

1.1-Register Page:- 
User Account	Owner Account
User name	Store name
Email	Email 
Phone number	Branch name 
Password	Branch location
	Password

1.2-Sign in  Page:- 
Use the email and the password of Authorization to sign in for both application.

2-Home Page
2.1- For users account
In the home page it will appear the list of the stores that you can reserved on it. 
And toy can search for specific store
And it will show the location of the store.
When you chose the store you can specify a number of people that you want to reserve with you, and that in some conditions.
•	you can't book more than the number of people available.
•	you can't book with 0 number of people.
•	you can't book less than 0.

2.2- For owners account
In the home page it will appear the reservation users list and it will come with this information.
•	User name
•	User phone number(and you can call theme) 
•	Reservation was for how many people
And you as a store owner, you can add customers directly.
How? There is an add button if you clicked on it you can add any customers if they don't have an account.
What you need?
•	Customer name
•	Customer phone number
•	How many people
And you can delete any one in the list.

3-Profile Page
3.1- For users account
Here the information displayed from firestore.
•	User name
•	Email
•	Phone number 
And what can be modified?
•	User name 
•	Phone number(must be 10 numbers)
And form here you can logout.
3.2- For owners account
Here the information displayed from firestore.
•	Store name
•	Email
•	Branch name
•	Branch location
And there tow more are saved by default:-
•	The capacity of the store 
•	And if you want to publish the store or not 
And what can be modified?
•	Store name 
•	Branch name
•	The capacity of the store 
•	And if you want to publish the store or not

4-Reservation Page-user
When you reserved in the store you reservation will appear in this page and you can start the road in google map directly.
And you can cancel the reservation.

5-Settings Page-uesr
Here you can chose the theme(dark or light theme) and the language(English or Arabic).

6- The library
1.	Lottie
2.	Navigation
3.	Firestore
4.	Firebase Auth
5.	Work Manager
6.	Swipe Color
7.	Unit Test
8.	Google map API

## :camera_flash: Screenshots
<img src="https://user-images.githubusercontent.com/91476854/150305797-de846522-a0ae-495b-baf2-9dbf09d83eaf.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150305642-c41267b1-32fc-447c-a9e7-c38548dc0a2b.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150305881-349d6cbd-c7ce-436b-917a-c1a068d6d907.jpg" width="180">
----------------------------------------------------------
<img src="https://user-images.githubusercontent.com/91476854/150306023-7382cc76-f7f3-41c6-8607-0fd550566e7d.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150306163-cb37351a-502e-436f-b009-2e80b31e9008.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150306254-9777a607-8efe-4c20-b23d-42c532203514.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150306372-a7df2afe-8bef-4756-b18a-97d66517cebf.jpg" width="180">


