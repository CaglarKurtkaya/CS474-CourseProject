# CS 474 Course Project
## Description: object-oriented pure functional design and implementation of a [GraphQL](https://graphql.org) client framework for [Github](https://github.com/) as an I/O monad

### Team Members
#### - Emily Lin 
#### - Sakina Yusuf Master
#### - Aleksandar Knezevic 
#### - Caglar Kurtkaya


## Functionality
In this project, we will be creating a pure functional object-oriented framework for composing and executing external GraphQL commands using Scala.
Users will be using our framework for generating the information of GitHub repositories.
Users can define the languages, how many repositories they want to check, set how many commit comments they want to see and search the repositories based on REPOSITORY or USER.
The result will be saved in the output.txt file in a format that is easy for users to see. 

GitHub object has following methods:

1. def setHttp()(implicit ev: GitHubObj =:= GitHubObj with EmptyObj):(GitHub[GitHubObj with EmptyObj with SetHttp])
1. def setAuthorization(value : String, token : String)(implicit ev: GitHubObj =:= GitHubObj with EmptyObj with SetHttp): (GitHub[GitHubObj with EmptyObj with SetHttp with SetAuthorization])
1. def setHeader(name : String, value : String)(implicit ev: GitHubObj =:= GitHubObj with EmptyObj with SetHttp with SetAuthorization) : (GitHub[GitHubObj with EmptyObj with SetHttp with SetAuthorization with SetHeader])

