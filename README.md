# Database Browser

Spring Boot REST service with Docker integration.

## Build Instructions

### Gradle

`You need to have installed Java 16 JDK!`

For build, you can run gradle in a shell such as the example below.

```shell
./gradle build
```

Output of the build is a self-running JAR application placed in the `/app/build/libs` folder. The Name of this JAR will
be something such as `app.database-browser-0.0.1-SNAPSHOT.jar`.

### Docker

Application is possible to build and run in a Docker container. For this is implemented two phase build process in
Docker.

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

In the project is `docker-compose.yml`. After starting docker compose, the application will be built and then started
with MySQL database. In the file are also set environment variables for connection application to MySQL db. So no more
configuration is needed.

## Usage instructions

Application is Spring Boot Web Application.

### REST Api description

#### Connection URL

Connection endpoints are useful for CRUD operation with `connection` entity. The entity holds connection information to
external database. Application supports two implementation external database -> MySQL and PostgreSQL.

Support of MySQL is better than PostgreSQL.

* GET `/api/v1/connections` -> Get all connections
* POST `/api/v1/connections` -> Save new connection
* GET `/api/v1/connections/{id}` -> Get one connection by ID
* PUT `/api/v1/connections/{id}` -> Save connection to ID
* DELETE `/api/v1/connections/{id}` -> DELETE connection with ID
* PATCH `/api/v1/connections/{id}` -> Save connection with ID

Connection entity structure - in swagger is documented with validation restrictions:
```
{
  databaseName*	  string,
  databaseType*	  string,
  hostname*	   string,
  id	          integer,
  name*	          string,
  password	   string,
  port*	          integer,
  username*	   string,
}
```

### Database Structure Information URL
Endpoints for providing structural information of schema, tables, columns. And also example of table payload.

Endpoint accept variables:
* `connectionId` (int) -> ID for `connection` entity
* `schema` (string) -> Schema name
* `table` (string) -> table name

Endpoint URL options:
* GET `/api/v1/connections/{connectionId}/schema` -> Get list of all schema in db
* GET `/api/v1/connections/{connectionId}/schema/{schema}/table` -> Get list of all tables in schema
* GET `/api/v1/connections/{connectionId}/schema/{schema}/table/{table}/column` -> Get list of all columns in table with information of them
* GET `/api/v1/connections/{connectionId}/schema/{schema}/table/{table}/example` -> Get example of data in table.

All response data are not structured. They may have different shapes for every kind of database.

 ### Database Statistics URL
Endpoints for providing statistic information of tables and columns. 

Endpoint accept variables:
* `connectionId` (int) -> ID for `connection` entity
* `schema` (string) -> Schema name
* `table` (string) -> table name

Endpoint URL options:
* GET `/api/v1/connections/{connectionId}/statistics/schema/{schema}/table/{table}/column` -> Get statistic information for every column in table. (In PG is limited) 
* GET `/api/v1/connections/{connectionId}/statistics/table` -> Get statistic information of table (In PG is limited)

All response data are not structured. They may have different shapes for every kind of database.

### Useful links

* Swagger URL: http://hostname:port/swagger-ui/#
* Prometheus endpoint: http://hostname:port/actuator/prometheus
* Application HealthCheck: http://hostname:port/actuator/health

## Improvements

* MVC Tests are written only for happy path. And only of response code. Will be better to test the Response Payload and
  also failing paths.
* Improving Error Handler for REST, error handler is implemented only for a few exceptions, but systems like this can
  produce many variations of exceptions.
* In GitHub actions should be implemented integration testing with external databases.
* Better Swagger documentation of Exceptions and error codes in REST responses.