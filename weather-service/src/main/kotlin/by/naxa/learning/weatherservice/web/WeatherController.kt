package by.naxa.learning.weatherservice.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherController {
    @Value("\${weather-service.instance.name}")
    private lateinit var instance: String

    @GetMapping
    fun message(): String {
        return "Hello from $instance"
    }
}