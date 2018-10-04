package by.naxa.learning.datetimeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class DatetimeServiceApplication

fun main(args: Array<String>) {
    runApplication<DatetimeServiceApplication>(*args)
}
