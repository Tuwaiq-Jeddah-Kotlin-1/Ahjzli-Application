# Ahjzli-Application
<img src="https://user-images.githubusercontent.com/91476854/150669781-0493e8a9-9038-43ca-a74f-bfb73df35f1e.gif" width="180">

## :scroll:Introduction
The idea of the application is to reserved in the store, such as (restaurant, cafes .. etc) and solve the problem of wasting time in waiting in the queue.
There is two application one for the users and the second one for the store owners.

## :bulb: Motivation and Context
1-Data storage

-Use the firebase to store the data.

•Firestore to store all the data except the password

•Authorization to store the email and the password.


1.1-Register Page:- 

User Account=>	/User name	/Email	/Phone number	/Password	
	
Owner Account=>/Store name  /Email  /Branch name   /Branch location    /Password

------------------------------


1.2-Sign in  Page:- 

Use the email and the password of Authorization to sign in for both application.

------------------------------


2-Home Page
2.1- For users account

In the home page it will appear the list of the stores that you can reserved on it.

And you can search for specific store.

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

What do you need?

•	Customer name

•	Customer phone number

•	How many people

And you can delete any one in the list.

------------------------------


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

------------------------------

4-Reservation Page-user

When you reserved in the store you reservation will appear in this page and you can start the road in google map directly.

And you can cancel the reservation.

5-Settings Page-uesr

Here you can chose the theme(dark or light theme) and the language(English or Arabic).

6- The technology

Used design patterns: MVC architectural pattern

libraries:

1.	Lottie
2.	Navigation
3.	Firestore
4.	Firebase Auth
5.	Work Manager
6.	Swipe Color
7.	Unit Test
8.	Google map API

7-Demo
---------------------
user account ->
email: s@gmail.com
pass: 123456

## :camera_flash: Screenshots
<img src="https://user-images.githubusercontent.com/91476854/150305797-de846522-a0ae-495b-baf2-9dbf09d83eaf.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150305642-c41267b1-32fc-447c-a9e7-c38548dc0a2b.jpg" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150305881-349d6cbd-c7ce-436b-917a-c1a068d6d907.jpg" width="180">
----------------------------------------------------------
<img src="https://user-images.githubusercontent.com/91476854/150670437-eab03c72-a70b-4660-88f1-71309ba30c75.gif" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150670462-955cbcf0-64d4-49e2-af92-f908bf529cf6.gif" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150670514-e3078518-7ccb-48e8-9745-84e5de9db66b.gif" width="180"> <img src="https://user-images.githubusercontent.com/91476854/150670524-38c4d8c2-d0e8-4fcf-bb66-543e38479778.gif" width="180">


