# Spring Cloud Example

[![Статус сборки](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example)

Эта открытая площадка предназначена для изучения [Spring Cloud](http://spring-projects.ru/projects/spring-cloud/) и [Kotlin](https://kotlinlang.ru/) на примерах.

_Читать на других языках: [In English](README.en.md)_

## Вклад

&#x1F49A; Начните с известных проблем в [баг-трекере](https://github.com/naXa777/spring-cloud-example/issues).

## Описание

> [Cloud-native](https://pivotal.io/de/cloud-native) - это подход к разработке приложений, который использует преимущества облачных вычислений и виртуализации инфраструктуры.

> Архитектурный стиль [микросервисов](https://pivotal.io/microservices) — это подход, при котором единое приложение строится как набор небольших сервисов, каждый из которых работает в собственном процессе и коммуницирует с остальными используя легковесные механизмы, как правило HTTP. Эти сервисы построены вокруг бизнес-потребностей и развертываются независимо с использованием полностью автоматизированной среды. Существует абсолютный минимум централизованного управления этими сервисами. Сами по себе эти сервисы могут быть написаны на разных языках и использовать разные технологии хранения данных.

[(c) vkhorikov / Хабр](https://habr.com/post/249183/)

Мы разбиваем большие монолитные приложения на кусочки, называемые "сервисами". И каждый из сервисов может разворачиваться и масштабироваться независимо от других. 
_Как сервисы находят друг друга?_

### Service Discovery

|      Модуль      |          URL              |
| :--------------: | :-----------------------: |
| discovery-server | `http://host:8761/eureka` |

В Spring Cloud есть несколько способов обнаружения сервисов:

* Spring Cloud [Consul](https://cloud.spring.io/spring-cloud-consul/)
* Spring Cloud [Zookeeper](https://cloud.spring.io/spring-cloud-zookeeper/)
* Spring Cloud [Netflix](https://cloud.spring.io/spring-cloud-netflix/)

Данный проект уделяет основное внимание последнему из них - Spring Cloud Netflix. Американский гигант потокового телевещания Netflix полностью открыл код своего микросервисного стека.

Ради простоты демонстрации настраивается только один экземпляр сервера Eureka. Поэтому он сконфигурирован таким образом, чтобы не регистрироваться среди пиров (других экземпляров сервера Eureka).
Скорей всего, вам потребуется запускать одновременно несколько серверов для обнаружения (Discovery servers) для того, чтобы гарантировать высокую доступность в продакшене. Для этого измените значения следующих свойств:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true    

#### Высокая доступность

Eureka постоянно проверяет, что сервисы, которые она возвращает клиентам, доступны и оперируют нормально. А также гарантирует, что в случае, если Discovery Server на какое-то время перестанет быть доступным, клиенты продолжат работать без него.

Eureka была разработана с учётом высокой доступности:

 * Реестр сервисов является распределённым (кэшируется на каждом клиенте).
 * Клиенты _могут_ оперировать без Discovery сервера.
 * Клиенты получают дельты изменений, чтобы обновить реестр.

#### Приборная панель

Приборная панель Eureka (aka панель управления, dashboard) включена по умолчанию. Она показывает полезную информацию о зарегистрированных сервисах.

В локальной среде доступна по адресу http://localhost:8761

### Weather Service

| Модуль          | URL                        |
| :-------------: | :------------------------: |
| weather-service | `http://host:port/weather` |

Аннотация `@EnableDiscoveryClient` используется, чтобы превратить приложение `WeatherServiceApplication` в клиента Discovery Server и заставить его зарегистрироваться в Discovery Server во время запуска.

### Weather App

| Модуль      | URL                                |
| :---------: | :--------------------------------: |
| weather-app | `http://host:port/current/weather` |

### Datetime Service

| Модуль           | URL                         |
| :--------------: | :-------------------------: |
| datetime-service | `http://host:port/datetime` |

Аннотация `@EnableDiscoveryClient` используется, чтобы превратить приложение `DatetimeServiceApplication` в клиента Discovery Server и заставить его зарегистрироваться в Discovery Server во время запуска.

### Datetime App

| Модуль       | URL                                 |
| :----------: | :---------------------------------: |
| datetime-app | `http://host:port/current/datetime` |

### Client

| Модуль  | URL                |
| :-----: | :----------------: |
| client  | `http://host:port` |

Аннотация `@EnableDiscoveryClient` используется, чтобы превратить приложение `ClientApplication` в клиента Discovery Server.

Но клиентское приложение не регистрирует себя в Eureka, потому что нам не нужно, чтобы его кто-то обнаруживал. Поэтому свойство ниже установлено в false:

    eureka.client.register-with-eureka=false
    
### Отказоустойчивость

Когда имеешь дело с распределённой системой, можно быть абсолютно уверенным насчёт одного... ОТКАЗ НЕИЗБЕЖЕН.

В частности негативным последствием отказов в распределённой системе является каскадный отказ (cascading failure) - неисправность, вызывающая выход из строя других элементов или систем.

Как правильно обработать отказ?

 * Проектирование под отказ.  
    > Любое обращение к сервису может не сработать из-за его недоступности. Клиент должен реагировать на это настолько терпимо, насколько возможно.
 * Изящная деградация (graceful degradation)
    > Принцип сохранения работоспособности при потере части функциональности.
 * Ограничение ресурсов

Шаблон "Автоматический выключатель" ([Circuit breaker design pattern](https://en.wikipedia.org/wiki/Circuit_breaker_design_pattern)) - шаблон проектирования, применяемый в разработке современного ПО. Используется для обнаружения отказов и инкапсулирует логику предотвращения повторного возникновения отказа.

#### Hystrix

[Netflix Hystrix](https://github.com/Netflix/Hystrix) - ...

См. [Как это работает?](https://github.com/Netflix/Hystrix/wiki/How-it-Works)

#### Приборная панель Hystrix

| Модуль             | URL                        |
| :----------------: | :------------------------: |
| hystrix-dashboard  | `http://host:port/hystrix` |

Документация: [Circuit Breaker: Hystrix Dashboard](https://cloud.spring.io/spring-cloud-static/Edgware.SR4/multi/multi__circuit_breaker_hystrix_dashboard.html)<sup>(на англ.)</sup>

#### Turbine

| Module   | URL                               |
| :------: | :-------------------------------: |
| turbine  | `http://host:3000/turbine.stream` |

Приборная панель Hystrix позволяет отслеживать только один микросервис. Если у вас много микросервисов, приходится переключаться между ними в панели, вручную меняя эндпойнт для сбора метрики. Это довольно утомительное занятие.

Turbine (проект Spring Cloud Netflix) собирает потоки (streams) с метриками из нескольких экземпляров Hystrix и преобразовывает их в единый поток таким образом, чтобы в приборной панели Hystrix был доступен агрегированный вид.

Пример конфигурации:

    turbine.app-config=weather-app,datetime-app
    turbine.cluster-name-expression='default'

## Собираем и запускаем локально

### Подготовка

Перед первой сборкой приложения необходимо предпринять несколько дополнительных шагов.

 - Первое: клонировать репозиторий.
 - Второе: определить несколько переменных окружения.
   - &#x1F4D7; _TODO_

Вы обладаете свободой выбора средства для сборки этого проекта: [Gradle](https://gradle.org/) или любимая среда разработки.
[IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea/), [STS](https://stackoverflow.com/q/34214685/1429387) / [Eclipse](http://www.vogella.com/tutorials/EclipseGradle/article.html), или [NetBeans](https://netbeans.org/features/java/build-tools.html) должны справиться с этой задачей без проблем.

### Используя обёртку Gradle

    ./gradlew :discovery-server:bootRun
    ./gradlew :weather-service:bootRun
    ./gradlew :weather-app:bootRun
    ./gradlew :client:bootRun
    ./gradlew :datetime-service:bootRun
    ./gradlew :datetime-app:bootRun
    ./gradlew :turbine:bootRun
    ./gradlew :hystrix-dashboard:bootRun

### Используя IntelliJ IDEA

1. Импортируйте корневой проект (файл build.gradle) в IntelliJ IDEA.
2. Синхронизируйте настройки проекта с Gradle (первая синхронизация может запуститься автоматически после импорта, в таком случае дополнительных действий не требуется).
3. После импорта и синхронизации у вас должны были появиться несколько конфигураций, по одной для каждого модуля. Запускайте их по порядку:
    1. DiscoveryServerApplication
    2. WeatherServiceApplication
    3. WeatherAppApplication
    4. ClientApplication
    5. DatetimeServiceApplication
    6. DatetimeAppApplication
    7. TurbineApplication
    8. HystrixDashboardApplication
    
Подсказка: убедитесь, что запускаете их на разных портах и что эти порты не заняты другими приложениями, во избежание конфликта.

## Развёртывание в облаке

&#x1F4D7; _TODO_

## Непрерывная интеграция

| [Travis CI](https://travis-ci.com/) | [![Статус сборки](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example) |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Лицензия

Данный проект лицензирован на условиях [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.ru.html).

Неофициальный перевод текста лицензии: [Google Code Archive](https://code.google.com/archive/p/gpl3rus/wikis/LatestRelease.wiki).
