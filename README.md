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
MYSQL_PASS=pass MYSQL_USER=user MYSQL_URL=jdbc:mysql//xxx:3306/db java -jar app.database-browser-0.0.1-SNAPSHOT.jar
```

There must be provided environment variables for running application:

* `MYSQL_PASS=pass` -> Password for application database
* `MYSQL_USER=user` -> Username for application database
* `MYSQL_URL=jdbc:mysql//xxx:3306/db` -> JDBC url for application database

The variables are mandatory for application startup.

### Docker

Application build by Docker can run in a container.

```shell
docker run -it -e MYSQL_PASS=pass -e MYSQL_USER=user -e MYSQL_URL=jdbc:mysql//xxx:3306/db lci/database-browser
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

**Connection entity structure** is in the swagger documented with validation restrictions:
```
{
  databaseName*	  string,
  databaseType*	  string,
  hostname*	  string,
  id	          integer,
  name*	          string,
  password        string,
  port*	          integer,
  username*	  string,
}
```

### Database Structure Information URL
Endpoints for providing structural information of the schema, tables, columns. And also an examples of the table payload.

Endpoint variables:
* `connectionId` (int) -> ID for `connection` entity
* `schema` (string) -> Schema name
* `table` (string) -> Table name

Endpoint URL options:
* GET `/api/v1/connections/{connectionId}/schema` -> Gets list of all the schema in db
* GET `/api/v1/connections/{connectionId}/schema/{schema}/table` -> Gets list of all the tables in schema
* GET `/api/v1/connections/{connectionId}/schema/{schema}/table/{table}/column` -> Gets list of all columns in the table with information of them
* GET `/api/v1/connections/{connectionId}/schema/{schema}/table/{table}/example` -> Gets example of the data in table.

All response data are not structured. They may have different shapes for every kind of database.

 ### Database Statistics URL
Endpoints for providing statistical information of the tables and columns. 

Endpoint variables:
* `connectionId` (int) -> ID for `connection` entity
* `schema` (string) -> Schema name
* `table` (string) -> Table name

Endpoint URL options:
* GET `/api/v1/connections/{connectionId}/statistics/schema/{schema}/table/{table}/column` -> Gets statistic information for every column in the table. (In PG is limited) 
* GET `/api/v1/connections/{connectionId}/statistics/table` -> Gets statistic information of table (In PG is limited)

All response data are not structured. They may have different shapes for every kind of database.

#### MySQL Response 

**Table statistics:**
```json
{
    "schema_name": "mysql",
    "table_name": "innodb_table_stats",
    "items": 5,
    "columns": 6
  }
```
**Data types of table statistics:**
```json
{
    "schema_name": "String",
    "table_name": "String",
    "items": "Number - Number of items in the table",
    "columns": "Number - Number of columns in the table"
  }
```

**Column statistics:**
```json
{
    "avg_CLUSTERED_INDEX_SIZE": 1,
    "min_clustered_index_size": 1,
    "max_clustered_index_size": 1,
    "avg_DATABASE_NAME": 0,
    "min_database_name": "mysql",
    "max_database_name": "test",
    "avg_LAST_UPDATE": 20210904190686,
    "min_last_update": "2021-09-04T19:06:52.000+00:00",
    "max_last_update": "2021-09-04T19:07:08.000+00:00",
    "avg_N_ROWS": 2,
    "min_n_rows": 0,
    "max_n_rows": 6,
    "avg_SUM_OF_OTHER_INDEX_SIZES": 0,
    "min_sum_of_other_index_sizes": 0,
    "max_sum_of_other_index_sizes": 0,
    "avg_TABLE_NAME": 0,
    "min_table_name": "DATABASECHANGELOG",
    "max_table_name": "sys_config"
}
```
For every column in table called functions `avg`, `min` and `max`. 

#### PostgreSQL Response

**Table statistics:**
```json
{
    "schema_name": "information_schema",
    "table_name": "_pg_foreign_data_wrappers",
    "items": "0 bytes",
    "columns": 7
}
```
**Data types of table statistics:**
```json
{
    "schema_name": "String",
    "table_name": "String",
    "items": "String - byte size of the table", 
    "columns": "Number - Number of columns in the table"
  }
```

**Column statistics:**
```json
{
    "min_oid": 10117,
    "max_oid": 10839,
    "min_amopfamily": 397,
    "max_amopfamily": 5067,
    "min_amoplefttype": 16,
    "max_amoplefttype": 5069,
    "min_amoprighttype": 16,
    "max_amoprighttype": 5069,
    "min_amopstrategy": 1,
    "max_amopstrategy": 68,
    "min_amoppurpose": "o",
    "max_amoppurpose": "s",
    "min_amopopr": 15,
    "max_amopopr": 5076,
    "min_amopmethod": 403,
    "max_amopmethod": 4000,
    "min_amopsortfamily": 0,
    "max_amopsortfamily": 1970
}
```
For every column in table called functions `avg`, `min` and `max`.

### Testing data
In application are prepared three testing `Connection` entity. This testing connections are there for easy testing of the application.

|ID|Type      |Hostname |Port|Database|User|Password|Description|
|--|----------|---------|----|--------|----|--------|-----------|
| 1|MySQL     |localhost|3306|test    |root|test    |Connection for local testing during development|
| 2|MySQL     |mysql    |3306|test    |root|test    |Connection for Docker MySQL testing|
| 3|PostgreSQL|postgre  |5432|test    |test|test    |Connection for Docker PostgreSQL testing|

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
* Improving support for PostgreSQL.
* Improving statistical endpoints. 
* Creation of response types. Unifying of the responses from any kind of the supported database brands. 
* Solving median in MySQL and avg + median in PostgreSQL.
* Implementation of DGS Netflix GraphQL library.
