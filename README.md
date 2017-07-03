# AQM
Air Traffic Control Queue Management

# Installation
## Requirements
* JDK 1.8 preferably Update 131
* Maven 3.9
* Web Browser supporting Backbone.js JavaScript framework

## Build
Execute the following:

    mvn clean package

which will also run the integration tests.

## Run
Execute the following:

    mvn spring-boot:run

or for faster boot time

    java -jar aqm-service/target/aqm-service-1.0-SNAPSHOT.jar
    
## REST Endpoints
The embedded [Swagger UI](http://localhost:8008/services/api-docs?url=http://localhost:8008/services/swagger.json) (an SPA web applications) can be used to interact with the APIs.

### Notes
* System must be booted (/boot operation) before allowing other API operations.
* The 'bulk' POST operation accepts JSON that is in the format of the example file 'data.json'.
* Swagger UI's interface for enumerations has a bug, wherein it requires values from all enumeration drop lists changed at least once before allowing 
execution a REST call.  This is only required once per SPA (re)load.

# Architecture

This microservice is meant to run as a singleton, since it contains a non-persistent data structure that holds in-memory state.  To properly implement this
prototype for production-grade, the underlying data structure needs to be implemented by a persistent priority queue implementation such as Apache Artemis, RabbitMQ, or Apache Kafka.

