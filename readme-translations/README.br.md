# Spring Cloud Example

[![Build Status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example)

Esse playground é para aprender [Spring Cloud](https://cloud.spring.io/) e [Kotlin](https://kotlinlang.org/) através de exemplos.

Leia em outros idiomas: 
* [На русском](../README.md) - Russo
* [Bahasa Indonesia](README.id.md) - Indonésio
* [In het Nederlands](README.nl.md) - Holândes
* [In English](README.en.md) - Inglês

## Contribuição

&#x1F49A; Comece [aqui](https://github.com/naXa777/spring-cloud-example/issues).

## Descrição

> [Cloud-native](https://pivotal.io/cloud-native) é uma abordagem para construir e executar aplicativos que exploram as vantagens do modelo de computação em nuvem.

> [Microservices](https://pivotal.io/microservices) é uma abordagem de arquitetura para desenvolver um aplicativo como uma coleção de pequenos serviços; cada serviço implementa recursos de negócios, é executado em seu próprio processo e se comunica por meio de uma API HTTP. Cada microsserviço pode ser implantado, atualizado, dimensionado e reiniciado independentemente de outros serviços no aplicativo, geralmente como parte de um sistema automatizado.

[(c) Pivotal](https://pivotal.io/cloud-native)

Estamos dividindo grandes aplicações em partes menores chamadas "serviços". E cada um desses serviços pode ser implantado e dimensionado por conta própria. _Como um serviço localiza outro?_

### Descoberta de Serviço

|      Módulo      |           URL             |
| :--------------: | :-----------------------: |
| discovery-server | `http://host:8761/eureka` |

Existem diversas maneiras que você pode descobrir serviços no Spring Cloud:

* Spring Cloud [Consul](https://cloud.spring.io/spring-cloud-consul/)
* Spring Cloud [Zookeeper](https://cloud.spring.io/spring-cloud-zookeeper/)
* Spring Cloud [Netflix](https://cloud.spring.io/spring-cloud-netflix/)

Este projeto se concentra especificamente no último, o projeto Spring Cloud Netflix. A gigante norte-americana de serviços de streaming de mídia baseados em nuvem, a Netflix, abriu sua estrutura de ferramentas de microsserviço.

Por uma questão de simplicidade, temos aqui uma única instância do servidor Eureka. Portanto, o servidor Eureka está configurado para não tentar se registrar com seus pares.
Você pode querer ter vários servidores de descoberta para alta disponibilidade em produção. Para isso, altere as seguintes propriedades:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true

#### Alta Disponibilidade

A Eureka está constantemente garantindo que os serviços sejam saudáveis ​​e estejam disponíveis. E também garante que, caso o Discovery Server fique inativo, todos os clientes continuem operando.

Eureka foi construído com o objetivo de entregar alta disponibilidade

 * O registro é distribuído (armazenado em cache localmente em cada cliente).
 * Cliente _podem_ operar sem o Discovery Server (se o servidor estiver indisponível).
 * O Cliente busca os deltas para atualizar o registro.

#### Painel de Controle - Dashboard

O Eureka possui um painel de controle web habilitado por padrão. Ele exibe informação úteis a respeito do servidor. 
 
Em ambiente local, o painel de controle está disponível no caminho http://localhost:8761

### Serviço de Clima

| Módulo          | URL                        |
| :-------------: | :------------------------: |
| weather-service | `http://host:port/weather` |

A anotação `@EnableDiscoveryClient` é usada para transformar o `WeatherServiceApplication` num cliente do Discovery Server e faz com que ele se registre no Discovery Server em sua inicialização. 

### Weather App

| Módulo      | URL                                |
| :---------: | :--------------------------------: |
| weather-app | `http://host:port/current/weather` |

### Serviço de Data e Hora

| Módulo           | URL                         |
| :--------------: | :-------------------------: |
| datetime-service | `http://host:port/datetime` |

A anotação `@EnableDiscoveryClient` é usada para transformar o `DatetimeServiceApplication` num cliente do Discovery Server e faz com que ele se registre no Discovery Server em sua inicialização.

### Datetime App

| Módulo       | URL                                 |
| :----------: | :---------------------------------: |
| datetime-app | `http://host:port/current/datetime` |

### Cliente

| Módulo  | URL                |
| :-----: | :----------------: |
| client  | `http://host:port` |

A anotação `@EnableDiscoveryClient` é usada para transformar o `ClientApplication` num cliente do Discovery Server.

O Cliente não precisa se registrar com Eureka porque ele não quer que ninguém o descubra. Portanto a segiunte propriedade é configurada como _false_

    eureka.client.register-with-eureka=false
    
### Tolerância a Falhas

Em Sistemas Distribuídos existe apenas uma certeza... FALHAS SÃO INEVITÁVEIS.

Um efeito particularmente ruim em falhas de sistemas distribuídos é a [falha em cascata](https://en.wikipedia.org/wiki/Cascading_failure). Traduzido da Wikipedia:

> É um processo em um sistema de partes interconectadas em que a falha de uma ou poucas partes pode desencadear a falha de outras partes e assim por diante.

Como aceitar falhas?

 * Tolerância ao erro
 * Degradação elegante (`Graceful degradation`)
 * Restringir recursos

> [Padrão de Projeto Circuit Breaker](https://en.wikipedia.org/wiki/Circuit_breaker_design_pattern) é um padrão de design usado no desenvolvimento de software moderno. É usado para detectar falhas e encapsula a lógica de evitar que uma falha se repita constantemente.

(From Wikipedia)

#### Hystrix

[Netflix Hystrix](https://github.com/Netflix/Hystrix) é uma biblioteca de latência e **tolerância a falhas** projetada para isolar pontos de acesso a sistemas remotos, serviços e bibliotecas de terceiros, **interromper a falha em cascata** e permitir a resiliência em sistemas distribuídos complexos onde a falha é inevitável.

See [How it works](https://github.com/Netflix/Hystrix/wiki/How-it-Works).

#### Hystrix Dashboard

| Module             | URL                        |
| :----------------: | :------------------------: |
| hystrix-dashboard  | `http://host:port/hystrix` |

Documentation: [Circuit Breaker: Hystrix Dashboard](https://cloud.spring.io/spring-cloud-static/Edgware.SR4/multi/multi__circuit_breaker_hystrix_dashboard.html)

#### Turbine

| Module   | URL                               |
| :------: | :-------------------------------: |
| turbine  | `http://host:3000/turbine.stream` |

O painel Hystrix só pode monitorar um microsserviço de cada vez. Se houver muitos microservices, o painel do Hystrix que aponta para o serviço deve ser alterado toda vez que o micro-serviço for alternado para o monitor. Isso é tedioso.

Turbine (fornecido pelo projeto Spring Cloud Netflix), agrega várias instâncias de streams de métricas do Hystrix, para que o painel possa exibir uma visualização agregada.

Exemplo de configuração:

    turbine.app-config=weather-app,datetime-app
    turbine.cluster-name-expression='default'

## Construindo e Executando localmente

### Preparação

Antes do primeiro build será necessário executar os seguintes passos adicionais: 

 1. Clonar o repositório
 2. Definir variáveis de ambiente
 3. &#x1F4D7; _TODO_

Você tem liberdade para escolher sua ferramenta de build: [Gradle](https://gradle.org/) ou sua IDE favorita.
[IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea/), [STS](https://stackoverflow.com/q/34214685/1429387) / [Eclipse](http://www.vogella.com/tutorials/EclipseGradle/article.html), ou [NetBeans](https://netbeans.org/features/java/build-tools.html) devem lidar com essa tarefa sem problemas.

### Usando Gradle Wrapper

    ./gradlew :discovery-server:bootRun
    ./gradlew :weather-service:bootRun
    ./gradlew :weather-app:bootRun
    ./gradlew :client:bootRun
    ./gradlew :datetime-service:bootRun
    ./gradlew :datetime-app:bootRun
    ./gradlew :turbine:bootRun
    ./gradlew :hystrix-dashboard:bootRun

### Usando IntelliJ IDEA

1. Importe o projeto raiz para o IntelliJ IDEA
2. Sincronize o projeto com o Gradle (a sincronização inicial pode ocorrer automaticamente)
3. Agora você deve ter diversas configurações de execução para cada módulo. Execute-os um por um:
    1. DiscoveryServerApplication
    2. WeatherServiceApplication
    3. WeatherAppApplication
    4. ClientApplication
    5. DatetimeServiceApplication
    6. DatetimeAppApplication
    7. TurbineApplication
    8. HystrixDashboardApplication

Dica: Certifique-se de executá-los em portas diferentes e que essas portas estejam livres, caso contrário você receberá um erro. 

## Implantando para a nuvem

&#x1F4D7; _TODO_

## Integração Contínua (Continuous Integration)

| [Travis CI](https://travis-ci.com/) | [![Build Status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example) |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |

## Licença

Este projeto é licenciado sob os termos da licença [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.en.html).
