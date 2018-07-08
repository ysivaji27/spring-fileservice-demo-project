Project Title
File Service API to upload and download files.

Design Aproach :
---------------
1. Created swagger file using swagger editor (https://editor.swagger.io)
2. Generated Server code.
3. Integrated with existing code base 
4. Enabled Eureka client 
5. Created Eureka server to register the services .
6. Implemented ZUUL client to read the server urls from the eureka server .
6. Written client to upload , download and delete fiels using zuul client.

Getting Started:
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 


Prerequisites:-
 1. jdk1.8
 2. maven
 3. Eclipse 

Project structure:-
 spring-fileservice-demo-project
    1. client
    2. API
    3. Server
    4. Eureka

Steps for Running and verification :-
 1. ./spring-fileservice-demo-project\eureka>mvn spring-boot:run - this will start eureka server registry 
 2. please access this url : http://localhost:8761/ , this will display "Instances currently registered with Eureka"
 3. ./spring-fileservice-demo-project\api>mvn clean install package -U - this will create the jar file 
 4. ./spring-fileservice-demo-project\server>mvn clean install package -U - this will create the jar file 
 5. ./spring-fileservice-demo-project\server\target>java -Dserver.port=8090 -jar fileservice-server-1.0.jar 
 6. ./spring-fileservice-demo-project\server\targetjava -Dserver.port=8091 -jar fileservice-server-1.0.jar
 7. ./spring-fileservice-demo-project\client>mvn spring-boot:run - this will start eureka server registry  
 8. please access this url : http://localhost:8761/ , now you can see two instances for  "FILESERVICE"  with 9080 and 9081 ports and one instance for "ZUUL-SERVER" with 8762

Application Testing:- 
  http://localhost:8762/FileService.html - this will display the UI to upload and download the files.

Running the test cases :- 
 Please import the project in eclipse or any java IDE , please run  TestWebApp.java in fileservice-server project .

API Assumptions:
--------------------
This application running user has permissions to create folders and files .
 

