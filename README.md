# Project Alura backend challenge edition N°2
## A program for financial consult that lists expenses and receipts

This is a challenge project from Alura, where we create a program following the model model MVC.

It's a web service app that allows you to save in a database expenses, and receipts, search for them
based in their description, search for an individual one using its ID or get the summary of a month
which gives the total expenses, receipts, balance, expenses by category and the category in question.
For an easy creation of graphs that may provide useful information for the user

## Docker deploy

1. Download the project
2. change environmental variables as deem necessary in applications-prod.properties
3. use the command docker-compose up --build when in the same directory as the root folder in a terminal

## Function

* Manage expenses and receipts in a database using CRUD methods
* Search for summaries of specific months of your choosing
