# Database Browser 

Spring Boot REST service with Docker integration.

## Build Instructions
### Gradle

`You need to have installed Java 16 JDK!`

For build, you can run gradle in a shell such as the example below.

```shell
./gradle build
```

Output of the build is a self-running JAR application placed in the `/app/build/libs` folder.
The Name of this JAR will be something such as `app.database-browser-0.0.1-SNAPSHOT.jar`.

### Docker

Application is possible to build and run in a Docker container. For this is implemented two phase build
process in Docker.

For building just type in a shell
```shell
docker build . -t lci/database-browser
```
or 
```shell
docker-compose build
```

## Run Instructions
### Shell
For application start is possible use Java runtime and in shell just type

```shell
MYSQL_PASS=pass MYSQL_USER=user MYSQL_URL=jdbc:mysql//xxx:3306/db java -jar package-delivery-0.0.1-SNAPSHOT.jar
```

There must be provided environment variables for running application:
* MYSQL_PASS=pass -> Password for application database 
* MYSQL_USER=user -> Username for application database
* MYSQL_URL=jdbc:mysql//xxx:3306/db -> JDBC url for application database

The variables are mandatory for application startup.

### Docker
Application build by Docker can run in a container. 
```shell
docker run -it -e MYSQL_PASS=pass -e MYSQL_USER=user -e MYSQL_URL=jdbc:mysql//xxx:3306/db lci/package
```
or in docker compose.
```shell
docker-compose up
```

In the project is `docker-compose.yml`. After starting docker compose, the application will be built and then started with 
MySQL database. In the file are also set environment variables for connection application to MySQL db. So no more 
configuration is needed.

## Usage instructions

Application is Spring Boot Web Application.

## Improvements
* MVC Tests are written only for happy path. And only of response code. Will be better to test the Response Payload and also failing paths.
* Improving Error Handler for REST, error handler is implemented only for a few exceptions, but systems like this can produce many variations of exceptions.
* 