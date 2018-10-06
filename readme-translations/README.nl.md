# Spring Cloud Voorbeeld

[![Build status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example)

Deze speeltuin dient om [Spring Cloud](http://spring-projects.ru/projects/spring-cloud/) и [Kotlin](https://kotlinlang.ru/) te leren via voorbeelden.

Lees dit in andere talen: 
* [На русском](../README.md)
* [In English](README.en.md)

## Bijdrage leveren

&#x1F49A; Start [here](https://github.com/naXa777/spring-cloud-example/issues).

## Beschrijving

> [Cloud-native](https://pivotal.io/de/cloud-native) is een aanpak om applicaties te builden en te runnen die de voordelen van het cloud computing model benut.

> [Microservices](https://pivotal.io/microservices) is een aanpak op basis van architectuur om een applicatie te ontwikkelen als een verzameling van kleine services. Elke service implementeert business mogelijkheden, loopt in zijn eigen proces en communiceert via een HTTP API. Elke microservice kan worden gedployed, geüpgraded, geschaald en herstart onafhankelijk van andere services in de applicatie. Dit door een geautomatiseerd systeem.

[(c) Pivotal](https://pivotal.io/de/cloud-native)

We breken grote applicaties op in kleine stukjes genaamd 'services'. Elk van deze services kan worden gedeployed en geschaald op zichzelf. _Hoe lokaliseert de ene service de andere?_

### Service discovery

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

#### Hoge Beschikbaarheid

.... Bovendien verzekert het dat alle clients kunnen blijven werken wanneer er een Discovery Server uitvalt.

Eureka is constantly ensuring that application services that it's returning or handing back to clients are healthy and available.

Eureka was gebouwd met hoge toegankelijkheid als mindset:

 * Het register is verdeeld (lokaal gecached op elke client).
 * Clients _kunnen_ werken zonder discovery server (als de server uitvalt).
 * Clients fetchen delta's om het register te updaten.

#### Dashboard

Een web-based Eureka dashboard is standaard ingeschakeld. Het laat nuttig informatie zoals metadata zien.

In lokale omgevingen is het beschikbaar op http://localhost:8761

### Weer Service

| Module          | URL                        |
| :-------------: | :------------------------: |
| weather-service | `http://host:port/weather` |

De `@EnableDiscoveryClient` annotatie wordt gebruikt om `WeatherServiceApplication` in een client van de Discovery Server te veranderen en het zorgt ervoor dat het geregistreerd wordt bij de Discovery Server als het opstart.

### Weer App

| Module      | URL                                |
| :---------: | :--------------------------------: |
| weather-app | `http://host:port/current/weather` |

### Datetime Service

| Module           | URL                         |
| :--------------: | :-------------------------: |
| datetime-service | `http://host:port/datetime` |

De `@EnableDiscoveryClient` annotatie wordt gebruikt om  `DatetimeServiceApplication` in een client van de Discovery Server te veranderen en het zorgt ervoor dat het geregistreerd wordt bij de Discovery Server als het opstart.

### Datetime App

| Module       | URL                                 |
| :----------: | :---------------------------------: |
| datetime-app | `http://host:port/current/datetime` |

### Client

| Module  | URL                |
| :-----: | :----------------: |
| client  | `http://host:port` |

De `@EnableDiscoveryClient` annotatie wordt gebruikt om `ClientApplication` in een client van de Discovery Server te veranderen.

De Client moet niet registreren bij Eureka want men wilt niet dat iemand het kan ontdekken. Zodus is de volgende property op false gezet:

    eureka.client.register-with-eureka=false

### Fouttolerantie

In en gedistribueerd systeem is één ding onvermijdelijk... FALEN IS ONVERMIJDELIJK.

Een bijzonder slecht effect van falen in een gedistribueerd systeem is een [cascading failure] (https://en.wikipedia.org/wiki/Cascading_failure). Van Wikipedia:

> Het is een proces in een systeem van onderling verbonden delen waarin het falen van één of enkele onderdelen kan leiden tot het falen van andere onderdelen, enzovoort.

Hoe omarm je falen?

 * Fouttolerantie
 * Sierlijke degradatie
 * Beperk middelen

> [Circuit breaker design pattern](https://en.wikipedia.org/wiki/Circuit_breaker_design_pattern) is een ontwerppatroon dat wordt gebruikt in moderne softwareontwikkeling. Het wordt gebruikt om fouten te detecteren en kapselt de logica in om te voorkomen dat een fout zich voortdurend voordoet.

(Van Wikipedia)

#### Hystrix

[Netflix Hystrix](https://github.com/Netflix/Hystrix) is een latency en ** fouttolerantie ** -bibliotheek die is ontworpen om toegangspunten tot externe systemen, services en bibliotheken van derden te isoleren, ** stop cascading failure ** en maak veerkracht mogelijk in complexe gedistribueerde systemen waar falen onvermijdelijk is.

Zie [Hoe het werkt](https://github.com/Netflix/Hystrix/wiki/How-it-Works).

#### Hystrix Dashboard

| Module             | URL                        |
| :----------------: | :------------------------: |
| hystrix-dashboard  | `http://host:port/hystrix` |

Documentatie: [Circuit Breaker: Hystrix Dashboard](https://cloud.spring.io/spring-cloud-static/Edgware.SR4/multi/multi__circuit_breaker_hystrix_dashboard.html)

#### Turbine

| Module   | URL                               |
| :------: | :-------------------------------: |
| turbine  | `http://host:3000/turbine.stream` |

Het Hystrix-dashboard kan slechts één microservice tegelijkertijd bewaken. Als er veel microservices zijn, moet het Hystrix-dashboard dat verwijst naar de service telkens worden gewijzigd wanneer de microservices naar de monitor worden geschakeld. Het is vervelend.

Turbine (geleverd door het Spring Cloud Netflix-project) verzamelt meerdere instanties Hystrix-statistiekenstromen, zodat het dashboard een totaaloverzicht kan weergeven.

Config voorbeeld:

    turbine.app-config=weather-app,datetime-app
    turbine.cluster-name-expression='default'

## Lokaal Builden en Runnen

### Voorbereiding

Vóór de eerste build moet je extra stappen nemen.

 1. Clone de repository.
 2. Definieer een aantal omgevingsvariabelen.
 3. &#x1F4D7; _TODO_

Je hebt de vrijheid om build-tools voor dit project te kiezen: [Gradle](https://gradle.org/) of je favoriete IDE.
[IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea/), [STS](https://stackoverflow.com/q/34214685/1429387) / [Eclipse](http://www.vogella.com/tutorials/EclipseGradle/article.html) of [NetBeans](https://netbeans.org/features/java/build-tools.html) moeten deze taak soepel afhandelen.

### Gradle Wrapper Gebruiken

    ./gradlew :discovery-server:bootRun
    ./gradlew :weather-service:bootRun
    ./gradlew :weather-app:bootRun
    ./gradlew :client:bootRun
    ./gradlew :datetime-service:bootRun
    ./gradlew :datetime-app:bootRun
    ./gradlew :turbine:bootRun
    ./gradlew :hystrix-dashboard:bootRun

### IntelliJ IDEA Gebruiken

1. Importeer root-project in IntelliJ IDEA
2. Synchroniseer projectbestanden met Gradle (initiële synchronisatie kan automatisch gebeuren)
3. Nu zou u voor elke module meerdere configuraties moeten uitvoeren. Voer ze één voor één uit:
    1. DiscoveryServerApplication
    2. WeatherServiceApplication
    3. WeatherAppApplication
    4. ClientApplication
    5. DatetimeServiceApplication
    6. DatetimeAppApplication
    7. TurbineApplication
    8. HystrixDashboardApplicatie

Tip: zorg ervoor dat u ze op verschillende poorten uitvoert en deze poorten zijn gratis, anders krijgt u een foutmelding.

## Deploying in de Cloud

&#x1F4D7; _TODO_

## Continue Integratie

| [Travis CI](https://travis-ci.com/) | [![Build Status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example) |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |

## Licentie

Dit project is gelicenseerd onder de voorwaarden van de [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.en.html) licentie.
