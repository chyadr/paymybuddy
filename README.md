# Pay-My-Buddy
- A local user to user payment web application, with relational Data Base setup automatically through Liquibase.This app uses Java to run and stores the data in Postgres DB.
# Getting Started
- These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.
## Technologies
- Spring Boot v2.0.4
- Spring Security 5.5.3
- Spring Transactional 5.3.12
- Spring Data 5.3.12
- Hibernate 4.4.32
- Liquibase 4.4.3
- Java 11
- Thymeleaf 2.5.6
- Bootstrap v.4.3.1
- Jquery 3.4.1
- Postgres 14
- Junit 5

 # Installing
-A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Postgres:

https:https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- After downloading Postgres and installing it, you will be asked to configure the password for the default root account. This code uses the default root account to connect and the password can be set as rootroot. If you add another user/credentials make sure to change the same in the code base.

# Running App
- Post installation of Postgres, Java and Maven, you have to :
- Set up Environment variables (database url, user and password) like screen bellow :

![img_1.png](img_1.png)

- Create the Database (Database name should be the same as the name mentioned before)
        
        Create database <your_database_name>;       

- Set up the tables via liquibase. For this, All scripts.sql files under the resources/scripts folder will be run automatically by liquibase.

- Finally, you will be ready to import the code into an IDE of your choice and run the Java Application to launch the application.


# Testing:
- The app has tests covering Controllers and Services.
- To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

        mvn test


# Application Guide

- Run the application from your IDE

- Go to the Browser and type the link <http://localhost:8080>

- Register : You can register to the application
  - Login With your account:  After Registration you can login with your credentials 
  - Login With default Account : UserName : david.dupont@gmail.com / Password : daviddupont

# Documentation






