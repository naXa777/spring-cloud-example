package by.naxa.learning.weatherapp.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class WeatherService(@Autowired val restTemplate: RestTemplate) {

    @HystrixCommand(fallbackMethod = "unknown")
    fun weather(): String? = restTemplate.getForEntity("http://weather-service/weather", String::class.java).body

    @Suppress("unused")
    fun unknown(): String = "unknown"

}
