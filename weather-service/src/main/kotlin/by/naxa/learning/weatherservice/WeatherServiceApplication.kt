package by.naxa.learning.weatherservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class WeatherServiceApplication

fun main(args: Array<String>) {
    runApplication<WeatherServiceApplication>(*args)
}
