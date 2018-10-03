# Spring Cloud Example

Эта открытая площадка предназначена для изучения [Spring Cloud](https://cloud.spring.io/) через примеры.

_Читать на других языках: [In English](README.en.md)_

## Вклад

Начните с известных проблем в [баг-трекере](https://github.com/naXa777/spring-cloud-example/issues).

## Описание

### Service Discovery

В Spring Cloud есть несколько способов обнаруживать сервисы:

* Spring Cloud [Consul](https://cloud.spring.io/spring-cloud-consul/)
* Spring Cloud [Zookeeper](https://cloud.spring.io/spring-cloud-zookeeper/)
* Spring Cloud [Netflix](https://cloud.spring.io/spring-cloud-netflix/)

Данный проект уделяет основное внимание последнему из них - Spring Cloud Netflix.

Ради простоты настраивается только один экземпляр сервера Eureka. Поэтому он сконфигурирован таким образом, чтобы не регистрироваться среди пиров (других экземпляров сервера Eureka). Скорей всего, вам потребуется запускать одновременно несколько серверов для обнаружения (Discovery servers) для того, чтобы гарантировать высокую доступность в продакшене.
Для этого измените значения следующих свойств:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true    
