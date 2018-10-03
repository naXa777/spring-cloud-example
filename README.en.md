# Spring Cloud Example

This playground is for learning [Spring Cloud](https://cloud.spring.io/) via examples.

_Read this in other languages: [На русском](README.md)_

## Contribution

Start [here](https://github.com/naXa777/spring-cloud-example/issues).

## Description

### Service Discovery

There're multiple ways you can discover services in Spring Cloud:

* Spring Cloud Consul
* Spring Cloud Zookeeper
* Spring Cloud Netflix

This project specifically focuses on the last one, the Spring Cloud Netflix project.

For the sake of simplicity, there's a single instance of Eureka server. Therefore Eureka server is configured not to trying register itself with its peers. You may want to have multiple discovery servers for high availability in production, so change the following properties:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true    
