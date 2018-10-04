# Spring Cloud Example

[![Build Status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example)

This playground is for learning [Spring Cloud](https://cloud.spring.io/) and [Kotlin](https://kotlinlang.org/) via examples.

_Read this in other languages: [На русском](README.md)_

## Contribution

&#x1F49A; Start [here](https://github.com/naXa777/spring-cloud-example/issues).

## Description

> [Cloud-native](https://pivotal.io/de/cloud-native) is an approach to building and running applications that exploits the advantages of the cloud computing model.

> [Microservices](https://pivotal.io/microservices) is an architectural approach to developing an application as a collection of small services; each service implements business capabilities, runs in its own process and communicates via an HTTP API. Each microservice can be deployed, upgraded, scaled, and restarted independent of other services in the application, typically as part of an automated system.

[(c) Pivotal](https://pivotal.io/de/cloud-native)

We're breaking large applications into smaller pieces called 'services'. And each of these services can be deployed and scaled on their own. _How does one service locate another?_

### Service Discovery

|      Module      |
| :--------------: |
| discovery-server |

There're multiple ways you can discover services in Spring Cloud:

* Spring Cloud [Consul](https://cloud.spring.io/spring-cloud-consul/)
* Spring Cloud [Zookeeper](https://cloud.spring.io/spring-cloud-zookeeper/)
* Spring Cloud [Netflix](https://cloud.spring.io/spring-cloud-netflix/)

This project specifically focuses on the last one, the Spring Cloud Netflix project. The US-based giant of cloud-based streaming media services, namely Netflix, has open-sourced its framework of microservice tools.

For the sake of simplicity, there's a single instance of Eureka server. Therefore Eureka server is configured not to trying register itself with its peers.
You may want to have multiple discovery servers for high availability in production, so change the following properties:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true

#### High Availability

Eureka is constantly ensuring that application services that it's returning or handing back to clients are healthy and available. And it's also ensuring that in case if Discovery Server goes does all client still continue operate.

Eureka was built with high availability in mind:

 * The registry is distributed (cached locally on every client).
 * Clients _can_ operate without discovery server (if this server goes down).
 * Clients fetch deltas to update registry.

#### Dashboard

A web based Eureka dashboard is enabled by default. It shows useful information such as services metadata.
 
In local environment it's available at http://localhost:8761

### Weather Service

| Module          |
| :-------------: |
| weather-service |

The `@EnableDiscoveryClient` annotation is used to turn `WeatherServiceApplication` into a client of the Discovery Server and it causes it to register with the Discovery Server when it starts up.

### Client

| Module  |
| :-----: |
| client  |

The `@EnableDiscoveryClient` annotation is used to turn `ClientApplication` into a client of the Discovery Server.

The Client does not need to register with Eureka because it does not want anybody to discover it so the following property is set to false:

    eureka.client.register-with-eureka=false
    
## Building and Running Locally

### Prepare

Before the first build you need to take additional steps.

 - First, clone the repository.
 - Second, define some environment variables.
   - &#x1F4D7; _TODO_

You have a freedom of choosing build tools for this project: [Gradle](https://gradle.org/) or your favourite IDE.
[IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea/), [STS](https://stackoverflow.com/q/34214685/1429387) / [Eclipse](http://www.vogella.com/tutorials/EclipseGradle/article.html), or [NetBeans](https://netbeans.org/features/java/build-tools.html) should handle this task without any problems. 

### Using Gradle Wrapper

    ./gradlew :discovery-server:bootRun
    ./gradlew :weather-service:bootRun
    ./gradlew :client:bootRun

### Using IntelliJ IDEA

1. Import root project in IntelliJ IDEA
2. Sync project files with Gradle (initial sync may happen automatically)
3. Now you should have multiple run configurations for every module. Run them one-by-one:
    1. DiscoveryServerApplication
    2. WeatherServiceApplication
    3. ClientApplication

Tip: make sure that you run them on different ports and these ports are free, otherwise you'll get an error.

## Deploying to the Cloud

&#x1F4D7; _TODO_

## Continuous Integration

| [Travis CI](https://travis-ci.com/) | [![Build Status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example) |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |

## License

This project is licensed under the terms of the [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
