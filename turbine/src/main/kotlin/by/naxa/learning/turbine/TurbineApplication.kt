package by.naxa.learning.turbine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.turbine.EnableTurbine

@EnableTurbine
@SpringBootApplication
class TurbineApplication

fun main(args: Array<String>) {
    runApplication<TurbineApplication>(*args)
}
