package by.naxa.learning.weatherservice.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ThreadLocalRandom

@RestController
class WeatherController {
    @Value("\${weather-service.instance.name}")
    private lateinit var instance: String

    private val weather = arrayOf(
            "sunny", "cloudy", "rainy", "windy", "snowy", "stormy", "mostly clear",
            "chilly", "dry", "cold", "warm", "partly sunny", "partly cloudy")

    @GetMapping
    fun message(): String = "Hello from $instance"

    @GetMapping("/weather")
    fun weather(): String {
        val rand = ThreadLocalRandom.current().nextInt(0, weather.size)
        return weather[rand]
    }
}
