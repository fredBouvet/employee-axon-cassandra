# Employee 

## Table of Contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [API](#requirements)


## Introduction

[![License](https://img.shields.io/badge/License-MIT%202.0-blue.svg)](https://opensource.org/licenses/MIT)


Hello-world project: How to build a CQRS/ES hexagonal java microservice using Axon and Cassandra


## Requirements


### Local
* [Java 11 SDK](https://www.oracle.com/java/technologies/downloads/#java11)
* [Maven](https://maven.apache.org/download.cgi)

### Docker
* [Docker](https://www.docker.com/get-docker)

## Quick Start

### Set up cassandra

- Cassandra use the default port `9042`

```bash
$ docker-compose up -d cassandra
$ docker exec -ti cassandra cqlsh
```
```sql
create keyspace employee with replication ={'class':'SimpleStrategy', 'replication_factor':1};

use employee;

CREATE TABLE employee_by_id
(
    id        uuid,
    name      text,
    address   text,
    email     text,
    birthDate date,
    PRIMARY KEY (id)
);


CREATE TABLE employee_by_name
(
    name      text,
    id        uuid,
    address   text,
    email     text,
    birthDate date,
    PRIMARY KEY (name, id)
);

CREATE TABLE employee_by_birth_date
(
    birthDate date,
    id        uuid,
    name      text,
    address   text,
    email     text,
    PRIMARY KEY (birthDate, id)
);
```

### Set up Axon
```bash
$ docker-compose up -d axon
```
- Axon use the default port `8024` for http, and `8124` for grpc
- You can see the axon console here: http://localhost:8024/

### Run employee in local
```bash
$ mvn compile
$ mvn exec:java -pl employee-core -Dexec.mainClass="fr.fbouvet.employee.EmployeeApplication"
```

Application will run by default on port `8080`

Configure the port by changing `server.port` in __application.properties__


## API

### Create an employee
```bash
curl --location --request POST 'http://localhost:8080/employees' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"toto",
    "address": "toto address",
    "email":"toto@gmail.com",
    "birthDate":"1980-02-25"
}'
```
Reponse: the unique identifier
```bash
"a271c491-9892-446f-95bb-0be9765d1178"
```

### Change employee name
```bash
curl --location --request PUT 'http://localhost:8080/employees/a271c491-9892-446f-95bb-0be9765d1178/name' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"bob"
}'
```

### Find employee by id
```bash
curl --location --request GET 'http://localhost:8080/employees/a271c491-9892-446f-95bb-0be9765d1178'
```
Response
```json
{
    "id": "a271c491-9892-446f-95bb-0be9765d1178",
    "name": "bob",
    "address": "address de toto",
    "email": "toto@gmail.com",
    "birthDate": "1980-02-25"
}
```

### Find employees by name
```bash
curl --location --request GET 'http://localhost:8080/employees/search/by-name/bob'
```

### Find employees by birthDate
```bash
curl --location --request GET 'http://localhost:8080/employees/search/by-birth-date/1980-02-04'
```

### Additional Links
These additional references should also help you:
#### Axon
* [The Axon Framework open-source code repository on GitHub](https://github.com/AxonFramework)
* [The reference guide on how to use Axon](https://docs.axoniq.io/reference-guide/)
* [A full getting started tutorial for Axon in small simple steps (YouTube).](https://www.youtube.com/watch?v=tqn9p8Duy54&list=PL4O1nDpoa5KQkkApGXjKi3rzUW3II5pjm)
* [The reference guide section on how to use Axon Test module](https://docs.axoniq.io/reference-guide/axon-framework/testing)
* [The Axon Reactor extension open-source code repository for GitHub](https://github.com/AxonFramework/extension-reactor)
* [The reference guide section on how to use Axon Reactor extension](https://docs.axoniq.io/reference-guide/extensions/reactor)
 
#### Cassandra
* [Apache Cassandra](https://cassandra.apache.org/_/index.html)
* [Introduction to spring data cassandra by baeldung](https://www.baeldung.com/spring-data-cassandra-tutorial)
* [Spring data cassandra documentation](https://spring.io/projects/spring-data-cassandra)

#### Reactive api
* [Project reactor](https://projectreactor.io/)
* [Guide to Spring 5 WebFlux by baeldung](https://www.baeldung.com/spring-webflux)
