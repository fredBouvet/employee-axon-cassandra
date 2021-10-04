#Employee 

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)
- [API](#requirements)


## Introduction

[![License](https://img.shields.io/badge/License-MIT%202.0-blue.svg)](https://opensource.org/licenses/MIT)


Hello-world project: How to build a CQRS/ES hexagonal java microservice using Axon and Cassandra


## Features
TODO: Description of features

* Include a list of
* all the many beautiful
* web server features


## Requirements


### Local
* [Java 11 SDK](https://www.oracle.com/java/technologies/downloads/#java11)
* [Maven](https://maven.apache.org/download.cgi)

### Docker
* [Docker](https://www.docker.com/get-docker)

## Quick Start


### Run Axon and Cassandra 

With docker (see the docker-compose.yml in the project)
```bash
$ docker-compose up -d axon cassandra
```
 - Axon use the default port `8024` for http, and `8124` for grpc
 - Cassandra use the default port `9042`


### Run employee in local
```bash
$ mvn install
java -jar ./employee-core/target/employee-core-0.0.1-SNAPSHOT.jar
```

Application will run by default on port `8080`

Configure the port by changing `server.port` in __application.properties__


## Testing
TODO: Additional instructions for testing the application.


## API
TODO: API Reference with examples, or a link to a wiki or other documentation source.

### Additional Links
These additional references should also help you:
####Axon
* [The Axon Framework open-source code repository on GitHub](https://github.com/AxonFramework)
* [The reference guide on how to use Axon](https://docs.axoniq.io/reference-guide/)
* [A full getting started tutorial for Axon in small simple steps (YouTube).](https://www.youtube.com/watch?v=tqn9p8Duy54&list=PL4O1nDpoa5KQkkApGXjKi3rzUW3II5pjm)
* [The reference guide section on how to use Axon Test module](https://docs.axoniq.io/reference-guide/axon-framework/testing)
* [The Axon Reactor extension open-source code repository for GitHub](https://github.com/AxonFramework/extension-reactor)
* [The reference guide section on how to use Axon Reactor extension](https://docs.axoniq.io/reference-guide/extensions/reactor)
 
####Cassandra
* [Apache Cassandra](https://cassandra.apache.org/_/index.html)
* [Introduction to spring data cassandra by baeldung](https://www.baeldung.com/spring-data-cassandra-tutorial)
* [Spring data cassandra documentation](https://spring.io/projects/spring-data-cassandra)

####Reactive api
* [Project reactor](https://projectreactor.io/)
* [Guide to Spring 5 WebFlux by baeldung](https://www.baeldung.com/spring-webflux)
