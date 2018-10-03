package by.naxa.learning.discoveryserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class DiscoveryServerApplication

fun main(args: Array<String>) {
    runApplication<DiscoveryServerApplication>(*args)
}
