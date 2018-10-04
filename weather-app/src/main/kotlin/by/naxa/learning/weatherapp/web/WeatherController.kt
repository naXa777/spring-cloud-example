package by.naxa.learning.weatherapp.web

import by.naxa.learning.weatherapp.service.WeatherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherController(@Autowired val service: WeatherService) {

    @GetMapping("/current/weather")
    fun weather(): String = "the current weather is ${service.weather()}"

}
