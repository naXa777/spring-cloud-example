# Spring Cloud Voorbeeld

[![Статус сборки](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example)

Deze speeltuin dient om [Spring Cloud](http://spring-projects.ru/projects/spring-cloud/) и [Kotlin](https://kotlinlang.ru/) te leren via voorbeelden.

Lees dit in andere talen: 
* [На русском](../README.md)
* [In English](readme-translations/README.en.md)

## Bijdrage leveren

&#x1F49A; Start [here](https://github.com/naXa777/spring-cloud-example/issues).

## Beschrijving

> [Cloud-native](https://pivotal.io/de/cloud-native) is een aanpak om applicaties te builden en te runnen die de voordelen van het cloud computing model benut.

> [Microservices](https://pivotal.io/microservices) is een aanpak op basis van architectuur om een applicatie te ontwikkelen als een verzameling van kleine services. Elke service implementeerd business  each service implements business mogelijkheden, loopt in zijn eigen proces en communiceert via een HTTP API. Elke microservice kan worden gedployed, geüpgraded, geschaald en herstart onafhankelijk van andere services in de applicatie. Dit door een geautomatiseerd systeem.

[(c) Pivotal](https://pivotal.io/de/cloud-native)

We breken grote applicaties op in kleine stukjes genaamd 'services'. Elk van deze services kan worden gedeployed en geschaald op zichzelf. _Hoe lokaliseert de ene service de andere?_

### Service Ontdekking

|      Module      |           URL             |
| :--------------: | :-----------------------: |
| discovery-server | `http://host:8761/eureka` |

Er zijn verschillende manieren waarop je services in Spring Cloud kan ontdekken:

* Spring Cloud [Consul](https://cloud.spring.io/spring-cloud-consul/)
* Spring Cloud [Zookeeper](https://cloud.spring.io/spring-cloud-zookeeper/)
* Spring Cloud [Netflix](https://cloud.spring.io/spring-cloud-netflix/)

Dit project spitst zich voornamelijk toe op het laatste, het Spring Cloud Netflix project. Netflix heeft zijn framework van microservices publiek gemaakt.

Om het gemakkelijk te houden is er een enkele instantie van de Eureka server
Daarom is de Eureka server geconfigureerd om zichzelf niet te proberen registreren bij zijn peers.
Je zou best meerdere ondekkingsservers maken voor een hogere beschikbaarheid in productie, verander hiervoor de volgende eigenschappen:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true

